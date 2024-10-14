package net.minecraft.src;

import net.PeytonPlayz585.awt.image.BufferedImage;
import net.PeytonPlayz585.awt.image.ImageIO;

public class ColorizerFoliage {
	private static final int[] field_6529_a = new int[65536];

	public static int func_4146_a(double var0, double var2) {
		var2 *= var0;
		int var4 = (int)((1.0D - var0) * 255.0D);
		int var5 = (int)((1.0D - var2) * 255.0D);
		return field_6529_a[var5 << 8 | var4];
	}

	static {
		try {
			BufferedImage var0 = ImageIO.read(ImageIO.getResource("/misc/foliagecolor.png"));
			var0.getRGB(0, 0, 256, 256, field_6529_a, 0, 256);
		} catch (Exception var1) {
			var1.printStackTrace();
		}

	}
}