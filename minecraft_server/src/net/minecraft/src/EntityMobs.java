package net.minecraft.src;

public class EntityMobs extends EntityCreature implements IMobs {
	protected int field_404_af = 2;

	public EntityMobs(World var1) {
		super(var1);
		this.field_9109_aQ = 20;
	}

	public void onLivingUpdate() {
		float var1 = this.getEntityBrightness(1.0F);
		if(var1 > 0.5F) {
			this.field_9132_bn += 2;
		}

		super.onLivingUpdate();
	}

	public void onUpdate() {
		super.onUpdate();
		if(this.worldObj.monstersEnabled == 0) {
			this.setEntityDead();
		}

	}

	protected Entity func_158_i() {
		EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 16.0D);
		return var1 != null && this.func_145_g(var1) ? var1 : null;
	}

	public boolean attackEntity(Entity var1, int var2) {
		if(super.attackEntity(var1, var2)) {
			if(this.field_328_f != var1 && this.field_327_g != var1) {
				if(var1 != this) {
					this.field_389_ag = var1;
				}

				return true;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	protected void func_157_a(Entity var1, float var2) {
		if((double)var2 < 2.5D && var1.boundingBox.maxY > this.boundingBox.minY && var1.boundingBox.minY < this.boundingBox.maxY) {
			this.field_9103_aW = 20;
			var1.attackEntity(this, this.field_404_af);
		}

	}

	protected float func_159_a(int var1, int var2, int var3) {
		return 0.5F - this.worldObj.getLightBrightness(var1, var2, var3);
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
	}

	public boolean getCanSpawnHere() {
		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.boundingBox.minY);
		int var3 = MathHelper.floor_double(this.posZ);
		if(this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, var1, var2, var3) > this.field_9064_W.nextInt(32)) {
			return false;
		} else {
			int var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
			return var4 <= this.field_9064_W.nextInt(8) && super.getCanSpawnHere();
		}
	}
}
