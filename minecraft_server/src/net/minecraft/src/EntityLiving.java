package net.minecraft.src;

import java.util.List;

public class EntityLiving extends Entity {
	public int field_9099_av = 20;
	public float field_9098_aw;
	public float field_9097_ax;
	public float field_9096_ay;
	public float field_9095_az = 0.0F;
	public float field_9125_aA = 0.0F;
	protected float field_9124_aB;
	protected float field_9123_aC;
	protected float field_9122_aD;
	protected float field_9121_aE;
	protected boolean field_9120_aF = true;
	protected String field_9119_aG = "/mob/char.png";
	protected boolean field_9118_aH = true;
	protected float field_9117_aI = 0.0F;
	protected String field_9116_aJ = null;
	protected float field_9115_aK = 1.0F;
	protected int field_9114_aL = 0;
	protected float field_9113_aM = 0.0F;
	public boolean field_9112_aN = false;
	public float field_9111_aO;
	public float field_9110_aP;
	public int field_9109_aQ = 10;
	public int field_9108_aR;
	private int a;
	public int field_9107_aS;
	public int field_9106_aT;
	public float field_9105_aU = 0.0F;
	public int field_9104_aV = 0;
	public int field_9103_aW = 0;
	public float field_9102_aX;
	public float field_9101_aY;
	protected boolean field_9100_aZ = false;
	public int field_9144_ba = -1;
	public float field_9143_bb = (float)(Math.random() * (double)0.9F + (double)0.1F);
	public float field_9142_bc;
	public float field_9141_bd;
	public float field_386_ba;
	protected int field_9140_bf;
	protected double field_9139_bg;
	protected double field_9138_bh;
	protected double field_9137_bi;
	protected double field_9136_bj;
	protected double field_9135_bk;
	float field_9134_bl = 0.0F;
	protected int field_9133_bm = 0;
	protected int field_9132_bn = 0;
	protected float field_9131_bo;
	protected float field_9130_bp;
	protected float field_9129_bq;
	protected boolean field_9128_br = false;
	protected float field_9127_bs = 0.0F;
	protected float field_9126_bt = 0.7F;
	private Entity b;
	private int c = 0;

	public EntityLiving(World var1) {
		super(var1);
		this.field_329_e = true;
		this.field_9096_ay = (float)(Math.random() + 1.0D) * 0.01F;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.field_9098_aw = (float)Math.random() * 12398.0F;
		this.rotationYaw = (float)(Math.random() * (double)((float)Math.PI) * 2.0D);
		this.field_9097_ax = 1.0F;
		this.field_9067_S = 0.5F;
	}

	public boolean func_145_g(Entity var1) {
		return this.worldObj.func_486_a(Vec3D.createVector(this.posX, this.posY + (double)this.func_104_p(), this.posZ), Vec3D.createVector(var1.posX, var1.posY + (double)var1.func_104_p(), var1.posZ)) == null;
	}

	public boolean func_129_c_() {
		return !this.field_304_B;
	}

	public boolean func_124_r() {
		return !this.field_304_B;
	}

	public float func_104_p() {
		return this.height * 0.85F;
	}

	public int func_146_b() {
		return 80;
	}

