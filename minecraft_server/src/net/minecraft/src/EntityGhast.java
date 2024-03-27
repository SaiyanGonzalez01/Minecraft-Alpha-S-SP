package net.minecraft.src;

public class EntityGhast extends EntityFlying implements IMobs {
	public int field_4099_a = 0;
	public double field_4098_b;
	public double field_4104_c;
	public double field_4102_d;
	private Entity field_4097_ai = null;
	private int field_4103_aj = 0;
	public int field_4101_e = 0;
	public int field_4100_f = 0;

	public EntityGhast(World var1) {
		super(var1);
		this.field_9119_aG = "/mob/ghast.png";
		this.setSize(4.0F, 4.0F);
		this.field_9079_ae = true;
	}

	protected void func_152_d_() {
		if(this.worldObj.monstersEnabled == 0) {
			this.setEntityDead();
		}

		this.field_4101_e = this.field_4100_f;
		double var1 = this.field_4098_b - this.posX;
		double var3 = this.field_4104_c - this.posY;
		double var5 = this.field_4102_d - this.posZ;
		double var7 = (double)MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5);
		if(var7 < 1.0D || var7 > 60.0D) {
			this.field_4098_b = this.posX + (double)((this.field_9064_W.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.field_4104_c = this.posY + (double)((this.field_9064_W.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.field_4102_d = this.posZ + (double)((this.field_9064_W.nextFloat() * 2.0F - 1.0F) * 16.0F);
		}

		if(this.field_4099_a-- <= 0) {
			this.field_4099_a += this.field_9064_W.nextInt(5) + 2;
			if(this.func_4046_a(this.field_4098_b, this.field_4104_c, this.field_4102_d, var7)) {
				this.motionX += var1 / var7 * 0.1D;
				this.motionY += var3 / var7 * 0.1D;
				this.motionZ += var5 / var7 * 0.1D;
			} else {
				this.field_4098_b = this.posX;
				this.field_4104_c = this.posY;
				this.field_4102_d = this.posZ;
			}
		}

		if(this.field_4097_ai != null && this.field_4097_ai.field_304_B) {
			this.field_4097_ai = null;
		}

		if(this.field_4097_ai == null || this.field_4103_aj-- <= 0) {
			this.field_4097_ai = this.worldObj.getClosestPlayerToEntity(this, 100.0D);
			if(this.field_4097_ai != null) {
				this.field_4103_aj = 20;
			}
		}

		double var9 = 64.0D;
		if(this.field_4097_ai != null && this.field_4097_ai.getDistanceSqToEntity(this) < var9 * var9) {
			double var11 = this.field_4097_ai.posX - this.posX;
			double var13 = this.field_4097_ai.boundingBox.minY + (double)(this.field_4097_ai.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
			double var15 = this.field_4097_ai.posZ - this.posZ;
			this.field_9095_az = this.rotationYaw = -((float)Math.atan2(var11, var15)) * 180.0F / (float)Math.PI;
			if(this.func_145_g(this.field_4097_ai)) {
				if(this.field_4100_f == 10) {
					this.worldObj.playSoundAtEntity(this, "mob.ghast.charge", this.getSoundVolume(), (this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.2F + 1.0F);
				}

				++this.field_4100_f;
				if(this.field_4100_f == 20) {
					this.worldObj.playSoundAtEntity(this, "mob.ghast.fireball", this.getSoundVolume(), (this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.2F + 1.0F);
					EntityFireball var17 = new EntityFireball(this.worldObj, this, var11, var13, var15);
					double var18 = 4.0D;
					Vec3D var20 = this.func_141_d(1.0F);
					var17.posX = this.posX + var20.xCoord * var18;
					var17.posY = this.posY + (double)(this.height / 2.0F) + 0.5D;
					var17.posZ = this.posZ + var20.zCoord * var18;
					this.worldObj.entityJoinedWorld(var17);
					this.field_4100_f = -40;
				}
			} else if(this.field_4100_f > 0) {
				--this.field_4100_f;
			}
		} else {
			this.field_9095_az = this.rotationYaw = -((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI;
			if(this.field_4100_f > 0) {
				--this.field_4100_f;
			}
		}

		this.field_9119_aG = this.field_4100_f > 10 ? "/mob/ghast_fire.png" : "/mob/ghast.png";
	}

	private boolean func_4046_a(double var1, double var3, double var5, double var7) {
		double var9 = (this.field_4098_b - this.posX) / var7;
		double var11 = (this.field_4104_c - this.posY) / var7;
		double var13 = (this.field_4102_d - this.posZ) / var7;
		AxisAlignedBB var15 = this.boundingBox.copy();

		for(int var16 = 1; (double)var16 < var7; ++var16) {
			var15.offset(var9, var11, var13);
			if(this.worldObj.getCollidingBoundingBoxes(this, var15).size() > 0) {
				return false;
			}
		}

		return true;
	}

	protected String getLivingSound() {
		return "mob.ghast.moan";
	}

	protected String getHurtSound() {
		return "mob.ghast.scream";
	}

	protected String getDeathSound() {
		return "mob.ghast.death";
	}

	protected int getDropItemId() {
		return Item.gunpowder.swiftedIndex;
	}

	protected float getSoundVolume() {
		return 10.0F;
	}

	public boolean getCanSpawnHere() {
		return this.field_9064_W.nextInt(20) == 0 && super.getCanSpawnHere() && this.worldObj.monstersEnabled > 0;
	}

	public int func_4045_i() {
		return 1;
	}
}
