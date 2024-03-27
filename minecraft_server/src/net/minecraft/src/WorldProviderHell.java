package net.minecraft.src;

import java.io.File;

public class WorldProviderHell extends WorldProvider {
	public void func_4090_a() {
		this.field_4301_b = new WorldChunkManagerHell(MobSpawnerBase.hell, 1.0D, 0.0D);
		this.field_6167_c = true;
		this.field_6166_d = true;
		this.field_4306_c = true;
		this.field_6165_g = -1;
	}

	protected void generateLightBrightnessTable() {
		float var1 = 0.1F;

		for(int var2 = 0; var2 <= 15; ++var2) {
			float var3 = 1.0F - (float)var2 / 15.0F;
			this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
		}

	}

	public IChunkProvider getChunkProvider() {
		return new ChunkProviderHell(this.field_4302_a, this.field_4302_a.randomSeed);
	}

	public IChunkLoader getChunkLoader(File var1) {
		File var2 = new File(var1, "DIM-1");
		var2.mkdirs();
		return new ChunkLoader(var2, true);
	}

	public boolean canCoordinateBeSpawn(int var1, int var2) {
		int var3 = this.field_4302_a.func_528_f(var1, var2);
		return var3 == Block.bedrock.blockID ? false : (var3 == 0 ? false : Block.field_540_p[var3]);
	}

	public float func_4089_a(long var1, float var3) {
		return 0.5F;
	}
}
