package net.minecraft.src;

import java.util.List;
import java.util.Random;

public abstract class Entity {
	private static int field_384_a = 0;
	public int field_331_c = field_384_a++;
	public double field_9094_h = 1.0D;
	public boolean field_329_e = false;
	public Entity field_328_f;
	public Entity field_327_g;
	public World worldObj;
	public double prevPosX;
	public double prevPosY;
	public double prevPosZ;
	public double posX;
	public double posY;
	public double posZ;
	public double motionX;
	public double motionY;
	public double motionZ;
	public float rotationYaw;
	public float rotationPitch;
	public float prevRotationYaw;
	public float prevRotationPitch;
	public final AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
	public boolean onGround = false;
	public boolean field_9084_B;
	public boolean field_9082_C;
	public boolean field_9080_D = false;
	public boolean field_9078_E = false;
	public boolean field_9077_F = true;
	public boolean field_304_B = false;
	public float yOffset = 0.0F;
	public float width = 0.6F;
	public float height = 1.8F;
	public float field_9075_K = 0.0F;
	public float field_9074_L = 0.0F;
	protected boolean entityWalks = true;
	protected float fallDistance = 0.0F;
	private int field_6151_b = 1;
	public double field_9071_O;
	public double field_9070_P;
	public double field_9069_Q;
	public float field_9068_R = 0.0F;
	public float field_9067_S = 0.0F;
	public boolean field_9066_T = false;
	public float field_286_P = 0.0F;
	public boolean field_9065_V = false;
	protected Random field_9064_W = new Random();
	public int field_9063_X = 0;
	public int field_9062_Y = 1;
	public int field_9061_Z = 0;
	protected int field_9087_aa = 300;
	protected boolean field_9085_ab = false;
	public int field_9083_ac = 0;
	public int air = 300;
	private boolean field_4131_c = true;
	protected boolean field_9079_ae = false;
	private double field_4130_d;
	private double field_4128_e;
	public boolean field_276_Z = false;
	public int field_307_aa;
	public int field_305_ab;
	public int field_303_ac;

	public Entity(World var1) {
		this.worldObj = var1;
		this.setPosition(0.0D, 0.0D, 0.0D);
	}

	public boolean equals(Object var1) {
		return var1 instanceof Entity ? ((Entity)var1).field_331_c == this.field_331_c : false;
	}

	public int hashCode() {
		return this.field_331_c;
	}

	public void setEntityDead() {
		this.field_304_B = true;
	}

	protected void setSize(float var1, float var2) {
		this.width = var1;
		this.height = var2;
	}

	protected void setRotation(float var1, float var2) {
		this.rotationYaw = var1;
		this.rotationPitch = var2;
	}

	public void setPosition(double var1, double var3, double var5) {
		this.posX = var1;
		this.posY = var3;
		this.posZ = var5;
		float var7 = this.width / 2.0F;
		float var8 = this.height;
		this.boundingBox.setBounds(var1 - (double)var7, var3 - (double)this.yOffset + (double)this.field_9068_R, var5 - (double)var7, var1 + (double)var7, var3 - (double)this.yOffset + (double)this.field_9068_R + (double)var8, var5 + (double)var7);
	}

	public void onUpdate() {
		this.func_84_k();
	}