	public void func_84_k() {
		this.field_9111_aO = this.field_9110_aP;
		super.func_84_k();
		if(this.field_9064_W.nextInt(1000) < this.a++) {
			this.a = -this.func_146_b();
			String var1 = this.getLivingSound();
			if(var1 != null) {
				this.worldObj.playSoundAtEntity(this, var1, this.getSoundVolume(), (this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.2F + 1.0F);
			}
		}

		if(this.func_120_t() && this.func_91_u()) {
			this.attackEntity((Entity)null, 1);
		}

		if(this.field_9079_ae || this.worldObj.multiplayerWorld) {
			this.field_9061_Z = 0;
		}

		int var8;
		if(this.func_120_t() && this.isInsideOfMaterial(Material.water)) {
			--this.air;
			if(this.air == -20) {
				this.air = 0;

				for(var8 = 0; var8 < 8; ++var8) {
					float var2 = this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat();
					float var3 = this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat();
					float var4 = this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat();
					this.worldObj.spawnParticle("bubble", this.posX + (double)var2, this.posY + (double)var3, this.posZ + (double)var4, this.motionX, this.motionY, this.motionZ);
				}

				this.attackEntity((Entity)null, 2);
			}

			this.field_9061_Z = 0;
		} else {
			this.air = this.field_9087_aa;
		}

		this.field_9102_aX = this.field_9101_aY;
		if(this.field_9103_aW > 0) {
			--this.field_9103_aW;
		}

		if(this.field_9107_aS > 0) {
			--this.field_9107_aS;
		}

		if(this.field_9083_ac > 0) {
			--this.field_9083_ac;
		}

		if(this.field_9109_aQ <= 0) {
			++this.field_9104_aV;
			if(this.field_9104_aV > 20) {
				this.func_6101_K();
				this.setEntityDead();

				for(var8 = 0; var8 < 20; ++var8) {
					double var9 = this.field_9064_W.nextGaussian() * 0.02D;
					double var10 = this.field_9064_W.nextGaussian() * 0.02D;
					double var6 = this.field_9064_W.nextGaussian() * 0.02D;
					this.worldObj.spawnParticle("explode", this.posX + (double)(this.field_9064_W.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.field_9064_W.nextFloat() * this.height), this.posZ + (double)(this.field_9064_W.nextFloat() * this.width * 2.0F) - (double)this.width, var9, var10, var6);
				}
			}
		}

		this.field_9121_aE = this.field_9122_aD;
		this.field_9125_aA = this.field_9095_az;
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
	}

	public void func_156_D() {
		for(int var1 = 0; var1 < 20; ++var1) {
			double var2 = this.field_9064_W.nextGaussian() * 0.02D;
			double var4 = this.field_9064_W.nextGaussian() * 0.02D;
			double var6 = this.field_9064_W.nextGaussian() * 0.02D;
			double var8 = 10.0D;
			this.worldObj.spawnParticle("explode", this.posX + (double)(this.field_9064_W.nextFloat() * this.width * 2.0F) - (double)this.width - var2 * var8, this.posY + (double)(this.field_9064_W.nextFloat() * this.height) - var4 * var8, this.posZ + (double)(this.field_9064_W.nextFloat() * this.width * 2.0F) - (double)this.width - var6 * var8, var2, var4, var6);
		}

	}

	public void func_115_v() {
		super.func_115_v();
		this.field_9124_aB = this.field_9123_aC;
		this.field_9123_aC = 0.0F;
	}

	public void onUpdate() {
		super.onUpdate();
		this.onLivingUpdate();
		double var1 = this.posX - this.prevPosX;
		double var3 = this.posZ - this.prevPosZ;
		float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
		float var6 = this.field_9095_az;
		float var7 = 0.0F;
		this.field_9124_aB = this.field_9123_aC;
		float var8 = 0.0F;
		if(var5 > 0.05F) {
			var8 = 1.0F;
			var7 = var5 * 3.0F;
			var6 = (float)Math.atan2(var3, var1) * 180.0F / (float)Math.PI - 90.0F;
		}

		if(this.field_9110_aP > 0.0F) {
			var6 = this.rotationYaw;
		}

		if(!this.onGround) {
			var8 = 0.0F;
		}

		this.field_9123_aC += (var8 - this.field_9123_aC) * 0.3F;

		float var9;
		for(var9 = var6 - this.field_9095_az; var9 < -180.0F; var9 += 360.0F) {
		}

		while(var9 >= 180.0F) {
			var9 -= 360.0F;
		}

		this.field_9095_az += var9 * 0.3F;

		float var10;
		for(var10 = this.rotationYaw - this.field_9095_az; var10 < -180.0F; var10 += 360.0F) {
		}

		while(var10 >= 180.0F) {
			var10 -= 360.0F;
		}

		boolean var11 = var10 < -90.0F || var10 >= 90.0F;
		if(var10 < -75.0F) {
			var10 = -75.0F;
		}

		if(var10 >= 75.0F) {
			var10 = 75.0F;
		}

		this.field_9095_az = this.rotationYaw - var10;
		if(var10 * var10 > 2500.0F) {
			this.field_9095_az += var10 * 0.2F;
		}

		if(var11) {
			var7 *= -1.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}

		while(this.field_9095_az - this.field_9125_aA < -180.0F) {
			this.field_9125_aA -= 360.0F;
		}

		while(this.field_9095_az - this.field_9125_aA >= 180.0F) {
			this.field_9125_aA += 360.0F;
		}

		while(this.rotationPitch - this.prevRotationPitch < -180.0F) {
			this.prevRotationPitch -= 360.0F;
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		this.field_9122_aD += var7;
	}

	protected void setSize(float var1, float var2) {
		super.setSize(var1, var2);
	}

	public void heal(int var1) {
		if(this.field_9109_aQ > 0) {
			this.field_9109_aQ += var1;
			if(this.field_9109_aQ > 20) {
				this.field_9109_aQ = 20;
			}

			this.field_9083_ac = this.field_9099_av / 2;
		}
	}

	public boolean attackEntity(Entity var1, int var2) {
		if(this.worldObj.multiplayerWorld) {
			return false;
		} else {
			this.field_9132_bn = 0;
			if(this.field_9109_aQ <= 0) {
				return false;
			} else {
				this.field_9141_bd = 1.5F;
				boolean var3 = true;
				if((float)this.field_9083_ac > (float)this.field_9099_av / 2.0F) {
					if(var2 <= this.field_9133_bm) {
						return false;
					}

					this.func_6099_c(var2 - this.field_9133_bm);
					this.field_9133_bm = var2;
					var3 = false;
				} else {
					this.field_9133_bm = var2;
					this.field_9108_aR = this.field_9109_aQ;
					this.field_9083_ac = this.field_9099_av;
					this.func_6099_c(var2);
					this.field_9107_aS = this.field_9106_aT = 10;
				}

				this.field_9105_aU = 0.0F;
				if(var3) {
					this.worldObj.func_9206_a(this, (byte)2);
					this.func_9060_u();
					if(var1 != null) {
						double var4 = var1.posX - this.posX;

						double var6;
						for(var6 = var1.posZ - this.posZ; var4 * var4 + var6 * var6 < 1.0E-4D; var6 = (Math.random() - Math.random()) * 0.01D) {
							var4 = (Math.random() - Math.random()) * 0.01D;
						}

						this.field_9105_aU = (float)(Math.atan2(var6, var4) * 180.0D / (double)((float)Math.PI)) - this.rotationYaw;
						this.func_143_a(var1, var2, var4, var6);
					} else {
						this.field_9105_aU = (float)((int)(Math.random() * 2.0D) * 180);
					}
				}

				if(this.field_9109_aQ <= 0) {
					if(var3) {
						this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), (this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.2F + 1.0F);
					}

					this.onDeath(var1);
				} else if(var3) {
					this.worldObj.playSoundAtEntity(this, this.getHurtSound(), this.getSoundVolume(), (this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.2F + 1.0F);
				}

				return true;
			}
		}
	}

	protected void func_6099_c(int var1) {
		this.field_9109_aQ -= var1;
	}

	protected float getSoundVolume() {
		return 1.0F;
	}

	protected String getLivingSound() {
		return null;
	}

	protected String getHurtSound() {
		return "random.hurt";
	}

	protected String getDeathSound() {
		return "random.hurt";
	}

	public void func_143_a(Entity var1, int var2, double var3, double var5) {
		float var7 = MathHelper.sqrt_double(var3 * var3 + var5 * var5);
		float var8 = 0.4F;
		this.motionX /= 2.0D;
		this.motionY /= 2.0D;
		this.motionZ /= 2.0D;
		this.motionX -= var3 / (double)var7 * (double)var8;
		this.motionY += (double)0.4F;
		this.motionZ -= var5 / (double)var7 * (double)var8;
		if(this.motionY > (double)0.4F) {
			this.motionY = (double)0.4F;
		}

	}

	public void onDeath(Entity var1) {
		if(this.field_9114_aL > 0 && var1 != null) {
			var1.func_96_b(this, this.field_9114_aL);
		}

		this.field_9100_aZ = true;
		if(!this.worldObj.multiplayerWorld) {
			int var2 = this.getDropItemId();
			if(var2 > 0) {
				int var3 = this.field_9064_W.nextInt(3);

				for(int var4 = 0; var4 < var3; ++var4) {
					this.dropItem(var2, 1);
				}
			}
		}

		this.worldObj.func_9206_a(this, (byte)3);
	}

	protected int getDropItemId() {
		return 0;
	}

	protected void fall(float var1) {
		int var2 = (int)Math.ceil((double)(var1 - 3.0F));
		if(var2 > 0) {
			this.attackEntity((Entity)null, var2);
			int var3 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - (double)0.2F - (double)this.yOffset), MathHelper.floor_double(this.posZ));
			if(var3 > 0) {
				StepSound var4 = Block.blocksList[var3].stepSound;
				this.worldObj.playSoundAtEntity(this, var4.func_737_c(), var4.func_738_a() * 0.5F, var4.func_739_b() * (12.0F / 16.0F));
			}
		}

	}

