package net.minecraft.src;

import java.util.List;

public class EntityBoat extends Entity {
	public int field_9178_a;
	public int field_9177_b;
	public int field_436_ad;
	private int field_9176_d;
	private double field_9174_e;
	private double field_9172_f;
	private double field_9175_aj;
	private double field_9173_ak;
	private double field_9171_al;

	public EntityBoat(World var1) {
		super(var1);
		this.field_9178_a = 0;
		this.field_9177_b = 0;
		this.field_436_ad = 1;
		this.field_329_e = true;
		this.setSize(1.5F, 0.6F);
		this.yOffset = this.height / 2.0F;
		this.entityWalks = false;
	}

	public AxisAlignedBB func_89_d(Entity var1) {
		return var1.boundingBox;
	}

	public AxisAlignedBB func_93_n() {
		return this.boundingBox;
	}

	public boolean func_124_r() {
		return true;
	}

	public EntityBoat(World var1, double var2, double var4, double var6) {
		this(var1);
		this.setPosition(var2, var4 + (double)this.yOffset, var6);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = var2;
		this.prevPosY = var4;
		this.prevPosZ = var6;
	}

	public double func_130_h() {
		return (double)this.height * 0.0D - (double)0.3F;
	}

	public boolean attackEntity(Entity var1, int var2) {
		if(!this.worldObj.multiplayerWorld && !this.field_304_B) {
			this.field_436_ad = -this.field_436_ad;
			this.field_9177_b = 10;
			this.field_9178_a += var2 * 10;
			this.func_9060_u();
			if(this.field_9178_a > 40) {
				int var3;
				for(var3 = 0; var3 < 3; ++var3) {
					this.dropItemWithOffset(Block.planks.blockID, 1, 0.0F);
				}

				for(var3 = 0; var3 < 2; ++var3) {
					this.dropItemWithOffset(Item.stick.swiftedIndex, 1, 0.0F);
				}

				this.setEntityDead();
			}

			return true;
		} else {
			return true;
		}
	}

	public boolean func_129_c_() {
		return !this.field_304_B;
	}

	public void onUpdate() {
		super.onUpdate();
		if(this.field_9177_b > 0) {
			--this.field_9177_b;
		}

		if(this.field_9178_a > 0) {
			--this.field_9178_a;
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		byte var1 = 5;
		double var2 = 0.0D;

		for(int var4 = 0; var4 < var1; ++var4) {
			double var5 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var4 + 0) / (double)var1 - 0.125D;
			double var7 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var4 + 1) / (double)var1 - 0.125D;
			AxisAlignedBB var9 = AxisAlignedBB.getBoundingBoxFromPool(this.boundingBox.minX, var5, this.boundingBox.minZ, this.boundingBox.maxX, var7, this.boundingBox.maxZ);
			if(this.worldObj.func_524_b(var9, Material.water)) {
				var2 += 1.0D / (double)var1;
			}
		}

