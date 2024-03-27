package net.minecraft.src;

final class ChunkCoordinates {
	public final int field_529_a;
	public final int field_528_b;

	public ChunkCoordinates(int var1, int var2) {
		this.field_529_a = var1;
		this.field_528_b = var2;
	}

	public boolean equals(Object var1) {
		if(!(var1 instanceof ChunkCoordinates)) {
			return false;
		} else {
			ChunkCoordinates var2 = (ChunkCoordinates)var1;
			return this.field_529_a == var2.field_529_a && this.field_528_b == var2.field_528_b;
		}
	}

	public int hashCode() {
		return this.field_529_a << 16 ^ this.field_528_b;
	}
}
