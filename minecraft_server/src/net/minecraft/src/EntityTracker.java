package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.server.MinecraftServer;

public class EntityTracker {
	private Set field_911_a = new HashSet();
	private MCHashTable field_910_b = new MCHashTable();
	private MinecraftServer mcServer;
	private int field_912_d;

	public EntityTracker(MinecraftServer var1) {
		this.mcServer = var1;
		this.field_912_d = var1.configManager.func_640_a();
	}

	public void func_611_a(Entity var1) {
		if(var1 instanceof EntityPlayerMP) {
			this.func_6187_a(var1, 512, 2);
			EntityPlayerMP var2 = (EntityPlayerMP)var1;
			Iterator var3 = this.field_911_a.iterator();

			while(var3.hasNext()) {
				EntityTrackerEntry var4 = (EntityTrackerEntry)var3.next();
				if(var4.field_909_a != var2) {
					var4.func_606_a(var2);
				}
			}
		} else if(var1 instanceof EntityFish) {
			this.func_6186_a(var1, 64, 5, true);
		} else if(var1 instanceof EntityArrow) {
			this.func_6186_a(var1, 64, 5, true);
		} else if(var1 instanceof EntitySnowball) {
			this.func_6186_a(var1, 64, 5, true);
		} else if(var1 instanceof EntityItem) {
			this.func_6186_a(var1, 64, 20, true);
		} else if(var1 instanceof EntityMinecart) {
			this.func_6186_a(var1, 160, 5, true);
		} else if(var1 instanceof EntityBoat) {
			this.func_6186_a(var1, 160, 5, true);
		} else if(var1 instanceof IAnimals) {
			this.func_6187_a(var1, 160, 3);
		} else if(var1 instanceof EntityTNTPrimed) {
			this.func_6186_a(var1, 160, 10, true);
		}

	}

	public void func_6187_a(Entity var1, int var2, int var3) {
		this.func_6186_a(var1, var2, var3, false);
	}

	public void func_6186_a(Entity var1, int var2, int var3, boolean var4) {
		if(var2 > this.field_912_d) {
			var2 = this.field_912_d;
		}

		if(this.field_910_b.containsItem(var1.field_331_c)) {
			throw new IllegalStateException("Entity is already tracked!");
		} else {
			EntityTrackerEntry var5 = new EntityTrackerEntry(var1, var2, var3, var4);
			this.field_911_a.add(var5);
			this.field_910_b.addKey(var1.field_331_c, var5);
			var5.func_601_b(this.mcServer.worldMngr.playerEntities);
		}
	}

	public void func_610_b(Entity var1) {
		if(var1 instanceof EntityPlayerMP) {
			EntityPlayerMP var2 = (EntityPlayerMP)var1;
			Iterator var3 = this.field_911_a.iterator();

			while(var3.hasNext()) {
				EntityTrackerEntry var4 = (EntityTrackerEntry)var3.next();
				var4.func_12019_a(var2);
			}
		}

		EntityTrackerEntry var5 = (EntityTrackerEntry)this.field_910_b.removeObject(var1.field_331_c);
		if(var5 != null) {
			this.field_911_a.remove(var5);
			var5.func_604_a();
		}

	}

	public void func_607_a() {
		ArrayList var1 = new ArrayList();
		Iterator var2 = this.field_911_a.iterator();

		while(var2.hasNext()) {
			EntityTrackerEntry var3 = (EntityTrackerEntry)var2.next();
			var3.func_605_a(this.mcServer.worldMngr.playerEntities);
			if(var3.field_900_j && var3.field_909_a instanceof EntityPlayerMP) {
				var1.add((EntityPlayerMP)var3.field_909_a);
			}
		}

		for(int var6 = 0; var6 < var1.size(); ++var6) {
			EntityPlayerMP var7 = (EntityPlayerMP)var1.get(var6);
			Iterator var4 = this.field_911_a.iterator();

			while(var4.hasNext()) {
				EntityTrackerEntry var5 = (EntityTrackerEntry)var4.next();
				if(var5.field_909_a != var7) {
					var5.func_606_a(var7);
				}
			}
		}

	}

	public void func_12021_a(Entity var1, Packet var2) {
		EntityTrackerEntry var3 = (EntityTrackerEntry)this.field_910_b.lookup(var1.field_331_c);
		if(var3 != null) {
			var3.func_603_a(var2);
		}

	}

	public void func_609_a(Entity var1, Packet var2) {
		EntityTrackerEntry var3 = (EntityTrackerEntry)this.field_910_b.lookup(var1.field_331_c);
		if(var3 != null) {
			var3.func_12018_b(var2);
		}

	}

	public void func_9238_a(EntityPlayerMP var1) {
		Iterator var2 = this.field_911_a.iterator();

		while(var2.hasNext()) {
			EntityTrackerEntry var3 = (EntityTrackerEntry)var2.next();
			var3.func_9219_b(var1);
		}

	}
}
