package net.minecraft.src;

public class EntitySheep extends EntityAnimals {
	public boolean sheared = false;

	public EntitySheep(World var1) {
		super(var1);
		this.field_9119_aG = "/mob/sheep.png";
		this.setSize(0.9F, 1.3F);
	}

	public boolean attackEntity(Entity var1, int var2) {
		if(!this.worldObj.multiplayerWorld && !this.sheared && var1 instanceof EntityLiving) {
			this.sheared = true;
			int var3 = 1 + this.field_9064_W.nextInt(3);

			for(int var4 = 0; var4 < var3; ++var4) {
				EntityItem var5 = this.dropItemWithOffset(Block.cloth.blockID, 1, 1.0F);
				var5.motionY += (double)(this.field_9064_W.nextFloat() * 0.05F);
				var5.motionX += (double)((this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.1F);
				var5.motionZ += (double)((this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.1F);
			}
		}

		return super.attackEntity(var1, var2);
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
		var1.setBoolean("Sheared", this.sheared);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
		this.sheared = var1.getBoolean("Sheared");
	}

	protected String getLivingSound() {
		return "mob.sheep";
	}

	protected String getHurtSound() {
		return "mob.sheep";
	}

	protected String getDeathSound() {
		return "mob.sheep";
	}
}
