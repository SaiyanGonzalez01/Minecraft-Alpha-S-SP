package net.minecraft.src;

import net.PeytonPlayz585.EaglercraftRandom;

public class BlockLog extends Block {
	protected BlockLog(int var1) {
		super(var1, Material.wood);
		this.blockIndexInTexture = 20;
	}

	public int quantityDropped(EaglercraftRandom var1) {
		return 1;
	}

	public int idDropped(int var1, EaglercraftRandom var2) {
		return Block.wood.blockID;
	}

	public int getBlockTextureFromSide(int var1) {
		return var1 == 1 ? 21 : (var1 == 0 ? 21 : 20);
	}
}
