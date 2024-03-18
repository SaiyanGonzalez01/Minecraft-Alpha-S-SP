package net.minecraft.src;

import net.PeytonPlayz585.opengl.GL11;

public class ColorizerFoliage {
	private static int field_6529_a[] = null;

	public static int func_4146_a(double var0, double var2) {
		if(field_6529_a == null) {
			field_6529_a = GL11.loadPNG(GL11.loadResourceBytes("/misc/foliagecolor.png")).data();
		}
		var2 *= var0;
		int var4 = (int)((1.0D - var0) * 255.0D);
		int var5 = (int)((1.0D - var2) * 255.0D);
		return field_6529_a[var5 << 8 | var4];
	}	
}