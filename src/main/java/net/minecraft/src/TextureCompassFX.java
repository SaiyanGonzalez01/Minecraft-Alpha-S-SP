//package net.minecraft.src;
//
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import javax.imageio.ImageIO;
//import net.minecraft.client.Minecraft;
//
//public class TextureCompassFX extends TextureFX {
//	private Minecraft mc;
//	private int[] field_4230_h = new int[256];
//	private double field_4229_i;
//	private double field_4228_j;
//
//	public TextureCompassFX(Minecraft var1) {
//		super(Item.compass.getIconIndex((ItemStack)null));
//		this.mc = var1;
//		this.field_1128_f = 1;
//
//		try {
//			BufferedImage var2 = ImageIO.read(Minecraft.class.getResource("/gui/items.png"));
//			int var3 = this.field_1126_b % 16 * 16;
//			int var4 = this.field_1126_b / 16 * 16;
//			var2.getRGB(var3, var4, 16, 16, this.field_4230_h, 0, 16);
//		} catch (IOException var5) {
//			var5.printStackTrace();
//		}
//
//	}
//
//	public void func_783_a() {
//		for(int var1 = 0; var1 < 256; ++var1) {
//			int var2 = this.field_4230_h[var1] >> 24 & 255;
//			int var3 = this.field_4230_h[var1] >> 16 & 255;
//			int var4 = this.field_4230_h[var1] >> 8 & 255;
//			int var5 = this.field_4230_h[var1] >> 0 & 255;
//			if(this.field_1131_c) {
//				int var6 = (var3 * 30 + var4 * 59 + var5 * 11) / 100;
//				int var7 = (var3 * 30 + var4 * 70) / 100;
//				int var8 = (var3 * 30 + var5 * 70) / 100;
//				var3 = var6;
//				var4 = var7;
//				var5 = var8;
//			}
//
//			this.field_1127_a[var1 * 4 + 0] = (byte)var3;
//			this.field_1127_a[var1 * 4 + 1] = (byte)var4;
//			this.field_1127_a[var1 * 4 + 2] = (byte)var5;
//			this.field_1127_a[var1 * 4 + 3] = (byte)var2;
//		}
//
//		double var20 = 0.0D;
//		double var21;
//		double var22;
//		if(this.mc.theWorld != null && this.mc.thePlayer != null) {
//			var21 = (double)this.mc.theWorld.spawnX - this.mc.thePlayer.posX;
//			var22 = (double)this.mc.theWorld.spawnZ - this.mc.thePlayer.posZ;
//			var20 = (double)(this.mc.thePlayer.rotationYaw - 90.0F) * Math.PI / 180.0D - Math.atan2(var22, var21);
//			if(this.mc.theWorld.worldProvider.field_4220_c) {
//				var20 = Math.random() * (double)((float)Math.PI) * 2.0D;
//			}
//		}
//
//		for(var21 = var20 - this.field_4229_i; var21 < -Math.PI; var21 += Math.PI * 2.0D) {
//		}
//
//		while(var21 >= Math.PI) {
//			var21 -= Math.PI * 2.0D;
//		}
//
//		if(var21 < -1.0D) {
//			var21 = -1.0D;
//		}
//
//		if(var21 > 1.0D) {
//			var21 = 1.0D;
//		}
//
//		this.field_4228_j += var21 * 0.1D;
//		this.field_4228_j *= 0.8D;
//		this.field_4229_i += this.field_4228_j;
//		var22 = Math.sin(this.field_4229_i);
//		double var23 = Math.cos(this.field_4229_i);
//
//		int var9;
//		int var10;
//		int var11;
//		int var12;
//		int var13;
//		int var14;
//		int var15;
//		short var16;
//		int var17;
//		int var18;
//		int var19;
//		for(var9 = -4; var9 <= 4; ++var9) {
//			var10 = (int)(8.5D + var23 * (double)var9 * 0.3D);
//			var11 = (int)(7.5D - var22 * (double)var9 * 0.3D * 0.5D);
//			var12 = var11 * 16 + var10;
//			var13 = 100;
//			var14 = 100;
//			var15 = 100;
//			var16 = 255;
//			if(this.field_1131_c) {
//				var17 = (var13 * 30 + var14 * 59 + var15 * 11) / 100;
//				var18 = (var13 * 30 + var14 * 70) / 100;
//				var19 = (var13 * 30 + var15 * 70) / 100;
//				var13 = var17;
//				var14 = var18;
//				var15 = var19;
//			}
//
//			this.field_1127_a[var12 * 4 + 0] = (byte)var13;
//			this.field_1127_a[var12 * 4 + 1] = (byte)var14;
//			this.field_1127_a[var12 * 4 + 2] = (byte)var15;
//			this.field_1127_a[var12 * 4 + 3] = (byte)var16;
//		}
//
//		for(var9 = -8; var9 <= 16; ++var9) {
//			var10 = (int)(8.5D + var22 * (double)var9 * 0.3D);
//			var11 = (int)(7.5D + var23 * (double)var9 * 0.3D * 0.5D);
//			var12 = var11 * 16 + var10;
//			var13 = var9 >= 0 ? 255 : 100;
//			var14 = var9 >= 0 ? 20 : 100;
//			var15 = var9 >= 0 ? 20 : 100;
//			var16 = 255;
//			if(this.field_1131_c) {
//				var17 = (var13 * 30 + var14 * 59 + var15 * 11) / 100;
//				var18 = (var13 * 30 + var14 * 70) / 100;
//				var19 = (var13 * 30 + var15 * 70) / 100;
//				var13 = var17;
//				var14 = var18;
//				var15 = var19;
//			}
//
//			this.field_1127_a[var12 * 4 + 0] = (byte)var13;
//			this.field_1127_a[var12 * 4 + 1] = (byte)var14;
//			this.field_1127_a[var12 * 4 + 2] = (byte)var15;
//			this.field_1127_a[var12 * 4 + 3] = (byte)var16;
//		}
//
//	}
//}

