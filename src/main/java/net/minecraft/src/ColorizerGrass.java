package net.minecraft.src;

import net.PeytonPlayz585.opengl.GL11;

public class ColorizerGrass {
	private static int[] field_6540_a = null;

	public static int func_4147_a(double var0, double var2) {
		if(field_6540_a == null) {
			field_6540_a = GL11.loadPNG(GL11.loadResourceBytes("/misc/grasscolor.png")).data();
		}
		var2 *= var0;
		int var4 = (int)((1.0D - var0) * 255.0D);
		int var5 = (int)((1.0D - var2) * 255.0D);
		return field_6540_a[var5 << 8 | var4];
	}
}
