package net.minecraft.src;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EntityTrackerEntry {
	public Entity field_909_a;
	public boolean field_9237_b = false;
	public boolean field_9236_c = false;
	public boolean field_9225_n = false;
	public int field_9235_d;
	public int field_9234_e;
	public int field_9233_f;
	public int field_9232_g;
	public int field_9231_h;
	public int field_9230_i;
	public int field_9229_j;
	public double field_9228_k;
	public double field_9227_l;
	public double field_9226_m;
	public int field_9221_t = 0;
	private double field_9224_q;
	private double field_9223_r;
	private double field_9222_s;
	private boolean field_12020_u = false;
	private boolean field_9220_u;
	public boolean field_900_j = false;
	public Set field_899_k = new HashSet();

	public EntityTrackerEntry(Entity var1, int var2, int var3, boolean var4) {
		this.field_909_a = var1;
		this.field_9235_d = var2;
		this.field_9234_e = var3;
		this.field_9220_u = var4;
		this.field_9233_f = MathHelper.floor_double(var1.posX * 32.0D);
		this.field_9232_g = MathHelper.floor_double(var1.posY * 32.0D);
		this.field_9231_h = MathHelper.floor_double(var1.posZ * 32.0D);
		this.field_9230_i = MathHelper.floor_float(var1.rotationYaw * 256.0F / 360.0F);
		this.field_9229_j = MathHelper.floor_float(var1.rotationPitch * 256.0F / 360.0F);
	}

	public boolean equals(Object var1) {
		return var1 instanceof EntityTrackerEntry ? ((EntityTrackerEntry)var1).field_909_a.field_331_c == this.field_909_a.field_331_c : false;
	}

	public int hashCode() {
		return this.field_909_a.field_331_c;
	}

	public void func_605_a(List var1) {
		this.field_900_j = false;
		if(!this.field_12020_u || this.field_909_a.getDistanceSq(this.field_9224_q, this.field_9223_r, this.field_9222_s) > 16.0D) {
			this.field_9224_q = this.field_909_a.posX;
			this.field_9223_r = this.field_909_a.posY;
			this.field_9222_s = this.field_909_a.posZ;
			this.field_12020_u = true;
			this.field_900_j = true;
			this.func_601_b(var1);
		}

		if(this.field_9221_t++ % this.field_9234_e == 0) {
			int var2 = MathHelper.floor_double(this.field_909_a.posX * 32.0D);
			int var3 = MathHelper.floor_double(this.field_909_a.posY * 32.0D);
			int var4 = MathHelper.floor_double(this.field_909_a.posZ * 32.0D);
			int var5 = MathHelper.floor_float(this.field_909_a.rotationYaw * 256.0F / 360.0F);
			int var6 = MathHelper.floor_float(this.field_909_a.rotationPitch * 256.0F / 360.0F);
			boolean var7 = var2 != this.field_9233_f || var3 != this.field_9232_g || var4 != this.field_9231_h;
			boolean var8 = var5 != this.field_9230_i || var6 != this.field_9229_j;
			int var9 = var2 - this.field_9233_f;
			int var10 = var3 - this.field_9232_g;
			int var11 = var4 - this.field_9231_h;
			Object var12 = null;
			if(var9 >= -128 && var9 < 128 && var10 >= -128 && var10 < 128 && var11 >= -128 && var11 < 128) {
				if(var7 && var8) {
					var12 = new Packet33RelEntityMoveLook(this.field_909_a.field_331_c, (byte)var9, (byte)var10, (byte)var11, (byte)var5, (byte)var6);
				} else if(var7) {
					var12 = new Packet31RelEntityMove(this.field_909_a.field_331_c, (byte)var9, (byte)var10, (byte)var11);
				} else if(var8) {
					var12 = new Packet32EntityLook(this.field_909_a.field_331_c, (byte)var5, (byte)var6);
				} else {
					var12 = new Packet30Entity(this.field_909_a.field_331_c);
				}
			} else {
				var12 = new Packet34EntityTeleport(this.field_909_a.field_331_c, var2, var3, var4, (byte)var5, (byte)var6);
			}

			if(this.field_9220_u) {
				double var13 = this.field_909_a.motionX - this.field_9228_k;
				double var15 = this.field_909_a.motionY - this.field_9227_l;
				double var17 = this.field_909_a.motionZ - this.field_9226_m;
				double var19 = 0.02D;
				double var21 = var13 * var13 + var15 * var15 + var17 * var17;
				if(var21 > var19 * var19 || var21 > 0.0D && this.field_909_a.motionX == 0.0D && this.field_909_a.motionY == 0.0D && this.field_909_a.motionZ == 0.0D) {
					this.field_9228_k = this.field_909_a.motionX;
					this.field_9227_l = this.field_909_a.motionY;
					this.field_9226_m = this.field_909_a.motionZ;
					this.func_603_a(new Packet28(this.field_909_a.field_331_c, this.field_9228_k, this.field_9227_l, this.field_9226_m));
				}
			}

			if(var12 != null) {
				this.func_603_a((Packet)var12);
			}

			if(this.field_9237_b && this.field_909_a.field_327_g == null) {
				this.field_9237_b = false;
				this.func_12018_b(new Packet18ArmAnimation(this.field_909_a, 101));
			} else if(!this.field_9237_b && this.field_909_a.field_327_g != null) {
				this.field_9237_b = true;
				this.func_12018_b(new Packet18ArmAnimation(this.field_909_a, 100));
			}

			if(this.field_909_a instanceof EntityLiving) {
				if(this.field_9225_n && !this.field_909_a.func_9059_p()) {
					this.field_9225_n = false;
					this.func_12018_b(new Packet18ArmAnimation(this.field_909_a, 105));
				} else if(!this.field_9225_n && this.field_909_a.func_9059_p()) {
					this.field_9225_n = true;
					this.func_12018_b(new Packet18ArmAnimation(this.field_909_a, 104));
				}
			}

			if(this.field_9236_c && this.field_909_a.field_9061_Z <= 0) {
				this.field_9236_c = false;
				this.func_12018_b(new Packet18ArmAnimation(this.field_909_a, 103));
			} else if(!this.field_9236_c && this.field_909_a.field_9061_Z > 0) {
				this.field_9236_c = true;
				this.func_12018_b(new Packet18ArmAnimation(this.field_909_a, 102));
			}

			this.field_9233_f = var2;
			this.field_9232_g = var3;
			this.field_9231_h = var4;
			this.field_9230_i = var5;
			this.field_9229_j = var6;
		}

		if(this.field_909_a.field_9078_E) {
			this.func_12018_b(new Packet28(this.field_909_a));
			this.field_909_a.field_9078_E = false;
		}

	}

	public void func_603_a(Packet var1) {
		Iterator var2 = this.field_899_k.iterator();

		while(var2.hasNext()) {
			EntityPlayerMP var3 = (EntityPlayerMP)var2.next();
			var3.field_421_a.sendPacket(var1);
		}

	}

	public void func_12018_b(Packet var1) {
		this.func_603_a(var1);
		if(this.field_909_a instanceof EntityPlayerMP) {
			((EntityPlayerMP)this.field_909_a).field_421_a.sendPacket(var1);
		}

	}

	public void func_604_a() {
		this.func_603_a(new Packet29DestroyEntity(this.field_909_a.field_331_c));
	}

	public void func_12019_a(EntityPlayerMP var1) {
		if(this.field_899_k.contains(var1)) {
			this.field_899_k.remove(var1);
		}

	}

	public void func_606_a(EntityPlayerMP var1) {
		if(var1 != this.field_909_a) {
			double var2 = var1.posX - (double)(this.field_9233_f / 32);
			double var4 = var1.posZ - (double)(this.field_9231_h / 32);
			if(var2 >= (double)(-this.field_9235_d) && var2 <= (double)this.field_9235_d && var4 >= (double)(-this.field_9235_d) && var4 <= (double)this.field_9235_d) {
				if(!this.field_899_k.contains(var1)) {
					this.field_899_k.add(var1);
					var1.field_421_a.sendPacket(this.func_602_b());
					if(this.field_9225_n) {
						var1.field_421_a.sendPacket(new Packet18ArmAnimation(this.field_909_a, 104));
					}

					if(this.field_9237_b) {
						var1.field_421_a.sendPacket(new Packet18ArmAnimation(this.field_909_a, 100));
					}

					if(this.field_9236_c) {
						var1.field_421_a.sendPacket(new Packet18ArmAnimation(this.field_909_a, 102));
					}

					if(this.field_9220_u) {
						var1.field_421_a.sendPacket(new Packet28(this.field_909_a.field_331_c, this.field_909_a.motionX, this.field_909_a.motionY, this.field_909_a.motionZ));
					}
				}
			} else if(this.field_899_k.contains(var1)) {
				this.field_899_k.remove(var1);
				var1.field_421_a.sendPacket(new Packet29DestroyEntity(this.field_909_a.field_331_c));
			}

		}
	}

	public void func_601_b(List var1) {
		for(int var2 = 0; var2 < var1.size(); ++var2) {
			this.func_606_a((EntityPlayerMP)var1.get(var2));
		}

	}

	private Packet func_602_b() {
		if(this.field_909_a instanceof EntityItem) {
			EntityItem var3 = (EntityItem)this.field_909_a;
			Packet21PickupSpawn var2 = new Packet21PickupSpawn(var3);
			var3.posX = (double)var2.xPosition / 32.0D;
			var3.posY = (double)var2.yPosition / 32.0D;
			var3.posZ = (double)var2.zPosition / 32.0D;
			return var2;
		} else if(this.field_909_a instanceof EntityPlayerMP) {
			return new Packet20NamedEntitySpawn((EntityPlayer)this.field_909_a);
		} else {
			if(this.field_909_a instanceof EntityMinecart) {
				EntityMinecart var1 = (EntityMinecart)this.field_909_a;
				if(var1.minecartType == 0) {
					return new Packet23VehicleSpawn(this.field_909_a, 10);
				}

				if(var1.minecartType == 1) {
					return new Packet23VehicleSpawn(this.field_909_a, 11);
				}

				if(var1.minecartType == 2) {
					return new Packet23VehicleSpawn(this.field_909_a, 12);
				}
			}

			if(this.field_909_a instanceof EntityBoat) {
				return new Packet23VehicleSpawn(this.field_909_a, 1);
			} else if(this.field_909_a instanceof IAnimals) {
				return new Packet24MobSpawn((EntityLiving)this.field_909_a);
			} else if(this.field_909_a instanceof EntityFish) {
				return new Packet23VehicleSpawn(this.field_909_a, 90);
			} else if(this.field_909_a instanceof EntityArrow) {
				return new Packet23VehicleSpawn(this.field_909_a, 60);
			} else if(this.field_909_a instanceof EntitySnowball) {
				return new Packet23VehicleSpawn(this.field_909_a, 61);
			} else if(this.field_909_a instanceof EntityTNTPrimed) {
				return new Packet23VehicleSpawn(this.field_909_a, 50);
			} else {
				throw new IllegalArgumentException("Don\'t know how to add " + this.field_909_a.getClass() + "!");
			}
		}
	}

	public void func_9219_b(EntityPlayerMP var1) {
		if(this.field_899_k.contains(var1)) {
			this.field_899_k.remove(var1);
			var1.field_421_a.sendPacket(new Packet29DestroyEntity(this.field_909_a.field_331_c));
		}

	}
}
