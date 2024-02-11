package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderHelper {

	public static void disableStandardItemLighting() {
		GL11.glDisable(2896);
		GL11.glDisable(16384);
	}

	public static void enableStandardItemLighting() {
		GL11.glEnable(2896);
	    GL11.glEnable(16384);
	    GL11.glEnable(2903);
	    GL11.glColorMaterial(1032, 5634);
	    GL11.copyModelToLightMatrix();
	    GL11.flipLightMatrix();
	}
}