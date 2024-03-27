package net.minecraft.src;

public class TileEntityFurnace extends TileEntity implements IInventory {
	private ItemStack[] field_489_e = new ItemStack[3];
	private int field_488_f = 0;
	private int field_487_g = 0;
	private int field_486_h = 0;

	public int func_83_a() {
		return this.field_489_e.length;
	}

	public ItemStack getStackInSlot(int var1) {
		return this.field_489_e[var1];
	}

	public void readFromNBT(NBTTagCompound var1) {
		super.readFromNBT(var1);
		NBTTagList var2 = var1.getTagList("Items");
		this.field_489_e = new ItemStack[this.func_83_a()];

		for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");
			if(var5 >= 0 && var5 < this.field_489_e.length) {
				this.field_489_e[var5] = new ItemStack(var4);
			}
		}

		this.field_488_f = var1.getShort("BurnTime");
		this.field_486_h = var1.getShort("CookTime");
		this.field_487_g = this.func_194_a(this.field_489_e[1]);
	}

	public void writeToNBT(NBTTagCompound var1) {
		super.writeToNBT(var1);
		var1.setShort("BurnTime", (short)this.field_488_f);
		var1.setShort("CookTime", (short)this.field_486_h);
		NBTTagList var2 = new NBTTagList();

		for(int var3 = 0; var3 < this.field_489_e.length; ++var3) {
			if(this.field_489_e[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				this.field_489_e[var3].writeToNBT(var4);
				var2.setTag(var4);
			}
		}

		var1.setTag("Items", var2);
	}

	public int func_190_d() {
		return 64;
	}

	public boolean func_191_e() {
		return this.field_488_f > 0;
	}

	public void updateEntity() {
		boolean var1 = this.field_488_f > 0;
		boolean var2 = false;
		if(this.field_488_f > 0) {
			--this.field_488_f;
		}

		if(!this.worldObj.multiplayerWorld) {
			if(this.field_488_f == 0 && this.func_193_g()) {
				this.field_487_g = this.field_488_f = this.func_194_a(this.field_489_e[1]);
				if(this.field_488_f > 0) {
					var2 = true;
					if(this.field_489_e[1] != null) {
						--this.field_489_e[1].stackSize;
						if(this.field_489_e[1].stackSize == 0) {
							this.field_489_e[1] = null;
						}
					}
				}
			}

			if(this.func_191_e() && this.func_193_g()) {
				++this.field_486_h;
				if(this.field_486_h == 200) {
					this.field_486_h = 0;
					this.func_189_f();
					var2 = true;
				}
			} else {
				this.field_486_h = 0;
			}

			if(var1 != this.field_488_f > 0) {
				var2 = true;
				BlockFurnace.func_295_a(this.field_488_f > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}

		if(var2) {
			this.func_183_c();
		}

	}

	private boolean func_193_g() {
		if(this.field_489_e[0] == null) {
			return false;
		} else {
			int var1 = this.func_192_b(this.field_489_e[0].getItem().swiftedIndex);
			return var1 < 0 ? false : (this.field_489_e[2] == null ? true : (this.field_489_e[2].itemID != var1 ? false : (this.field_489_e[2].stackSize < this.func_190_d() && this.field_489_e[2].stackSize < this.field_489_e[2].getMaxStackSize() ? true : this.field_489_e[2].stackSize < Item.itemsList[var1].getItemStackLimit())));
		}
	}

	public void func_189_f() {
		if(this.func_193_g()) {
			int var1 = this.func_192_b(this.field_489_e[0].getItem().swiftedIndex);
			if(this.field_489_e[2] == null) {
				this.field_489_e[2] = new ItemStack(var1, 1);
			} else if(this.field_489_e[2].itemID == var1) {
				++this.field_489_e[2].stackSize;
			}

			--this.field_489_e[0].stackSize;
			if(this.field_489_e[0].stackSize <= 0) {
				this.field_489_e[0] = null;
			}

		}
	}

	private int func_192_b(int var1) {
		return var1 == Block.oreIron.blockID ? Item.ingotIron.swiftedIndex : (var1 == Block.oreGold.blockID ? Item.ingotGold.swiftedIndex : (var1 == Block.oreDiamond.blockID ? Item.diamond.swiftedIndex : (var1 == Block.sand.blockID ? Block.glass.blockID : (var1 == Item.porkRaw.swiftedIndex ? Item.porkCooked.swiftedIndex : (var1 == Item.fishRaw.swiftedIndex ? Item.fishCooked.swiftedIndex : (var1 == Block.cobblestone.blockID ? Block.stone.blockID : (var1 == Item.clay.swiftedIndex ? Item.brick.swiftedIndex : -1)))))));
	}

	private int func_194_a(ItemStack var1) {
		if(var1 == null) {
			return 0;
		} else {
			int var2 = var1.getItem().swiftedIndex;
			return var2 < 256 && Block.blocksList[var2].blockMaterial == Material.wood ? 300 : (var2 == Item.stick.swiftedIndex ? 100 : (var2 == Item.coal.swiftedIndex ? 1600 : (var2 == Item.bucketLava.swiftedIndex ? 20000 : 0)));
		}
	}
}
