package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet39 extends Packet {
	public int field_6044_a;
	public int field_6043_b;

	public Packet39() {
	}

	public Packet39(Entity var1, Entity var2) {
		this.field_6044_a = var1.field_331_c;
		this.field_6043_b = var2 != null ? var2.field_331_c : -1;
	}

	public int getPacketSize() {
		return 8;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.field_6044_a = var1.readInt();
		this.field_6043_b = var1.readInt();
	}

	public void writePacketData(DataOutputStream var1) throws IOException {
		var1.writeInt(this.field_6044_a);
		var1.writeInt(this.field_6043_b);
	}

	public void processPacket(NetHandler var1) {
		var1.func_6003_a(this);
	}
}
