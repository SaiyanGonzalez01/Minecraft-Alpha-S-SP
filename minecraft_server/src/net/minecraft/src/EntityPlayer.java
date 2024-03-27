package net.minecraft.src;

import java.util.List;

public class EntityPlayer extends EntityLiving {
	public InventoryPlayer inventory = new InventoryPlayer(this);
	public byte field_9152_am = 0;
	public int field_9151_an = 0;
	public float field_9150_ao;
	public float field_9149_ap;
	public boolean field_9148_aq = false;
	public int field_9147_ar = 0;
	public String username;
	public int field_4110_as;
	private int a = 0;
	public EntityFish field_6124_at = null;

	public EntityPlayer(World var1) {
		super(var1);
		this.yOffset = 1.62F;
		this.func_107_c((double)var1.spawnX + 0.5D, (double)(var1.spawnY + 1), (double)var1.spawnZ + 0.5D, 0.0F, 0.0F);
		this.field_9109_aQ = 20;
		this.field_9116_aJ = "humanoid";
		this.field_9117_aI = 180.0F;
		this.field_9062_Y = 20;
		this.field_9119_aG = "/mob/char.png";
	}

	public void func_115_v() {
		super.func_115_v();
		this.field_9150_ao = this.field_9149_ap;
		this.field_9149_ap = 0.0F;
	}

	protected void func_152_d_() {
		if(this.field_9148_aq) {
			++this.field_9147_ar;
			if(this.field_9147_ar == 8) {
				this.field_9147_ar = 0;
				this.field_9148_aq = false;
			}
		} else {
			this.field_9147_ar = 0;
		}

		this.field_9110_aP = (float)this.field_9147_ar / 8.0F;
	}

