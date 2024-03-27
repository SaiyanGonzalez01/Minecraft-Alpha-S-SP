package net.minecraft.src;

public class ChunkCache implements IBlockAccess {
	private int field_823_a;
	private int field_822_b;
	private Chunk[][] field_825_c;
	private World worldObj;

	public ChunkCache(World var1, int var2, int var3, int var4, int var5, int var6, int var7) {
		this.worldObj = var1;
		this.field_823_a = var2 >> 4;
		this.field_822_b = var4 >> 4;
		int var8 = var5 >> 4;
		int var9 = var7 >> 4;
		this.field_825_c = new Chunk[var8 - this.field_823_a + 1][var9 - this.field_822_b + 1];

		for(int var10 = this.field_823_a; var10 <= var8; ++var10) {
			for(int var11 = this.field_822_b; var11 <= var9; ++var11) {
				this.field_825_c[var10 - this.field_823_a][var11 - this.field_822_b] = var1.getChunkFromChunkCoords(var10, var11);
			}
		}

	}

	public int getBlockId(int var1, int var2, int var3) {
		if(var2 < 0) {
			return 0;
		} else if(var2 >= 128) {
			return 0;
		} else {
			int var4 = (var1 >> 4) - this.field_823_a;
			int var5 = (var3 >> 4) - this.field_822_b;
			return this.field_825_c[var4][var5].getBlockID(var1 & 15, var2, var3 & 15);
		}
	}

	public int getBlockMetadata(int var1, int var2, int var3) {
		if(var2 < 0) {
			return 0;
		} else if(var2 >= 128) {
			return 0;
		} else {
			int var4 = (var1 >> 4) - this.field_823_a;
			int var5 = (var3 >> 4) - this.field_822_b;
			return this.field_825_c[var4][var5].getBlockMetadata(var1 & 15, var2, var3 & 15);
		}
	}

	public Material getBlockMaterial(int var1, int var2, int var3) {
		int var4 = this.getBlockId(var1, var2, var3);
		return var4 == 0 ? Material.air : Block.blocksList[var4].blockMaterial;
	}

	public boolean doesBlockAllowAttachment(int var1, int var2, int var3) {
		Block var4 = Block.blocksList[this.getBlockId(var1, var2, var3)];
		return var4 == null ? false : var4.allowsAttachment();
	}
}
