package net.minecraft.src;

import java.io.IOException;

import net.PeytonPlayz585.opengl.GL11;
import net.minecraft.client.Minecraft;

public abstract class TexturePackBase {
	public String texturePackFileName;
	public String firstDescriptionLine;
	public String secondDescriptionLine;
	public String field_6488_d;

	public void func_6482_a() {
	}

	public void closeTexturePackFile() {
	}

	public void func_6485_a(Minecraft var1) throws IOException {
	}

	public void func_6484_b(Minecraft var1) {
	}

	public void func_6483_c(Minecraft var1) {
	}

	public byte[] func_6481_a(String s) {
		return GL11.loadResourceBytes(s);
	}
}
