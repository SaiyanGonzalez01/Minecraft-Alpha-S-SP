package net.minecraft.src;

public class EntityCreature extends EntityLiving {
	private PathEntity field_388_a;
	protected Entity field_389_ag;
	protected boolean field_387_ah = false;

	public EntityCreature(World var1) {
		super(var1);
	}

	protected void func_152_d_() {
		this.field_387_ah = false;
		float var1 = 16.0F;
		if(this.field_389_ag == null) {
			this.field_389_ag = this.func_158_i();
			if(this.field_389_ag != null) {
				this.field_388_a = this.worldObj.func_482_a(this, this.field_389_ag, var1);
			}
		} else if(!this.field_389_ag.func_120_t()) {
			this.field_389_ag = null;
		} else {
			float var2 = this.field_389_ag.getDistanceToEntity(this);
			if(this.func_145_g(this.field_389_ag)) {
				this.func_157_a(this.field_389_ag, var2);
			}
		}

		if(this.field_387_ah || this.field_389_ag == null || this.field_388_a != null && this.field_9064_W.nextInt(20) != 0) {
			if(this.field_388_a == null && this.field_9064_W.nextInt(80) == 0 || this.field_9064_W.nextInt(80) == 0) {
				boolean var21 = false;
				int var3 = -1;
				int var4 = -1;
				int var5 = -1;
				float var6 = -99999.0F;

				for(int var7 = 0; var7 < 10; ++var7) {
					int var8 = MathHelper.floor_double(this.posX + (double)this.field_9064_W.nextInt(13) - 6.0D);
					int var9 = MathHelper.floor_double(this.posY + (double)this.field_9064_W.nextInt(7) - 3.0D);
					int var10 = MathHelper.floor_double(this.posZ + (double)this.field_9064_W.nextInt(13) - 6.0D);
					float var11 = this.func_159_a(var8, var9, var10);
					if(var11 > var6) {
						var6 = var11;
						var3 = var8;
						var4 = var9;
						var5 = var10;
						var21 = true;
					}
				}

				if(var21) {
					this.field_388_a = this.worldObj.func_501_a(this, var3, var4, var5, 10.0F);
				}
			}
		} else {
			this.field_388_a = this.worldObj.func_482_a(this, this.field_389_ag, var1);
		}

		int var22 = MathHelper.floor_double(this.boundingBox.minY);
		boolean var23 = this.handleWaterMovement();
		boolean var24 = this.func_112_q();
		this.rotationPitch = 0.0F;
		if(this.field_388_a != null && this.field_9064_W.nextInt(100) != 0) {
			Vec3D var25 = this.field_388_a.getPosition(this);
			double var26 = (double)(this.width * 2.0F);

			while(var25 != null && var25.squareDistanceTo(this.posX, var25.yCoord, this.posZ) < var26 * var26) {
				this.field_388_a.incrementPathIndex();
				if(this.field_388_a.isFinished()) {
					var25 = null;
					this.field_388_a = null;
				} else {
					var25 = this.field_388_a.getPosition(this);
				}
			}

			this.field_9128_br = false;
			if(var25 != null) {
				double var27 = var25.xCoord - this.posX;
				double var28 = var25.zCoord - this.posZ;
				double var12 = var25.yCoord - (double)var22;
				float var14 = (float)(Math.atan2(var28, var27) * 180.0D / (double)((float)Math.PI)) - 90.0F;
				float var15 = var14 - this.rotationYaw;

				for(this.field_9130_bp = this.field_9126_bt; var15 < -180.0F; var15 += 360.0F) {
				}

				while(var15 >= 180.0F) {
					var15 -= 360.0F;
				}

				if(var15 > 30.0F) {
					var15 = 30.0F;
				}

				if(var15 < -30.0F) {
					var15 = -30.0F;
				}

				this.rotationYaw += var15;
				if(this.field_387_ah && this.field_389_ag != null) {
					double var16 = this.field_389_ag.posX - this.posX;
					double var18 = this.field_389_ag.posZ - this.posZ;
					float var20 = this.rotationYaw;
					this.rotationYaw = (float)(Math.atan2(var18, var16) * 180.0D / (double)((float)Math.PI)) - 90.0F;
					var15 = (var20 - this.rotationYaw + 90.0F) * (float)Math.PI / 180.0F;
					this.field_9131_bo = -MathHelper.sin(var15) * this.field_9130_bp * 1.0F;
					this.field_9130_bp = MathHelper.cos(var15) * this.field_9130_bp * 1.0F;
				}

				if(var12 > 0.0D) {
					this.field_9128_br = true;
				}
			}

			if(this.field_389_ag != null) {
				this.func_147_b(this.field_389_ag, 30.0F);
			}

			if(this.field_9084_B) {
				this.field_9128_br = true;
			}

			if(this.field_9064_W.nextFloat() < 0.8F && (var23 || var24)) {
				this.field_9128_br = true;
			}

		} else {
			super.func_152_d_();
			this.field_388_a = null;
		}
	}

	protected void func_157_a(Entity var1, float var2) {
	}

	protected float func_159_a(int var1, int var2, int var3) {
		return 0.0F;
	}

	protected Entity func_158_i() {
		return null;
	}

	public boolean getCanSpawnHere() {
		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.boundingBox.minY);
		int var3 = MathHelper.floor_double(this.posZ);
		return super.getCanSpawnHere() && this.func_159_a(var1, var2, var3) >= 0.0F;
	}
}
