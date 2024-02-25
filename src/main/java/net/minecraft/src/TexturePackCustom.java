package net.minecraft.src;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.PeytonPlayz585.opengl.GL11;
import net.minecraft.client.Minecraft;

public class TexturePackCustom extends TexturePackBase {
	
	public TexturePackCustom(String s) {
		this.texturePackFileName = s;
		
		try {
			deleteExistingTexturePackFiles();
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
				    String name = entry.getName();
				    if(name.startsWith("/")) {
				    	name = name.substring(1, name.length() - 1);
					}
				    GL11.writeFile("texturepackdata/" + name.replace(" ", ""), fileData);
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
			byte[] data = GL11.readFile("texturepackdata/pack.txt");
			InputStream var3 = new ByteArrayInputStream(data);
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
		byte[] data = GL11.readFile("texturepackdata/pack.png");
		if(data != null) {
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
			byte[] b = GL11.readFile("texturepackdata/" + s);
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
		String path = var1;
		if(path.startsWith("/")) {
			path = path.substring(1);
		}
		System.out.println(path);
		byte[] data = GL11.readFile("texturepackdata/" + path.replace(" ", ""));
		
		if(data == null) {
			return GL11.loadResourceBytes(var1);
		}
		
		System.out.println("Texture Found in texture pack: " + path);
		return data;
	}
	
	 private void deleteExistingTexturePackFiles() {
		 String path = "texturepackdata/";
		 Collection<GL11.FileEntry> lst = GL11.listFiles(path, true, true);
		 for(GL11.FileEntry t : lst) {
			 if(!t.isDirectory) {
				 GL11.deleteFile(t.path);
			 }
		 }
		 for(GL11.FileEntry t : lst) {
			 if(t.isDirectory) {
				 GL11.deleteFile(t.path);
			 }
		 }
		 GL11.deleteFile(path);
	 }
}