	public void func_148_c(float var1, float var2) {
		double var3;
		if(this.handleWaterMovement()) {
			var3 = this.posY;
			this.func_90_a(var1, var2, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= (double)0.8F;
			this.motionY *= (double)0.8F;
			this.motionZ *= (double)0.8F;
			this.motionY -= 0.02D;
			if(this.field_9084_B && this.func_133_b(this.motionX, this.motionY + (double)0.6F - this.posY + var3, this.motionZ)) {
				this.motionY = (double)0.3F;
			}
		} else if(this.func_112_q()) {
			var3 = this.posY;
			this.func_90_a(var1, var2, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
			this.motionY -= 0.02D;
			if(this.field_9084_B && this.func_133_b(this.motionX, this.motionY + (double)0.6F - this.posY + var3, this.motionZ)) {
				this.motionY = (double)0.3F;
			}
		} else {
			float var8 = 0.91F;
			if(this.onGround) {
				var8 = 546.0F * 0.1F * 0.1F * 0.1F;
				int var4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
				if(var4 > 0) {
					var8 = Block.blocksList[var4].slipperiness * 0.91F;
				}
			}

			float var9 = 0.16277136F / (var8 * var8 * var8);
			this.func_90_a(var1, var2, this.onGround ? 0.1F * var9 : 0.02F);
			var8 = 0.91F;
			if(this.onGround) {
				var8 = 546.0F * 0.1F * 0.1F * 0.1F;
				int var5 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
				if(var5 > 0) {
					var8 = Block.blocksList[var5].slipperiness * 0.91F;
				}
			}

			if(this.func_144_E()) {
				this.fallDistance = 0.0F;
				if(this.motionY < -0.15D) {
					this.motionY = -0.15D;
				}
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			if(this.field_9084_B && this.func_144_E()) {
				this.motionY = 0.2D;
			}

			this.motionY -= 0.08D;
			this.motionY *= (double)0.98F;
			this.motionX *= (double)var8;
			this.motionZ *= (double)var8;
		}

		this.field_9142_bc = this.field_9141_bd;
		var3 = this.posX - this.prevPosX;
		double var10 = this.posZ - this.prevPosZ;
		float var7 = MathHelper.sqrt_double(var3 * var3 + var10 * var10) * 4.0F;
		if(var7 > 1.0F) {
			var7 = 1.0F;
		}

		this.field_9141_bd += (var7 - this.field_9141_bd) * 0.4F;
		this.field_386_ba += this.field_9141_bd;
	}

	public boolean func_144_E() {
		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.boundingBox.minY);
		int var3 = MathHelper.floor_double(this.posZ);
		return this.worldObj.getBlockId(var1, var2, var3) == Block.ladder.blockID || this.worldObj.getBlockId(var1, var2 + 1, var3) == Block.ladder.blockID;
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		var1.setShort("Health", (short)this.field_9109_aQ);
		var1.setShort("HurtTime", (short)this.field_9107_aS);
		var1.setShort("DeathTime", (short)this.field_9104_aV);
		var1.setShort("AttackTime", (short)this.field_9103_aW);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		this.field_9109_aQ = var1.getShort("Health");
		if(!var1.hasKey("Health")) {
			this.field_9109_aQ = 10;
		}

		this.field_9107_aS = var1.getShort("HurtTime");
		this.field_9104_aV = var1.getShort("DeathTime");
		this.field_9103_aW = var1.getShort("AttackTime");
	}

	public boolean func_120_t() {
		return !this.field_304_B && this.field_9109_aQ > 0;
	}

	public void onLivingUpdate() {
		if(this.field_9140_bf > 0) {
			double var1 = this.posX + (this.field_9139_bg - this.posX) / (double)this.field_9140_bf;
			double var3 = this.posY + (this.field_9138_bh - this.posY) / (double)this.field_9140_bf;
			double var5 = this.posZ + (this.field_9137_bi - this.posZ) / (double)this.field_9140_bf;

			double var7;
			for(var7 = this.field_9136_bj - (double)this.rotationYaw; var7 < -180.0D; var7 += 360.0D) {
			}

			while(var7 >= 180.0D) {
				var7 -= 360.0D;
			}

			this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.field_9140_bf);
			this.rotationPitch = (float)((double)this.rotationPitch + (this.field_9135_bk - (double)this.rotationPitch) / (double)this.field_9140_bf);
			--this.field_9140_bf;
			this.setPosition(var1, var3, var5);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}

		if(this.field_9109_aQ <= 0) {
			this.field_9128_br = false;
			this.field_9131_bo = 0.0F;
			this.field_9130_bp = 0.0F;
			this.field_9129_bq = 0.0F;
		} else if(!this.field_9112_aN) {
			this.func_152_d_();
		}

		boolean var9 = this.handleWaterMovement();
		boolean var2 = this.func_112_q();
		if(this.field_9128_br) {
			if(var9) {
				this.motionY += (double)0.04F;
			} else if(var2) {
				this.motionY += (double)0.04F;
			} else if(this.onGround) {
				this.func_154_F();
			}
		}

		this.field_9131_bo *= 0.98F;
		this.field_9130_bp *= 0.98F;
		this.field_9129_bq *= 0.9F;
		this.func_148_c(this.field_9131_bo, this.field_9130_bp);
		List var10 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expands((double)0.2F, 0.0D, (double)0.2F));
		if(var10 != null && var10.size() > 0) {
			for(int var4 = 0; var4 < var10.size(); ++var4) {
				Entity var11 = (Entity)var10.get(var4);
				if(var11.func_124_r()) {
					var11.applyEntityCollision(this);
				}
			}
		}

	}

