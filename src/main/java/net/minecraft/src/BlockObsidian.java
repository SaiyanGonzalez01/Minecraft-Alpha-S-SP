package net.minecraft.src;

import net.PeytonPlayz585.EaglercraftRandom;

public class BlockObsidian extends BlockStone {
	public BlockObsidian(int var1, int var2) {
		super(var1, var2);
	}

	public int quantityDropped(EaglercraftRandom var1) {
		return 1;
	}

	public int idDropped(int var1, EaglercraftRandom var2) {
		return Block.obsidian.blockID;
	}
}
