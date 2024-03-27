package net.minecraft.src;

import java.util.Random;

public class BlockRedstoneWire extends Block {
	private boolean field_652_a = true;

	public BlockRedstoneWire(int var1, int var2) {
		super(var1, var2, Material.circuits);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F / 16.0F, 1.0F);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return null;
	}

	public boolean allowsAttachment() {
		return false;
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return var1.doesBlockAllowAttachment(var2, var3 - 1, var4);
	}

	private void func_292_g(World var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockMetadata(var2, var3, var4);
		int var6 = 0;
		this.field_652_a = false;
		boolean var7 = var1.isBlockIndirectlyGettingPowered(var2, var3, var4);
		this.field_652_a = true;
		int var8;
		int var9;
		int var10;
		if(var7) {
			var6 = 15;
		} else {
			for(var8 = 0; var8 < 4; ++var8) {
				var9 = var2;
				var10 = var4;
				if(var8 == 0) {
					var9 = var2 - 1;
				}

				if(var8 == 1) {
					++var9;
				}

				if(var8 == 2) {
					var10 = var4 - 1;
				}

				if(var8 == 3) {
					++var10;
				}

				var6 = this.func_290_f(var1, var9, var3, var10, var6);
				if(var1.doesBlockAllowAttachment(var9, var3, var10) && !var1.doesBlockAllowAttachment(var2, var3 + 1, var4)) {
					var6 = this.func_290_f(var1, var9, var3 + 1, var10, var6);
				} else if(!var1.doesBlockAllowAttachment(var9, var3, var10)) {
					var6 = this.func_290_f(var1, var9, var3 - 1, var10, var6);
				}
			}

			if(var6 > 0) {
				--var6;
			} else {
				var6 = 0;
			}
		}

		if(var5 != var6) {
			var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
			var1.func_519_b(var2, var3, var4, var2, var3, var4);
			if(var6 > 0) {
				--var6;
			}

			for(var8 = 0; var8 < 4; ++var8) {
				var9 = var2;
				var10 = var4;
				int var11 = var3 - 1;
				if(var8 == 0) {
					var9 = var2 - 1;
				}

				if(var8 == 1) {
					++var9;
				}

				if(var8 == 2) {
					var10 = var4 - 1;
				}

				if(var8 == 3) {
					++var10;
				}

				if(var1.doesBlockAllowAttachment(var9, var3, var10)) {
					var11 += 2;
				}

				int var12 = this.func_290_f(var1, var9, var3, var10, -1);
				if(var12 >= 0 && var12 != var6) {
					this.func_292_g(var1, var9, var3, var10);
				}

				var12 = this.func_290_f(var1, var9, var11, var10, -1);
				if(var12 >= 0 && var12 != var6) {
					this.func_292_g(var1, var9, var11, var10);
				}
			}

			if(var5 == 0 || var6 == 0) {
				var1.notifyBlocksOfNeighborChange(var2, var3, var4, this.blockID);
				var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
				var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
				var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
				var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
				var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
				var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
			}
		}

	}

	private void func_291_h(World var1, int var2, int var3, int var4) {
		if(var1.getBlockId(var2, var3, var4) == this.blockID) {
			var1.notifyBlocksOfNeighborChange(var2, var3, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
		}
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		super.onBlockAdded(var1, var2, var3, var4);
		if(!var1.multiplayerWorld) {
			this.func_292_g(var1, var2, var3, var4);
			var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
			this.func_291_h(var1, var2 - 1, var3, var4);
			this.func_291_h(var1, var2 + 1, var3, var4);
			this.func_291_h(var1, var2, var3, var4 - 1);
			this.func_291_h(var1, var2, var3, var4 + 1);
			if(var1.doesBlockAllowAttachment(var2 - 1, var3, var4)) {
				this.func_291_h(var1, var2 - 1, var3 + 1, var4);
			} else {
				this.func_291_h(var1, var2 - 1, var3 - 1, var4);
			}

			if(var1.doesBlockAllowAttachment(var2 + 1, var3, var4)) {
				this.func_291_h(var1, var2 + 1, var3 + 1, var4);
			} else {
				this.func_291_h(var1, var2 + 1, var3 - 1, var4);
			}

			if(var1.doesBlockAllowAttachment(var2, var3, var4 - 1)) {
				this.func_291_h(var1, var2, var3 + 1, var4 - 1);
			} else {
				this.func_291_h(var1, var2, var3 - 1, var4 - 1);
			}

			if(var1.doesBlockAllowAttachment(var2, var3, var4 + 1)) {
				this.func_291_h(var1, var2, var3 + 1, var4 + 1);
			} else {
				this.func_291_h(var1, var2, var3 - 1, var4 + 1);
			}

		}
	}

	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
		super.onBlockRemoval(var1, var2, var3, var4);
		if(!var1.multiplayerWorld) {
			var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
			this.func_292_g(var1, var2, var3, var4);
			this.func_291_h(var1, var2 - 1, var3, var4);
			this.func_291_h(var1, var2 + 1, var3, var4);
			this.func_291_h(var1, var2, var3, var4 - 1);
			this.func_291_h(var1, var2, var3, var4 + 1);
			if(var1.doesBlockAllowAttachment(var2 - 1, var3, var4)) {
				this.func_291_h(var1, var2 - 1, var3 + 1, var4);
			} else {
				this.func_291_h(var1, var2 - 1, var3 - 1, var4);
			}

			if(var1.doesBlockAllowAttachment(var2 + 1, var3, var4)) {
				this.func_291_h(var1, var2 + 1, var3 + 1, var4);
			} else {
				this.func_291_h(var1, var2 + 1, var3 - 1, var4);
			}

			if(var1.doesBlockAllowAttachment(var2, var3, var4 - 1)) {
				this.func_291_h(var1, var2, var3 + 1, var4 - 1);
			} else {
				this.func_291_h(var1, var2, var3 - 1, var4 - 1);
			}

			if(var1.doesBlockAllowAttachment(var2, var3, var4 + 1)) {
				this.func_291_h(var1, var2, var3 + 1, var4 + 1);
			} else {
				this.func_291_h(var1, var2, var3 - 1, var4 + 1);
			}

		}
	}

	private int func_290_f(World var1, int var2, int var3, int var4, int var5) {
		if(var1.getBlockId(var2, var3, var4) != this.blockID) {
			return var5;
		} else {
			int var6 = var1.getBlockMetadata(var2, var3, var4);
			return var6 > var5 ? var6 : var5;
		}
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		if(!var1.multiplayerWorld) {
			int var6 = var1.getBlockMetadata(var2, var3, var4);
			boolean var7 = this.canPlaceBlockAt(var1, var2, var3, var4);
			if(!var7) {
				this.dropBlockAsItem(var1, var2, var3, var4, var6);
				var1.setBlockWithNotify(var2, var3, var4, 0);
			} else {
				this.func_292_g(var1, var2, var3, var4);
			}

			super.onNeighborBlockChange(var1, var2, var3, var4, var5);
		}
	}

	public int idDropped(int var1, Random var2) {
		return Item.redstone.swiftedIndex;
	}

	public boolean isIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5) {
		return !this.field_652_a ? false : this.isPoweringTo(var1, var2, var3, var4, var5);
	}

	public boolean isPoweringTo(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		if(!this.field_652_a) {
			return false;
		} else if(var1.getBlockMetadata(var2, var3, var4) == 0) {
			return false;
		} else if(var5 == 1) {
			return true;
		} else {
			boolean var6 = func_293_b(var1, var2 - 1, var3, var4) || !var1.doesBlockAllowAttachment(var2 - 1, var3, var4) && func_293_b(var1, var2 - 1, var3 - 1, var4);
			boolean var7 = func_293_b(var1, var2 + 1, var3, var4) || !var1.doesBlockAllowAttachment(var2 + 1, var3, var4) && func_293_b(var1, var2 + 1, var3 - 1, var4);
			boolean var8 = func_293_b(var1, var2, var3, var4 - 1) || !var1.doesBlockAllowAttachment(var2, var3, var4 - 1) && func_293_b(var1, var2, var3 - 1, var4 - 1);
			boolean var9 = func_293_b(var1, var2, var3, var4 + 1) || !var1.doesBlockAllowAttachment(var2, var3, var4 + 1) && func_293_b(var1, var2, var3 - 1, var4 + 1);
			if(!var1.doesBlockAllowAttachment(var2, var3 + 1, var4)) {
				if(var1.doesBlockAllowAttachment(var2 - 1, var3, var4) && func_293_b(var1, var2 - 1, var3 + 1, var4)) {
					var6 = true;
				}

				if(var1.doesBlockAllowAttachment(var2 + 1, var3, var4) && func_293_b(var1, var2 + 1, var3 + 1, var4)) {
					var7 = true;
				}

				if(var1.doesBlockAllowAttachment(var2, var3, var4 - 1) && func_293_b(var1, var2, var3 + 1, var4 - 1)) {
					var8 = true;
				}

				if(var1.doesBlockAllowAttachment(var2, var3, var4 + 1) && func_293_b(var1, var2, var3 + 1, var4 + 1)) {
					var9 = true;
				}
			}

			return !var8 && !var7 && !var6 && !var9 && var5 >= 2 && var5 <= 5 ? true : (var5 == 2 && var8 && !var6 && !var7 ? true : (var5 == 3 && var9 && !var6 && !var7 ? true : (var5 == 4 && var6 && !var8 && !var9 ? true : var5 == 5 && var7 && !var8 && !var9)));
		}
	}

	public boolean canProvidePower() {
		return this.field_652_a;
	}

	public static boolean func_293_b(IBlockAccess var0, int var1, int var2, int var3) {
		int var4 = var0.getBlockId(var1, var2, var3);
		return var4 == Block.redstoneWire.blockID ? true : (var4 == 0 ? false : Block.blocksList[var4].canProvidePower());
	}
}
