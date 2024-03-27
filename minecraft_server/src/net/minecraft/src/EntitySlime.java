package net.minecraft.src;

public class EntitySlime extends EntityLiving implements IMobs {
	public float field_401_a;
	public float field_400_b;
	private int field_402_ae = 0;
	public int field_403_ad = 1;

	public EntitySlime(World var1) {
		super(var1);
		this.field_9119_aG = "/mob/slime.png";
		this.field_403_ad = 1 << this.field_9064_W.nextInt(3);
		this.yOffset = 0.0F;
		this.field_402_ae = this.field_9064_W.nextInt(20) + 10;
		this.func_160_c(this.field_403_ad);
	}

	public void func_160_c(int var1) {
		this.field_403_ad = var1;
		this.setSize(0.6F * (float)var1, 0.6F * (float)var1);
		this.field_9109_aQ = var1 * var1;
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
		var1.setInteger("Size", this.field_403_ad - 1);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
		this.field_403_ad = var1.getInteger("Size") + 1;
	}

	public void onUpdate() {
		this.field_400_b = this.field_401_a;
		boolean var1 = this.onGround;
		super.onUpdate();
		if(this.onGround && !var1) {
			for(int var2 = 0; var2 < this.field_403_ad * 8; ++var2) {
				float var3 = this.field_9064_W.nextFloat() * (float)Math.PI * 2.0F;
				float var4 = this.field_9064_W.nextFloat() * 0.5F + 0.5F;
				float var5 = MathHelper.sin(var3) * (float)this.field_403_ad * 0.5F * var4;
				float var6 = MathHelper.cos(var3) * (float)this.field_403_ad * 0.5F * var4;
				this.worldObj.spawnParticle("slime", this.posX + (double)var5, this.boundingBox.minY, this.posZ + (double)var6, 0.0D, 0.0D, 0.0D);
			}

			if(this.field_403_ad > 2) {
				this.worldObj.playSoundAtEntity(this, "mob.slime", this.getSoundVolume(), ((this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			}

			this.field_401_a = -0.5F;
		}

		this.field_401_a *= 0.6F;
	}

	protected void func_152_d_() {
		EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 16.0D);
		if(var1 != null) {
			this.func_147_b(var1, 10.0F);
		}

		if(this.onGround && this.field_402_ae-- <= 0) {
			this.field_402_ae = this.field_9064_W.nextInt(20) + 10;
			if(var1 != null) {
				this.field_402_ae /= 3;
			}

			this.field_9128_br = true;
			if(this.field_403_ad > 1) {
				this.worldObj.playSoundAtEntity(this, "mob.slime", this.getSoundVolume(), ((this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.2F + 1.0F) * 0.8F);
			}

			this.field_401_a = 1.0F;
			this.field_9131_bo = 1.0F - this.field_9064_W.nextFloat() * 2.0F;
			this.field_9130_bp = (float)(1 * this.field_403_ad);
		} else {
			this.field_9128_br = false;
			if(this.onGround) {
				this.field_9131_bo = this.field_9130_bp = 0.0F;
			}
		}

	}

	public void setEntityDead() {
		if(this.field_403_ad > 1 && this.field_9109_aQ == 0) {
			for(int var1 = 0; var1 < 4; ++var1) {
				float var2 = ((float)(var1 % 2) - 0.5F) * (float)this.field_403_ad / 4.0F;
				float var3 = ((float)(var1 / 2) - 0.5F) * (float)this.field_403_ad / 4.0F;
				EntitySlime var4 = new EntitySlime(this.worldObj);
				var4.func_160_c(this.field_403_ad / 2);
				var4.func_107_c(this.posX + (double)var2, this.posY + 0.5D, this.posZ + (double)var3, this.field_9064_W.nextFloat() * 360.0F, 0.0F);
				this.worldObj.entityJoinedWorld(var4);
			}
		}

		super.setEntityDead();
	}

	public void onCollideWithPlayer(EntityPlayer var1) {
		if(this.field_403_ad > 1 && this.func_145_g(var1) && (double)this.getDistanceToEntity(var1) < 0.6D * (double)this.field_403_ad && var1.attackEntity(this, this.field_403_ad)) {
			this.worldObj.playSoundAtEntity(this, "mob.slimeattack", 1.0F, (this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.2F + 1.0F);
		}

	}

	protected String getHurtSound() {
		return "mob.slime";
	}

	protected String getDeathSound() {
		return "mob.slime";
	}

	protected int getDropItemId() {
		return this.field_403_ad == 1 ? Item.slimeBall.swiftedIndex : 0;
	}

	public boolean getCanSpawnHere() {
		Chunk var1 = this.worldObj.getChunkFromBlockCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
		return (this.field_403_ad == 1 || this.worldObj.monstersEnabled > 0) && this.field_9064_W.nextInt(10) == 0 && var1.func_334_a(987234911L).nextInt(10) == 0 && this.posY < 16.0D;
	}

	protected float getSoundVolume() {
		return 0.6F;
	}
}
