package net.minecraft.src;

public class NibbleArray {
	public final byte[] data;

	public NibbleArray(int var1) {
		this.data = new byte[var1 >> 1];
	}

	public NibbleArray(byte[] var1) {
		this.data = var1;
	}

	public int getNibble(int var1, int var2, int var3) {
		int var4 = var1 << 11 | var3 << 7 | var2;
		int var5 = var4 >> 1;
		int var6 = var4 & 1;
		if (var6 == 0) {
			return data[var5] & 0xf;
		} else {
			return data[var5] >> 4 & 0xf;
		}
	}

	public void setNibble(int var1, int var2, int var3, int var4) {
		int var5 = var1 << 11 | var3 << 7 | var2;
		int var6 = var5 >> 1;
		int var7 = var5 & 1;
		if (var7 == 0) {
			data[var6] = (byte) (data[var6] & 0xf0 | var4 & 0xf);
		} else {
			data[var6] = (byte) (data[var6] & 0xf | (var4 & 0xf) << 4);
		}

	}

	public boolean isValid() {
		return this.data != null;
	}
}
