package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class ColorizerFoliage {
	private static int field_6529_a[] = null;

	public static int func_4146_a(double var0, double var2) {
		if(field_6529_a == null) {
			field_6529_a = GL11.loadPNG(GL11.loadResourceBytes("/misc/foliagecolor.png")).data;
		}
		var2 *= var0;
		int i = (int) ((1.0D - var0) * 255D);
		int j = (int) ((1.0D - var2) * 255D);
		return field_6529_a[j << 8 | i];
	}
}