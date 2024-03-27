package net.minecraft.src;

public class EntityPig extends EntityAnimals {
	public boolean rideable = false;

	public EntityPig(World var1) {
		super(var1);
		this.field_9119_aG = "/mob/pig.png";
		this.setSize(0.9F, 0.9F);
		this.rideable = false;
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
		var1.setBoolean("Saddle", this.rideable);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
		this.rideable = var1.getBoolean("Saddle");
	}

	protected String getLivingSound() {
		return "mob.pig";
	}

	protected String getHurtSound() {
		return "mob.pig";
	}

	protected String getDeathSound() {
		return "mob.pigdeath";
	}

	public boolean func_6092_a(EntityPlayer var1) {
		if(this.rideable) {
			var1.func_6094_e(this);
			return true;
		} else {
			return false;
		}
	}

	protected int getDropItemId() {
		return Item.porkRaw.swiftedIndex;
	}
}
