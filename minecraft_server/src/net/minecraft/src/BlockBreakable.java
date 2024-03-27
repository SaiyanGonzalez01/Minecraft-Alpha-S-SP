package net.minecraft.src;

public class BlockBreakable extends Block {
	private boolean field_6084_a;

	protected BlockBreakable(int var1, int var2, Material var3, boolean var4) {
		super(var1, var2, var3);
		this.field_6084_a = var4;
	}

	public boolean allowsAttachment() {
		return false;
	}

	public boolean isSideInsideCoordinate(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		int var6 = var1.getBlockId(var2, var3, var4);
		return !this.field_6084_a && var6 == this.blockID ? false : super.isSideInsideCoordinate(var1, var2, var3, var4, var5);
	}
}
