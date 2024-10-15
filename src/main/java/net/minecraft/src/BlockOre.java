package net.minecraft.src;

import net.PeytonPlayz585.EaglercraftRandom;

public class BlockOre extends Block {
	public BlockOre(int var1, int var2) {
		super(var1, var2, Material.rock);
	}

	public int idDropped(int var1, EaglercraftRandom var2) {
		return this.blockID == Block.oreCoal.blockID ? Item.coal.shiftedIndex : (this.blockID == Block.oreDiamond.blockID ? Item.diamond.shiftedIndex : this.blockID);
	}

	public int quantityDropped(EaglercraftRandom var1) {
		return 1;
	}
}
