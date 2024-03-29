package net.minecraft.server;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.ConsoleLogManager;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EntityTracker;
import net.minecraft.src.ICommandListener;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.IUpdatePlayerListBox;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NetworkListenThread;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet4UpdateTime;
import net.minecraft.src.PropertyManager;
import net.minecraft.src.ServerCommand;
import net.minecraft.src.ServerConfigurationManager;
import net.minecraft.src.ServerGUI;
import net.minecraft.src.ThreadCommandReader;
import net.minecraft.src.ThreadServerApplication;
import net.minecraft.src.ThreadSleepForever;
import net.minecraft.src.Vec3D;
import net.minecraft.src.WorldManager;
import net.minecraft.src.WorldServer;

public class MinecraftServer implements ICommandListener, Runnable {
	public static Logger logger = Logger.getLogger("Minecraft");
	public static HashMap field_6037_b = new HashMap();
	public NetworkListenThread field_6036_c;
	public PropertyManager propertyManagerObj;
	public static WorldServer worldMngr;
	public static ServerConfigurationManager configManager;
	private boolean field_6025_n = true;
	public boolean field_6032_g = false;
	int field_9014_h = 0;
	public String field_9013_i;
	public int field_9012_j;
	private List field_9010_p = new ArrayList();
	private List commands = Collections.synchronizedList(new ArrayList());
	public EntityTracker field_6028_k;
	public boolean onlineMode;
	public boolean noAnimals;
	public boolean field_9011_n;
	public static int port = 0;
	public static String levelName = null;
	public static String serverIP = null;

	public MinecraftServer() {
		new ThreadSleepForever(this);
	}

	private boolean func_6008_d() throws UnknownHostException {
		ThreadCommandReader var1 = new ThreadCommandReader(this);
		var1.setDaemon(true);
		var1.start();
		ConsoleLogManager.init();
		logger.info("Starting minecraft server version 0.2.8");
		if(Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
			logger.warning("**** NOT ENOUGH RAM!");
			logger.warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
		}

		logger.info("Loading properties");
		this.propertyManagerObj = new PropertyManager(new File("server.properties"));
		String var2 = this.propertyManagerObj.getStringProperty("server-ip", "");
		this.onlineMode = this.propertyManagerObj.getBooleanProperty("online-mode", true);
		this.noAnimals = this.propertyManagerObj.getBooleanProperty("spawn-animals", true);
		this.field_9011_n = this.propertyManagerObj.getBooleanProperty("pvp", true);
		InetAddress var3 = null;
		if(var2.length() > 0) {
			var3 = InetAddress.getByName(var2);
			serverIP = var3.toString();
		}

		int var4 = this.propertyManagerObj.getIntProperty("server-port", 25565);
		port = var4;
		logger.info("Starting Minecraft server on " + (var2.length() == 0 ? "*" : var2) + ":" + var4);

		try {
			this.field_6036_c = new NetworkListenThread(this, var3, var4);
		} catch (IOException var6) {
			logger.warning("**** FAILED TO BIND TO PORT!");
			logger.log(Level.WARNING, "The exception was: " + var6.toString());
			logger.warning("Perhaps a server is already running on that port?");
			return false;
		}

		if(!this.onlineMode) {
			logger.warning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
			logger.warning("The server will make no attempt to authenticate usernames. Beware.");
			logger.warning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
			logger.warning("To change this, set \"online-mode\" to \"true\" in the server.settings file.");
		}

		this.configManager = new ServerConfigurationManager(this);
		this.field_6028_k = new EntityTracker(this);
		String var5 = this.propertyManagerObj.getStringProperty("level-name", "world");
		levelName = var5;
		logger.info("Preparing level \"" + var5 + "\"");
		this.func_6017_c(var5);
		logger.info("Done! For help, type \"help\" or \"?\"");
		return true;
	}

	private void func_6017_c(String var1) {
		logger.info("Preparing start region");
		this.worldMngr = new WorldServer(this, new File("."), var1, this.propertyManagerObj.getBooleanProperty("hellworld", false) ? -1 : 0);
		this.worldMngr.func_4072_a(new WorldManager(this));
		this.worldMngr.monstersEnabled = this.propertyManagerObj.getBooleanProperty("spawn-monsters", true) ? 1 : 0;
		this.configManager.setPlayerManager(this.worldMngr);
		byte var2 = 20;

		for(int var3 = -var2; var3 <= var2; ++var3) {
			this.func_6019_a("Preparing spawn area", (var3 + var2) * 100 / (var2 + var2 + 1));

			for(int var4 = -var2; var4 <= var2; ++var4) {
				if(!this.field_6025_n) {
					return;
				}

				this.worldMngr.A.loadChunk((this.worldMngr.spawnX >> 4) + var3, (this.worldMngr.spawnZ >> 4) + var4);
			}
		}

		this.func_6011_e();
	}

