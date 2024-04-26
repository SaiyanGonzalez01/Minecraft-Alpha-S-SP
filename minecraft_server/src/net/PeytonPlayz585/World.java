package net.PeytonPlayz585;

import java.util.ArrayList;
import java.util.List;

import net.PeytonPlayzt585.entity.CreatureType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.WorldProvider;

public class World {
	
	public static Block getBlockAt(Location location) {
		return Block.blocksList[MinecraftServer.worldMngr.getBlockId((int)location.getX(), (int)location.getY(), (int)location.getZ())];
	}
	
	public static Chunk getChunkFromBlockCoords(Location location) {
		return MinecraftServer.worldMngr.getChunkFromBlockCoords((int)location.getX(), (int)location.getZ());
	}
	
	public static Chunk getChunkFromChunkCoords(Location location) {
		return MinecraftServer.worldMngr.getChunkFromChunkCoords((int)location.getX(), (int)location.getZ());
	}
	
	public static boolean isChunkLoaded(Location location) {
		Chunk chunk = getChunkFromChunkCoords(location);
		if(chunk == null) {
			chunk = getChunkFromBlockCoords(location);
			
			if(chunk == null) {
				return false;
			}
		}
		return chunk.func_347_a();
	}
	
	public static boolean loadChunk(Location location, boolean var1) {
		Chunk chunk = MinecraftServer.worldMngr.A.loadChunk((int)location.getX(), (int)location.getZ());
		if(chunk == null) {
			return false;
		}
		return true;
	}
	
	public static void spawnCreature(CreatureType type, Location location) {
		Entity entity = type.nameToEntity(type.name());
		entity.func_107_c(location.getX(), location.getY(), location.getZ(), MinecraftServer.worldMngr.rand.nextFloat() * 360.0F, 0.0F);
		MinecraftServer.worldMngr.entityJoinedWorld(entity);
	}
	
	public static List<Entity> getEntities() {
		List<Entity> list = new ArrayList<Entity>();
		for(int i = 0; i > MinecraftServer.worldMngr.field_815_a.size(); i++) {
			Entity entity = (Entity)MinecraftServer.worldMngr.field_815_a.get(i);
			if(entity != null) {
				list.add(entity);
			}
		}
		return list;
	}
	
	public static List<EntityLiving> getLivingEntities() {
		List<EntityLiving> list = new ArrayList<EntityLiving>();
		for(int i = 0; i > MinecraftServer.worldMngr.field_815_a.size(); i++) {
			Entity entity = (Entity)MinecraftServer.worldMngr.field_815_a.get(i);
			if(entity != null) {
				if(entity instanceof EntityLiving) {
					list.add((EntityLiving)entity);
				}
			}
		}
		return list;
	}
	
	public static Location getSpawnLocation() {
		int x = MinecraftServer.worldMngr.spawnX;
		int y = MinecraftServer.worldMngr.spawnY;
		int z = MinecraftServer.worldMngr.spawnZ;
		return new Location(x, y, z);
	}
	
	public static void setSpawnLocation(Location location) {
		MinecraftServer.worldMngr.spawnX = (int) location.getX();
		MinecraftServer.worldMngr.spawnY = (int) location.getY();
		MinecraftServer.worldMngr.spawnZ = (int) location.getZ();
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
