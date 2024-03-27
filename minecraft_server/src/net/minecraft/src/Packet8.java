package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet8 extends Packet {
	public int field_9017_a;

	public Packet8() {
	}

	public Packet8(int var1) {
		this.field_9017_a = var1;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.field_9017_a = var1.readByte();
	}

	public void writePacketData(DataOutputStream var1) throws IOException {
		var1.writeByte(this.field_9017_a);
	}

	public void processPacket(NetHandler var1) {
		var1.func_9003_a(this);
	}

	public int getPacketSize() {
		return 1;
	}
}
