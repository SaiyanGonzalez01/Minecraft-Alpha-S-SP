package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NetworkManager {
	public static final Object threadSyncObject = new Object();
	public static int numReadThreads;
	public static int numWriteThreads;
	private Object sendQueueLock = new Object();
	private Socket networkSocket;
	private final SocketAddress field_12032_f;
	private DataInputStream socketInputStream;
	private DataOutputStream socketOutputStream;
	private boolean isRunning = true;
	private List readPackets = Collections.synchronizedList(new ArrayList());
	private List dataPackets = Collections.synchronizedList(new ArrayList());
	private List chunkDataPackets = Collections.synchronizedList(new ArrayList());
	private NetHandler netHandler;
	private boolean isServerTerminating = false;
	private Thread writeThread;
	private Thread readThread;
	private boolean isTerminating = false;
	private String terminationReason = "";
	private int timeSinceLastRead = 0;
	private int sendQueueByteLength = 0;
	private int chunkDataSendCounter = 0;

	public NetworkManager(Socket var1, String var2, NetHandler var3) throws IOException {
		this.networkSocket = var1;
		this.field_12032_f = var1.getRemoteSocketAddress();
		this.netHandler = var3;
		var1.setTrafficClass(24);
		this.socketInputStream = new DataInputStream(var1.getInputStream());
		this.socketOutputStream = new DataOutputStream(var1.getOutputStream());
		this.readThread = new NetworkReaderThread(this, var2 + " read thread");
		this.writeThread = new NetworkWriterThread(this, var2 + " write thread");
		this.readThread.start();
		this.writeThread.start();
	}

	public void setNetHandler(NetHandler var1) {
		this.netHandler = var1;
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

	private ByteArrayOutputStream sendBuffer;
	
	private void sendPacket() {
		try {
			boolean var1 = true;
			Packet var2;
			Object var3;
			if(!this.dataPackets.isEmpty()) {
				var1 = false;
				var3 = this.sendQueueLock;
				int oldSendQueue = this.sendQueueByteLength;
				synchronized(var3) {
					var2 = (Packet)this.dataPackets.remove(0);
					this.sendQueueByteLength -= var2.getPacketSize() + 1;
				}

				try {
					sendBuffer = new ByteArrayOutputStream();
					DataOutputStream yee = new DataOutputStream(sendBuffer);
					Packet.writePacket(var2, yee);
					yee.flush();
					socketOutputStream.write(sendBuffer.toByteArray());
					sendBuffer.flush();
					socketOutputStream.flush();
				} catch(Exception e) {
					this.sendQueueByteLength = oldSendQueue;
					System.err.println("Error occured while sending data packets! " + e.getStackTrace().toString());
				}
			}

			if((var1 || this.chunkDataSendCounter-- <= 0) && !this.chunkDataPackets.isEmpty()) {
				var1 = false;
				var3 = this.sendQueueLock;
				int oldSendQueue = this.sendQueueByteLength;
				synchronized(var3) {
					var2 = (Packet)this.chunkDataPackets.remove(0);
					this.sendQueueByteLength -= var2.getPacketSize() + 1;
				}

				int oldChunkData = this.chunkDataSendCounter;
				try {
					sendBuffer = new ByteArrayOutputStream();
					DataOutputStream yee = new DataOutputStream(sendBuffer);
					Packet.writePacket(var2, yee);
					yee.flush();
					socketOutputStream.write(sendBuffer.toByteArray());
					sendBuffer.flush();
					socketOutputStream.flush();
					this.chunkDataSendCounter = 50;
				} catch(Exception e) {
					this.sendQueueByteLength = oldSendQueue;
					this.chunkDataSendCounter = oldChunkData;
					System.err.println("Error occured while sending chunk data! " + e.getStackTrace().toString());
				}
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
	
	private LinkedList<ByteBuffer> readChunks = new LinkedList();

	private void readPacket() {
		try {
			byte[] packet;
			ByteArrayInputStream bis = getByteArrayInputStream(socketInputStream);
			if(bis != null) {
				while (bis.available() > 0) {
					packet = new byte[bis.available()];
					try {
						bis.read(packet);
					} catch(IOException e) {
						e.printStackTrace();
					}
					
					int cap = 0;
					ByteBuffer b = ByteBuffer.wrap(packet);
					cap += b.limit();
					ByteBuffer stream = ByteBuffer.allocate(cap);
					
					stream.put(b);
					stream.flip();
					DataInputStream packetStream = new DataInputStream(new ByteBufferDirectInputStream(stream));
					while(stream.hasRemaining()) {
						stream.mark();
						try {
							Packet pkt = Packet.readPacket(packetStream);
							if(pkt == null) {
								this.networkShutdown("End of Stream");
							}
							readPackets.add(pkt);
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
				}
			} else {
				this.networkShutdown("End of stream");
			}
		} catch (Exception var2) {
			if(!this.isTerminating) {
				this.onNetworkError(var2);
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
			(new NetworkMasterThread(this)).start();
			this.isRunning = false;

			try {
				this.socketInputStream.close();
				this.socketInputStream = null;
			} catch (Throwable var5) {
			}

			try {
				this.socketOutputStream.close();
				this.socketOutputStream = null;
			} catch (Throwable var4) {
			}

			try {
				this.networkSocket.close();
				this.networkSocket = null;
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
			try {
				Packet var2 = (Packet)this.readPackets.remove(0);
				var2.processPacket(this.netHandler);
			} catch(Exception e) {
				continue;
			} catch(Throwable t) {
				continue;
			}
		}

		if(this.isTerminating && this.readPackets.isEmpty()) {
			this.netHandler.handleErrorMessage(this.terminationReason);
		}

	}

	public SocketAddress getRemoteAddress() {
		return this.field_12032_f;
	}

	public void serverShutdown() {
		this.isServerTerminating = true;
		this.readThread.interrupt();
		(new ThreadMonitorConnection(this)).start();
	}

	public int getNumChunkDataPackets() {
		return this.chunkDataPackets.size();
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

	static Thread getReadThread(NetworkManager var0) {
		return var0.readThread;
	}

	static Thread getWriteThread(NetworkManager var0) {
		return var0.writeThread;
	}
	
	public static ByteArrayInputStream getByteArrayInputStream(DataInputStream dataInputStream) {
        try {
        	byte[] buffer = new byte[dataInputStream.available()];
            int bytesRead;
            bytesRead = dataInputStream.read(buffer, 0, buffer.length);
            byte[] data = bytesRead == buffer.length ? buffer : new byte[bytesRead];
            System.arraycopy(buffer, 0, data, 0, data.length);
            return new ByteArrayInputStream(data);
        } catch (Exception e) {
            return null;
        }
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
