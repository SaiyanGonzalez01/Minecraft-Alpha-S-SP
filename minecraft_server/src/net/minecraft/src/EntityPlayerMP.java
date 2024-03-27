package net.minecraft.src;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import net.minecraft.server.MinecraftServer;

public class EntityPlayerMP extends EntityPlayer {
	public NetServerHandler field_421_a;
	public MinecraftServer mcServer;
	public ItemInWorldManager field_425_ad;
	public double field_9155_d;
	public double field_9154_e;
	public List field_422_ag = new LinkedList();
	public Set field_420_ah = new HashSet();
	public double field_418_ai;
	public boolean field_12012_al = false;
	private int field_9156_bu = -99999999;
	private int field_15004_bw = 60;

	public EntityPlayerMP(MinecraftServer var1, World var2, String var3, ItemInWorldManager var4) {
		super(var2);
		int var5 = var2.spawnX;
		int var6 = var2.spawnZ;
		int var7 = var2.spawnY;
		if(!var2.field_4272_q.field_4306_c) {
			var5 += this.field_9064_W.nextInt(20) - 10;
			var7 = var2.func_4075_e(var5, var6);
			var6 += this.field_9064_W.nextInt(20) - 10;
		}

		this.func_107_c((double)var5 + 0.5D, (double)var7, (double)var6 + 0.5D, 0.0F, 0.0F);
		this.mcServer = var1;
		this.field_9067_S = 0.0F;
		var4.field_675_a = this;
		this.username = var3;
		this.field_425_ad = var4;
		this.yOffset = 0.0F;
	}

	public void onUpdate() {
		--this.field_15004_bw;
	}

	public void onDeath(Entity var1) {
		this.inventory.dropAllItems();
	}

	public boolean attackEntity(Entity var1, int var2) {
		if(this.field_15004_bw > 0) {
			return false;
		} else {
			if(!this.mcServer.field_9011_n) {
				if(var1 instanceof EntityPlayer) {
					return false;
				}

				if(var1 instanceof EntityArrow) {
					EntityArrow var3 = (EntityArrow)var1;
					if(var3.field_439_ah instanceof EntityPlayer) {
						return false;
					}
				}
			}

			return super.attackEntity(var1, var2);
		}
	}

	public void heal(int var1) {
		super.heal(var1);
	}

	public void func_175_i() {
		super.onUpdate();
		ChunkCoordIntPair var1 = null;
		double var2 = 0.0D;

		for(int var4 = 0; var4 < this.field_422_ag.size(); ++var4) {
			ChunkCoordIntPair var5 = (ChunkCoordIntPair)this.field_422_ag.get(var4);
			double var6 = var5.func_73_a(this);
			if(var4 == 0 || var6 < var2) {
				var1 = var5;
				var2 = var5.func_73_a(this);
			}
		}

		if(var1 != null) {
			boolean var8 = false;
			if(var2 < 1024.0D) {
				var8 = true;
			}

			if(this.field_421_a.func_38_b() < 2) {
				var8 = true;
			}

			if(var8) {
				this.field_422_ag.remove(var1);
				this.field_421_a.sendPacket(new Packet51MapChunk(var1.field_152_a * 16, 0, var1.field_151_b * 16, 16, 128, 16, this.mcServer.worldMngr));
				List var9 = this.mcServer.worldMngr.func_532_d(var1.field_152_a * 16, 0, var1.field_151_b * 16, var1.field_152_a * 16 + 16, 128, var1.field_151_b * 16 + 16);

				for(int var10 = 0; var10 < var9.size(); ++var10) {
					TileEntity var7 = (TileEntity)var9.get(var10);
					this.field_421_a.sendPacket(new Packet59ComplexEntity(var7.xCoord, var7.yCoord, var7.zCoord, var7));
				}
			}
		}

		if(this.field_9109_aQ != this.field_9156_bu) {
			this.field_421_a.sendPacket(new Packet8(this.field_9109_aQ));
			this.field_9156_bu = this.field_9109_aQ;
		}

	}

	public void onLivingUpdate() {
		this.motionX = this.motionY = this.motionZ = 0.0D;
		this.field_9128_br = false;
		super.onLivingUpdate();
	}

	public void func_163_c(Entity var1, int var2) {
		if(!var1.field_304_B) {
			if(var1 instanceof EntityItem) {
				this.field_421_a.sendPacket(new Packet17AddToInventory(((EntityItem)var1).item, var2));
				this.mcServer.field_6028_k.func_12021_a(var1, new Packet22Collect(var1.field_331_c, this.field_331_c));
			}

			if(var1 instanceof EntityArrow) {
				this.field_421_a.sendPacket(new Packet17AddToInventory(new ItemStack(Item.arrow), 1));
				this.mcServer.field_6028_k.func_12021_a(var1, new Packet22Collect(var1.field_331_c, this.field_331_c));
			}
		}

		super.func_163_c(var1, var2);
	}

	public void func_168_z() {
		if(!this.field_9148_aq) {
			this.field_9147_ar = -1;
			this.field_9148_aq = true;
			this.mcServer.field_6028_k.func_12021_a(this, new Packet18ArmAnimation(this, 1));
		}

	}

	public float func_104_p() {
		return 1.62F;
	}

	public void func_6094_e(Entity var1) {
		super.func_6094_e(var1);
		this.field_421_a.sendPacket(new Packet39(this, this.field_327_g));
		this.field_421_a.func_41_a(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
	}

	protected void interact(double var1, boolean var3) {
	}

	public void func_9153_b(double var1, boolean var3) {
		super.interact(var1, var3);
	}

	public boolean func_9059_p() {
		return this.field_12012_al;
	}
}
