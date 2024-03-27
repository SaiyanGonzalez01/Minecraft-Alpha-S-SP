package net.minecraft.src;

public class TileEntityChest extends TileEntity implements IInventory {
	private ItemStack[] field_494_e = new ItemStack[36];

	public int func_83_a() {
		return 27;
	}

	public ItemStack getStackInSlot(int var1) {
		return this.field_494_e[var1];
	}

	public void func_197_a(int var1, ItemStack var2) {
		this.field_494_e[var1] = var2;
		if(var2 != null && var2.stackSize > this.func_198_d()) {
			var2.stackSize = this.func_198_d();
		}

		this.func_183_c();
	}

	public void readFromNBT(NBTTagCompound var1) {
		super.readFromNBT(var1);
		NBTTagList var2 = var1.getTagList("Items");
		this.field_494_e = new ItemStack[this.func_83_a()];

		for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			int var5 = var4.getByte("Slot") & 255;
			if(var5 >= 0 && var5 < this.field_494_e.length) {
				this.field_494_e[var5] = new ItemStack(var4);
			}
		}

	}

	public void writeToNBT(NBTTagCompound var1) {
		super.writeToNBT(var1);
		NBTTagList var2 = new NBTTagList();

		for(int var3 = 0; var3 < this.field_494_e.length; ++var3) {
			if(this.field_494_e[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				this.field_494_e[var3].writeToNBT(var4);
				var2.setTag(var4);
			}
		}

		var1.setTag("Items", var2);
	}

	public int func_198_d() {
		return 64;
	}
}