	public void onLivingUpdate() {
		if(this.worldObj.monstersEnabled == 0 && this.field_9109_aQ < 20 && this.field_9063_X % 20 * 4 == 0) {
			this.heal(1);
		}

		this.inventory.decrementAnimations();
		this.field_9150_ao = this.field_9149_ap;
		super.onLivingUpdate();
		float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		float var2 = (float)Math.atan(-this.motionY * (double)0.2F) * 15.0F;
		if(var1 > 0.1F) {
			var1 = 0.1F;
		}

		if(!this.onGround || this.field_9109_aQ <= 0) {
			var1 = 0.0F;
		}

		if(this.onGround || this.field_9109_aQ <= 0) {
			var2 = 0.0F;
		}

		this.field_9149_ap += (var1 - this.field_9149_ap) * 0.4F;
		this.field_9101_aY += (var2 - this.field_9101_aY) * 0.8F;
		if(this.field_9109_aQ > 0) {
			List var3 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expands(1.0D, 0.0D, 1.0D));
			if(var3 != null) {
				for(int var4 = 0; var4 < var3.size(); ++var4) {
					this.func_171_h((Entity)var3.get(var4));
				}
			}
		}

	}

	private void func_171_h(Entity var1) {
		var1.onCollideWithPlayer(this);
	}

	public void onDeath(Entity var1) {
		super.onDeath(var1);
		this.setSize(0.2F, 0.2F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.motionY = (double)0.1F;
		if(this.username.equals("Notch")) {
			this.func_169_a(new ItemStack(Item.appleRed, 1), true);
		}

		this.inventory.dropAllItems();
		if(var1 != null) {
			this.motionX = (double)(-MathHelper.cos((this.field_9105_aU + this.rotationYaw) * (float)Math.PI / 180.0F) * 0.1F);
			this.motionZ = (double)(-MathHelper.sin((this.field_9105_aU + this.rotationYaw) * (float)Math.PI / 180.0F) * 0.1F);
		} else {
			this.motionX = this.motionZ = 0.0D;
		}

		this.yOffset = 0.1F;
	}

	public void func_96_b(Entity var1, int var2) {
		this.field_9151_an += var2;
	}

	public void func_161_a(ItemStack var1) {
		this.func_169_a(var1, false);
	}

	public void func_169_a(ItemStack var1, boolean var2) {
		if(var1 != null) {
			EntityItem var3 = new EntityItem(this.worldObj, this.posX, this.posY - (double)0.3F + (double)this.func_104_p(), this.posZ, var1);
			var3.field_433_ad = 40;
			float var4 = 0.1F;
			float var5;
			if(var2) {
				var5 = this.field_9064_W.nextFloat() * 0.5F;
				float var6 = this.field_9064_W.nextFloat() * (float)Math.PI * 2.0F;
				var3.motionX = (double)(-MathHelper.sin(var6) * var5);
				var3.motionZ = (double)(MathHelper.cos(var6) * var5);
				var3.motionY = (double)0.2F;
			} else {
				var4 = 0.3F;
				var3.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var4);
				var3.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var4);
				var3.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI) * var4 + 0.1F);
				var4 = 0.02F;
				var5 = this.field_9064_W.nextFloat() * (float)Math.PI * 2.0F;
				var4 *= this.field_9064_W.nextFloat();
				var3.motionX += Math.cos((double)var5) * (double)var4;
				var3.motionY += (double)((this.field_9064_W.nextFloat() - this.field_9064_W.nextFloat()) * 0.1F);
				var3.motionZ += Math.sin((double)var5) * (double)var4;
			}

			this.func_162_a(var3);
		}
	}

	protected void func_162_a(EntityItem var1) {
		this.worldObj.entityJoinedWorld(var1);
	}

	public float getCurrentPlayerStrVsBlock(Block var1) {
		float var2 = this.inventory.getStrVsBlock(var1);
		if(this.isInsideOfMaterial(Material.water)) {
			var2 /= 5.0F;
		}

		if(!this.onGround) {
			var2 /= 5.0F;
		}

		return var2;
	}

	public boolean func_167_b(Block var1) {
		return this.inventory.canHarvestBlock(var1);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
		NBTTagList var2 = var1.getTagList("Inventory");
		this.inventory.readFromNBT(var2);
		this.field_4110_as = var1.getInteger("Dimension");
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
		var1.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
		var1.setInteger("Dimension", this.field_4110_as);
	}

	public void func_166_a(IInventory var1) {
	}

	public void func_174_A() {
	}

	public void func_163_c(Entity var1, int var2) {
	}

	public float func_104_p() {
		return 0.12F;
	}

	public boolean attackEntity(Entity var1, int var2) {
		this.field_9132_bn = 0;
		if(this.field_9109_aQ <= 0) {
			return false;
		} else {
			if(var1 instanceof EntityMobs || var1 instanceof EntityArrow) {
				if(this.worldObj.monstersEnabled == 0) {
					var2 = 0;
				}

				if(this.worldObj.monstersEnabled == 1) {
					var2 = var2 / 3 + 1;
				}

				if(this.worldObj.monstersEnabled == 3) {
					var2 = var2 * 3 / 2;
				}
			}

			return var2 == 0 ? false : super.attackEntity(var1, var2);
		}
	}

	protected void func_6099_c(int var1) {
		int var2 = 25 - this.inventory.getTotalArmorValue();
		int var3 = var1 * var2 + this.a;
		this.inventory.damageArmor(var1);
		var1 = var3 / 25;
		this.a = var3 % 25;
		super.func_6099_c(var1);
	}

	public void func_170_a(TileEntityFurnace var1) {
	}

	public void func_4048_a(TileEntitySign var1) {
	}

	public void func_9145_g(Entity var1) {
		var1.func_6092_a(this);
	}

	public ItemStack func_172_B() {
		return this.inventory.getCurrentItem();
	}

	public void func_164_C() {
		this.inventory.setInventorySlotContents(this.inventory.currentItem, (ItemStack)null);
	}

	public double func_117_x() {
		return (double)(this.yOffset - 0.5F);
	}

	public void func_168_z() {
		this.field_9147_ar = -1;
		this.field_9148_aq = true;
	}

	public void func_9146_h(Entity var1) {
		int var2 = this.inventory.func_9157_a(var1);
		if(var2 > 0) {
			var1.attackEntity(this, var2);
			ItemStack var3 = this.func_172_B();
			if(var3 != null && var1 instanceof EntityLiving) {
				var3.func_9217_a((EntityLiving)var1);
				if(var3.stackSize <= 0) {
					var3.func_577_a(this);
					this.func_164_C();
				}
			}
		}

	}
}
