package net.minecraft.src;

import net.PeytonPlayz585.EaglercraftRandom;

public class BlockStep extends Block {
	private boolean field_469_a;

	public BlockStep(int var1, boolean var2) {
		super(var1, 6, Material.rock);
		this.field_469_a = var2;
		if(!var2) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}

		this.setLightOpacity(255);
	}

	public int getBlockTextureFromSide(int var1) {
		return var1 <= 1 ? 6 : 5;
	}

	public boolean isOpaqueCube() {
		return this.field_469_a;
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		if(this == Block.stairSingle) {
		}
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		if(this != Block.stairSingle) {
			super.onBlockAdded(var1, var2, var3, var4);
		}

		int var5 = var1.getBlockId(var2, var3 - 1, var4);
		if(var5 == stairSingle.blockID) {
			var1.setBlockWithNotify(var2, var3, var4, 0);
			var1.setBlockWithNotify(var2, var3 - 1, var4, Block.stairDouble.blockID);
		}

	}

	public int idDropped(int var1, EaglercraftRandom var2) {
		return Block.stairSingle.blockID;
	}

	public boolean renderAsNormalBlock() {
		return this.field_469_a;
	}

	public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		if(this != Block.stairSingle) {
			super.shouldSideBeRendered(var1, var2, var3, var4, var5);
		}

		return var5 == 1 ? true : (!super.shouldSideBeRendered(var1, var2, var3, var4, var5) ? false : (var5 == 0 ? true : var1.getBlockId(var2, var3, var4) != this.blockID));
	}
}
