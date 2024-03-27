package net.minecraft.src;

public class InventoryLargeChest implements IInventory {
	private String name;
	private IInventory upperChest;
	private IInventory lowerChest;

	public InventoryLargeChest(String var1, IInventory var2, IInventory var3) {
		this.name = var1;
		this.upperChest = var2;
		this.lowerChest = var3;
	}

	public int func_83_a() {
		return this.upperChest.func_83_a() + this.lowerChest.func_83_a();
	}

	public ItemStack getStackInSlot(int var1) {
		return var1 >= this.upperChest.func_83_a() ? this.lowerChest.getStackInSlot(var1 - this.upperChest.func_83_a()) : this.upperChest.getStackInSlot(var1);
	}
}
