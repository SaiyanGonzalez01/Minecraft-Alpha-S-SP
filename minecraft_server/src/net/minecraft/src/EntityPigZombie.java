package net.minecraft.src;

import java.util.List;

public class EntityPigZombie extends EntityZombie {
	private int field_4106_a = 0;
	private int field_4105_b = 0;
	private static final ItemStack field_4107_c = new ItemStack(Item.swordGold, 1);

	public EntityPigZombie(World var1) {
		super(var1);
		this.field_9119_aG = "/mob/pigzombie.png";
		this.field_9126_bt = 0.5F;
		this.field_404_af = 5;
		this.field_9079_ae = true;
	}

	public void onUpdate() {
		this.field_9126_bt = this.field_389_ag != null ? 0.95F : 0.5F;
		if(this.field_4105_b > 0 && --this.field_4105_b == 0) {
			this.worldObj.playSoundAtEntity(this, "mob.zombiepig.zpigangry", this.getSoundVolume() * 2.0F, ((this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.2F + 1.0F) * 1.8F);
		}

		super.onUpdate();
	}

	public boolean getCanSpawnHere() {
		return this.worldObj.monstersEnabled > 0 && this.worldObj.func_522_a(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.getIsAnyLiquid(this.boundingBox);
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
		var1.setShort("Anger", (short)this.field_4106_a);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
		this.field_4106_a = var1.getShort("Anger");
	}

	protected Entity func_158_i() {
		return this.field_4106_a == 0 ? null : super.func_158_i();
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
	}

	public boolean attackEntity(Entity var1, int var2) {
		if(var1 instanceof EntityPlayer) {
			List var3 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expands(32.0D, 32.0D, 32.0D));

			for(int var4 = 0; var4 < var3.size(); ++var4) {
				Entity var5 = (Entity)var3.get(var4);
				if(var5 instanceof EntityPigZombie) {
					EntityPigZombie var6 = (EntityPigZombie)var5;
					var6.func_4047_h(var1);
				}
			}

			this.func_4047_h(var1);
		}

		return super.attackEntity(var1, var2);
	}

	private void func_4047_h(Entity var1) {
		this.field_389_ag = var1;
		this.field_4106_a = 400 + this.field_9064_W.nextInt(400);
		this.field_4105_b = this.field_9064_W.nextInt(40);
	}

	protected String getLivingSound() {
		return "mob.zombiepig.zpig";
	}

	protected String getHurtSound() {
		return "mob.zombiepig.zpighurt";
	}

	protected String getDeathSound() {
		return "mob.zombiepig.zpigdeath";
	}

	protected int getDropItemId() {
		return Item.porkCooked.swiftedIndex;
	}
}