	protected void func_154_F() {
		this.motionY = (double)0.42F;
	}

	protected void func_152_d_() {
		++this.field_9132_bn;
		EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
		if(var1 != null) {
			double var2 = var1.posX - this.posX;
			double var4 = var1.posY - this.posY;
			double var6 = var1.posZ - this.posZ;
			double var8 = var2 * var2 + var4 * var4 + var6 * var6;
			if(var8 > 16384.0D) {
				this.setEntityDead();
			}

			if(this.field_9132_bn > 600 && this.field_9064_W.nextInt(800) == 0) {
				if(var8 < 1024.0D) {
					this.field_9132_bn = 0;
				} else {
					this.setEntityDead();
				}
			}
		}

		this.field_9131_bo = 0.0F;
		this.field_9130_bp = 0.0F;
		float var10 = 8.0F;
		if(this.field_9064_W.nextFloat() < 0.02F) {
			var1 = this.worldObj.getClosestPlayerToEntity(this, (double)var10);
			if(var1 != null) {
				this.b = var1;
				this.c = 10 + this.field_9064_W.nextInt(20);
			} else {
				this.field_9129_bq = (this.field_9064_W.nextFloat() - 0.5F) * 20.0F;
			}
		}

		if(this.b != null) {
			this.func_147_b(this.b, 10.0F);
			if(this.c-- <= 0 || this.b.field_304_B || this.b.getDistanceSqToEntity(this) > (double)(var10 * var10)) {
				this.b = null;
			}
		} else {
			if(this.field_9064_W.nextFloat() < 0.05F) {
				this.field_9129_bq = (this.field_9064_W.nextFloat() - 0.5F) * 20.0F;
			}

			this.rotationYaw += this.field_9129_bq;
			this.rotationPitch = this.field_9127_bs;
		}

		boolean var3 = this.handleWaterMovement();
		boolean var11 = this.func_112_q();
		if(var3 || var11) {
			this.field_9128_br = this.field_9064_W.nextFloat() < 0.8F;
		}

	}

