package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.MinecraftServer;

public class PlayerManager {
	private List field_9216_a = new ArrayList();
	private MCHashTable2 field_9215_b = new MCHashTable2();
	private List field_833_c = new ArrayList();
	private MinecraftServer mcServer;

	public PlayerManager(MinecraftServer var1) {
		this.mcServer = var1;
	}

	public void func_538_a() {
		for(int var1 = 0; var1 < this.field_833_c.size(); ++var1) {
			((PlayerInstance)this.field_833_c.get(var1)).func_777_a();
		}

		this.field_833_c.clear();
	}

	private PlayerInstance func_537_a(int var1, int var2, boolean var3) {
		long var4 = (long)var1 + 2147483647L | (long)var2 + 2147483647L << 32;
		PlayerInstance var6 = (PlayerInstance)this.field_9215_b.func_677_a(var4);
		if(var6 == null && var3) {
			var6 = new PlayerInstance(this, var1, var2);
			this.field_9215_b.func_675_a(var4, var6);
		}

		return var6;
	}

	public void func_541_a(Packet var1, int var2, int var3, int var4) {
		int var5 = var2 >> 4;
		int var6 = var4 >> 4;
		PlayerInstance var7 = this.func_537_a(var5, var6, false);
		if(var7 != null) {
			var7.func_776_a(var1);
		}

	}

	public void func_535_a(int var1, int var2, int var3) {
		int var4 = var1 >> 4;
		int var5 = var3 >> 4;
		PlayerInstance var6 = this.func_537_a(var4, var5, false);
		if(var6 != null) {
			var6.func_775_a(var1 & 15, var2, var3 & 15);
		}

	}

	public void func_9214_a(EntityPlayerMP var1) {
		int var2 = (int)var1.posX >> 4;
		int var3 = (int)var1.posZ >> 4;
		var1.field_9155_d = var1.posX;
		var1.field_9154_e = var1.posZ;

		for(int var4 = var2 - 10; var4 <= var2 + 10; ++var4) {
			for(int var5 = var3 - 10; var5 <= var3 + 10; ++var5) {
				this.func_537_a(var4, var5, true).func_779_a(var1);
			}
		}

		this.field_9216_a.add(var1);
	}

	public void func_9213_b(EntityPlayerMP var1) {
		int var2 = (int)var1.field_9155_d >> 4;
		int var3 = (int)var1.field_9154_e >> 4;

		for(int var4 = var2 - 10; var4 <= var2 + 10; ++var4) {
			for(int var5 = var3 - 10; var5 <= var3 + 10; ++var5) {
				PlayerInstance var6 = this.func_537_a(var4, var5, false);
				if(var6 != null) {
					var6.func_778_b(var1);
				}
			}
		}

		this.field_9216_a.remove(var1);
	}

	private boolean func_544_a(int var1, int var2, int var3, int var4) {
		int var5 = var1 - var3;
		int var6 = var2 - var4;
		return var5 >= -10 && var5 <= 10 ? var6 >= -10 && var6 <= 10 : false;
	}

	public void func_543_c(EntityPlayerMP var1) {
		int var2 = (int)var1.posX >> 4;
		int var3 = (int)var1.posZ >> 4;
		double var4 = var1.field_9155_d - var1.posX;
		double var6 = var1.field_9154_e - var1.posZ;
		double var8 = var4 * var4 + var6 * var6;
		if(var8 >= 64.0D) {
			int var10 = (int)var1.field_9155_d >> 4;
			int var11 = (int)var1.field_9154_e >> 4;
			int var12 = var2 - var10;
			int var13 = var3 - var11;
			if(var12 != 0 || var13 != 0) {
				for(int var14 = var2 - 10; var14 <= var2 + 10; ++var14) {
					for(int var15 = var3 - 10; var15 <= var3 + 10; ++var15) {
						if(!this.func_544_a(var14, var15, var10, var11)) {
							this.func_537_a(var14, var15, true).func_779_a(var1);
						}

						if(!this.func_544_a(var14 - var12, var15 - var13, var2, var3)) {
							PlayerInstance var16 = this.func_537_a(var14 - var12, var15 - var13, false);
							if(var16 != null) {
								var16.func_778_b(var1);
							}
						}
					}
				}

				var1.field_9155_d = var1.posX;
				var1.field_9154_e = var1.posZ;
			}
		}
	}

	public int func_542_b() {
		return 144;
	}

	static MinecraftServer getMinecraftServer(PlayerManager var0) {
		return var0.mcServer;
	}

	static MCHashTable2 func_539_b(PlayerManager var0) {
		return var0.field_9215_b;
	}

	static List func_533_c(PlayerManager var0) {
		return var0.field_833_c;
	}
}
