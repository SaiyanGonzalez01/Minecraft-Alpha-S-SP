package net.minecraft.src;

import java.util.List;

import net.PeytonPlayz585.awt.image.BufferedImage;
import net.PeytonPlayz585.opengl.GL11;

public class FontRenderer {

	public FontRenderer(GameSettings gamesettings, String s, RenderEngine renderengine) {
		charWidth = new int[256];
		fontTextureName = 0;
		BufferedImage bufferedimage = GL11.EaglerAdapterImpl2.loadPNG(GL11.EaglerAdapterImpl2.loadResourceBytes(s));
		int i = bufferedimage.getWidth();
		int j = bufferedimage.getHeight();
		int ai[] = bufferedimage.getData();
		for (int k = 0; k < 256; k++) {
			int l = k % 16;
			int k1 = k / 16;
			int j2 = 7;
			do {
				if (j2 < 0) {
					break;
				}
				int i3 = l * 8 + j2;
				boolean flag = true;
				for (int l3 = 0; l3 < 8 && flag; l3++) {
					int i4 = (k1 * 8 + l3) * i;
					int k4 = ai[i3 + i4] & 0xff;
					if (k4 > 0) {
						flag = false;
					}
				}

				if (!flag) {
					break;
				}
				j2--;
			} while (true);
			if (k == 32) {
				j2 = 2;
			}
			charWidth[k] = j2 + 2;
		}

		fontTextureName = renderengine.allocateAndSetupTexture(bufferedimage);
		fontDisplayLists = GLAllocation.generateDisplayLists(288);
		Tessellator tessellator = Tessellator.instance;
		for (int i1 = 0; i1 < 256; i1++) {
			GL11.glNewList(fontDisplayLists + i1, 4864 /* GL_COMPILE */);
			tessellator.startDrawingQuads();
			int l1 = (i1 % 16) * 8;
			int k2 = (i1 / 16) * 8;
			float f = 7.99F;
			float f1 = 0.0F;
			float f2 = 0.0F;
			tessellator.addVertexWithUV(0.0D, 0.0F + f, 0.0D, (float) l1 / 128F + f1, ((float) k2 + f) / 128F + f2);
			tessellator.addVertexWithUV(0.0F + f, 0.0F + f, 0.0D, ((float) l1 + f) / 128F + f1,
					((float) k2 + f) / 128F + f2);
			tessellator.addVertexWithUV(0.0F + f, 0.0D, 0.0D, ((float) l1 + f) / 128F + f1, (float) k2 / 128F + f2);
			tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, (float) l1 / 128F + f1, (float) k2 / 128F + f2);
			tessellator.draw();
			GL11.glEndList();
		}

		for (int j1 = 0; j1 < 32; j1++) {
			int i2 = (j1 >> 3 & 1) * 85;
			int l2 = (j1 >> 2 & 1) * 170 + i2;
			int j3 = (j1 >> 1 & 1) * 170 + i2;
			int k3 = (j1 >> 0 & 1) * 170 + i2;
			if (j1 == 6) {
				l2 += 85;
			}
			boolean flag1 = j1 >= 16;
			if (gamesettings.anaglyph) {
				int j4 = (l2 * 30 + j3 * 59 + k3 * 11) / 100;
				int l4 = (l2 * 30 + j3 * 70) / 100;
				int i5 = (l2 * 30 + k3 * 70) / 100;
				l2 = j4;
				j3 = l4;
				k3 = i5;
			}
			if (flag1) {
				l2 /= 4;
				j3 /= 4;
				k3 /= 4;
			}
			GL11.glNewList(fontDisplayLists + 256 + j1, 4864 /* GL_COMPILE */);
			GL11.glColor3f((float) l2 / 255F, (float) j3 / 255F, (float) k3 / 255F);
			GL11.glEndList();
		}

	}

	public void drawStringWithShadow(String s, int i, int j, int k) {
		renderString(s, i + 1, j + 1, k, true);
		drawString(s, i, j, k);
	}

	public void drawString(String s, int i, int j, int k) {
		renderString(s, i, j, k, false);
	}

	public void renderString(String s, int i, int j, int k, boolean flag) {
		if (s == null) {
			return;
		}
		if (flag) {
			int l = k & 0xff000000;
			k = (k & 0xfcfcfc) >> 2;
			k += l;
		}
		GL11.glBindTexture(3553 /* GL_TEXTURE_2D */, fontTextureName);
		float f = (float) (k >> 16 & 0xff) / 255F;
		float f1 = (float) (k >> 8 & 0xff) / 255F;
		float f2 = (float) (k & 0xff) / 255F;
		float f3 = (float) (k >> 24 & 0xff) / 255F;
		if (f3 == 0.0F) {
			f3 = 1.0F;
		}
		GL11.glColor4f(f, f1, f2, f3);
		GL11.glPushMatrix();
		GL11.glTranslatef(i, j, 0.0F);
		for (int i1 = 0; i1 < s.length(); i1++) {
			for (; s.length() > i1 + 1 && s.charAt(i1) == '\247'; i1 += 2) {
				int j1 = "0123456789abcdef".indexOf(s.toLowerCase().charAt(i1 + 1));
				if (j1 < 0 || j1 > 15) {
					j1 = 15;
				}
				continue;
			}

			if (i1 < s.length()) {
				int k1 = FontAllowedCharacters.isAllowed(s.charAt(i1));
				if (k1 >= 0) {
					GL11.glCallList(fontDisplayLists + k1 + 32);
					GL11.glTranslatef(charWidth[k1 + 32], 0.0F, 0.0F);
				}
			}
		}
		
		GL11.glPopMatrix();
	}

	public int getStringWidth(String s) {
		if (s == null) {
			return 0;
		}
		int i = 0;
		for (int j = 0; j < s.length(); j++) {
			if (s.charAt(j) == '\247') {
				j++;
				continue;
			}
			int k = FontAllowedCharacters.isAllowed(s.charAt(j));
			if (k >= 0) {
				i += charWidth[k + 32];
			}
		}

		return i;
	}

	private int charWidth[];
	public int fontTextureName;
	private int fontDisplayLists;
	
	public static final char formatChar = '\247';

	public List<String> listFormattedStringToWidth(String title2) {
		java.util.ArrayList arraylist = new java.util.ArrayList();
		String[] string = title2.split("\n");
		for (String list : string) {
		    arraylist.add(list);
		}
		return arraylist;
	}
}