	public void func_147_b(Entity var1, float var2) {
		double var3 = var1.posX - this.posX;
		double var7 = var1.posZ - this.posZ;
		double var5;
		if(var1 instanceof EntityLiving) {
			EntityLiving var9 = (EntityLiving)var1;
			var5 = var9.posY + (double)var9.func_104_p() - (this.posY + (double)this.func_104_p());
		} else {
			var5 = (var1.boundingBox.minY + var1.boundingBox.maxY) / 2.0D - (this.posY + (double)this.func_104_p());
		}

		double var13 = (double)MathHelper.sqrt_double(var3 * var3 + var7 * var7);
		float var11 = (float)(Math.atan2(var7, var3) * 180.0D / (double)((float)Math.PI)) - 90.0F;
		float var12 = (float)(Math.atan2(var5, var13) * 180.0D / (double)((float)Math.PI));
		this.rotationPitch = -this.func_140_b(this.rotationPitch, var12, var2);
		this.rotationYaw = this.func_140_b(this.rotationYaw, var11, var2);
	}

	private float func_140_b(float var1, float var2, float var3) {
		float var4;
		for(var4 = var2 - var1; var4 < -180.0F; var4 += 360.0F) {
		}

		while(var4 >= 180.0F) {
			var4 -= 360.0F;
		}

		if(var4 > var3) {
			var4 = var3;
		}

		if(var4 < -var3) {
			var4 = -var3;
		}

		return var1 + var4;
	}

