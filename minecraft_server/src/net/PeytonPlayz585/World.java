package net.PeytonPlayz585;

import net.PeytonPlayzt585.entity.CreatureType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.Entity;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.WorldProvider;

public class World {
	
	public static Block getBlockAt(int var1, int var2, int var3) {
		return Block.blocksList[MinecraftServer.worldMngr.getBlockId(var1, var2, var3)];
	}
	
	public static Chunk getChunkFromBlockCoords(int var1, int var2) {
		return MinecraftServer.worldMngr.getChunkFromBlockCoords(var1, var2);
	}
	
	public static Chunk getChunkFromChunkCoords(int var1, int var2) {
		return MinecraftServer.worldMngr.getChunkFromChunkCoords(var1, var2);
	}
	
	public static boolean isChunkLoaded(int var1, int var2) {
		Chunk chunk = getChunkFromChunkCoords(var1, var2);
		if(chunk == null) {
			chunk = getChunkFromBlockCoords(var1, var2);
			
			if(chunk == null) {
				return false;
			}
		}
		return chunk.func_347_a();
	}
	
	public static boolean loadChunk(int x, int y, boolean var1) {
		Chunk chunk = MinecraftServer.worldMngr.A.loadChunk(x, y);
		if(chunk == null) {
			return false;
		}
		return true;
	}
	
	public static void spawnCreature(CreatureType type, double x, double y, double z) {
		Entity entity = type.nameToEntity(type.name());
		entity.func_107_c(x, y, z, MinecraftServer.worldMngr.rand.nextFloat() * 360.0F, 0.0F);
		MinecraftServer.worldMngr.entityJoinedWorld(entity);
	}
	
	public static void spawnCreature(CreatureType type, float x, float y, float z) {
		Entity entity = type.nameToEntity(type.name());
		entity.func_107_c(x, y, z, MinecraftServer.worldMngr.rand.nextFloat() * 360.0F, 0.0F);
		MinecraftServer.worldMngr.entityJoinedWorld(entity);
	}
	
	public static void spawnCreature(CreatureType type, int x, int y, int z) {
		Entity entity = type.nameToEntity(type.name());
		entity.func_107_c(x, y, z, MinecraftServer.worldMngr.rand.nextFloat() * 360.0F, 0.0F);
		MinecraftServer.worldMngr.entityJoinedWorld(entity);
	}
	
	public static Location getSpawnLocation() {
		int x = MinecraftServer.worldMngr.spawnX;
		int y = MinecraftServer.worldMngr.spawnY;
		int z = MinecraftServer.worldMngr.spawnZ;
		return new Location(x, y, z);
	}
	
	public static void setSpawnLocation(int x, int y, int z) {
		MinecraftServer.worldMngr.spawnX = x;
		MinecraftServer.worldMngr.spawnY = y;
		MinecraftServer.worldMngr.spawnZ = z;
	}
	
	public static long getWorldTime() {
		return MinecraftServer.worldMngr.worldTime;
	}
	
	public static void setWorldTime(long time) {
		MinecraftServer.worldMngr.worldTime = time;
	}
	
	public static void saveWorld() {
		MinecraftServer.logger.info("Saving World!");
		
		if(MinecraftServer.configManager != null) {
			MinecraftServer.configManager.savePlayerStates();
		}
		
		MinecraftServer.worldMngr.func_485_a(true, (IProgressUpdate)null);
	}
	
	public static Environment getEnvironment() {
		if(WorldProvider.dimension == 0) {
			return Environment.NORMAL;
		} else {
			return Environment.NETHER;
		}
	}
	
	public enum Environment {
		NORMAL, NETHER;
	}

}
