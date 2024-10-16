package net.PeytonPlayz585.network;

import net.PeytonPlayz585.opengl.GL11;

import java.io.IOException;
import java.io.OutputStream;

public class Socket {
	
	public Socket(String ip) throws IOException {
		if(!GL11.EaglerAdapterImpl2.startConnection(ip)) {
			throw new IOException("Failed to connect to '" + ip + "'!");
		}
	}
	
	public void close() {
		if(!GL11.EaglerAdapterImpl2.connectionOpen()) {
			return;
		}
		GL11.EaglerAdapterImpl2.endConnection();
	}
	
	public boolean open() {
		return GL11.EaglerAdapterImpl2.connectionOpen();
	}
	
	public void write(byte[] data) {
		if(open()) {
			GL11.EaglerAdapterImpl2.writePacket(data);
		}
	}
	
	public byte[] read() {
		if(open()) {
			return GL11.EaglerAdapterImpl2.readPacket();
		}
		return null;
	}

}
