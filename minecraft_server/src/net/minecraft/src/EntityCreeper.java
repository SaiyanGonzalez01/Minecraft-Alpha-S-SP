package net.minecraft.src;

public class EntityCreeper extends EntityMobs {
	int field_406_a;
	int field_405_b;
	int field_408_ad = 30;
	int field_407_ae = -1;
	int field_12011_e = -1;

	public EntityCreeper(World var1) {
		super(var1);
		this.field_9119_aG = "/mob/creeper.png";
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
	}

	public void onUpdate() {
		this.field_405_b = this.field_406_a;
		if(this.worldObj.multiplayerWorld) {
			this.field_406_a += this.field_407_ae;
			if(this.field_406_a < 0) {
				this.field_406_a = 0;
			}

			if(this.field_406_a >= this.field_408_ad) {
				this.field_406_a = this.field_408_ad;
			}
		}

		super.onUpdate();
	}

	protected void func_152_d_() {
		if(this.field_12011_e != this.field_407_ae) {
			this.field_12011_e = this.field_407_ae;
			if(this.field_407_ae > 0) {
				this.worldObj.func_9206_a(this, (byte)4);
			} else {
				this.worldObj.func_9206_a(this, (byte)5);
			}
		}

		this.field_405_b = this.field_406_a;
		if(this.worldObj.multiplayerWorld) {
			super.func_152_d_();
		} else {
			if(this.field_406_a > 0 && this.field_407_ae < 0) {
				--this.field_406_a;
			}

			if(this.field_407_ae >= 0) {
				this.field_407_ae = 2;
			}

			super.func_152_d_();
			if(this.field_407_ae != 1) {
				this.field_407_ae = -1;
			}
		}

	}

	protected String getHurtSound() {
		return "mob.creeper";
	}

	protected String getDeathSound() {
		return "mob.creeperdeath";
	}

	public void onDeath(Entity var1) {
		super.onDeath(var1);
		if(var1 instanceof EntitySkeleton) {
			this.dropItem(Item.record13.swiftedIndex + this.field_9064_W.nextInt(2), 1);
		}

	}

	protected void func_157_a(Entity var1, float var2) {
		if(this.field_407_ae <= 0 && var2 < 3.0F || this.field_407_ae > 0 && var2 < 7.0F) {
			if(this.field_406_a == 0) {
				this.worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 0.5F);
			}

			this.field_407_ae = 1;
			++this.field_406_a;
			if(this.field_406_a == this.field_408_ad) {
				this.worldObj.func_12013_a(this, this.posX, this.posY, this.posZ, 3.0F);
				this.setEntityDead();
			}

			this.field_387_ah = true;
		}

	}

	protected int getDropItemId() {
		return Item.gunpowder.swiftedIndex;
	}
}
