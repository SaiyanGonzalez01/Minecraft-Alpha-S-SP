package net.minecraft.src;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.server.MinecraftServer;

public class WorldServer extends World {
	public ChunkProviderServer A;
	public boolean field_819_z = false;
	public boolean field_816_A;
	private MinecraftServer field_6160_D;
	private MCHashTable E = new MCHashTable();

	public WorldServer(MinecraftServer var1, File var2, String var3, int var4) {
		super(var2, var3, (new Random()).nextLong(), WorldProvider.func_4091_a(var4));
		this.field_6160_D = var1;
	}

	public void tick() {
		super.tick();
	}

	public void func_4074_a(Entity var1, boolean var2) {
		if(!this.field_6160_D.noAnimals && var1 instanceof EntityAnimals) {
			var1.setEntityDead();
		}

		if(var1.field_328_f == null || !(var1.field_328_f instanceof EntityPlayer)) {
			super.func_4074_a(var1, var2);
		}

	}

	public void func_12017_b(Entity var1, boolean var2) {
		super.func_4074_a(var1, var2);
	}

	protected IChunkProvider func_4076_a(File var1) {
		this.A = new ChunkProviderServer(this, this.field_4272_q.getChunkLoader(var1), this.field_4272_q.getChunkProvider());
		return this.A;
	}

	public List func_532_d(int var1, int var2, int var3, int var4, int var5, int var6) {
		ArrayList var7 = new ArrayList();

		for(int var8 = 0; var8 < this.field_814_b.size(); ++var8) {
			TileEntity var9 = (TileEntity)this.field_814_b.get(var8);
			if(var9.xCoord >= var1 && var9.yCoord >= var2 && var9.zCoord >= var3 && var9.xCoord < var4 && var9.yCoord < var5 && var9.zCoord < var6) {
				var7.add(var9);
			}
		}

		return var7;
	}

	public boolean func_6157_a(EntityPlayer var1, int var2, int var3, int var4) {
		int var5 = (int)MathHelper.abs((float)(var2 - this.spawnX));
		int var6 = (int)MathHelper.abs((float)(var4 - this.spawnZ));
		if(var5 > var6) {
			var6 = var5;
		}

		return var6 > 16 || this.field_6160_D.configManager.isOp(var1.username) || !this.spawnProtection;
	}

	protected void func_479_b(Entity var1) {
		super.func_479_b(var1);
		this.E.addKey(var1.field_331_c, var1);
	}

	protected void func_531_c(Entity var1) {
		super.func_531_c(var1);
		this.E.removeObject(var1.field_331_c);
	}

	public Entity func_6158_a(int var1) {
		return (Entity)this.E.lookup(var1);
	}

	public void func_9206_a(Entity var1, byte var2) {
		Packet38 var3 = new Packet38(var1.field_331_c, var2);
		this.field_6160_D.field_6028_k.func_609_a(var1, var3);
	}

	public Explosion func_12015_a(Entity var1, double var2, double var4, double var6, float var8, boolean var9) {
		Explosion var10 = super.func_12015_a(var1, var2, var4, var6, var8, var9);
		this.field_6160_D.configManager.func_12022_a(var2, var4, var6, 64.0D, new Packet60(var2, var4, var6, var8, var10.field_12025_g));
		return var10;
	}
}
