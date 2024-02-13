package net.minecraft.src;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class TexturePackCustom extends TexturePackBase {
	
	private HashMap<String, byte[]> filePool = new HashMap<String, byte[]>();

	public TexturePackCustom(String s) {
		this.texturePackFileName = s;
		
		try {
			byte[] data = GL11.readFile("texturepacks/" + s);
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ZipInputStream zis = new ZipInputStream(bais);
		
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
					byte[] buffer = new byte[(int)entry.getSize()];
					int len;
					while ((len = zis.read(buffer)) > 0) {
						baos.write(buffer, 0, len);
					}
				
					byte[] fileData = baos.toByteArray();
				    System.out.println(entry.getName());
					filePool.put(entry.getName(), fileData);
				}
			
				zis.closeEntry();
			}
		
			zis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String func_6492_b(String var1) {
		if(var1 != null && var1.length() > 34) {
			var1 = var1.substring(0, 34);
		}

		return var1;
	}

	public void func_6485_a(Minecraft var1) throws IOException {
		try {
			InputStream var3 = new ByteArrayInputStream(filePool.get("pack.txt"));
			BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));
			this.firstDescriptionLine = this.func_6492_b(var4.readLine());
			this.secondDescriptionLine = this.func_6492_b(var4.readLine());
			var4.close();
			var3.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	int packPNG = -1;

	public void func_6483_c(Minecraft var1) {
		if(filePool.containsKey("pack.png")) {
			if(packPNG == -1) {
				packPNG = getTexture("pack.png");
			}
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, packPNG);
		} else {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, var1.renderEngine.getTexture("/gui/unknown_pack.png"));
		}

	}
	
	private int getTexture(String s) {
		try {
			byte[] b = filePool.get(s);
			Minecraft.getMinecraft().renderEngine.singleIntBuffer.clear();
			GLAllocation.generateTextureNames(Minecraft.getMinecraft().renderEngine.singleIntBuffer);
			int i = Minecraft.getMinecraft().renderEngine.singleIntBuffer.get(0);
			Minecraft.getMinecraft().renderEngine.setupTexture(Minecraft.getMinecraft().renderEngine.readTextureImage(b), i);
			return i;
		} catch (IOException e) {
			throw new RuntimeException("!!");
		}
	}

	public byte[] func_6481_a(String var1) {
		if(filePool.containsKey(var1)) {
			return filePool.get(var1);
		}
		
		return GL11.loadResourceBytes(var1);
	}
}
