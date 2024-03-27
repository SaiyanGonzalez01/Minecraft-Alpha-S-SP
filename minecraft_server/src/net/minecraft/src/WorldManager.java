package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class WorldManager implements IWorldAccess {
	private MinecraftServer mcServer;

	public WorldManager(MinecraftServer var1) {
		this.mcServer = var1;
	}

	public void spawnParticle(String var1, double var2, double var4, double var6, double var8, double var10, double var12) {
	}

	public void func_681_a(Entity var1) {
		this.mcServer.field_6028_k.func_611_a(var1);
	}

	public void func_688_b(Entity var1) {
		this.mcServer.field_6028_k.func_610_b(var1);
	}

	public void playSound(String var1, double var2, double var4, double var6, float var8, float var9) {
	}

	public void func_685_a(int var1, int var2, int var3, int var4, int var5, int var6) {
	}

	public void func_684_a() {
	}

	public void func_683_a(int var1, int var2, int var3) {
		this.mcServer.configManager.func_622_a(var1, var2, var3);
	}

	public void playRecord(String var1, int var2, int var3, int var4) {
	}

	public void func_686_a(int var1, int var2, int var3, TileEntity var4) {
		this.mcServer.configManager.sentTileEntityToPlayer(var1, var2, var3, var4);
	}
}