	public void func_84_k() {
		if(this.field_327_g != null && this.field_327_g.field_304_B) {
			this.field_327_g = null;
		}

		++this.field_9063_X;
		this.field_9075_K = this.field_9074_L;
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.prevRotationPitch = this.rotationPitch;
		this.prevRotationYaw = this.rotationYaw;
		if(this.handleWaterMovement()) {
			if(!this.field_9085_ab && !this.field_4131_c) {
				float var1 = MathHelper.sqrt_double(this.motionX * this.motionX * (double)0.2F + this.motionY * this.motionY + this.motionZ * this.motionZ * (double)0.2F) * 0.2F;
				if(var1 > 1.0F) {
					var1 = 1.0F;
				}

				this.worldObj.playSoundAtEntity(this, "random.splash", var1, 1.0F + (this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.4F);
				float var2 = (float)MathHelper.floor_double(this.boundingBox.minY);

				int var3;
				float var4;
				float var5;
				for(var3 = 0; (float)var3 < 1.0F + this.width * 20.0F; ++var3) {
					var4 = (this.field_9064_W.nextFloat() * 2.0F - 1.0F) * this.width;
					var5 = (this.field_9064_W.nextFloat() * 2.0F - 1.0F) * this.width;
					this.worldObj.spawnParticle("bubble", this.posX + (double)var4, (double)(var2 + 1.0F), this.posZ + (double)var5, this.motionX, this.motionY - (double)(this.field_9064_W.nextFloat() * 0.2F), this.motionZ);
				}

				for(var3 = 0; (float)var3 < 1.0F + this.width * 20.0F; ++var3) {
					var4 = (this.field_9064_W.nextFloat() * 2.0F - 1.0F) * this.width;
					var5 = (this.field_9064_W.nextFloat() * 2.0F - 1.0F) * this.width;
					this.worldObj.spawnParticle("splash", this.posX + (double)var4, (double)(var2 + 1.0F), this.posZ + (double)var5, this.motionX, this.motionY, this.motionZ);
				}
			}

			this.fallDistance = 0.0F;
			this.field_9085_ab = true;
			this.field_9061_Z = 0;
		} else {
			this.field_9085_ab = false;
		}

		if(this.worldObj.multiplayerWorld) {
			this.field_9061_Z = 0;
		} else if(this.field_9061_Z > 0) {
			if(this.field_9079_ae) {
				this.field_9061_Z -= 4;
				if(this.field_9061_Z < 0) {
					this.field_9061_Z = 0;
				}
			} else {
				if(this.field_9061_Z % 20 == 0) {
					this.attackEntity((Entity)null, 1);
				}

				--this.field_9061_Z;
			}
		}

		if(this.func_112_q()) {
			this.func_4040_n();
		}

		if(this.posY < -64.0D) {
			this.func_4043_o();
		}

		this.field_4131_c = false;
	}

	protected void func_4040_n() {
		if(!this.field_9079_ae) {
			this.attackEntity((Entity)null, 4);
			this.field_9061_Z = 600;
		}

	}

	protected void func_4043_o() {
		this.setEntityDead();
	}

	public boolean func_133_b(double var1, double var3, double var5) {
		AxisAlignedBB var7 = this.boundingBox.getOffsetBoundingBox(var1, var3, var5);
		List var8 = this.worldObj.getCollidingBoundingBoxes(this, var7);
		return var8.size() > 0 ? false : !this.worldObj.getIsAnyLiquid(var7);
	}

	public void moveEntity(double var1, double var3, double var5) {
		if(this.field_9066_T) {
			this.boundingBox.offset(var1, var3, var5);
			this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0D;
			this.posY = this.boundingBox.minY + (double)this.yOffset - (double)this.field_9068_R;
			this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D;
		} else {
			double var7 = this.posX;
			double var9 = this.posZ;
			double var11 = var1;
			double var13 = var3;
			double var15 = var5;
			AxisAlignedBB var17 = this.boundingBox.copy();
			boolean var18 = this.onGround && this.func_9059_p();
			if(var18) {
				double var19;
				for(var19 = 0.05D; var1 != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(var1, -1.0D, 0.0D)).size() == 0; var11 = var1) {
					if(var1 < var19 && var1 >= -var19) {
						var1 = 0.0D;
					} else if(var1 > 0.0D) {
						var1 -= var19;
					} else {
						var1 += var19;
					}
				}

				for(; var5 != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(0.0D, -1.0D, var5)).size() == 0; var15 = var5) {
					if(var5 < var19 && var5 >= -var19) {
						var5 = 0.0D;
					} else if(var5 > 0.0D) {
						var5 -= var19;
					} else {
						var5 += var19;
					}
				}
			}

			List var35 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(var1, var3, var5));

			for(int var20 = 0; var20 < var35.size(); ++var20) {
				var3 = ((AxisAlignedBB)var35.get(var20)).func_701_b(this.boundingBox, var3);
			}

