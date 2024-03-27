package net.minecraft.src;

public interface IChunkProvider {
	boolean chunkExists(int var1, int var2);

	Chunk func_363_b(int var1, int var2);

	void populate(IChunkProvider var1, int var2, int var3);

	boolean saveWorld(boolean var1, IProgressUpdate var2);

	boolean func_361_a();

	boolean func_364_b();
}
