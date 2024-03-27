package net.minecraft.src;

public abstract class BlockContainer extends Block {
	protected BlockContainer(int var1, Material var2) {
		super(var1, var2);
		isBlockContainer[var1] = true;
	}

	protected BlockContainer(int var1, int var2, Material var3) {
		super(var1, var2, var3);
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		super.onBlockAdded(var1, var2, var3, var4);
		var1.func_473_a(var2, var3, var4, this.func_294_a_());
	}

	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
		super.onBlockRemoval(var1, var2, var3, var4);
		var1.func_513_l(var2, var3, var4);
	}

	protected abstract TileEntity func_294_a_();
}
