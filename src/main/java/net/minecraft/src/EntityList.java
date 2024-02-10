package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;

public class EntityList {
	private static Map stringToClassMapping = new HashMap();
	private static Map classToStringMapping = new HashMap();
	private static Map IDtoClassMapping = new HashMap();
	private static Map classToIDMapping = new HashMap();

	private static void addMapping(Object var0, String var1, int var2) {
		stringToClassMapping.put(var1, var0);
		classToStringMapping.put(var0, var1);
		IDtoClassMapping.put(Integer.valueOf(var2), var0);
		classToIDMapping.put(var0, Integer.valueOf(var2));
	}

	public static Entity createEntityInWorld(String var0, World var1) {
		Entity var2 = null;

		try {
			Class var3 = (Class)stringToClassMapping.get(var0);
			Entity entity = getEntity(var3);
			if(var3 != null && entity != null & !(entity instanceof EntityPlayer || entity instanceof EntityPlayerSP)) {
				var2 = entity;
			}
		} catch (Exception var4) {
			var4.printStackTrace();
		}

		return var2;
	}

	public static Entity createEntityFromNBT(NBTTagCompound var0, World var1) {
		Entity var2 = null;

		try {
			Class var3 = (Class)stringToClassMapping.get(var0.getString("id"));
			Entity entity = getEntity(var3);
			if(var3 != null && entity != null && !(entity instanceof EntityPlayer || entity instanceof EntityPlayerSP)) {
				var2 = entity;
			}
		} catch (Exception var4) {
			var4.printStackTrace();
		}

		if(var2 != null) {
			var2.readFromNBT(var0);
		} else {
			System.out.println("Skipping Entity with id " + var0.getString("id"));
		}

		return var2;
	}

	public static Entity createEntity(int var0, World var1) {
		Entity var2 = null;

		try {
			Class var3 = (Class)IDtoClassMapping.get(Integer.valueOf(var0));
			Entity entity = getEntity(var3);
			if(var3 != null && entity != null && !(entity instanceof EntityPlayer || entity instanceof EntityPlayerSP)) {
				var2 = entity;
			}
		} catch (Exception var4) {
			var4.printStackTrace();
		}

		if(var2 == null) {
			System.out.println("Skipping Entity with id " + var0);
		}

		return var2;
	}

	public static int getEntityID(Entity var0) {
		return ((Integer)classToIDMapping.get(var0.getClass())).intValue();
	}

	public static String getEntityString(Entity var0) {
		return (String)classToStringMapping.get(var0.getClass());
	}
	
	public static Entity getEntity(Class c) {
		
		if(c == EntityPlayer.class || c == EntityPlayerSP.class) {
			return null;
		}
		
		if(c == EntityArrow.class) {
			return new EntityArrow(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntitySnowball.class) {
			return new EntitySnowball(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityItem.class) {
			return new EntityItem(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityPainting.class) {
			return new EntityPainting(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityCreeper.class) {
			return new EntityCreeper(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntitySkeleton.class) {
			return new EntitySkeleton(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntitySpider.class) {
			return new EntitySpider(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityZombieSimple.class) {
			return new EntityZombieSimple(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityZombie.class) {
			return new EntityZombie(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntitySlime.class) {
			return new EntitySlime(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityGhast.class) {
			return new EntityGhast(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityPigZombie.class) {
			return new EntityPigZombie(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityPig.class) {
			return new EntityPig(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntitySheep.class) {
			return new EntitySheep(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityCow.class) {
			return new EntityCow(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityChicken.class) {
			return new EntityChicken(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityTNTPrimed.class) {
			return new EntityTNTPrimed(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityFallingSand.class) {
			return new EntityFallingSand(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityMinecart.class) {
			return new EntityMinecart(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityBoat.class) {
			return new EntityBoat(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityMobs.class) {
			return new EntityMobs(Minecraft.getMinecraft().theWorld);
		}
		
		if(c == EntityLiving.class) {
			return new EntityLiving(Minecraft.getMinecraft().theWorld);
		}
		
		return null;
	}

	static {
		addMapping(EntityArrow.class, "Arrow", 10);
		addMapping(EntitySnowball.class, "Snowball", 11);
		addMapping(EntityItem.class, "Item", 1);
		addMapping(EntityPainting.class, "Painting", 9);
		addMapping(EntityLiving.class, "Mob", 48);
		addMapping(EntityMobs.class, "Monster", 49);
		addMapping(EntityCreeper.class, "Creeper", 50);
		addMapping(EntitySkeleton.class, "Skeleton", 51);
		addMapping(EntitySpider.class, "Spider", 52);
		addMapping(EntityZombieSimple.class, "Giant", 53);
		addMapping(EntityZombie.class, "Zombie", 54);
		addMapping(EntitySlime.class, "Slime", 55);
		addMapping(EntityGhast.class, "Ghast", 56);
		addMapping(EntityPigZombie.class, "PigZombie", 57);
		addMapping(EntityPig.class, "Pig", 90);
		addMapping(EntitySheep.class, "Sheep", 91);
		addMapping(EntityCow.class, "Cow", 92);
		addMapping(EntityChicken.class, "Chicken", 93);
		addMapping(EntityTNTPrimed.class, "PrimedTnt", 20);
		addMapping(EntityFallingSand.class, "FallingSand", 21);
		addMapping(EntityMinecart.class, "Minecart", 40);
		addMapping(EntityBoat.class, "Boat", 41);
	}
}