			this.boundingBox.offset(0.0D, var3, 0.0D);
			if(!this.field_9077_F && var13 != var3) {
				var5 = 0.0D;
				var3 = var5;
				var1 = var5;
			}

			boolean var36 = this.onGround || var13 != var3 && var13 < 0.0D;

			int var21;
			for(var21 = 0; var21 < var35.size(); ++var21) {
				var1 = ((AxisAlignedBB)var35.get(var21)).func_710_a(this.boundingBox, var1);
			}

			this.boundingBox.offset(var1, 0.0D, 0.0D);
			if(!this.field_9077_F && var11 != var1) {
				var5 = 0.0D;
				var3 = var5;
				var1 = var5;
			}

			for(var21 = 0; var21 < var35.size(); ++var21) {
				var5 = ((AxisAlignedBB)var35.get(var21)).func_709_c(this.boundingBox, var5);
			}

			this.boundingBox.offset(0.0D, 0.0D, var5);
			if(!this.field_9077_F && var15 != var5) {
				var5 = 0.0D;
				var3 = var5;
				var1 = var5;
			}

			double var23;
			int var28;
			double var37;
			if(this.field_9067_S > 0.0F && var36 && this.field_9068_R < 0.05F && (var11 != var1 || var15 != var5)) {
				var37 = var1;
				var23 = var3;
				double var25 = var5;
				var1 = var11;
				var3 = (double)this.field_9067_S;
				var5 = var15;
				AxisAlignedBB var27 = this.boundingBox.copy();
				this.boundingBox.setBB(var17);
				var35 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(var11, var3, var15));

				for(var28 = 0; var28 < var35.size(); ++var28) {
					var3 = ((AxisAlignedBB)var35.get(var28)).func_701_b(this.boundingBox, var3);
				}

				this.boundingBox.offset(0.0D, var3, 0.0D);
				if(!this.field_9077_F && var13 != var3) {
					var5 = 0.0D;
					var3 = var5;
					var1 = var5;
				}

				for(var28 = 0; var28 < var35.size(); ++var28) {
					var1 = ((AxisAlignedBB)var35.get(var28)).func_710_a(this.boundingBox, var1);
				}

				this.boundingBox.offset(var1, 0.0D, 0.0D);
				if(!this.field_9077_F && var11 != var1) {
					var5 = 0.0D;
					var3 = var5;
					var1 = var5;
				}

				for(var28 = 0; var28 < var35.size(); ++var28) {
					var5 = ((AxisAlignedBB)var35.get(var28)).func_709_c(this.boundingBox, var5);
				}

				this.boundingBox.offset(0.0D, 0.0D, var5);
				if(!this.field_9077_F && var15 != var5) {
					var5 = 0.0D;
					var3 = var5;
					var1 = var5;
				}

