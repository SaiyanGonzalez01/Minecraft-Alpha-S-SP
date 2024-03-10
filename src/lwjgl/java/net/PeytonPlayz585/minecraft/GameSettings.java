package net.PeytonPlayz585.minecraft;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import net.minecraft.client.Minecraft;

public class GameSettings {
	public static void loadOptions() {
		try {
			File file1 = new File("filesystem");
			File file = new File(file1, "options.txt");
			if(!file.exists()) {
				file.createNewFile();
				return;
			}
			
			byte[] fileData = new byte[(int) file.length()];
			try (FileInputStream fis = new FileInputStream(file)) {
		        fis.read(fileData);
		    } catch(IOException e) {
		    	e.printStackTrace();
		    }
			
			if(fileData == null || fileData.length == 0) {
				return;
			}

			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData);
			InputStreamReader inputStreamReader = new InputStreamReader(byteArrayInputStream, "UTF-8");
			BufferedReader var1 = new BufferedReader(inputStreamReader);
			String var2 = "";

			while(true) {
				var2 = var1.readLine();
				if(var2 == null) {
					var1.close();
					break;
				}

				String[] var3 = var2.split(":");
				if(var3[0].equals("music")) {
					Minecraft.getMinecraft().gameSettings.musicVolume = parseFloat(var3[1]);
				}

				if(var3[0].equals("sound")) {
					Minecraft.getMinecraft().gameSettings.soundVolume = parseFloat(var3[1]);
				}

				if(var3[0].equals("mouseSensitivity")) {
					Minecraft.getMinecraft().gameSettings.mouseSensitivity = parseFloat(var3[1]);
				}

				if(var3[0].equals("invertYMouse")) {
					Minecraft.getMinecraft().gameSettings.invertMouse = var3[1].equals("true");
				}

				if(var3[0].equals("viewDistance")) {
					Minecraft.getMinecraft().gameSettings.renderDistance = Integer.parseInt(var3[1]);
				}

				if(var3[0].equals("bobView")) {
					Minecraft.getMinecraft().gameSettings.viewBobbing = var3[1].equals("true");
				}

				if(var3[0].equals("anaglyph3d")) {
					Minecraft.getMinecraft().gameSettings.anaglyph = var3[1].equals("true");
				}

				if(var3[0].equals("limitFramerate")) {
					Minecraft.getMinecraft().gameSettings.limitFramerate = var3[1].equals("true");
				}

				if(var3[0].equals("difficulty")) {
					Minecraft.getMinecraft().gameSettings.difficulty = Integer.parseInt(var3[1]);
				}

				if(var3[0].equals("fancyGraphics")) {
					Minecraft.getMinecraft().gameSettings.fancyGraphics = var3[1].equals("true");
				}

				if(var3[0].equals("skin")) {
					Minecraft.getMinecraft().gameSettings.skin = var3[1];
				}

				if(var3[0].equals("lastServer")) {
					Minecraft.getMinecraft().gameSettings.field_12259_z = var3[1];
				}
				
				if(var3[0].equals("username")) {
					Minecraft.getMinecraft().gameSettings.username = var3[1];
				}

				for(int var4 = 0; var4 < Minecraft.getMinecraft().gameSettings.keyBindings.length; ++var4) {
					if(var3[0].equals("key_" + Minecraft.getMinecraft().gameSettings.keyBindings[var4].keyDescription)) {
						Minecraft.getMinecraft().gameSettings.keyBindings[var4].keyCode = Integer.parseInt(var3[1]);
					}
				}
			}
		} catch (Exception var5) {
			System.out.println("Failed to load options");
			var5.printStackTrace();
		}

	}

	private static float parseFloat(String var1) {
		return var1.equals("true") ? 1.0F : (var1.equals("false") ? 0.0F : Float.parseFloat(var1));
	}

	public static void saveOptions() {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintWriter var1 = new PrintWriter(byteArrayOutputStream);
		var1.println("music:" + Minecraft.getMinecraft().gameSettings.musicVolume);
		var1.println("sound:" + Minecraft.getMinecraft().gameSettings.soundVolume);
		var1.println("invertYMouse:" + Minecraft.getMinecraft().gameSettings.invertMouse);
		var1.println("mouseSensitivity:" + Minecraft.getMinecraft().gameSettings.mouseSensitivity);
		var1.println("viewDistance:" + Minecraft.getMinecraft().gameSettings.renderDistance);
		var1.println("bobView:" + Minecraft.getMinecraft().gameSettings.viewBobbing);
		var1.println("anaglyph3d:" + Minecraft.getMinecraft().gameSettings.anaglyph);
		var1.println("limitFramerate:" + Minecraft.getMinecraft().gameSettings.limitFramerate);
		var1.println("difficulty:" + Minecraft.getMinecraft().gameSettings.difficulty);
		var1.println("fancyGraphics:" + Minecraft.getMinecraft().gameSettings.fancyGraphics);
		var1.println("skin:" + Minecraft.getMinecraft().gameSettings.skin);
		if(Minecraft.getMinecraft().gameSettings.field_12259_z.length() != 0) {
			var1.println("lastServer:" + Minecraft.getMinecraft().gameSettings.field_12259_z);
		}
		if(Minecraft.getMinecraft().gameSettings.username.length() != 0) {
			var1.println("username:" + Minecraft.getMinecraft().gameSettings.username);
		}

		for(int var2 = 0; var2 < Minecraft.getMinecraft().gameSettings.keyBindings.length; ++var2) {
			var1.println("key_" + Minecraft.getMinecraft().gameSettings.keyBindings[var2].keyDescription + ":" + Minecraft.getMinecraft().gameSettings.keyBindings[var2].keyCode);
		}
			
		var1.flush();
		byte[] fileData = byteArrayOutputStream.toByteArray();
		File var2 = new File("filesystem");
		try (FileOutputStream fos = new FileOutputStream(new File(var2, "options.txt"))) {
            fos.write(fileData);
        } catch (IOException e) {
        	e.printStackTrace();
        }

		var1.close();
	}
}
