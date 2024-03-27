package net.minecraft.src;

public class ItemFishingRod extends Item {
	public ItemFishingRod(int var1) {
		super(var1);
		this.maxDamage = 64;
	}

	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
		if(var3.field_6124_at != null) {
			int var4 = var3.field_6124_at.func_6143_c();
			var1.damageItem(var4);
			var3.func_168_z();
		} else {
			var2.playSoundAtEntity(var3, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
			if(!var2.multiplayerWorld) {
				var2.entityJoinedWorld(new EntityFish(var2, var3));
			}

			var3.func_168_z();
		}

		return var1;
	}
}