	private void func_6019_a(String var1, int var2) {
		this.field_9013_i = var1;
		this.field_9012_j = var2;
		System.out.println(var1 + ": " + var2 + "%");
	}

	private void func_6011_e() {
		this.field_9013_i = null;
		this.field_9012_j = 0;
	}

	private void saveServerWorld() {
		logger.info("Saving chunks");
		this.worldMngr.func_485_a(true, (IProgressUpdate)null);
	}

	private void func_6013_g() {
		logger.info("Stopping server");
		if(this.configManager != null) {
			this.configManager.savePlayerStates();
		}

		if(this.worldMngr != null) {
			this.saveServerWorld();
		}

	}

	public void func_6016_a() {
		this.field_6025_n = false;
	}

	public void run() {
		try {
			if(this.func_6008_d()) {
				long var1 = System.currentTimeMillis();
				long var3 = 0L;

				while(this.field_6025_n) {
					long var5 = System.currentTimeMillis();
					long var7 = var5 - var1;
					if(var7 > 2000L) {
						logger.warning("Can\'t keep up! Did the system time change, or is the server overloaded?");
						var7 = 2000L;
					}

					if(var7 < 0L) {
						logger.warning("Time ran backwards! Did the system time change?");
						var7 = 0L;
					}

					var3 += var7;
					var1 = var5;

					while(var3 > 50L) {
						var3 -= 50L;
						this.func_6018_h();
					}

					Thread.sleep(1L);
				}
			} else {
				while(this.field_6025_n) {
					this.commandLineParser();

					try {
						Thread.sleep(10L);
					} catch (InterruptedException var15) {
						var15.printStackTrace();
					}
				}
			}
		} catch (Exception var16) {
			var16.printStackTrace();
			logger.log(Level.SEVERE, "Unexpected exception", var16);

			while(this.field_6025_n) {
				this.commandLineParser();

				try {
					Thread.sleep(10L);
				} catch (InterruptedException var14) {
					var14.printStackTrace();
				}
			}
		} finally {
			this.func_6013_g();
			this.field_6032_g = true;
			System.exit(0);
		}

	}

	private void func_6018_h() {
		ArrayList var1 = new ArrayList();
		Iterator var2 = field_6037_b.keySet().iterator();

		while(var2.hasNext()) {
			String var3 = (String)var2.next();
			int var4 = ((Integer)field_6037_b.get(var3)).intValue();
			if(var4 > 0) {
				field_6037_b.put(var3, Integer.valueOf(var4 - 1));
			} else {
				var1.add(var3);
			}
		}

		int var6;
		for(var6 = 0; var6 < var1.size(); ++var6) {
			field_6037_b.remove(var1.get(var6));
		}

		AxisAlignedBB.clearBoundingBoxPool();
		Vec3D.initialize();
		++this.field_9014_h;
		if(this.field_9014_h % 20 == 0) {
			this.configManager.sendPacketToAllPlayers(new Packet4UpdateTime(this.worldMngr.worldTime));
		}

		this.worldMngr.tick();

		while(this.worldMngr.func_6156_d()) {
		}

		this.worldMngr.func_459_b();
		this.field_6036_c.func_715_a();
		this.configManager.func_637_b();
		this.field_6028_k.func_607_a();

		for(var6 = 0; var6 < this.field_9010_p.size(); ++var6) {
			((IUpdatePlayerListBox)this.field_9010_p.get(var6)).update();
		}

		try {
			this.commandLineParser();
		} catch (Exception var5) {
			logger.log(Level.WARNING, "Unexpected exception while parsing console command", var5);
		}

	}

	public void addCommand(String var1, ICommandListener var2) {
		this.commands.add(new ServerCommand(var1, var2));
	}

