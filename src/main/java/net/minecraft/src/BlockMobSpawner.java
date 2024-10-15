package net.minecraft.src;

import net.PeytonPlayz585.EaglercraftRandom;

public class BlockMobSpawner extends BlockContainer {
	protected BlockMobSpawner(int var1, int var2) {
		super(var1, var2, Material.rock);
	}

	protected TileEntity SetBlockEntity() {
		return new TileEntityMobSpawner();
	}

	public int idDropped(int var1, EaglercraftRandom var2) {
		return 0;
	}

	public int quantityDropped(EaglercraftRandom var1) {
		return 0;
	}

	public boolean isOpaqueCube() {
		return false;
	}
}
