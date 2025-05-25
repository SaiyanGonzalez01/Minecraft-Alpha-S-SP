package net.minecraft.src;

public class ItemAxe extends ItemTool {
	private static Block[] blocksEffectiveAgainst = new Block[]{Block.planks, Block.bookShelf, Block.wood, Block.crate, Block.planksRed, Block.planksYellow};

	public ItemAxe(int var1, int var2) {
		super(var1, 3, var2, blocksEffectiveAgainst);
	}
}
