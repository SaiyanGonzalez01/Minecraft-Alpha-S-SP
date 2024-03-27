package net.minecraft.src;

public class BlockPumpkin extends Block {
	private boolean field_4086_a;

	protected BlockPumpkin(int var1, int var2, boolean var3) {
		super(var1, Material.field_4213_w);
		this.blockIndexInTexture = var2;
		this.setTickOnLoad(true);
		this.field_4086_a = var3;
	}

	public int getBlockTextureFromSide(int var1) {
		return var1 == 1 ? this.blockIndexInTexture : (var1 == 0 ? this.blockIndexInTexture : (var1 == 3 ? this.blockIndexInTexture + 1 + 16 : this.blockIndexInTexture + 16));
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		super.onBlockAdded(var1, var2, var3, var4);
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockId(var2, var3, var4);
		return (var5 == 0 || Block.blocksList[var5].blockMaterial.getIsLiquid()) && var1.doesBlockAllowAttachment(var2, var3 - 1, var4);
	}

	public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5) {
		int var6 = MathHelper.floor_double((double)(var5.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
	}
}