package net.minecraft.src;

import java.io.IOException;

import net.PeytonPlayz585.opengl.GL11;
import net.minecraft.client.Minecraft;

public class TextureCompassFX extends TextureFX {
	private final int[] compassSpriteSheet;
	private final int compassSpriteSheetLength;
	private float angleDelta = 0.0f;
	private float currentAngle = 0.0f;

	public TextureCompassFX(Minecraft var1) {
		super(Item.compass.getIconIndex(null));
		field_1128_f = 1;
		this.compassSpriteSheet = GL11.loadPNG(GL11.loadResourceBytes("/gui/items.png")).data();
		this.compassSpriteSheetLength = compassSpriteSheet.length / 256;
	}
	
	public void func_783_a() {
		Minecraft var1 = Minecraft.getMinecraft();
		if (var1.theWorld != null && var1.thePlayer != null) {
			this.updateCompass(var1.theWorld, var1.thePlayer.posX, var1.thePlayer.posZ, (double) var1.thePlayer.rotationYaw, false, false);
		} else {
			this.updateCompass((World) null, 0.0D, 0.0D, 0.0D, true, false);
		}
	}
	
	public void updateCompass(World par1World, double par2, double par4, double par6, boolean par8, boolean par9) {
		double var10 = 0.0D;

		if (par1World != null && !par8) {
		    double var13 = (double)par1World.spawnX - par2;
			double var15 = (double)par1World.spawnZ - par4;
			par6 %= 360.0D;
			var10 = -((par6 - 90.0D) * Math.PI / 180.0D - Math.atan2(var15, var13));

			if (!par1World.worldProvider.field_4220_c) {
				var10 = Math.random() * Math.PI * 2.0D;
			}
		}

		if (par9) {
			this.currentAngle = (float) var10;
		} else {
			double var17;

			for (var17 = var10 - this.currentAngle; var17 < -Math.PI; var17 += (Math.PI * 2D)) {
				;
			}

			while (var17 >= Math.PI) {
				var17 -= (Math.PI * 2D);
			}

			if (var17 < -1.0D) {
				var17 = -1.0D;
			}

			if (var17 > 1.0D) {
				var17 = 1.0D;
			}

			this.angleDelta += var17 * 0.1D;
			this.angleDelta *= 0.8D;
			this.currentAngle += this.angleDelta;
		}

		int var18;

		for (var18 = (int) ((this.currentAngle / (Math.PI * 2D) + 1.0D) * (double) compassSpriteSheetLength) % compassSpriteSheetLength; var18 < 0; var18 = (var18 + compassSpriteSheetLength) % compassSpriteSheetLength) {
			;
		}
		
		int offset = var18 * 256;
		for(int i = 0; i < 256; ++i) {
			this.field_1127_a[i * 4] = (byte)((compassSpriteSheet[offset + i] >> 16) & 0xFF);
			this.field_1127_a[i * 4 + 1] = (byte)((compassSpriteSheet[offset + i] >> 8) & 0xFF);
			this.field_1127_a[i * 4 + 2] = (byte)((compassSpriteSheet[offset + i]) & 0xFF);
			this.field_1127_a[i * 4 + 3] = (byte)((compassSpriteSheet[offset + i] >> 24) & 0xFF);
		}
	}
}

