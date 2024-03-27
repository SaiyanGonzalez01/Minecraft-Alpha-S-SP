package net.minecraft.src;

public class ItemAxe extends ItemTool {
	private static Block[] field_4207_bb = new Block[]{Block.planks, Block.bookShelf, Block.wood, Block.crate};

	public ItemAxe(int var1, int var2) {
		super(var1, 3, var2, field_4207_bb);
	}
}
