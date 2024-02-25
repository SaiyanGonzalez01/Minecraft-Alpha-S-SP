package net.minecraft.src;

import net.PeytonPlayz585.opengl.GL11;
import net.minecraft.client.Minecraft;

public class TexturePackDefault extends TexturePackBase {

	public TexturePackDefault() {
		this.texturePackFileName = "Default";
		this.firstDescriptionLine = "The default look of Minecraft";
	}

	public void func_6484_b(Minecraft var1) {
	}

	public void func_6483_c(Minecraft var1) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, var1.renderEngine.getTexture("pack.png"));
	}
}
