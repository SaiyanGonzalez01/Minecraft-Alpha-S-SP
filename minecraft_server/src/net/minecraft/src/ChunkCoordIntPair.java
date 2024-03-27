package net.minecraft.src;

public class ChunkCoordIntPair {
	public int field_152_a;
	public int field_151_b;

	public ChunkCoordIntPair(int var1, int var2) {
		this.field_152_a = var1;
		this.field_151_b = var2;
	}

	public int hashCode() {
		return this.field_152_a << 8 | this.field_151_b;
	}

	public boolean equals(Object var1) {
		ChunkCoordIntPair var2 = (ChunkCoordIntPair)var1;
		return var2.field_152_a == this.field_152_a && var2.field_151_b == this.field_151_b;
	}

	public double func_73_a(Entity var1) {
		double var2 = (double)(this.field_152_a * 16 + 8);
		double var4 = (double)(this.field_151_b * 16 + 8);
		double var6 = var2 - var1.posX;
		double var8 = var4 - var1.posZ;
		return var6 * var6 + var8 * var8;
	}
}