	public void func_6101_K() {
	}

	public boolean getCanSpawnHere() {
		return this.worldObj.func_522_a(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.getIsAnyLiquid(this.boundingBox);
	}

	protected void func_4043_o() {
		this.attackEntity((Entity)null, 4);
	}

	public Vec3D func_4039_B() {
		return this.func_141_d(1.0F);
	}

	public Vec3D func_141_d(float var1) {
		float var2;
		float var3;
		float var4;
		float var5;
		if(var1 == 1.0F) {
			var2 = MathHelper.cos(-this.rotationYaw * ((float)Math.PI / 180.0F) - (float)Math.PI);
			var3 = MathHelper.sin(-this.rotationYaw * ((float)Math.PI / 180.0F) - (float)Math.PI);
			var4 = -MathHelper.cos(-this.rotationPitch * ((float)Math.PI / 180.0F));
			var5 = MathHelper.sin(-this.rotationPitch * ((float)Math.PI / 180.0F));
			return Vec3D.createVector((double)(var3 * var4), (double)var5, (double)(var2 * var4));
		} else {
			var2 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * var1;
			var3 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * var1;
			var4 = MathHelper.cos(-var3 * ((float)Math.PI / 180.0F) - (float)Math.PI);
			var5 = MathHelper.sin(-var3 * ((float)Math.PI / 180.0F) - (float)Math.PI);
			float var6 = -MathHelper.cos(-var2 * ((float)Math.PI / 180.0F));
			float var7 = MathHelper.sin(-var2 * ((float)Math.PI / 180.0F));
			return Vec3D.createVector((double)(var5 * var6), (double)var7, (double)(var4 * var6));
		}
	}

	public int func_4045_i() {
		return 4;
	}
}
