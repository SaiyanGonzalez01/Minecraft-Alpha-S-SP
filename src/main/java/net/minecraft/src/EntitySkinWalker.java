package net.minecraft.src;

public class EntitySkinWalker extends EntityMobs {
	public EntitySkinWalker(World var1) {
		super(var1);
		this.texture = "/mob/skinwalker.png";
		this.field_9333_am = 0.5F;
		this.field_762_e = 5;
	}

	protected String getLivingSound() {
		return "mob.skin";
	}

	protected String getHurtSound() {
		return "mob.skinhurt";
	}

	protected String getDeathSound() {
		return "mob.skindeath";
	}

	protected int getDropItemId() {
		return Item.feather.shiftedIndex;
	}
}
