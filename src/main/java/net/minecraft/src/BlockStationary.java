package net.minecraft.src;

import net.PeytonPlayz585.EaglercraftRandom;

public class BlockStationary extends BlockFluids {
	protected BlockStationary(int var1, Material var2) {
		super(var1, var2);
		this.setTickOnLoad(false);
		if(var2 == Material.lava) {
			this.setTickOnLoad(true);
		}

	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		super.onNeighborBlockChange(var1, var2, var3, var4, var5);
		if(var1.getBlockId(var2, var3, var4) == this.blockID) {
			this.func_15234_j(var1, var2, var3, var4);
		}

	}

	private void func_15234_j(World var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockMetadata(var2, var3, var4);
		var1.field_1043_h = true;
		var1.setBlockAndMetadata(var2, var3, var4, this.blockID - 1, var5);
		var1.func_701_b(var2, var3, var4, var2, var3, var4);
		var1.scheduleBlockUpdate(var2, var3, var4, this.blockID - 1);
		var1.field_1043_h = false;
	}

	public void updateTick(World var1, int var2, int var3, int var4, EaglercraftRandom var5) {
		if(this.blockMaterial == Material.lava) {
			int var6 = var5.nextInt(3);

			for(int var7 = 0; var7 < var6; ++var7) {
				var2 += var5.nextInt(3) - 1;
				++var3;
				var4 += var5.nextInt(3) - 1;
				int var8 = var1.getBlockId(var2, var3, var4);
				if(var8 == 0) {
					if(this.func_301_k(var1, var2 - 1, var3, var4) || this.func_301_k(var1, var2 + 1, var3, var4) || this.func_301_k(var1, var2, var3, var4 - 1) || this.func_301_k(var1, var2, var3, var4 + 1) || this.func_301_k(var1, var2, var3 - 1, var4) || this.func_301_k(var1, var2, var3 + 1, var4)) {
						var1.setBlockWithNotify(var2, var3, var4, Block.fire.blockID);
						return;
					}
				} else if(Block.blocksList[var8].blockMaterial.func_880_c()) {
					return;
				}
			}
		}

	}

	private boolean func_301_k(World var1, int var2, int var3, int var4) {
		return var1.getBlockMaterial(var2, var3, var4).getBurning();
	}
}
