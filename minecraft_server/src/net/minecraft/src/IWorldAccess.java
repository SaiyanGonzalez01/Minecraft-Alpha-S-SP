package net.minecraft.src;

public interface IWorldAccess {
	void func_683_a(int var1, int var2, int var3);

	void func_685_a(int var1, int var2, int var3, int var4, int var5, int var6);

	void playSound(String var1, double var2, double var4, double var6, float var8, float var9);

	void spawnParticle(String var1, double var2, double var4, double var6, double var8, double var10, double var12);

	void func_681_a(Entity var1);

	void func_688_b(Entity var1);

	void func_684_a();

	void playRecord(String var1, int var2, int var3, int var4);

	void func_686_a(int var1, int var2, int var3, TileEntity var4);
}
