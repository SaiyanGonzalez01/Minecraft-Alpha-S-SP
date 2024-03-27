package net.minecraft.src;

public enum EnumCreatureType {
	monster(IMobs.class, 100),
	creature(EntityAnimals.class, 20);

	public final Class field_4221_c;
	public final int field_4220_d;

	private EnumCreatureType(Class var3, int var4) {
		this.field_4221_c = var3;
		this.field_4220_d = var4;
	}
}
