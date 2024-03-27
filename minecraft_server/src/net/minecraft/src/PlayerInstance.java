package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

class PlayerInstance {
	private List field_1072_b;
	private int field_1071_c;
	private int field_1070_d;
	private ChunkCoordIntPair field_1069_e;
	private short[] field_1068_f;
	private int field_1067_g;
	private int field_1066_h;
	private int field_1065_i;
	private int field_1064_j;
	private int field_1063_k;
	private int field_1062_l;
	private int field_1061_m;
	final PlayerManager field_1073_a;

	public PlayerInstance(PlayerManager var1, int var2, int var3) {
		this.field_1073_a = var1;
		this.field_1072_b = new ArrayList();
		this.field_1068_f = new short[10];
		this.field_1067_g = 0;
		this.field_1071_c = var2;
		this.field_1070_d = var3;
		this.field_1069_e = new ChunkCoordIntPair(var2, var3);
		PlayerManager.getMinecraftServer(var1).worldMngr.A.loadChunk(var2, var3);
	}

	public void func_779_a(EntityPlayerMP var1) {
		if(this.field_1072_b.contains(var1)) {
			throw new IllegalStateException("Failed to add player. " + var1 + " already is in chunk " + this.field_1071_c + ", " + this.field_1070_d);
		} else {
			var1.field_420_ah.add(this.field_1069_e);
			var1.field_421_a.sendPacket(new Packet50PreChunk(this.field_1069_e.field_152_a, this.field_1069_e.field_151_b, true));
			this.field_1072_b.add(var1);
			var1.field_422_ag.add(this.field_1069_e);
		}
	}

	public void func_778_b(EntityPlayerMP var1) {
		if(!this.field_1072_b.contains(var1)) {
			(new IllegalStateException("Failed to remove player. " + var1 + " isn\'t in chunk " + this.field_1071_c + ", " + this.field_1070_d)).printStackTrace();
		} else {
			this.field_1072_b.remove(var1);
			if(this.field_1072_b.size() == 0) {
				long var2 = (long)this.field_1071_c + 2147483647L | (long)this.field_1070_d + 2147483647L << 32;
				PlayerManager.func_539_b(this.field_1073_a).func_670_b(var2);
				if(this.field_1067_g > 0) {
					PlayerManager.func_533_c(this.field_1073_a).remove(this);
				}

				PlayerManager.getMinecraftServer(this.field_1073_a).worldMngr.A.func_374_c(this.field_1071_c, this.field_1070_d);
			}

			var1.field_422_ag.remove(this.field_1069_e);
			if(var1.field_420_ah.contains(this.field_1069_e)) {
				var1.field_421_a.sendPacket(new Packet50PreChunk(this.field_1071_c, this.field_1070_d, false));
			}

		}
	}

	public void func_775_a(int var1, int var2, int var3) {
		if(this.field_1067_g == 0) {
			PlayerManager.func_533_c(this.field_1073_a).add(this);
			this.field_1066_h = this.field_1065_i = var1;
			this.field_1064_j = this.field_1063_k = var2;
			this.field_1062_l = this.field_1061_m = var3;
		}

		if(this.field_1066_h > var1) {
			this.field_1066_h = var1;
		}

		if(this.field_1065_i < var1) {
			this.field_1065_i = var1;
		}

		if(this.field_1064_j > var2) {
			this.field_1064_j = var2;
		}

		if(this.field_1063_k < var2) {
			this.field_1063_k = var2;
		}

		if(this.field_1062_l > var3) {
			this.field_1062_l = var3;
		}

		if(this.field_1061_m < var3) {
			this.field_1061_m = var3;
		}

		if(this.field_1067_g < 10) {
			short var4 = (short)(var1 << 12 | var3 << 8 | var2);

			for(int var5 = 0; var5 < this.field_1067_g; ++var5) {
				if(this.field_1068_f[var5] == var4) {
					return;
				}
			}

			this.field_1068_f[this.field_1067_g++] = var4;
		}

	}

	public void func_776_a(Packet var1) {
		for(int var2 = 0; var2 < this.field_1072_b.size(); ++var2) {
			EntityPlayerMP var3 = (EntityPlayerMP)this.field_1072_b.get(var2);
			if(var3.field_420_ah.contains(this.field_1069_e)) {
				var3.field_421_a.sendPacket(var1);
			}
		}

	}

	public void func_777_a() {
		if(this.field_1067_g != 0) {
			int var1;
			int var2;
			int var3;
			if(this.field_1067_g == 1) {
				var1 = this.field_1071_c * 16 + this.field_1066_h;
				var2 = this.field_1064_j;
				var3 = this.field_1070_d * 16 + this.field_1062_l;
				this.func_776_a(new Packet53BlockChange(var1, var2, var3, PlayerManager.getMinecraftServer(this.field_1073_a).worldMngr));
				if(Block.isBlockContainer[PlayerManager.getMinecraftServer(this.field_1073_a).worldMngr.getBlockId(var1, var2, var3)]) {
					this.func_776_a(new Packet59ComplexEntity(var1, var2, var3, PlayerManager.getMinecraftServer(this.field_1073_a).worldMngr.getBlock(var1, var2, var3)));
				}
			} else {
				int var4;
				if(this.field_1067_g == 10) {
					this.field_1064_j = this.field_1064_j / 2 * 2;
					this.field_1063_k = (this.field_1063_k / 2 + 1) * 2;
					var1 = this.field_1066_h + this.field_1071_c * 16;
					var2 = this.field_1064_j;
					var3 = this.field_1062_l + this.field_1070_d * 16;
					var4 = this.field_1065_i - this.field_1066_h + 1;
					int var5 = this.field_1063_k - this.field_1064_j + 2;
					int var6 = this.field_1061_m - this.field_1062_l + 1;
					this.func_776_a(new Packet51MapChunk(var1, var2, var3, var4, var5, var6, PlayerManager.getMinecraftServer(this.field_1073_a).worldMngr));
					List var7 = PlayerManager.getMinecraftServer(this.field_1073_a).worldMngr.func_532_d(var1, var2, var3, var1 + var4, var2 + var5, var3 + var6);

					for(int var8 = 0; var8 < var7.size(); ++var8) {
						TileEntity var9 = (TileEntity)var7.get(var8);
						this.func_776_a(new Packet59ComplexEntity(var9.xCoord, var9.yCoord, var9.zCoord, var9));
					}
				} else {
					this.func_776_a(new Packet52MultiBlockChange(this.field_1071_c, this.field_1070_d, this.field_1068_f, this.field_1067_g, PlayerManager.getMinecraftServer(this.field_1073_a).worldMngr));

					for(var1 = 0; var1 < this.field_1067_g; ++var1) {
						var2 = this.field_1071_c * 16 + (this.field_1067_g >> 12 & 15);
						var3 = this.field_1067_g & 255;
						var4 = this.field_1070_d * 16 + (this.field_1067_g >> 8 & 15);
						if(Block.isBlockContainer[PlayerManager.getMinecraftServer(this.field_1073_a).worldMngr.getBlockId(var2, var3, var4)]) {
							this.func_776_a(new Packet59ComplexEntity(var2, var3, var4, PlayerManager.getMinecraftServer(this.field_1073_a).worldMngr.getBlock(var2, var3, var4)));
						}
					}
				}
			}

			this.field_1067_g = 0;
		}
	}
}
