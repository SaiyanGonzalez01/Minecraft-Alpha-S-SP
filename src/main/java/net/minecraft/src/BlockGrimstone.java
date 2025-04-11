package net.minecraft.src;

import net.PeytonPlayz585.EaglercraftRandom;

public class BlockGrimstone extends Block {
	public BlockGrimstone(int var1, int var2) {
		super(var1, var2, Material.rock);
	}

	public int idDropped(int var1, EaglercraftRandom var2) {
		return Block.grimcCbblestone.blockID;
	}
}