				if(var37 * var37 + var25 * var25 >= var1 * var1 + var5 * var5) {
					var1 = var37;
					var3 = var23;
					var5 = var25;
					this.boundingBox.setBB(var27);
				} else {
					this.field_9068_R = (float)((double)this.field_9068_R + 0.5D);
				}
			}

			this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0D;
			this.posY = this.boundingBox.minY + (double)this.yOffset - (double)this.field_9068_R;
			this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D;
			this.field_9084_B = var11 != var1 || var15 != var5;
			this.field_9082_C = var13 != var3;
			this.onGround = var13 != var3 && var13 < 0.0D;
			this.field_9080_D = this.field_9084_B || this.field_9082_C;
			this.interact(var3, this.onGround);
			if(var11 != var1) {
				this.motionX = 0.0D;
			}

			if(var13 != var3) {
				this.motionY = 0.0D;
			}

			if(var15 != var5) {
				this.motionZ = 0.0D;
			}

			var37 = this.posX - var7;
			var23 = this.posZ - var9;
			int var26;
			int var38;
			int var40;
			if(this.entityWalks && !var18) {
				this.field_9074_L = (float)((double)this.field_9074_L + (double)MathHelper.sqrt_double(var37 * var37 + var23 * var23) * 0.6D);
				var38 = MathHelper.floor_double(this.posX);
				var26 = MathHelper.floor_double(this.posY - (double)0.2F - (double)this.yOffset);
				var40 = MathHelper.floor_double(this.posZ);
				var28 = this.worldObj.getBlockId(var38, var26, var40);
				if(this.field_9074_L > (float)this.field_6151_b && var28 > 0) {
					++this.field_6151_b;
					StepSound var29 = Block.blocksList[var28].stepSound;
					if(this.worldObj.getBlockId(var38, var26 + 1, var40) == Block.snow.blockID) {
						var29 = Block.snow.stepSound;
						this.worldObj.playSoundAtEntity(this, var29.func_737_c(), var29.func_738_a() * 0.15F, var29.func_739_b());
					} else if(!Block.blocksList[var28].blockMaterial.getIsLiquid()) {
						this.worldObj.playSoundAtEntity(this, var29.func_737_c(), var29.func_738_a() * 0.15F, var29.func_739_b());
					}

					Block.blocksList[var28].onEntityWalking(this.worldObj, var38, var26, var40, this);
				}
			}

			var38 = MathHelper.floor_double(this.boundingBox.minX);
			var26 = MathHelper.floor_double(this.boundingBox.minY);
			var40 = MathHelper.floor_double(this.boundingBox.minZ);
			var28 = MathHelper.floor_double(this.boundingBox.maxX);
			int var41 = MathHelper.floor_double(this.boundingBox.maxY);
			int var30 = MathHelper.floor_double(this.boundingBox.maxZ);

			for(int var31 = var38; var31 <= var28; ++var31) {
				for(int var32 = var26; var32 <= var41; ++var32) {
					for(int var33 = var40; var33 <= var30; ++var33) {
						int var34 = this.worldObj.getBlockId(var31, var32, var33);
						if(var34 > 0) {
							Block.blocksList[var34].onEntityCollidedWithBlock(this.worldObj, var31, var32, var33, this);
						}
					}
				}
			}

			this.field_9068_R *= 0.4F;
			boolean var39 = this.handleWaterMovement();
			if(this.worldObj.func_523_c(this.boundingBox)) {
				this.func_125_b(1);
				if(!var39) {
					++this.field_9061_Z;
					if(this.field_9061_Z == 0) {
						this.field_9061_Z = 300;
					}
				}
			} else if(this.field_9061_Z <= 0) {
				this.field_9061_Z = -this.field_9062_Y;
			}

			if(var39 && this.field_9061_Z > 0) {
				this.worldObj.playSoundAtEntity(this, "random.fizz", 0.7F, 1.6F + (this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.4F);
				this.field_9061_Z = -this.field_9062_Y;
			}

		}
	}

	protected void interact(double var1, boolean var3) {
		if(var3) {
			if(this.fallDistance > 0.0F) {
				this.fall(this.fallDistance);
				this.fallDistance = 0.0F;
			}
		} else if(var1 < 0.0D) {
			this.fallDistance = (float)((double)this.fallDistance - var1);
		}

	}

	public boolean func_9059_p() {
		return false;
	}

	public AxisAlignedBB func_93_n() {
		return null;
	}

	protected void func_125_b(int var1) {
		if(!this.field_9079_ae) {
			this.attackEntity((Entity)null, var1);
		}

	}

	protected void fall(float var1) {
	}

	public boolean handleWaterMovement() {
		return this.worldObj.func_490_a(this.boundingBox.expands(0.0D, (double)-0.4F, 0.0D), Material.water, this);
	}

	public boolean isInsideOfMaterial(Material var1) {
		double var2 = this.posY + (double)this.func_104_p();
		int var4 = MathHelper.floor_double(this.posX);
		int var5 = MathHelper.floor_float((float)MathHelper.floor_double(var2));
		int var6 = MathHelper.floor_double(this.posZ);
		int var7 = this.worldObj.getBlockId(var4, var5, var6);
		if(var7 != 0 && Block.blocksList[var7].blockMaterial == var1) {
			float var8 = BlockFluids.setFluidHeight(this.worldObj.getBlockMetadata(var4, var5, var6)) - 1.0F / 9.0F;
			float var9 = (float)(var5 + 1) - var8;
			return var2 < (double)var9;
		} else {
			return false;
		}
	}

	public float func_104_p() {
		return 0.0F;
	}

	public boolean func_112_q() {
		return this.worldObj.isMaterialInBB(this.boundingBox.expands(0.0D, (double)-0.4F, 0.0D), Material.lava);
	}

	public void func_90_a(float var1, float var2, float var3) {
		float var4 = MathHelper.sqrt_float(var1 * var1 + var2 * var2);
		if(var4 >= 0.01F) {
			if(var4 < 1.0F) {
				var4 = 1.0F;
			}

			var4 = var3 / var4;
			var1 *= var4;
			var2 *= var4;
			float var5 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F);
			float var6 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F);
			this.motionX += (double)(var1 * var6 - var2 * var5);
			this.motionZ += (double)(var2 * var6 + var1 * var5);
		}
	}

	public float getEntityBrightness(float var1) {
		int var2 = MathHelper.floor_double(this.posX);
		double var3 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66D;
		int var5 = MathHelper.floor_double(this.posY - (double)this.yOffset + var3);
		int var6 = MathHelper.floor_double(this.posZ);
		return this.worldObj.getLightBrightness(var2, var5, var6);
	}

	public void setPositionAndRotation(double var1, double var3, double var5, float var7, float var8) {
		this.prevPosX = this.posX = var1;
		this.prevPosY = this.posY = var3;
		this.prevPosZ = this.posZ = var5;
		this.prevRotationYaw = this.rotationYaw = var7;
		this.prevRotationPitch = this.rotationPitch = var8;
		this.field_9068_R = 0.0F;
		double var9 = (double)(this.prevRotationYaw - var7);
		if(var9 < -180.0D) {
			this.prevRotationYaw += 360.0F;
		}

		if(var9 >= 180.0D) {
			this.prevRotationYaw -= 360.0F;
		}

		this.setPosition(this.posX, this.posY, this.posZ);
		this.setRotation(var7, var8);
	}

	public void func_107_c(double var1, double var3, double var5, float var7, float var8) {
		this.prevPosX = this.posX = var1;
		this.prevPosY = this.posY = var3 + (double)this.yOffset;
		this.prevPosZ = this.posZ = var5;
		this.rotationYaw = var7;
		this.rotationPitch = var8;
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	public float getDistanceToEntity(Entity var1) {
		float var2 = (float)(this.posX - var1.posX);
		float var3 = (float)(this.posY - var1.posY);
		float var4 = (float)(this.posZ - var1.posZ);
		return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
	}

	public double getDistanceSq(double var1, double var3, double var5) {
		double var7 = this.posX - var1;
		double var9 = this.posY - var3;
		double var11 = this.posZ - var5;
		return var7 * var7 + var9 * var9 + var11 * var11;
	}

	public double getDistance(double var1, double var3, double var5) {
		double var7 = this.posX - var1;
		double var9 = this.posY - var3;
		double var11 = this.posZ - var5;
		return (double)MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
	}

	public double getDistanceSqToEntity(Entity var1) {
		double var2 = this.posX - var1.posX;
		double var4 = this.posY - var1.posY;
		double var6 = this.posZ - var1.posZ;
		return var2 * var2 + var4 * var4 + var6 * var6;
	}

	public void onCollideWithPlayer(EntityPlayer var1) {
	}

	public void applyEntityCollision(Entity var1) {
		if(var1.field_328_f != this && var1.field_327_g != this) {
			double var2 = var1.posX - this.posX;
			double var4 = var1.posZ - this.posZ;
			double var6 = MathHelper.abs_max(var2, var4);
			if(var6 >= (double)0.01F) {
				var6 = (double)MathHelper.sqrt_double(var6);
				var2 /= var6;
				var4 /= var6;
				double var8 = 1.0D / var6;
				if(var8 > 1.0D) {
					var8 = 1.0D;
				}

				var2 *= var8;
				var4 *= var8;
				var2 *= (double)0.05F;
				var4 *= (double)0.05F;
				var2 *= (double)(1.0F - this.field_286_P);
				var4 *= (double)(1.0F - this.field_286_P);
				this.addVelocity(-var2, 0.0D, -var4);
				var1.addVelocity(var2, 0.0D, var4);
			}

		}
	}

	public void addVelocity(double var1, double var3, double var5) {
		this.motionX += var1;
		this.motionY += var3;
		this.motionZ += var5;
	}

	protected void func_9060_u() {
		this.field_9078_E = true;
	}

	public boolean attackEntity(Entity var1, int var2) {
		this.func_9060_u();
		return false;
	}

	public boolean func_129_c_() {
		return false;
	}

	public boolean func_124_r() {
		return false;
	}

	public void func_96_b(Entity var1, int var2) {
	}

	public boolean func_95_c(NBTTagCompound var1) {
		String var2 = this.func_109_s();
		if(!this.field_304_B && var2 != null) {
			var1.setString("id", var2);
			this.writeToNBT(var1);
			return true;
		} else {
			return false;
		}
	}

	public void writeToNBT(NBTTagCompound var1) {
		var1.setTag("Pos", this.func_132_a(new double[]{this.posX, this.posY, this.posZ}));
		var1.setTag("Motion", this.func_132_a(new double[]{this.motionX, this.motionY, this.motionZ}));
		var1.setTag("Rotation", this.func_85_a(new float[]{this.rotationYaw, this.rotationPitch}));
		var1.setFloat("FallDistance", this.fallDistance);
		var1.setShort("Fire", (short)this.field_9061_Z);
		var1.setShort("Air", (short)this.air);
		var1.setBoolean("OnGround", this.onGround);
		this.writeEntityToNBT(var1);
	}

	public void readFromNBT(NBTTagCompound var1) {
		NBTTagList var2 = var1.getTagList("Pos");
		NBTTagList var3 = var1.getTagList("Motion");
		NBTTagList var4 = var1.getTagList("Rotation");
		this.setPosition(0.0D, 0.0D, 0.0D);
		this.motionX = ((NBTTagDouble)var3.tagAt(0)).doubleValue;
		this.motionY = ((NBTTagDouble)var3.tagAt(1)).doubleValue;
		this.motionZ = ((NBTTagDouble)var3.tagAt(2)).doubleValue;
		this.prevPosX = this.field_9071_O = this.posX = ((NBTTagDouble)var2.tagAt(0)).doubleValue;
		this.prevPosY = this.field_9070_P = this.posY = ((NBTTagDouble)var2.tagAt(1)).doubleValue;
		this.prevPosZ = this.field_9069_Q = this.posZ = ((NBTTagDouble)var2.tagAt(2)).doubleValue;
		this.prevRotationYaw = this.rotationYaw = ((NBTTagFloat)var4.tagAt(0)).floatValue;
		this.prevRotationPitch = this.rotationPitch = ((NBTTagFloat)var4.tagAt(1)).floatValue;
		this.fallDistance = var1.getFloat("FallDistance");
		this.field_9061_Z = var1.getShort("Fire");
		this.air = var1.getShort("Air");
		this.onGround = var1.getBoolean("OnGround");
		this.setPosition(this.posX, this.posY, this.posZ);
		this.readEntityFromNBT(var1);
	}

	protected final String func_109_s() {
		return EntityList.func_564_b(this);
	}

	protected abstract void readEntityFromNBT(NBTTagCompound var1);

	protected abstract void writeEntityToNBT(NBTTagCompound var1);

	protected NBTTagList func_132_a(double... var1) {
		NBTTagList var2 = new NBTTagList();
		double[] var3 = var1;
		int var4 = var1.length;

		for(int var5 = 0; var5 < var4; ++var5) {
			double var6 = var3[var5];
			var2.setTag(new NBTTagDouble(var6));
		}

		return var2;
	}

	protected NBTTagList func_85_a(float... var1) {
		NBTTagList var2 = new NBTTagList();
		float[] var3 = var1;
		int var4 = var1.length;

		for(int var5 = 0; var5 < var4; ++var5) {
			float var6 = var3[var5];
			var2.setTag(new NBTTagFloat(var6));
		}

		return var2;
	}

	public EntityItem dropItem(int var1, int var2) {
		return this.dropItemWithOffset(var1, var2, 0.0F);
	}

	public EntityItem dropItemWithOffset(int var1, int var2, float var3) {
		EntityItem var4 = new EntityItem(this.worldObj, this.posX, this.posY + (double)var3, this.posZ, new ItemStack(var1, var2));
		var4.field_433_ad = 10;
		this.worldObj.entityJoinedWorld(var4);
		return var4;
	}

	public boolean func_120_t() {
		return !this.field_304_B;
	}

	public boolean func_91_u() {
		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.posY + (double)this.func_104_p());
		int var3 = MathHelper.floor_double(this.posZ);
		return this.worldObj.doesBlockAllowAttachment(var1, var2, var3);
	}

	public boolean func_6092_a(EntityPlayer var1) {
		return false;
	}

	public AxisAlignedBB func_89_d(Entity var1) {
		return null;
	}

	public void func_115_v() {
		if(this.field_327_g.field_304_B) {
			this.field_327_g = null;
		} else {
			this.motionX = 0.0D;
			this.motionY = 0.0D;
			this.motionZ = 0.0D;
			this.onUpdate();
			this.field_327_g.func_127_w();
			this.field_4128_e += (double)(this.field_327_g.rotationYaw - this.field_327_g.prevRotationYaw);

			for(this.field_4130_d += (double)(this.field_327_g.rotationPitch - this.field_327_g.prevRotationPitch); this.field_4128_e >= 180.0D; this.field_4128_e -= 360.0D) {
			}

			while(this.field_4128_e < -180.0D) {
				this.field_4128_e += 360.0D;
			}

			while(this.field_4130_d >= 180.0D) {
				this.field_4130_d -= 360.0D;
			}

			while(this.field_4130_d < -180.0D) {
				this.field_4130_d += 360.0D;
			}

			double var1 = this.field_4128_e * 0.5D;
			double var3 = this.field_4130_d * 0.5D;
			float var5 = 10.0F;
			if(var1 > (double)var5) {
				var1 = (double)var5;
			}

			if(var1 < (double)(-var5)) {
				var1 = (double)(-var5);
			}

			if(var3 > (double)var5) {
				var3 = (double)var5;
			}

			if(var3 < (double)(-var5)) {
				var3 = (double)(-var5);
			}

			this.field_4128_e -= var1;
			this.field_4130_d -= var3;
			this.rotationYaw = (float)((double)this.rotationYaw + var1);
			this.rotationPitch = (float)((double)this.rotationPitch + var3);
		}
	}

	public void func_127_w() {
		this.field_328_f.setPosition(this.posX, this.posY + this.func_130_h() + this.field_328_f.func_117_x(), this.posZ);
	}

	public double func_117_x() {
		return (double)this.yOffset;
	}

	public double func_130_h() {
		return (double)this.height * 0.75D;
	}

	public void func_6094_e(Entity var1) {
		this.field_4130_d = 0.0D;
		this.field_4128_e = 0.0D;
		if(var1 == null) {
			if(this.field_327_g != null) {
				this.func_107_c(this.field_327_g.posX, this.field_327_g.boundingBox.minY + (double)this.field_327_g.height, this.field_327_g.posZ, this.rotationYaw, this.rotationPitch);
				this.field_327_g.field_328_f = null;
			}

			this.field_327_g = null;
		} else if(this.field_327_g == var1) {
			this.field_327_g.field_328_f = null;
			this.field_327_g = null;
			this.func_107_c(var1.posX, var1.boundingBox.minY + (double)var1.height, var1.posZ, this.rotationYaw, this.rotationPitch);
		} else {
			if(this.field_327_g != null) {
				this.field_327_g.field_328_f = null;
			}

			if(var1.field_328_f != null) {
				var1.field_328_f.field_327_g = null;
			}

			this.field_327_g = var1;
			var1.field_328_f = this;
		}
	}

	public Vec3D func_4039_B() {
		return null;
	}

	public void func_4042_C() {
	}
}
