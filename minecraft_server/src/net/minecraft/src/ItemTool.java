package net.minecraft.src;

public class ItemTool extends Item {
	private Block[] blocksEffectiveAgainst;
	private float field_264_aY = 4.0F;
	private int field_263_aZ;
	protected int field_262_a;

	public ItemTool(int var1, int var2, int var3, Block[] var4) {
		super(var1);
		this.field_262_a = var3;
		this.blocksEffectiveAgainst = var4;
		this.maxStackSize = 1;
		this.maxDamage = 32 << var3;
		if(var3 == 3) {
			this.maxDamage *= 4;
		}

		this.field_264_aY = (float)((var3 + 1) * 2);
		this.field_263_aZ = var2 + var3;
	}

	public float getStrVsBlock(ItemStack var1, Block var2) {
		for(int var3 = 0; var3 < this.blocksEffectiveAgainst.length; ++var3) {
			if(this.blocksEffectiveAgainst[var3] == var2) {
				return this.field_264_aY;
			}
		}

		return 1.0F;
	}

	public void func_9201_a(ItemStack var1, EntityLiving var2) {
		var1.damageItem(2);
	}

	public void hitBlock(ItemStack var1, int var2, int var3, int var4, int var5) {
		var1.damageItem(1);
	}

	public int func_9203_a(Entity var1) {
		return this.field_263_aZ;
	}
}
