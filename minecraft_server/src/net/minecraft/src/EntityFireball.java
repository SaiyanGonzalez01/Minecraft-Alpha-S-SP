package net.minecraft.src;

import java.util.List;

public class EntityFireball extends Entity {
	private int field_9195_e = -1;
	private int field_9193_f = -1;
	private int field_9197_aj = -1;
	private int field_9194_ak = 0;
	private boolean field_9192_al = false;
	public int field_9200_a = 0;
	private EntityLiving field_9191_am;
	private int field_9190_an;
	private int field_9189_ao = 0;
	public double field_9199_b;
	public double field_9198_c;
	public double field_9196_d;

	public EntityFireball(World var1) {
		super(var1);
		this.setSize(1.0F, 1.0F);
	}

	public EntityFireball(World var1, EntityLiving var2, double var3, double var5, double var7) {
		super(var1);
		this.field_9191_am = var2;
		this.setSize(1.0F, 1.0F);
		this.func_107_c(var2.posX, var2.posY, var2.posZ, var2.rotationYaw, var2.rotationPitch);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = this.motionY = this.motionZ = 0.0D;
		var3 += this.field_9064_W.nextGaussian() * 0.4D;
		var5 += this.field_9064_W.nextGaussian() * 0.4D;
		var7 += this.field_9064_W.nextGaussian() * 0.4D;
		double var9 = (double)MathHelper.sqrt_double(var3 * var3 + var5 * var5 + var7 * var7);
		this.field_9199_b = var3 / var9 * 0.1D;
		this.field_9198_c = var5 / var9 * 0.1D;
		this.field_9196_d = var7 / var9 * 0.1D;
	}

	public void onUpdate() {
		super.onUpdate();
		this.field_9061_Z = 10;
		if(this.field_9200_a > 0) {
			--this.field_9200_a;
		}

		if(this.field_9192_al) {
			int var1 = this.worldObj.getBlockId(this.field_9195_e, this.field_9193_f, this.field_9197_aj);
			if(var1 == this.field_9194_ak) {
				++this.field_9190_an;
				if(this.field_9190_an == 1200) {
					this.setEntityDead();
				}

				return;
			}

			this.field_9192_al = false;
			this.motionX *= (double)(this.field_9064_W.nextFloat() * 0.2F);
			this.motionY *= (double)(this.field_9064_W.nextFloat() * 0.2F);
			this.motionZ *= (double)(this.field_9064_W.nextFloat() * 0.2F);
			this.field_9190_an = 0;
			this.field_9189_ao = 0;
		} else {
			++this.field_9189_ao;
		}

		Vec3D var15 = Vec3D.createVector(this.posX, this.posY, this.posZ);
		Vec3D var2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		MovingObjectPosition var3 = this.worldObj.func_486_a(var15, var2);
		var15 = Vec3D.createVector(this.posX, this.posY, this.posZ);
		var2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		if(var3 != null) {
			var2 = Vec3D.createVector(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
		}

		Entity var4 = null;
		List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expands(1.0D, 1.0D, 1.0D));
		double var6 = 0.0D;

		for(int var8 = 0; var8 < var5.size(); ++var8) {
			Entity var9 = (Entity)var5.get(var8);
			if(var9.func_129_c_() && (var9 != this.field_9191_am || this.field_9189_ao >= 25)) {
				float var10 = 0.3F;
				AxisAlignedBB var11 = var9.boundingBox.expands((double)var10, (double)var10, (double)var10);
				MovingObjectPosition var12 = var11.func_706_a(var15, var2);
				if(var12 != null) {
					double var13 = var15.distanceTo(var12.hitVec);
					if(var13 < var6 || var6 == 0.0D) {
						var4 = var9;
						var6 = var13;
					}
				}
			}
		}

		if(var4 != null) {
			var3 = new MovingObjectPosition(var4);
		}

		if(var3 != null) {
			if(var3.entityHit != null && var3.entityHit.attackEntity(this.field_9191_am, 0)) {
			}

			this.worldObj.func_12015_a((Entity)null, this.posX, this.posY, this.posZ, 1.0F, true);
			this.setEntityDead();
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float var16 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / (double)((float)Math.PI));

		for(this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var16) * 180.0D / (double)((float)Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		float var17 = 0.95F;
		if(this.handleWaterMovement()) {
			for(int var18 = 0; var18 < 4; ++var18) {
				float var19 = 0.25F;
				this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)var19, this.posY - this.motionY * (double)var19, this.posZ - this.motionZ * (double)var19, this.motionX, this.motionY, this.motionZ);
			}

			var17 = 0.8F;
		}

		this.motionX += this.field_9199_b;
		this.motionY += this.field_9198_c;
		this.motionZ += this.field_9196_d;
		this.motionX *= (double)var17;
		this.motionY *= (double)var17;
		this.motionZ *= (double)var17;
		this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		var1.setShort("xTile", (short)this.field_9195_e);
		var1.setShort("yTile", (short)this.field_9193_f);
		var1.setShort("zTile", (short)this.field_9197_aj);
		var1.setByte("inTile", (byte)this.field_9194_ak);
		var1.setByte("shake", (byte)this.field_9200_a);
		var1.setByte("inGround", (byte)(this.field_9192_al ? 1 : 0));
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		this.field_9195_e = var1.getShort("xTile");
		this.field_9193_f = var1.getShort("yTile");
		this.field_9197_aj = var1.getShort("zTile");
		this.field_9194_ak = var1.getByte("inTile") & 255;
		this.field_9200_a = var1.getByte("shake") & 255;
		this.field_9192_al = var1.getByte("inGround") == 1;
	}

	public boolean func_129_c_() {
		return true;
	}

	public boolean attackEntity(Entity var1, int var2) {
		this.func_9060_u();
		if(var1 != null) {
			Vec3D var3 = var1.func_4039_B();
			if(var3 != null) {
				this.motionX = var3.xCoord;
				this.motionY = var3.yCoord;
				this.motionZ = var3.zCoord;
				this.field_9199_b = this.motionX * 0.1D;
				this.field_9198_c = this.motionY * 0.1D;
				this.field_9196_d = this.motionZ * 0.1D;
			}

			return true;
		} else {
			return false;
		}
	}
}
