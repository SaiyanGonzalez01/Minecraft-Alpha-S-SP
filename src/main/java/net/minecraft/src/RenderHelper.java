package net.minecraft.src;

import net.PeytonPlayz585.opengl.GL11;

public class RenderHelper {

	public static void disableStandardItemLighting() {
		GL11.glDisable(2896);
		GL11.glDisable(16384);
	}

	public static void enableStandardItemLighting2() {
		GL11.glEnable(2896);
		GL11.glEnable(16384);
		GL11.glEnable(16385);
		GL11.glEnable(2903);
		GL11.glColorMaterial(1032, 5634);
	    GL11.copyModelToLightMatrix();
	}
	
	public static void enableStandardItemLighting() {
		GL11.glPushMatrix();
		float var0 = 0.4F;
		float var1 = 0.6F;
		float var2 = 0.0F;
		Vec3D var3 = Vec3D.createVector((double)0.2F, 1.0D, (double)-0.7F).normalize();
		GL11.glRotatef((float)var3.xCoord, (float)var3.yCoord, (float)var3.zCoord, 0.0F);
		GL11.glRotatef(var1, var1, var1, 1.0F);
		GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(var2, var2, var2, 1.0F);
		GL11.glRotatef(var2, var0, var1, var2);
		var3 = Vec3D.createVector((double)-0.2F, 1.0D, (double)0.7F).normalize();
		GL11.glRotatef((float)var3.xCoord, (float)var3.yCoord, (float)var3.zCoord, 0.0F);
		GL11.glRotatef(var1, var1, var1, 1.0F);
		GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(var2, var2, var2, 1.0F);
		enableStandardItemLighting2();
		GL11.glPopMatrix();
	}
}