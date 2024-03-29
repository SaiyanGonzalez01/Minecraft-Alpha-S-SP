package net.PeytonPlayz585;

import java.util.logging.Logger;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;

public class Server {
	
	public static String getName() {
		return "Vanilla Server";
	}
	
	public static String getVersion() {
		return "Alpha v1.2.6";
	}
	
	public static EntityPlayer[] getOnlinePlayers() {
		return (EntityPlayer[])MinecraftServer.configManager.playerEntities.toArray(new EntityPlayerMP[MinecraftServer.configManager.playerEntities.size()]);
	}
	
	public static int getMaxPlayers() {
		return MinecraftServer.configManager.maxPlayers;
	}
	
	public static int getPort() {
		return MinecraftServer.port == 0 ? 25565 : MinecraftServer.port;
	}
	
	public static String getIP() {
		if(MinecraftServer.serverIP == null) {
			return "localhost";
		}
		return MinecraftServer.serverIP;
	}
	
	public static String getLevelName() {
		if(MinecraftServer.levelName == null) {
			return "world";
		}
		return MinecraftServer.levelName;
	}
	
	public static void broadcastMessage(String message) {
		MinecraftServer.configManager.sendChatMessageToAllPlayers("\u00a77(" + message + ")");
	}
	
	public static EntityPlayer getPlayerByName(String name) {
		for(int var1 = 0; var1 < MinecraftServer.configManager.playerEntities.size(); ++var1) {
			EntityPlayerMP player = (EntityPlayerMP)MinecraftServer.configManager.playerEntities.get(var1);
			if(player.username.equalsIgnoreCase(name)) {
				return player;
			}
		}
		return null;
	}
	
	public static void savePlayers() {
		if(MinecraftServer.configManager != null) {
			MinecraftServer.configManager.savePlayerStates();
		}
	}
	
	public static Logger getLogger() {
		return MinecraftServer.logger;
	}

}
