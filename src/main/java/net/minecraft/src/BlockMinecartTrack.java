package net.minecraft.src;

import net.PeytonPlayz585.EaglercraftRandom;

public class BlockMinecartTrack extends Block {
	protected BlockMinecartTrack(int var1, int var2) {
		super(var1, var2, Material.circuits);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F / 16.0F, 1.0F);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return null;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public MovingObjectPosition collisionRayTrace(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6) {
		this.setBlockBoundsBasedOnState(var1, var2, var3, var4);
		return super.collisionRayTrace(var1, var2, var3, var4, var5, var6);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockMetadata(var2, var3, var4);
		if(var5 >= 2 && var5 <= 5) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 10.0F / 16.0F, 1.0F);
		} else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F / 16.0F, 1.0F);
		}

	}

	public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
		return var2 >= 6 ? this.blockIndexInTexture - 16 : this.blockIndexInTexture;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 9;
	}

	public int quantityDropped(EaglercraftRandom var1) {
		return 1;
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return var1.isBlockOpaqueCube(var2, var3 - 1, var4);
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		if(!var1.multiplayerWorld) {
			var1.setBlockMetadataWithNotify(var2, var3, var4, 15);
			this.func_4031_h(var1, var2, var3, var4);
		}

	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		if(!var1.multiplayerWorld) {
			int var6 = var1.getBlockMetadata(var2, var3, var4);
			boolean var7 = false;
			if(!var1.isBlockOpaqueCube(var2, var3 - 1, var4)) {
				var7 = true;
			}

			if(var6 == 2 && !var1.isBlockOpaqueCube(var2 + 1, var3, var4)) {
				var7 = true;
			}

			if(var6 == 3 && !var1.isBlockOpaqueCube(var2 - 1, var3, var4)) {
				var7 = true;
			}

			if(var6 == 4 && !var1.isBlockOpaqueCube(var2, var3, var4 - 1)) {
				var7 = true;
			}

			if(var6 == 5 && !var1.isBlockOpaqueCube(var2, var3, var4 + 1)) {
				var7 = true;
			}

			if(var7) {
				this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
				var1.setBlockWithNotify(var2, var3, var4, 0);
			} else if(var5 > 0 && Block.blocksList[var5].canProvidePower() && MinecartTrackLogic.func_791_a(new MinecartTrackLogic(this, var1, var2, var3, var4)) == 3) {
				this.func_4031_h(var1, var2, var3, var4);
			}

		}
	}

	private void func_4031_h(World var1, int var2, int var3, int var4) {
		if(!var1.multiplayerWorld) {
			(new MinecartTrackLogic(this, var1, var2, var3, var4)).func_792_a(var1.isBlockIndirectlyGettingPowered(var2, var3, var4));
		}
	}
}
