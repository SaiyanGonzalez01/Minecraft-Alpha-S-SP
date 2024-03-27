package net.minecraft.src;

import java.util.List;

public class EntitySnowball extends Entity {
	private int field_456_b = -1;
	private int field_461_ad = -1;
	private int field_460_ae = -1;
	private int field_459_af = 0;
	private boolean field_457_ag = false;
	public int field_458_a = 0;
	private EntityLiving field_455_ah;
	private int field_454_ai;
	private int field_453_aj = 0;

	public EntitySnowball(World var1) {
		super(var1);
		this.setSize(0.25F, 0.25F);
	}

	public EntitySnowball(World var1, EntityLiving var2) {
		super(var1);
		this.field_455_ah = var2;
		this.setSize(0.25F, 0.25F);
		this.func_107_c(var2.posX, var2.posY + (double)var2.func_104_p(), var2.posZ, var2.rotationYaw, var2.rotationPitch);
		this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.posY -= (double)0.1F;
		this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		float var3 = 0.4F;
		this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var3);
		this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var3);
		this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI) * var3);
		this.func_6141_a(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
	}

	public void func_6141_a(double var1, double var3, double var5, float var7, float var8) {
		float var9 = MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5);
		var1 /= (double)var9;
		var3 /= (double)var9;
		var5 /= (double)var9;
		var1 += this.field_9064_W.nextGaussian() * (double)0.0075F * (double)var8;
		var3 += this.field_9064_W.nextGaussian() * (double)0.0075F * (double)var8;
		var5 += this.field_9064_W.nextGaussian() * (double)0.0075F * (double)var8;
		var1 *= (double)var7;
		var3 *= (double)var7;
		var5 *= (double)var7;
		this.motionX = var1;
		this.motionY = var3;
		this.motionZ = var5;
		float var10 = MathHelper.sqrt_double(var1 * var1 + var5 * var5);
		this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(var1, var5) * 180.0D / (double)((float)Math.PI));
		this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(var3, (double)var10) * 180.0D / (double)((float)Math.PI));
		this.field_454_ai = 0;
	}

	public void onUpdate() {
		this.field_9071_O = this.posX;
		this.field_9070_P = this.posY;
		this.field_9069_Q = this.posZ;
		super.onUpdate();
		if(this.field_458_a > 0) {
			--this.field_458_a;
		}

		if(this.field_457_ag) {
			int var1 = this.worldObj.getBlockId(this.field_456_b, this.field_461_ad, this.field_460_ae);
			if(var1 == this.field_459_af) {
				++this.field_454_ai;
				if(this.field_454_ai == 1200) {
					this.setEntityDead();
				}

				return;
			}

			this.field_457_ag = false;
			this.motionX *= (double)(this.field_9064_W.nextFloat() * 0.2F);
			this.motionY *= (double)(this.field_9064_W.nextFloat() * 0.2F);
			this.motionZ *= (double)(this.field_9064_W.nextFloat() * 0.2F);
			this.field_454_ai = 0;
			this.field_453_aj = 0;
		} else {
			++this.field_453_aj;
		}

		Vec3D var15 = Vec3D.createVector(this.posX, this.posY, this.posZ);
		Vec3D var2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		MovingObjectPosition var3 = this.worldObj.func_486_a(var15, var2);
		var15 = Vec3D.createVector(this.posX, this.posY, this.posZ);
		var2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		if(var3 != null) {
			var2 = Vec3D.createVector(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
		}

		if(!this.worldObj.multiplayerWorld) {
			Entity var4 = null;
			List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expands(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

			for(int var8 = 0; var8 < var5.size(); ++var8) {
				Entity var9 = (Entity)var5.get(var8);
				if(var9.func_129_c_() && (var9 != this.field_455_ah || this.field_453_aj >= 5)) {
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
		}

		if(var3 != null) {
			if(var3.entityHit != null && var3.entityHit.attackEntity(this.field_455_ah, 0)) {
			}

			for(int var16 = 0; var16 < 8; ++var16) {
				this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			}

			this.setEntityDead();
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float var17 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / (double)((float)Math.PI));

		for(this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var17) * 180.0D / (double)((float)Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
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
		float var18 = 0.99F;
		float var19 = 0.03F;
		if(this.handleWaterMovement()) {
			for(int var7 = 0; var7 < 4; ++var7) {
				float var20 = 0.25F;
				this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)var20, this.posY - this.motionY * (double)var20, this.posZ - this.motionZ * (double)var20, this.motionX, this.motionY, this.motionZ);
			}

			var18 = 0.8F;
		}

		this.motionX *= (double)var18;
		this.motionY *= (double)var18;
		this.motionZ *= (double)var18;
		this.motionY -= (double)var19;
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		var1.setShort("xTile", (short)this.field_456_b);
		var1.setShort("yTile", (short)this.field_461_ad);
		var1.setShort("zTile", (short)this.field_460_ae);
		var1.setByte("inTile", (byte)this.field_459_af);
		var1.setByte("shake", (byte)this.field_458_a);
		var1.setByte("inGround", (byte)(this.field_457_ag ? 1 : 0));
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		this.field_456_b = var1.getShort("xTile");
		this.field_461_ad = var1.getShort("yTile");
		this.field_460_ae = var1.getShort("zTile");
		this.field_459_af = var1.getByte("inTile") & 255;
		this.field_458_a = var1.getByte("shake") & 255;
		this.field_457_ag = var1.getByte("inGround") == 1;
	}

	public void onCollideWithPlayer(EntityPlayer var1) {
		if(this.field_457_ag && this.field_455_ah == var1 && this.field_458_a <= 0 && var1.inventory.addItemStackToInventory(new ItemStack(Item.arrow.swiftedIndex, 1))) {
			this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			var1.func_163_c(this, 1);
			this.setEntityDead();
		}

	}
}
