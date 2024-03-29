package net.PeytonPlayzt585.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityChicken;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityPig;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.World;

public enum CreatureType {
  CHICKEN("Chicken"),
  COW("Cow"),
  CREEPER("Creeper"),
  GHAST("Ghast"),
  PIG("Pig"),
  SHEEP("Sheep"),
  SKELETON("Skeleton"),
  SLIME("Slime"),
  SPIDER("Spider"),
  ZOMBIE("Zombie");
  
  private String name;
  
  private static final Map<String, CreatureType> mapping;
  
  static {
    mapping = new HashMap<String, CreatureType>();
    for (CreatureType type : EnumSet.<CreatureType>allOf(CreatureType.class))
      mapping.put(type.name, type); 
  }
  
  CreatureType(String name) {
    this.name = name;
  }
  
  public String getName() {
    return this.name;
  }
  
  public Entity nameToEntity(String name) {
	  try {
		  if(name.equals("Chicken")) {
			  return EntityChicken.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{MinecraftServer.worldMngr});
		  } else if(name.equals("Cow")) {
			  return EntityCow.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{MinecraftServer.worldMngr});
		  } else if(name.equals("Creeper")) {
			  return EntityCreeper.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{MinecraftServer.worldMngr});
		  } else if(name.equals("Ghast")) {
			  return EntityGhast.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{MinecraftServer.worldMngr});
		  } else if(name.equals("Pig")) {
			  return EntityPig.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{MinecraftServer.worldMngr});
		  } else if(name.equals("Sheep")) {
			  return EntitySheep.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{MinecraftServer.worldMngr});
		  } else if(name.equals("Skeleton")) {
			  return EntitySkeleton.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{MinecraftServer.worldMngr});
		  } else if(name.equals("Slime")) {
			  return EntitySlime.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{MinecraftServer.worldMngr});
		  } else if(name.equals("Spider")) {
			  return EntitySpider.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{MinecraftServer.worldMngr});
		  } else if(name.equals("Zombie")) {
			  return EntityZombie.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{MinecraftServer.worldMngr});
		  }
	  } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
		  System.err.println("Error mapping entity name to class!");
		  return null;
	  }
	  
	  System.err.println("Entity (" + name + ") not found!");
	  return null;
  }
  
  public static CreatureType fromName(String name) {
    return mapping.get(name);
  }
}
