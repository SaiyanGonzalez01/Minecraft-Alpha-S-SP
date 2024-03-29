package net.minecraft.src;

import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public class NetServerHandler extends NetHandler implements ICommandListener {
	public static Logger logger = Logger.getLogger("Minecraft");
	public NetworkManager netManager;
	public boolean field_18_c = false;
	private MinecraftServer mcServer;
	private EntityPlayerMP playerEntity;
	private int field_15_f = 0;
	private double field_9009_g;
	private double field_9008_h;
	private double field_9007_i;
	private boolean field_9006_j = true;
	private ItemStack field_10_k = null;

	public NetServerHandler(MinecraftServer var1, NetworkManager var2, EntityPlayerMP var3) {
		this.mcServer = var1;
		this.netManager = var2;
		var2.setNetHandler(this);
		this.playerEntity = var3;
		var3.field_421_a = this;
	}

	public void func_42_a() {
		if(!field_18_c) {
			this.netManager.processReadPackets();
		}
		
		if(this.field_15_f++ % 20 == 0) {
			this.netManager.addToSendQueue(new Packet0KeepAlive());
		}

	}

	public void func_43_c(String var1) {
		this.netManager.addToSendQueue(new Packet255KickDisconnect(var1));
		this.netManager.serverShutdown();
		this.mcServer.configManager.sendPacketToAllPlayers(new Packet3Chat("\u00a7e" + this.playerEntity.username + " left the game."));
		this.mcServer.configManager.playerLoggedOut(this.playerEntity);
		this.field_18_c = true;
	}

	public void handleFlying(Packet10Flying var1) {
		double var2;
		if(!this.field_9006_j) {
			var2 = var1.yPosition - this.field_9008_h;
			if(var1.xPosition == this.field_9009_g && var2 * var2 < 0.01D && var1.zPosition == this.field_9007_i) {
				this.field_9006_j = true;
			}
		}

		if(this.field_9006_j) {
			double var4;
			double var6;
			double var8;
			double var12;
			if(this.playerEntity.field_327_g != null) {
				float var24 = this.playerEntity.rotationYaw;
				float var3 = this.playerEntity.rotationPitch;
				this.playerEntity.field_327_g.func_127_w();
				var4 = this.playerEntity.posX;
				var6 = this.playerEntity.posY;
				var8 = this.playerEntity.posZ;
				double var25 = 0.0D;
				var12 = 0.0D;
				if(var1.rotating) {
					var24 = var1.yaw;
					var3 = var1.pitch;
				}

				if(var1.moving && var1.yPosition == -999.0D && var1.stance == -999.0D) {
					var25 = var1.xPosition;
					var12 = var1.zPosition;
				}

				this.playerEntity.onGround = var1.onGround;
				this.playerEntity.func_175_i();
				this.playerEntity.moveEntity(var25, 0.0D, var12);
				this.playerEntity.setPositionAndRotation(var4, var6, var8, var24, var3);
				this.playerEntity.motionX = var25;
				this.playerEntity.motionZ = var12;
				if(this.playerEntity.field_327_g != null) {
					this.mcServer.worldMngr.func_12017_b(this.playerEntity.field_327_g, true);
				}

				if(this.playerEntity.field_327_g != null) {
					this.playerEntity.field_327_g.func_127_w();
				}

				this.mcServer.configManager.func_613_b(this.playerEntity);
				this.field_9009_g = this.playerEntity.posX;
				this.field_9008_h = this.playerEntity.posY;
				this.field_9007_i = this.playerEntity.posZ;
				this.mcServer.worldMngr.func_520_e(this.playerEntity);
				return;
			}

			var2 = this.playerEntity.posY;
			this.field_9009_g = this.playerEntity.posX;
			this.field_9008_h = this.playerEntity.posY;
			this.field_9007_i = this.playerEntity.posZ;
			var4 = this.playerEntity.posX;
			var6 = this.playerEntity.posY;
			var8 = this.playerEntity.posZ;
			float var10 = this.playerEntity.rotationYaw;
			float var11 = this.playerEntity.rotationPitch;
			if(var1.moving && var1.yPosition == -999.0D && var1.stance == -999.0D) {
				var1.moving = false;
			}

			if(var1.moving) {
				var4 = var1.xPosition;
				var6 = var1.yPosition;
				var8 = var1.zPosition;
				var12 = var1.stance - var1.yPosition;
				if(var12 > 1.65D || var12 < 0.1D) {
					this.func_43_c("Illegal stance");
					logger.warning(this.playerEntity.username + " had an illegal stance: " + var12);
				}

				this.playerEntity.field_418_ai = var1.stance;
			}

			if(var1.rotating) {
				var10 = var1.yaw;
				var11 = var1.pitch;
			}

			this.playerEntity.func_175_i();
			this.playerEntity.field_9068_R = 0.0F;
			this.playerEntity.setPositionAndRotation(this.field_9009_g, this.field_9008_h, this.field_9007_i, var10, var11);
			var12 = var4 - this.playerEntity.posX;
			double var14 = var6 - this.playerEntity.posY;
			double var16 = var8 - this.playerEntity.posZ;
			float var18 = 1.0F / 16.0F;
			boolean var19 = this.mcServer.worldMngr.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.boundingBox.copy().func_694_e((double)var18, (double)var18, (double)var18)).size() == 0;
			this.playerEntity.moveEntity(var12, var14, var16);
			var12 = var4 - this.playerEntity.posX;
			var14 = var6 - this.playerEntity.posY;
			if(var14 > -0.5D || var14 < 0.5D) {
				var14 = 0.0D;
			}

			var16 = var8 - this.playerEntity.posZ;
			double var20 = var12 * var12 + var14 * var14 + var16 * var16;
			boolean var22 = false;
			if(var20 > 1.0D / 16.0D) {
				var22 = true;
				logger.warning(this.playerEntity.username + " moved wrongly!");
				System.out.println("Got position " + var4 + ", " + var6 + ", " + var8);
				System.out.println("Expected " + this.playerEntity.posX + ", " + this.playerEntity.posY + ", " + this.playerEntity.posZ);
			}

			this.playerEntity.setPositionAndRotation(var4, var6, var8, var10, var11);
			boolean var23 = this.mcServer.worldMngr.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.boundingBox.copy().func_694_e((double)var18, (double)var18, (double)var18)).size() == 0;
			if(var19 && (var22 || !var23)) {
				this.func_41_a(this.field_9009_g, this.field_9008_h, this.field_9007_i, var10, var11);
				return;
			}

			this.playerEntity.onGround = var1.onGround;
			this.mcServer.configManager.func_613_b(this.playerEntity);
			this.playerEntity.func_9153_b(this.playerEntity.posY - var2, var1.onGround);
		}

	}

	public void func_41_a(double var1, double var3, double var5, float var7, float var8) {
		this.field_9006_j = false;
		this.field_9009_g = var1;
		this.field_9008_h = var3;
		this.field_9007_i = var5;
		this.playerEntity.setPositionAndRotation(var1, var3, var5, var7, var8);
		this.playerEntity.field_421_a.sendPacket(new Packet13PlayerLookMove(var1, var3 + (double)1.62F, var3, var5, var7, var8, false));
	}

	public void handleBlockDig(Packet14BlockDig var1) {
		this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = this.field_10_k;
		boolean var2 = this.mcServer.worldMngr.field_819_z = this.mcServer.configManager.isOp(this.playerEntity.username);
		boolean var3 = false;
		if(var1.status == 0) {
			var3 = true;
		}

		if(var1.status == 1) {
			var3 = true;
		}

		int var4 = var1.xPosition;
		int var5 = var1.yPosition;
		int var6 = var1.zPosition;
		if(var3) {
			double var7 = this.playerEntity.posX - ((double)var4 + 0.5D);
			double var9 = this.playerEntity.posY - ((double)var5 + 0.5D);
			double var11 = this.playerEntity.posZ - ((double)var6 + 0.5D);
			double var13 = var7 * var7 + var9 * var9 + var11 * var11;
			if(var13 > 36.0D) {
				return;
			}

			double var15 = this.playerEntity.posY;
			this.playerEntity.posY = this.playerEntity.field_418_ai;
			this.playerEntity.posY = var15;
		}

		int var18 = var1.face;
		int var8 = (int)MathHelper.abs((float)(var4 - this.mcServer.worldMngr.spawnX));
		int var19 = (int)MathHelper.abs((float)(var6 - this.mcServer.worldMngr.spawnZ));
		if(var8 > var19) {
			var19 = var8;
		}

		if(var1.status == 0) {
			if(var19 > 16 || var2 || !this.mcServer.worldMngr.spawnProtection) {
				this.playerEntity.field_425_ad.func_324_a(var4, var5, var6);
			}
		} else if(var1.status == 2) {
			this.playerEntity.field_425_ad.func_328_a();
		} else if(var1.status == 1) {
			if(var19 > 16 || var2 || !this.mcServer.worldMngr.spawnProtection) {
				this.playerEntity.field_425_ad.func_326_a(var4, var5, var6, var18);
			}
		} else if(var1.status == 3) {
			double var10 = this.playerEntity.posX - ((double)var4 + 0.5D);
			double var12 = this.playerEntity.posY - ((double)var5 + 0.5D);
			double var14 = this.playerEntity.posZ - ((double)var6 + 0.5D);
			double var16 = var10 * var10 + var12 * var12 + var14 * var14;
			if(var16 < 256.0D) {
				this.playerEntity.field_421_a.sendPacket(new Packet53BlockChange(var4, var5, var6, this.mcServer.worldMngr));
			}
			if(!(var19 > 16 || var2) && this.mcServer.worldMngr.spawnProtection) {
				this.playerEntity.field_421_a.sendPacket(new Packet3Chat("You cannot place/break blocks in this area!"));
			}
		}

		this.mcServer.worldMngr.field_819_z = false;
	}

	public void handlePlace(Packet15Place var1) {
		boolean var2 = this.mcServer.worldMngr.field_819_z = this.mcServer.configManager.isOp(this.playerEntity.username);
		ItemStack stack = this.playerEntity.inventory.getCurrentItem();
		if(var1.direction == 255) {
			ItemStack var3 = var1.id >= 0 ? new ItemStack(var1.id) : null;
			this.playerEntity.field_425_ad.func_6154_a(this.playerEntity, this.mcServer.worldMngr, var3);
		} else {
			int var10 = var1.xPosition;
			int var4 = var1.yPosition;
			int var5 = var1.zPosition;
			int var6 = var1.direction;
			int var7 = (int)MathHelper.abs((float)(var10 - this.mcServer.worldMngr.spawnX));
			int var8 = (int)MathHelper.abs((float)(var5 - this.mcServer.worldMngr.spawnZ));
			if(var7 > var8) {
				var8 = var7;
			}

			if(this.mcServer.worldMngr.spawnProtection) {
				if(var8 > 16 || var2) {
					ItemStack var9 = var1.id >= 0 ? new ItemStack(var1.id) : null;
					this.playerEntity.field_425_ad.func_327_a(this.playerEntity, this.mcServer.worldMngr, var9, var10, var4, var5, var6);
				}
			} else {
				ItemStack var9 = var1.id >= 0 ? new ItemStack(var1.id) : null;
				this.playerEntity.field_425_ad.func_327_a(this.playerEntity, this.mcServer.worldMngr, var9, var10, var4, var5, var6);
			}

			this.playerEntity.field_421_a.sendPacket(new Packet53BlockChange(var10, var4, var5, this.mcServer.worldMngr));
			if(var6 == 0) {
				--var4;
			}

			if(var6 == 1) {
				++var4;
			}

			if(var6 == 2) {
				--var5;
			}

			if(var6 == 3) {
				++var5;
			}

			if(var6 == 4) {
				--var10;
			}

			if(var6 == 5) {
				++var10;
			}

			this.playerEntity.field_421_a.sendPacket(new Packet53BlockChange(var10, var4, var5, this.mcServer.worldMngr));
			
			if(!(var8 > 16 | var2) && this.mcServer.worldMngr.spawnProtection) {
				this.playerEntity.field_421_a.sendPacket(new Packet3Chat("You cannot place/break blocks in this area!"));
				if(stack != null && stack.stackSize == 0) {
					this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
				}
				this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.func_20117_a(this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]);
				this.playerEntity.field_421_a.sendPacket(new Packet5PlayerInventory(-1, this.playerEntity.inventory.mainInventory));
			}
		}

		this.mcServer.worldMngr.field_819_z = false;
	}

	public void handleErrorMessage(String var1) {
		logger.info(this.playerEntity.username + " lost connection: " + var1);
		this.mcServer.configManager.sendPacketToAllPlayers(new Packet3Chat("\u00a7e" + this.playerEntity.username + " left the game."));
		this.mcServer.configManager.playerLoggedOut(this.playerEntity);
		this.field_18_c = true;
	}

	public void func_6001_a(Packet var1) {
		logger.warning(this.getClass() + " wasn\'t prepared to deal with a " + var1.getClass());
		this.func_43_c("Protocol error, unexpected packet");
	}

	public void sendPacket(Packet var1) {
		this.netManager.addToSendQueue(var1);
	}

	public void handleBlockItemSwitch(Packet16BlockItemSwitch var1) {
		int var2 = var1.id;
		this.playerEntity.inventory.currentItem = this.playerEntity.inventory.mainInventory.length - 1;
		if(var2 == 0) {
			this.field_10_k = null;
		} else {
			this.field_10_k = new ItemStack(var2);
		}

		this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = this.field_10_k;
		this.mcServer.field_6028_k.func_12021_a(this.playerEntity, new Packet16BlockItemSwitch(this.playerEntity.field_331_c, var2));
	}

	public void handlePickupSpawn(Packet21PickupSpawn var1) {
		double var2 = (double)var1.xPosition / 32.0D;
		double var4 = (double)var1.yPosition / 32.0D;
		double var6 = (double)var1.zPosition / 32.0D;
		EntityItem var8 = new EntityItem(this.mcServer.worldMngr, var2, var4, var6, new ItemStack(var1.itemId, var1.count));
		var8.motionX = (double)var1.rotation / 128.0D;
		var8.motionY = (double)var1.pitch / 128.0D;
		var8.motionZ = (double)var1.roll / 128.0D;
		var8.field_433_ad = 10;
		this.mcServer.worldMngr.entityJoinedWorld(var8);
	}

	public void handleChat(Packet3Chat var1) {
		String var2 = var1.message;
		if(var2.length() > 100) {
			this.func_43_c("Chat message too long");
		} else {
			var2 = var2.trim();

			for(int var3 = 0; var3 < var2.length(); ++var3) {
				if(" !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb".indexOf(var2.charAt(var3)) < 0) {
					this.func_43_c("Illegal characters in chat");
					return;
				}
			}

			if(var2.startsWith("/")) {
				this.func_4010_d(var2);
			} else {
				var2 = "<" + this.playerEntity.username + "> " + var2;
				logger.info(var2);
				this.mcServer.configManager.sendPacketToAllPlayers(new Packet3Chat(var2));
			}

		}
	}

	private void func_4010_d(String var1) {
		if(var1.toLowerCase().startsWith("/me ")) {
			var1 = "* " + this.playerEntity.username + " " + var1.substring(var1.indexOf(" ")).trim();
			logger.info(var1);
			this.mcServer.configManager.sendPacketToAllPlayers(new Packet3Chat(var1));
		} else if(var1.toLowerCase().startsWith("/kill")) {
			this.playerEntity.attackEntity((Entity)null, 1000);
		} else if(var1.toLowerCase().startsWith("/tell ")) {
			String[] var2 = var1.split(" ");
			if(var2.length >= 3) {
				var1 = var1.substring(var1.indexOf(" ")).trim();
				var1 = var1.substring(var1.indexOf(" ")).trim();
				var1 = "\u00a77" + this.playerEntity.username + " whispers " + var1;
				logger.info(var1 + " to " + var2[1]);
				if(!this.mcServer.configManager.sendPacketToPlayer(var2[1], new Packet3Chat(var1))) {
					this.sendPacket(new Packet3Chat("\u00a7cThere\'s no player by that name online."));
				}
			}
		} else {
			String var3;
			if(this.mcServer.configManager.isOp(this.playerEntity.username)) {
				var3 = var1.substring(1);
				logger.info(this.playerEntity.username + " issued server command: " + var3);
				this.mcServer.addCommand(var3, this);
			} else {
				var3 = var1.substring(1);
				logger.info(this.playerEntity.username + " tried command: " + var3);
			}
		}

	}

	public void handleArmAnimation(Packet18ArmAnimation var1) {
		if(var1.animate == 1) {
			this.playerEntity.func_168_z();
		} else if(var1.animate == 104) {
			this.playerEntity.field_12012_al = true;
		} else if(var1.animate == 105) {
			this.playerEntity.field_12012_al = false;
		}

	}

	public void handleKickDisconnect(Packet255KickDisconnect var1) {
		this.netManager.networkShutdown("Quitting");
	}

	public int func_38_b() {
		return this.netManager.getNumChunkDataPackets();
	}

	public void log(String var1) {
		this.sendPacket(new Packet3Chat("\u00a77" + var1));
	}

	public String getUsername() {
		return this.playerEntity.username;
	}

	public void handlePlayerInventory(Packet5PlayerInventory var1) {
		if(var1.type == -1) {
			this.playerEntity.inventory.mainInventory = var1.stacks;
		}

		if(var1.type == -2) {
			this.playerEntity.inventory.craftingInventory = var1.stacks;
		}

		if(var1.type == -3) {
			this.playerEntity.inventory.armorInventory = var1.stacks;
		}

	}

	public void func_40_d() {
		this.netManager.addToSendQueue(new Packet5PlayerInventory(-1, this.playerEntity.inventory.mainInventory));
		this.netManager.addToSendQueue(new Packet5PlayerInventory(-2, this.playerEntity.inventory.craftingInventory));
		this.netManager.addToSendQueue(new Packet5PlayerInventory(-3, this.playerEntity.inventory.armorInventory));
	}

	public void handleComplexEntity(Packet59ComplexEntity var1) {
		if(var1.entityNBT.getInteger("x") == var1.xPosition) {
			if(var1.entityNBT.getInteger("y") == var1.yPosition) {
				if(var1.entityNBT.getInteger("z") == var1.zPosition) {
					TileEntity var2 = this.mcServer.worldMngr.getBlock(var1.xPosition, var1.yPosition, var1.zPosition);
					if(var2 != null) {
						try {
							var2.readFromNBT(var1.entityNBT);
						} catch (Exception var4) {
						}

						var2.func_183_c();
					}

				}
			}
		}
	}

	public void func_6006_a(Packet7 var1) {
		Entity var2 = this.mcServer.worldMngr.func_6158_a(var1.field_9018_b);
		this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = this.field_10_k;
		if(var2 != null && this.playerEntity.func_145_g(var2)) {
			if(var1.field_9020_c == 0) {
				this.playerEntity.func_9145_g(var2);
			} else if(var1.field_9020_c == 1) {
				this.playerEntity.func_9146_h(var2);
			}
		}

	}

	public void func_9002_a(Packet9 var1) {
		if(this.playerEntity.field_9109_aQ <= 0) {
			this.playerEntity = this.mcServer.configManager.func_9242_d(this.playerEntity);
		}
	}
}
