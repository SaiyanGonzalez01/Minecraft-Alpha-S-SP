package net.minecraft.src;

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

import net.PeytonPlayz585.network.ByteBufferDirectInputStream;
import net.PeytonPlayz585.network.Socket;

public class NetworkManager {
	private Object sendQueueLock = new Object();
	private Socket field_12258_e;
	private boolean isRunning = true;
	private List readPackets = Collections.synchronizedList(new ArrayList());
	private List dataPackets = Collections.synchronizedList(new ArrayList());
	private List chunkDataPackets = Collections.synchronizedList(new ArrayList());
	private NetHandler netHandler;
	private boolean isServerTerminating = false;
	private boolean isTerminating = false;
	private String terminationReason = "";
	private int timeSinceLastRead = 0;
	private int sendQueueByteLength = 0;
	private int chunkDataSendCounter = 0;

	public NetworkManager(String var4, NetHandler var3) throws IOException {
		
		this.netHandler = var3;
		String uri = null;
		System.out.println(uri);
		if(var4.startsWith("ws://")) {
			uri = var4.substring(5);
		}else if(var4.startsWith("wss://")){
			uri = var4.substring(6);
		}else if(!var4.contains("://")){
			uri = var4;
			var4 = "ws://" + var4;
		}else {
			throw new IOException("Invalid URI Protocol!");
		}
		
		this.field_12258_e = new Socket(var4);
	}

	public void addToSendQueue(Packet var1) {
		if(!this.isServerTerminating) {
			Object var2 = this.sendQueueLock;
			synchronized(var2) {
				this.sendQueueByteLength += var1.getPacketSize() + 1;
				if(var1.isChunkDataPacket) {
					this.chunkDataPackets.add(var1);
				} else {
					this.dataPackets.add(var1);
				}

			}
		}
	}

	
	ByteArrayOutputStream os;
	private void sendPacket() {
		try {
			boolean var1 = true;
			Packet var2;
			Object var3;
			if(!this.dataPackets.isEmpty()) {
				var1 = false;
				var3 = this.sendQueueLock;
				synchronized(var3) {
					var2 = (Packet)this.dataPackets.remove(0);
					this.sendQueueByteLength -= var2.getPacketSize() + 1;
				}

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				DataOutputStream socketOutputStream = new DataOutputStream(baos);
				Packet.writePacket(var2, socketOutputStream);
				baos.flush();
				socketOutputStream.flush();
				this.field_12258_e.write(baos.toByteArray());
				baos.flush();
				socketOutputStream.flush();
			}

			if((var1 || this.chunkDataSendCounter-- <= 0) && !this.chunkDataPackets.isEmpty()) {
				var1 = false;
				var3 = this.sendQueueLock;
				synchronized(var3) {
					var2 = (Packet)this.chunkDataPackets.remove(0);
					this.sendQueueByteLength -= var2.getPacketSize() + 1;
				}

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				DataOutputStream socketOutputStream = new DataOutputStream(baos);
				Packet.writePacket(var2, socketOutputStream);
				baos.flush();
				socketOutputStream.flush();
				this.field_12258_e.write(baos.toByteArray());
				baos.flush();
				socketOutputStream.flush();
				this.chunkDataSendCounter = 50;
			}

			if(var1) {
				Thread.sleep(10L);
			}
		} catch (InterruptedException var8) {
		} catch (Exception var9) {
			if(!this.isTerminating) {
				this.onNetworkError(var9);
			}
		}

	}
	
	private ByteBuffer oldChunkBuffer = null;
	private LinkedList<ByteBuffer> readChunks = new LinkedList();

	public void readPacket() {
		readChunks.clear();
		
		if(oldChunkBuffer != null) {
			readChunks.add(oldChunkBuffer);
		}
		
		if(this.field_12258_e.open()) {
			byte[] packet;
			while((packet = this.field_12258_e.read()) != null) {
				readChunks.add(ByteBuffer.wrap(packet));
			}
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
					Packet var1 = Packet.readPacket(packetStream);
					if(var1 != null) {
						this.readPackets.add(var1);
					} else {
						this.networkShutdown("End of stream");
					}
				} catch(EOFException e) {
					stream.reset();
					break;
				} catch(Exception e) {
					continue;
				} catch(Throwable t) {
					continue;
				}
			}
			
			if(stream.hasRemaining()) {
				oldChunkBuffer = stream.slice();
			}else {
				oldChunkBuffer = null;
			}
		}
	}

	private void onNetworkError(Exception var1) {
		var1.printStackTrace();
		this.networkShutdown("Internal exception: " + var1.toString());
	}

	public void networkShutdown(String var1) {
		if(this.isRunning) {
			this.isTerminating = true;
			this.terminationReason = var1;
			this.isRunning = false;

			try {
				this.field_12258_e.close();
				this.field_12258_e = null;
			} catch (Throwable var3) {
			}

		}
	}

	public void processReadPackets() {
		if(this.sendQueueByteLength > 1048576) {
			this.networkShutdown("Send buffer overflow");
		}

		if(this.readPackets.isEmpty()) {
			if(this.timeSinceLastRead++ == 1200) {
				this.networkShutdown("Timed out");
			}
		} else {
			this.timeSinceLastRead = 0;
		}

		int var1 = 100;

		while(!this.readPackets.isEmpty() && var1-- >= 0) {
			Packet var2 = (Packet)this.readPackets.remove(0);
			var2.processPacket(this.netHandler);
		}

		if(this.isTerminating && this.readPackets.isEmpty()) {
			this.netHandler.handleErrorMessage(this.terminationReason);
		}

	}

	static boolean isRunning(NetworkManager var0) {
		return var0.isRunning;
	}

	static boolean isServerTerminating(NetworkManager var0) {
		return var0.isServerTerminating;
	}

	static void readNetworkPacket(NetworkManager var0) {
		var0.readPacket();
	}

	static void sendNetworkPacket(NetworkManager var0) {
		var0.sendPacket();
	}
}
