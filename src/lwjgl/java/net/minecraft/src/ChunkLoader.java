package net.minecraft.src;

import java.io.*;
import java.util.*;

public class ChunkLoader implements IChunkLoader {

	public ChunkLoader(String file, boolean flag) {
		if(file != null) {
			File file1 = new File("filesystem");
			saveDir = new File(file1, file);
		} else {
			saveDir = null;
		}
		createIfNecessary = flag;
	}

	private File chunkFileForXZ(int i, int j) {
		if(saveDir == null) {
			return null;
		}
		String s = (new StringBuilder()).append("c.").append(Integer.toString(i, 36)).append(".").append(Integer.toString(j, 36)).append(".dat").toString();
		String s1 = Integer.toString(i & 0x3f, 36);
		String s2 = Integer.toString(j & 0x3f, 36);
		File file = new File(saveDir, s1);
		if (!file.exists()) {
			if (createIfNecessary) {
				file.mkdir();
			} else {
				return null;
			}
		}
		file = new File(file, s2);
		if (!file.exists()) {
			if (createIfNecessary) {
				file.mkdir();
			} else {
				return null;
			}
		}
		file = new File(file, s);
		if (!file.exists() && !createIfNecessary) {
			return null;
		} else {
			return file;
		}
	}

	public Chunk loadChunk(World world, int i, int j) {
		if(saveDir == null) {
			return null;
		}
		File file = chunkFileForXZ(i, j);
		if (file != null && file.exists()) {
			try {
				FileInputStream fileinputstream = new FileInputStream(file);
				NBTTagCompound nbttagcompound = CompressedStreamTools.func_1138_a(fileinputstream);
				if (!nbttagcompound.hasKey("Level")) {
					System.out.println((new StringBuilder()).append("Chunk file at ").append(i).append(",").append(j)
							.append(" is missing level data, skipping").toString());
					return null;
				}
				if (!nbttagcompound.getCompoundTag("Level").hasKey("Blocks")) {
					System.out.println((new StringBuilder()).append("Chunk file at ").append(i).append(",").append(j)
							.append(" is missing block data, skipping").toString());
					return null;
				}
				Chunk chunk = loadChunkIntoWorldFromCompound(world, nbttagcompound.getCompoundTag("Level"));
				if (!chunk.isAtLocation(i, j)) {
					System.out.println((new StringBuilder()).append("Chunk file at ").append(i).append(",").append(j)
							.append(" is in the wrong location; relocating. (Expected ").append(i).append(", ")
							.append(j).append(", got ").append(chunk.xPosition).append(", ").append(chunk.zPosition)
							.append(")").toString());
					nbttagcompound.setInteger("xPos", i);
					nbttagcompound.setInteger("zPos", j);
					chunk = loadChunkIntoWorldFromCompound(world, nbttagcompound.getCompoundTag("Level"));
				}
				return chunk;
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}

	public void saveChunk(World world, Chunk chunk) {
		if(saveDir == null) {
			return;
		}
		world.func_663_l();
		File file = chunkFileForXZ(chunk.xPosition, chunk.zPosition);
		if (file.exists()) {
			world.sizeOnDisk -= file.length();
		}
		try {
			File file1 = new File(saveDir, "tmp_chunk.dat");
			FileOutputStream fileoutputstream = new FileOutputStream(file1);
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound.setTag("Level", nbttagcompound1);
			storeChunkInCompound(chunk, world, nbttagcompound1);
			CompressedStreamTools.writeGzippedCompoundToOutputStream(nbttagcompound, fileoutputstream);
			fileoutputstream.close();
			if (file.exists()) {
				file.delete();
			}
			file1.renameTo(file);
			world.sizeOnDisk += file.length();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void storeChunkInCompound(Chunk var1, World var2, NBTTagCompound var3) {
		var2.func_663_l();
		var3.setInteger("xPos", var1.xPosition);
		var3.setInteger("zPos", var1.zPosition);
		var3.setLong("LastUpdate", var2.worldTime);
		var3.setByteArray("Blocks", var1.blocks);
		var3.setByteArray("Data", var1.data.data);
		var3.setByteArray("SkyLight", var1.skylightMap.data);
		var3.setByteArray("BlockLight", var1.blocklightMap.data);
		var3.setByteArray("HeightMap", var1.heightMap);
		var3.setBoolean("TerrainPopulated", var1.isTerrainPopulated);
		var1.hasEntities = false;
		NBTTagList var4 = new NBTTagList();

		Iterator var6;
		NBTTagCompound var8;
		for(int var5 = 0; var5 < var1.entities.length; ++var5) {
			var6 = var1.entities[var5].iterator();

			while(var6.hasNext()) {
				Entity var7 = (Entity)var6.next();
				if(!(var7 instanceof EntityPlayer) && !(var7 instanceof EntityPlayerSP)) {
					var1.hasEntities = true;
					var8 = new NBTTagCompound();
					if(var7.func_358_c(var8)) {
						var4.setTag(var8);
					}
				}
			}
		}

		var3.setTag("Entities", var4);
		NBTTagList var9 = new NBTTagList();
		var6 = var1.chunkTileEntityMap.values().iterator();

		while(var6.hasNext()) {
			TileEntity var10 = (TileEntity)var6.next();
			var8 = new NBTTagCompound();
			var10.writeToNBT(var8);
			var9.setTag(var8);
		}

		var3.setTag("TileEntities", var9);
	}

	public static Chunk loadChunkIntoWorldFromCompound(World var0, NBTTagCompound var1) {
		int var2 = var1.getInteger("xPos");
		int var3 = var1.getInteger("zPos");
		Chunk var4 = new Chunk(var0, var2, var3);
		var4.blocks = var1.getByteArray("Blocks");
		var4.data = new NibbleArray(var1.getByteArray("Data"));
		var4.skylightMap = new NibbleArray(var1.getByteArray("SkyLight"));
		var4.blocklightMap = new NibbleArray(var1.getByteArray("BlockLight"));
		var4.heightMap = var1.getByteArray("HeightMap");
		var4.isTerrainPopulated = var1.getBoolean("TerrainPopulated");
		if(!var4.data.isValid()) {
			var4.data = new NibbleArray(var4.blocks.length);
		}

		if(var4.heightMap == null || !var4.skylightMap.isValid()) {
			var4.heightMap = new byte[256];
			var4.skylightMap = new NibbleArray(var4.blocks.length);
			var4.func_1024_c();
		}

		if(!var4.blocklightMap.isValid()) {
			var4.blocklightMap = new NibbleArray(var4.blocks.length);
			var4.func_1014_a();
		}

		NBTTagList var5 = var1.getTagList("Entities");
		if(var5 != null) {
			for(int var6 = 0; var6 < var5.tagCount(); ++var6) {
				NBTTagCompound var7 = (NBTTagCompound)var5.tagAt(var6);
				Entity var8 = EntityList.createEntityFromNBT(var7, var0);
				var4.hasEntities = true;
				if(var8 != null) {
					if(!(var8 instanceof EntityPlayer) && !(var8 instanceof EntityPlayerSP)) {
						var4.addEntity(var8);
					}
				}
			}
		}

		NBTTagList var10 = var1.getTagList("TileEntities");
		if(var10 != null) {
			for(int var11 = 0; var11 < var10.tagCount(); ++var11) {
				NBTTagCompound var12 = (NBTTagCompound)var10.tagAt(var11);
				TileEntity var9 = TileEntity.createAndLoadEntity(var12);
				if(var9 != null) {
					var4.func_1001_a(var9);
				}
			}
		}

		return var4;
	}

	public void func_814_a() {
	}

	public void saveExtraData() {
	}

	public void saveExtraChunkData(World world, Chunk chunk) {
	}

	private File saveDir;
	private boolean createIfNecessary;
}