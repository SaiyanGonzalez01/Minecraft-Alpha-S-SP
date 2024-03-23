package net.minecraft.src;

import java.io.*;
import java.util.Iterator;

import net.PeytonPlayz585.opengl.GL11;

public class ChunkLoader implements IChunkLoader {
	private String saveDir;
	private boolean createIfNecessary;

	public ChunkLoader(String var1, boolean var2) {
		this.saveDir = var1;
		this.createIfNecessary = var2;
	}
	
	private String chunkFileForXZ_OLD(int var1, int var2) {
		if(saveDir == null) {
			return null;
		}
		String var3 = "c." + Integer.toString(var1, 36) + "." + Integer.toString(var2, 36) + ".dat";
		String var4 = Integer.toString(var1 & 63, 36);
		String var5 = Integer.toString(var2 & 63, 36);
		String var6 = saveDir + "/" + var4 + "/" + var5 + "/" + var3;
		
		if(GL11.readFile(var6) != null) {
			return var6;
		} else {
			return null;
		}
	}
	
	public static final String CHUNK_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public String chunkFileForXZ(int x, int z) {
		if(saveDir == null) {
			return null;
		}
		boolean oldChunk = false;
		String oldChunkPath = chunkFileForXZ_OLD(x, z);
		if(oldChunkPath != null) {
			oldChunk = true;
		}
		int unsignedX = x + 30233088;
		int unsignedZ = z + 30233088;
		int radix = CHUNK_CHARS.length();
		char[] path = new char[10];
		for(int i = 0; i < 5; ++i) {
			path[i * 2] = CHUNK_CHARS.charAt(unsignedX % radix);
			unsignedX /= radix;
			path[i * 2 + 1] = CHUNK_CHARS.charAt(unsignedZ % radix);
			unsignedZ /= radix;
		}
		String s = this.saveDir + "/" + new String(path);
		if(oldChunk) {
			GL11.renameFile(oldChunkPath, s);
		}
		return s;
	}

	public Chunk loadChunk(World var1, int x, int z) throws IOException {
		if(saveDir == null) {
			return null;
		}
		String var4 = this.chunkFileForXZ(x, z);
		byte[] data = GL11.readFile(var4);
		if(data != null) {
			try {
				NBTTagCompound var6;
				if(GL11.isCompressed(data)) {
					var6 = CompressedStreamTools.func_1138_a(new ByteArrayInputStream(data));
				} else {
					var6 = (NBTTagCompound) NBTBase.readTag(new DataInputStream(new ByteArrayInputStream(data)));
				}
				var6 = var6.getCompoundTag("Level");

				int xx = var6.getInteger("xPos");
				int zz = var6.getInteger("zPos");
				if(x != xx || z != zz) {
					System.out.println("Chunk file at " + x + "," + z + " is in the wrong location; relocating. (Expected " + x + ", " + z + ", got " + xx + ", " + zz + ")");
					String name = chunkFileForXZ(xx, zz);
					GL11.renameFile(var4, name);
					return null;
				}

				return loadChunkIntoWorldFromCompound(var1, var6);
			} catch (Exception var8) {
				var8.printStackTrace();
			}
		}

		return null;
	}

	public void saveChunk(World var1, Chunk var2) throws IOException {
		if(saveDir == null) {
			return;
		}
		var1.func_663_l();
		String var3 = this.chunkFileForXZ(var2.xPosition, var2.zPosition);
		if(GL11.readFile(var3) != null) {
			var1.sizeOnDisk -= GL11.getFileSize(var3);
		}

		try {
			String var4 = this.saveDir + "/tmp_chunk.dat";
			ByteArrayOutputStream var5 = new ByteArrayOutputStream();
			NBTTagCompound var6 = new NBTTagCompound();
			NBTTagCompound var7 = new NBTTagCompound();
			var6.setTag("Level", var7);
			this.storeChunkInCompound(var2, var1, var7);
			CompressedStreamTools.writeGzippedCompoundToOutputStream(var6, var5);
			var5.flush();
			GL11.writeFile(var4, var5.toByteArray());
			var5.close();
			
			if(GL11.readFile(var3) != null) {
				GL11.deleteFile(var3);
			}

			GL11.renameFile(var4, var3);
			var1.sizeOnDisk += GL11.getFileSize(var3);
		} catch (Exception var8) {
			var8.printStackTrace();
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

	public void saveExtraChunkData(World var1, Chunk var2) throws IOException {
	}
}