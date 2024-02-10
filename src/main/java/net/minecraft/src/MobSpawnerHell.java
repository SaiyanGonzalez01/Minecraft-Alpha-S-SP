package net.minecraft.src;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

public class MobSpawnerHell extends MobSpawnerBase {
	public MobSpawnerHell() {	
		biomeMonsters = new ArrayList();
		biomeCreatures = new ArrayList();
	}
	
	@Override
	public void initBiomeMonsters() {
		biomeMonsters.add(new EntityGhast(Minecraft.getMinecraft().theWorld));
		biomeMonsters.add(new EntityPigZombie(Minecraft.getMinecraft().theWorld));
	}

	@Override
	public void initBiomeCreatures() {
		if(biomeCreatures != null) {
			biomeCreatures.clear();
		}
	}
}
