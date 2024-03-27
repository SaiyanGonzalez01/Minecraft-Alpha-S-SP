package net.minecraft.src;

public class ItemInWorldManager {
	private World field_674_b;
	public EntityPlayer field_675_a;
	private float field_673_c;
	private float field_672_d = 0.0F;
	private int field_671_e = 0;
	private float field_670_f = 0.0F;
	private int field_669_g;
	private int field_668_h;
	private int field_667_i;

	public ItemInWorldManager(World var1) {
		this.field_674_b = var1;
	}

	public void func_324_a(int var1, int var2, int var3) {
		int var4 = this.field_674_b.getBlockId(var1, var2, var3);
		if(var4 > 0 && this.field_672_d == 0.0F) {
			Block.blocksList[var4].onBlockClicked(this.field_674_b, var1, var2, var3, this.field_675_a);
		}

		if(var4 > 0 && Block.blocksList[var4].func_254_a(this.field_675_a) >= 1.0F) {
			this.func_325_c(var1, var2, var3);
		}

	}

	public void func_328_a() {
		this.field_672_d = 0.0F;
		this.field_671_e = 0;
	}

	public void func_326_a(int var1, int var2, int var3, int var4) {
		if(this.field_671_e > 0) {
			--this.field_671_e;
		} else {
			if(var1 == this.field_669_g && var2 == this.field_668_h && var3 == this.field_667_i) {
				int var5 = this.field_674_b.getBlockId(var1, var2, var3);
				if(var5 == 0) {
					return;
				}

				Block var6 = Block.blocksList[var5];
				this.field_672_d += var6.func_254_a(this.field_675_a);
				++this.field_670_f;
				if(this.field_672_d >= 1.0F) {
					this.func_325_c(var1, var2, var3);
					this.field_672_d = 0.0F;
					this.field_673_c = 0.0F;
					this.field_670_f = 0.0F;
					this.field_671_e = 5;
				}
			} else {
				this.field_672_d = 0.0F;
				this.field_673_c = 0.0F;
				this.field_670_f = 0.0F;
				this.field_669_g = var1;
				this.field_668_h = var2;
				this.field_667_i = var3;
			}

		}
	}

	public boolean func_323_b(int var1, int var2, int var3) {
		Block var4 = Block.blocksList[this.field_674_b.getBlockId(var1, var2, var3)];
		int var5 = this.field_674_b.getBlockMetadata(var1, var2, var3);
		boolean var6 = this.field_674_b.setBlockWithNotify(var1, var2, var3, 0);
		if(var4 != null && var6) {
			var4.onBlockDestroyedByPlayer(this.field_674_b, var1, var2, var3, var5);
		}

		return var6;
	}

	public boolean func_325_c(int var1, int var2, int var3) {
		int var4 = this.field_674_b.getBlockId(var1, var2, var3);
		int var5 = this.field_674_b.getBlockMetadata(var1, var2, var3);
		boolean var6 = this.func_323_b(var1, var2, var3);
		ItemStack var7 = this.field_675_a.func_172_B();
		if(var7 != null) {
			var7.hitBlock(var4, var1, var2, var3);
			if(var7.stackSize == 0) {
				var7.func_577_a(this.field_675_a);
				this.field_675_a.func_164_C();
			}
		}

		if(var6 && this.field_675_a.func_167_b(Block.blocksList[var4])) {
			Block.blocksList[var4].func_12007_g(this.field_674_b, var1, var2, var3, var5);
		}

		return var6;
	}

	public boolean func_6154_a(EntityPlayer var1, World var2, ItemStack var3) {
		int var4 = var3.stackSize;
		ItemStack var5 = var3.useItemRightClick(var2, var1);
		if(var5 != var3 || var5 != null && var5.stackSize != var4) {
			var1.inventory.mainInventory[var1.inventory.currentItem] = var5;
			if(var5.stackSize == 0) {
				var1.inventory.mainInventory[var1.inventory.currentItem] = null;
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean func_327_a(EntityPlayer var1, World var2, ItemStack var3, int var4, int var5, int var6, int var7) {
		int var8 = var2.getBlockId(var4, var5, var6);
		return var8 > 0 && Block.blocksList[var8].blockActivated(var2, var4, var5, var6, var1) ? true : (var3 == null ? false : var3.useItem(var1, var2, var4, var5, var6, var7));
	}
}
