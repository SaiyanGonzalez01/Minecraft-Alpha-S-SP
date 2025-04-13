package net.minecraft.src;

public class EntityZombie extends EntityMobs {
	public EntityZombie(World var1) {
		super(var1);
		this.texture = "/mob/char.png";
		this.field_9333_am = 0.5F;
		this.field_762_e = 5;
	}

	protected String getLivingSound() {
		return "mob.zombie";
	}

	protected String getHurtSound() {
		return "mob.zombiehurt";
	}

	protected String getDeathSound() {
		return "mob.zombiedeath";
	}

	protected int getDropItemId() {
		return Item.feather.shiftedIndex;
	}
}
