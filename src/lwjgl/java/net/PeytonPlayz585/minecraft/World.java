package net.PeytonPlayz585.minecraft;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.PeytonPlayz585.opengl.GL11;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.NBTTagCompound;

public class World {
	public static NBTTagCompound func_629_a(String var1) {
		File file = new File("filesystem");
		File var2 = new File(file, "saves");
		File var3 = new File(var2, var1);
		if(!var3.exists()) {
			return null;
		} else {
			File var4 = new File(var3, "level.dat");
			if(var4.exists()) {
				try {
					NBTTagCompound var5 = CompressedStreamTools.func_1138_a(new FileInputStream(var4));
					NBTTagCompound var6 = var5.getCompoundTag("Data");
					return var6;
				} catch (Exception var7) {
					var7.printStackTrace();
				}
			}

			return null;
		}
	}
	
	public static void deleteWorld(String var1) {
		File file = new File("filesystem");
		File var2 = new File(file, "saves");
		File var3 = new File(var2, var1);
		if(var3.exists()) {
			deleteFiles(var3.listFiles());
			var3.delete();
		}
	}

	private static void deleteFiles(File[] var0) {
		for(int var1 = 0; var1 < var0.length; ++var1) {
			if(var0[var1].isDirectory()) {
				deleteFiles(var0[var1].listFiles());
			}

			var0[var1].delete();
		}

	}

	public static void mkdirs(String s1, String s2) {
		File file = new File("filesystem");
		File var1 = new File(file, s1);
		File var2 = new File(var1, s2);
		var2.mkdirs();
	}

	public static DataOutputStream getSessionLockOutputStream(String s) {
		File file1 = new File("filesystem");
		File file = new File(file1, s);
		File var6 = new File(file, "session.lock");
		DataOutputStream var7;
		try {
			var7 = new DataOutputStream(new FileOutputStream(var6));
			return var7;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean doesLevelExist(String s) {
		File file1 = new File("filesystem", s);
		File file = new File(file1, "level.dat");
		return file.exists();
	}

	public static NBTTagCompound getNBTDataFromLevel(String var18) {
		File file1 = new File("filesystem");
		File file = new File(file1, var18);
		try {
			return CompressedStreamTools.func_1138_a(new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveLevel(net.minecraft.src.World world) {
		File file = new File("filesystem");
		world.func_663_l();
		NBTTagCompound var1 = new NBTTagCompound();
		var1.setLong("RandomSeed", world.randomSeed);
		var1.setInteger("SpawnX", world.spawnX);
		var1.setInteger("SpawnY", world.spawnY);
		var1.setInteger("SpawnZ", world.spawnZ);
		var1.setLong("Time", world.worldTime);
		var1.setLong("SizeOnDisk", world.sizeOnDisk);
		var1.setLong("LastPlayed", System.currentTimeMillis());
		EntityPlayer var2 = null;
		if(world.playerEntities.size() > 0) {
			var2 = (EntityPlayer)world.playerEntities.get(0);
		}
		NBTTagCompound var3;
		if(var2 != null) {
			var3 = new NBTTagCompound();
			var2.writeToNBT(var3);
			var1.setCompoundTag("Player", var3);
		}
		var3 = new NBTTagCompound();
		var3.setTag("Data", var1);

		try {
			File var4_1 = new File(world.field_9432_t, "level.dat_new");
			File var4 = new File(file, var4_1.getPath());
			File var5_1 = new File(world.field_9432_t, "level.dat_old");
			File var5 = new File(file, var5_1.getPath());
			File var6_1 = new File(world.field_9432_t, "level.dat");
			File var6 = new File(file, var6_1.getPath());
			CompressedStreamTools.writeGzippedCompoundToOutputStream(var3, new FileOutputStream(var4));
			if(var5.exists()) {
				var5.delete();
			}
			
			var6.renameTo(var5);
			if(var6.exists()) {
				var6.delete();
			}
			
			var4.renameTo(var6);
			if(var4.exists()) {
				var4.delete();
			}
		} catch (Exception var7) {
			var7.printStackTrace();
		}
	}

	public static void checkSessionLock(net.minecraft.src.World world) {
		try {
			File var1_1 = new File(world.field_9432_t, "session.lock");
			File var1 = new File("filesystem", var1_1.getPath());
			DataInputStream var2 = new DataInputStream(new FileInputStream(var1));
			
			try {
				if(var2.readLong() != world.field_1054_E) {
					throw new MinecraftException("The save is being accessed from another location, aborting");
				}
			} finally {
				var2.close();
			}

		} catch (IOException var7) {
			throw new MinecraftException("Failed to check session lock, aborting");
		}
	}
}
