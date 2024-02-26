package net.minecraft.src;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.PeytonPlayz585.opengl.GL11;

public class NetworkManager {
	
	private NetHandler netHandler;
	private String serverURI;
	
	private List dataPackets = Collections.synchronizedList(new ArrayList());
	private List chunkDataPackets = Collections.synchronizedList(new ArrayList());
	private int timeSinceLastRead = 0;
	private int sendQueueByteLength = 0;
	private int chunkDataSendCounter = 0;
	public static int numWriteThreads;
	private Thread writeThread;
	
	public NetworkManager(String uri, NetHandler netHandler) throws IOException {
		this.serverURI = uri;
		this.netHandler = netHandler;
		if(!GL11.startConnection(uri)) {
			throw new IOException("websocket to "+uri+" failed");
		}
		GL11.setDebugVar("minecraftServer", uri);
		this.writeThread = new NetworkWriterThread(this, uri + " write thread");
		this.writeThread.start();
	}
	
	public void setNetHandler(NetHandler netHandler) {
		this.netHandler = netHandler;
	}
	
	public void addToSendQueue(Packet var1) {
		if(this.isSocketOpen()) {
			this.sendQueueByteLength += var1.getPacketSize() + 1;
			if(var1.isChunkDataPacket) {
				this.chunkDataPackets.add(var1);
			} else {
				this.dataPackets.add(var1);
			}
		}
	}
	
	private ByteArrayOutputStream sendBuffer = new ByteArrayOutputStream();
	
	private void sendPacket() {
		try {
			sendBuffer.reset();
			DataOutputStream yee = new DataOutputStream(sendBuffer);
			boolean var1 = true;
			Packet var2;
			if(!this.dataPackets.isEmpty()) {
				var1 = false;
				var2 = (Packet)this.dataPackets.remove(0);
				this.sendQueueByteLength -= var2.getPacketSize() + 1;
				Packet.writePacket(var2, yee);
				GL11.writePacket(sendBuffer.toByteArray());
			}

			sendBuffer.reset();
			DataOutputStream yee2 = new DataOutputStream(sendBuffer);
			if((var1 || this.chunkDataSendCounter-- <= 0) && !this.chunkDataPackets.isEmpty()) {
				var1 = false;
				var2 = (Packet)this.chunkDataPackets.remove(0);
				this.sendQueueByteLength -= var2.getPacketSize() + 1;
				Packet.writePacket(var2, yee2);
				GL11.writePacket(sendBuffer.toByteArray());
				this.chunkDataSendCounter = 50;
			}

			if(var1) {
				Thread.sleep(10L);
			}
		} catch (InterruptedException var8) {
		} catch (Exception var9) {
			if(this.isSocketOpen()) {
				GL11.endConnection();
				var9.printStackTrace();
			}
		}

	}
	
//	public void processReadPackets() {
//		if(this.sendQueueByteLength > 1048576) {
//			this.networkShutdown("Send buffer overflow");
//		}
//
//		if(this.readPackets.isEmpty()) {
//			if(this.timeSinceLastRead++ == 1200) {
//				this.networkShutdown("Timed out");
//			}
//		} else {
//			this.timeSinceLastRead = 0;
//		}
//
//		int var1 = 100;
//
//		while(!this.readPackets.isEmpty() && var1-- >= 0) {
//			Packet var2 = (Packet)this.readPackets.remove(0);
//			var2.processPacket(this.netHandler);
//		}
//	}
	
	private ByteBuffer oldChunkBuffer = null;
	private LinkedList<ByteBuffer> readChunks = new LinkedList();
	
	public void processReadPackets() {
		readChunks.clear();
		
		if(oldChunkBuffer != null) {
			readChunks.add(oldChunkBuffer);
		}
		
		byte[] packet;
		while((packet = GL11.readPacket()) != null) {
			readChunks.add(ByteBuffer.wrap(packet));
		}
		if(!readChunks.isEmpty()) {
			int cap = 0;
			for(ByteBuffer b : readChunks) {
				cap += b.limit();
			}
			
			ByteBuffer stream = ByteBuffer.allocate(cap);
			for(ByteBuffer b : readChunks) {
				stream.put(b);
			}
			stream.flip();
			
			DataInputStream packetStream = new DataInputStream(new ByteBufferDirectInputStream(stream));
			while(stream.hasRemaining()) {
				stream.mark();
				try {
					Packet pkt = Packet.readPacket(packetStream);
					pkt.processPacket(this.netHandler);
				} catch (EOFException e) {
					stream.reset();
					break;
				}  catch (IOException e) {
					continue;
				} catch (Throwable e2) {
					e2.printStackTrace();
				}
			}
			
			if(stream.hasRemaining()) {
				oldChunkBuffer = stream.slice();
			}else {
				oldChunkBuffer = null;
			}
			
		}
	}
	
	public void serverShutdown() {
		if(GL11.connectionOpen()) {
			GL11.endConnection();
			GL11.setDebugVar("minecraftServer", "null");
		}
	}
	
	public int packetSize() {
		return 0;
	}
	
	public void networkShutdown(String var1, Object... var2) {
		serverShutdown();
	}
	
	public void closeConnections() {
		if(GL11.connectionOpen()) {
			GL11.endConnection();
			GL11.setDebugVar("minecraftServer", "null");
		}
	}
	
	public String getServerURI() {
		return this.serverURI;
	}

	public boolean isSocketOpen() {
		return GL11.connectionOpen();
	}
	
	static void sendNetworkPacket(NetworkManager var0) {
		var0.sendPacket();
	}

	public static boolean isRunning(NetworkManager netManager) {
		return netManager.isSocketOpen();
	}
	
	private static class ByteBufferDirectInputStream extends InputStream {
		private ByteBuffer buf;
		private ByteBufferDirectInputStream(ByteBuffer b) {
			this.buf = b;
		}
		
		@Override
		public int read() throws IOException {
			return buf.remaining() > 0 ? ((int)buf.get() & 0xFF) : -1;
		}
		
		@Override
		public int available() {
			return buf.remaining();
		}
	}
}