	public void commandLineParser() {
		while(this.commands.size() > 0) {
			ServerCommand var1 = (ServerCommand)this.commands.remove(0);
			String var2 = var1.command;
			ICommandListener var3 = var1.commandListener;
			String var4 = var3.getUsername();
			if(!var2.toLowerCase().startsWith("help") && !var2.toLowerCase().startsWith("?")) {
				if(var2.toLowerCase().startsWith("list")) {
					var3.log("Connected players: " + this.configManager.getPlayerList());
				} else if(var2.toLowerCase().startsWith("stop")) {
					this.func_6014_a(var4, "Stopping the server..");
					this.field_6025_n = false;
				} else if(var2.toLowerCase().startsWith("save-all")) {
					this.func_6014_a(var4, "Forcing save..");
					this.worldMngr.func_485_a(true, (IProgressUpdate)null);
					this.func_6014_a(var4, "Save complete.");
				} else if(var2.toLowerCase().startsWith("save-off")) {
					this.func_6014_a(var4, "Disabling level saving..");
					this.worldMngr.field_816_A = true;
				} else if(var2.toLowerCase().startsWith("save-on")) {
					this.func_6014_a(var4, "Enabling level saving..");
					this.worldMngr.field_816_A = false;
				} else if(var2.toLowerCase().startsWith("spawn-protection")) {
					boolean b = this.configManager.isOp(var4) || var4.equals("CONSOLE");
					if(!b) {
						return;
					}
					String var11 = var2.substring(var2.indexOf(" ")).trim();
					if(var11.equals("enable")) {
						this.worldMngr.spawnProtection = true;
					} else if(var11.equals("disable")) {
						this.worldMngr.spawnProtection = false;
					} else {
						return;
					}
				} else {
					String var11;
					if(var2.toLowerCase().startsWith("op ")) {
						var11 = var2.substring(var2.indexOf(" ")).trim();
						this.configManager.opPlayer(var11);
						this.func_6014_a(var4, "Opping " + var11);
						this.configManager.sendChatMessageToPlayer(var11, "\u00a7eYou are now op!");
					} else if(var2.toLowerCase().startsWith("deop ")) {
						var11 = var2.substring(var2.indexOf(" ")).trim();
						this.configManager.deopPlayer(var11);
						this.configManager.sendChatMessageToPlayer(var11, "\u00a7eYou are no longer op!");
						this.func_6014_a(var4, "De-opping " + var11);
					} else if(var2.toLowerCase().startsWith("ban-ip ")) {
						var11 = var2.substring(var2.indexOf(" ")).trim();
						this.configManager.banIP(var11);
						this.func_6014_a(var4, "Banning ip " + var11);
					} else if(var2.toLowerCase().startsWith("pardon-ip ")) {
						var11 = var2.substring(var2.indexOf(" ")).trim();
						this.configManager.unbanIP(var11);
						this.func_6014_a(var4, "Pardoning ip " + var11);
					} else {
						EntityPlayerMP var12;
						if(var2.toLowerCase().startsWith("ban ")) {
							var11 = var2.substring(var2.indexOf(" ")).trim();
							this.configManager.banPlayer(var11);
							this.func_6014_a(var4, "Banning " + var11);
							var12 = this.configManager.getPlayerEntity(var11);
							if(var12 != null) {
								var12.field_421_a.func_43_c("Banned by admin");
							}
						} else if(var2.toLowerCase().startsWith("pardon ")) {
							var11 = var2.substring(var2.indexOf(" ")).trim();
							this.configManager.unbanPlayer(var11);
							this.func_6014_a(var4, "Pardoning " + var11);
						} else if(var2.toLowerCase().startsWith("kick ")) {
							var11 = var2.substring(var2.indexOf(" ")).trim();
							var12 = null;

							for(int var13 = 0; var13 < this.configManager.playerEntities.size(); ++var13) {
								EntityPlayerMP var14 = (EntityPlayerMP)this.configManager.playerEntities.get(var13);
								if(var14.username.equalsIgnoreCase(var11)) {
									var12 = var14;
								}
							}

							if(var12 != null) {
								var12.field_421_a.func_43_c("Kicked by admin");
								this.func_6014_a(var4, "Kicking " + var12.username);
							} else {
								var3.log("Can\'t find user " + var11 + ". No kick.");
							}
						} else {
							String[] var5;
							EntityPlayerMP var7;
							if(var2.toLowerCase().startsWith("tp ")) {
								var5 = var2.split(" ");
								if(var5.length == 3) {
									var12 = this.configManager.getPlayerEntity(var5[1]);
									var7 = this.configManager.getPlayerEntity(var5[2]);
									if(var12 == null) {
										var3.log("Can\'t find user " + var5[1] + ". No tp.");
									} else if(var7 == null) {
										var3.log("Can\'t find user " + var5[2] + ". No tp.");
									} else {
										var12.field_421_a.func_41_a(var7.posX, var7.posY, var7.posZ, var7.rotationYaw, var7.rotationPitch);
										this.func_6014_a(var4, "Teleporting " + var5[1] + " to " + var5[2] + ".");
									}
								} else {
									var3.log("Syntax error, please provice a source and a target.");
								}
							} else if(var2.toLowerCase().startsWith("give ")) {
								var5 = var2.split(" ");
								if(var5.length != 3 && var5.length != 4) {
									return;
								}

								String var6 = var5[1];
								var7 = this.configManager.getPlayerEntity(var6);
								if(var7 != null) {
									try {
										int var8 = Integer.parseInt(var5[2]);
										if(Item.itemsList[var8] != null) {
											this.func_6014_a(var4, "Giving " + var7.username + " some " + var8);
											int var9 = 1;
											if(var5.length > 3) {
												var9 = this.func_6020_b(var5[3], 1);
											}

											if(var9 < 1) {
												var9 = 1;
											}

											if(var9 > 64) {
												var9 = 64;
											}

											var7.func_161_a(new ItemStack(var8, var9));
										} else {
											var3.log("There\'s no item with id " + var8);
										}
									} catch (NumberFormatException var10) {
										var3.log("There\'s no item with id " + var5[2]);
									}
								} else {
									var3.log("Can\'t find user " + var6);
								}
							} else if(var2.toLowerCase().startsWith("say ")) {
								var2 = var2.substring(var2.indexOf(" ")).trim();
								logger.info("[" + var4 + "] " + var2);
								this.configManager.sendPacketToAllPlayers(new Packet3Chat("\u00a7d[Server] " + var2));
							} else if(var2.toLowerCase().startsWith("tell ")) {
								var5 = var2.split(" ");
								if(var5.length >= 3) {
									var2 = var2.substring(var2.indexOf(" ")).trim();
									var2 = var2.substring(var2.indexOf(" ")).trim();
									logger.info("[" + var4 + "->" + var5[1] + "] " + var2);
									var2 = "\u00a77" + var4 + " whispers " + var2;
									logger.info(var2);
									if(!this.configManager.sendPacketToPlayer(var5[1], new Packet3Chat(var2))) {
										var3.log("There\'s no player by that name online.");
									}
								}
							} else {
								logger.info("Unknown console command. Type \"help\" for help.");
							}
						}
					}
				}
			} else {
				var3.log("To run the server without a gui, start it like this:");
				var3.log("   java -Xmx1024M -Xms1024M -jar minecraft_server.jar nogui");
				var3.log("Console commands:");
				var3.log("   help  or  ?               shows this message");
				var3.log("   kick <player>             removes a player from the server");
				var3.log("   ban <player>              bans a player from the server");
				var3.log("   pardon <player>           pardons a banned player so that they can connect again");
				var3.log("   ban-ip <ip>               bans an IP address from the server");
				var3.log("   pardon-ip <ip>            pardons a banned IP address so that they can connect again");
				var3.log("   op <player>               turns a player into an op");
				var3.log("   deop <player>             removes op status from a player");
				var3.log("   tp <player1> <player2>    moves one player to the same location as another player");
				var3.log("   give <player> <id> [num]  gives a player a resource");
				var3.log("   tell <player> <message>   sends a private message to a player");
				var3.log("   stop                      gracefully stops the server");
				var3.log("   save-all                  forces a server-wide level save");
				var3.log("   save-off                  disables terrain saving (useful for backup scripts)");
				var3.log("   save-on                   re-enables terrain saving");
				var3.log("   list                      lists all currently connected players");
				var3.log("   say <message>             broadcasts a message to all players");
			}
		}

	}

	private void func_6014_a(String var1, String var2) {
		String var3 = var1 + ": " + var2;
		this.configManager.sendChatMessageToAllPlayers("\u00a77(" + var3 + ")");
		logger.info(var3);
	}

	private int func_6020_b(String var1, int var2) {
		try {
			return Integer.parseInt(var1);
		} catch (NumberFormatException var4) {
			return var2;
		}
	}

	public void func_6022_a(IUpdatePlayerListBox var1) {
		this.field_9010_p.add(var1);
	}

	public static void main(String[] var0) {
		try {
			MinecraftServer var1 = new MinecraftServer();
			if(!GraphicsEnvironment.isHeadless() && (var0.length <= 0 || !var0[0].equals("nogui"))) {
				ServerGUI.initGui(var1);
			}

			(new ThreadServerApplication("Server thread", var1)).start();
		} catch (Exception var2) {
			logger.log(Level.SEVERE, "Failed to start the minecraft server", var2);
		}

	}

	public File getFile(String var1) {
		return new File(var1);
	}

	public void log(String var1) {
		logger.info(var1);
	}

	public String getUsername() {
		return "CONSOLE";
	}

	public static boolean func_6015_a(MinecraftServer var0) {
		return var0.field_6025_n;
	}
}
