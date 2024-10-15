package net.minecraft.src;

import net.PeytonPlayz585.EaglercraftRandom;

public class BlockBookshelf extends Block {
	public BlockBookshelf(int var1, int var2) {
		super(var1, var2, Material.wood);
	}

	public int getBlockTextureFromSide(int var1) {
		return var1 <= 1 ? 4 : this.blockIndexInTexture;
	}

	public int quantityDropped(EaglercraftRandom var1) {
		return 0;
	}
}
