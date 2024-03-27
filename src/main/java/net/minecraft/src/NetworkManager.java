package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.PeytonPlayz585.opengl.GL11;

public class NetworkManager {
	private Object sendQueueLock = new Object();
	private boolean isRunning = true;
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
		if(!GL11.startConnection(var4)) {
			throw new IOException("Websocket to " + uri + " failed!");
		}
	}

	public void addToSendQueue(Packet var1) {
		Thread thread = new Thread(new Runnable () {
			public void run() {
				if(!NetworkManager.this.isServerTerminating) {
					Object var2 = NetworkManager.this.sendQueueLock;
					synchronized(var2) {
						NetworkManager.this.sendQueueByteLength += var1.getPacketSize() + 1;
						if(var1.isChunkDataPacket) {
							NetworkManager.this.chunkDataPackets.add(var1);
						} else {
							NetworkManager.this.dataPackets.add(var1);
						}
						NetworkManager.this.sendPacket();
					}
				}
			}
		});
		thread.start();
	}

	private ByteArrayOutputStream sendBuffer;

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

				sendBuffer = new ByteArrayOutputStream();
				DataOutputStream yee = new DataOutputStream(sendBuffer);
				Packet.writePacket(var2, yee);
				yee.flush();
				GL11.writePacket(sendBuffer.toByteArray());
				sendBuffer.flush();
			}

			if((var1 || this.chunkDataSendCounter-- <= 0) && !this.chunkDataPackets.isEmpty()) {
				var1 = false;
				var3 = this.sendQueueLock;
				synchronized(var3) {
					var2 = (Packet)this.chunkDataPackets.remove(0);
					this.sendQueueByteLength -= var2.getPacketSize() + 1;
				}

				sendBuffer = new ByteArrayOutputStream();
				DataOutputStream yee = new DataOutputStream(sendBuffer);
				Packet.writePacket(var2, yee);
				yee.flush();
				GL11.writePacket(sendBuffer.toByteArray());
				sendBuffer.flush();
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
		if(this.sendQueueByteLength > 1048576) {
			this.networkShutdown("Send buffer overflow");
		}

		readChunks.clear();

		if(oldChunkBuffer != null) {
			readChunks.add(oldChunkBuffer);
		}

		byte[] packet;
		while((packet = GL11.readPacket()) != null) {
			readChunks.add(ByteBuffer.wrap(packet));
		}
		if(!readChunks.isEmpty()) {
			this.timeSinceLastRead = 0;
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
			int var1 = 100;
			while(stream.hasRemaining() && var1-- > 0) {
				stream.mark();
				try {
					Packet pkt = Packet.readPacket(packetStream);
					if(pkt == null) {
						this.networkShutdown("End of Stream");
					}
					pkt.processPacket(this.netHandler);
				} catch (EOFException e) {
					stream.reset();
					break;
				}  catch (IOException e) {
					continue;
				} catch(ArrayIndexOutOfBoundsException e) {
					continue;
				} catch(NullPointerException e) {
					continue;
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

		} else {
			if(this.timeSinceLastRead++ == 1200) {
				this.networkShutdown("Timed out");
			}
		}

		if(this.isTerminating && this.readChunks.isEmpty()) {
			this.netHandler.handleErrorMessage(this.terminationReason);
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
			if(GL11.connectionOpen()) {
				GL11.endConnection();
			}
		}
	}

	static boolean isRunning(NetworkManager var0) {
		return var0.isRunning;
	}

	static boolean isServerTerminating(NetworkManager var0) {
		return var0.isServerTerminating;
	}

//	static void readNetworkPacket(NetworkManager var0) {
//		var0.readPacket();
//	}

	static void sendNetworkPacket(NetworkManager var0) {
		var0.sendPacket();
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