		double var6;
		double var8;
		double var10;
		double var23;
		if(this.worldObj.multiplayerWorld) {
			if(this.field_9176_d > 0) {
				var23 = this.posX + (this.field_9174_e - this.posX) / (double)this.field_9176_d;
				var6 = this.posY + (this.field_9172_f - this.posY) / (double)this.field_9176_d;
				var8 = this.posZ + (this.field_9175_aj - this.posZ) / (double)this.field_9176_d;

				for(var10 = this.field_9173_ak - (double)this.rotationYaw; var10 < -180.0D; var10 += 360.0D) {
				}

				while(var10 >= 180.0D) {
					var10 -= 360.0D;
				}

				this.rotationYaw = (float)((double)this.rotationYaw + var10 / (double)this.field_9176_d);
				this.rotationPitch = (float)((double)this.rotationPitch + (this.field_9171_al - (double)this.rotationPitch) / (double)this.field_9176_d);
				--this.field_9176_d;
				this.setPosition(var23, var6, var8);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			} else {
				var23 = this.posX + this.motionX;
				var6 = this.posY + this.motionY;
				var8 = this.posZ + this.motionZ;
				this.setPosition(var23, var6, var8);
				if(this.onGround) {
					this.motionX *= 0.5D;
					this.motionY *= 0.5D;
					this.motionZ *= 0.5D;
				}

				this.motionX *= (double)0.99F;
				this.motionY *= (double)0.95F;
				this.motionZ *= (double)0.99F;
			}

		} else {
			var23 = var2 * 2.0D - 1.0D;
			this.motionY += (double)0.04F * var23;
			if(this.field_328_f != null) {
				this.motionX += this.field_328_f.motionX * 0.2D;
				this.motionZ += this.field_328_f.motionZ * 0.2D;
			}

			var6 = 0.4D;
			if(this.motionX < -var6) {
				this.motionX = -var6;
			}

			if(this.motionX > var6) {
				this.motionX = var6;
			}

			if(this.motionZ < -var6) {
				this.motionZ = -var6;
			}

			if(this.motionZ > var6) {
				this.motionZ = var6;
			}

			if(this.onGround) {
				this.motionX *= 0.5D;
				this.motionY *= 0.5D;
				this.motionZ *= 0.5D;
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			var8 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			double var12;
			if(var8 > 0.15D) {
				var10 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D);
				var12 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D);

				for(int var14 = 0; (double)var14 < 1.0D + var8 * 60.0D; ++var14) {
					double var15 = (double)(this.field_9064_W.nextFloat() * 2.0F - 1.0F);
					double var17 = (double)(this.field_9064_W.nextInt(2) * 2 - 1) * 0.7D;
					double var19;
					double var21;
					if(this.field_9064_W.nextBoolean()) {
						var19 = this.posX - var10 * var15 * 0.8D + var12 * var17;
						var21 = this.posZ - var12 * var15 * 0.8D - var10 * var17;
						this.worldObj.spawnParticle("splash", var19, this.posY - 0.125D, var21, this.motionX, this.motionY, this.motionZ);
					} else {
						var19 = this.posX + var10 + var12 * var15 * 0.7D;
						var21 = this.posZ + var12 - var10 * var15 * 0.7D;
						this.worldObj.spawnParticle("splash", var19, this.posY - 0.125D, var21, this.motionX, this.motionY, this.motionZ);
					}
				}
			}

			if(this.field_9084_B && var8 > 0.15D) {
				if(!this.worldObj.multiplayerWorld) {
					this.setEntityDead();

					int var24;
					for(var24 = 0; var24 < 3; ++var24) {
						this.dropItemWithOffset(Block.planks.blockID, 1, 0.0F);
					}

					for(var24 = 0; var24 < 2; ++var24) {
						this.dropItemWithOffset(Item.stick.swiftedIndex, 1, 0.0F);
					}
				}
			} else {
				this.motionX *= (double)0.99F;
				this.motionY *= (double)0.95F;
				this.motionZ *= (double)0.99F;
			}

			this.rotationPitch = 0.0F;
			var10 = (double)this.rotationYaw;
			var12 = this.prevPosX - this.posX;
			double var25 = this.prevPosZ - this.posZ;
			if(var12 * var12 + var25 * var25 > 0.001D) {
				var10 = (double)((float)(Math.atan2(var25, var12) * 180.0D / Math.PI));
			}

			double var16;
			for(var16 = var10 - (double)this.rotationYaw; var16 >= 180.0D; var16 -= 360.0D) {
			}

			while(var16 < -180.0D) {
				var16 += 360.0D;
			}

			if(var16 > 20.0D) {
				var16 = 20.0D;
			}

			if(var16 < -20.0D) {
				var16 = -20.0D;
			}

			this.rotationYaw = (float)((double)this.rotationYaw + var16);
			this.setRotation(this.rotationYaw, this.rotationPitch);
			List var18 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expands((double)0.2F, 0.0D, (double)0.2F));
			if(var18 != null && var18.size() > 0) {
				for(int var26 = 0; var26 < var18.size(); ++var26) {
					Entity var20 = (Entity)var18.get(var26);
					if(var20 != this.field_328_f && var20.func_124_r() && var20 instanceof EntityBoat) {
						var20.applyEntityCollision(this);
					}
				}
			}

			if(this.field_328_f != null && this.field_328_f.field_304_B) {
				this.field_328_f = null;
			}

		}
	}

	public void func_127_w() {
		if(this.field_328_f != null) {
			double var1 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			double var3 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			this.field_328_f.setPosition(this.posX + var1, this.posY + this.func_130_h() + this.field_328_f.func_117_x(), this.posZ + var3);
		}
	}

	protected void writeEntityToNBT(NBTTagCompound var1) {
	}

	protected void readEntityFromNBT(NBTTagCompound var1) {
	}

	public boolean func_6092_a(EntityPlayer var1) {
		if(this.field_328_f != null && this.field_328_f instanceof EntityPlayer && this.field_328_f != var1) {
			return true;
		} else {
			if(!this.worldObj.multiplayerWorld) {
				var1.func_6094_e(this);
			}

			return true;
		}
	}
}
