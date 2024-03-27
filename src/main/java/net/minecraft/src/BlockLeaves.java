package net.minecraft.src;

import java.util.Random;

public class BlockLeaves extends BlockLeavesBase {
	private int baseIndexInPNG;
	int field_464_c[];

	protected BlockLeaves(int var1, int var2) {
		super(var1, var2, Material.leaves, false);
		this.baseIndexInPNG = var2;
		setTickOnLoad(true);
	}

	public int colorMultiplier(IBlockAccess var1, int var2, int var3, int var4) {
		var1.func_4075_a().func_4069_a(var2, var4, 1, 1);
		double var5 = var1.func_4075_a().temperature[0];
		double var7 = var1.func_4075_a().humidity[0];
		return ColorizerFoliage.func_4146_a(var5, var7);
	}
	
	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
		int l = 1;
		int i1 = l + 1;
		if (var1.checkChunksExist(var2 - i1, var3 - i1, var4 - i1, var2 + i1, var3 + i1, var4 + i1)) {
			for (int j1 = -l; j1 <= l; j1++) {
				for (int k1 = -l; k1 <= l; k1++) {
					for (int l1 = -l; l1 <= l; l1++) {
						int i2 = var1.getBlockId(var2 + j1, var3 + k1, var4 + l1);
						if (i2 == Block.leaves.blockID) {
							int j2 = var1.getBlockMetadata(var2 + j1, var3 + k1, var4 + l1);
							var1.setBlockMetadata(var2 + j1, var3 + k1, var4 + l1, j2 | 4);
						}
					}

				}

			}

		}
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		if (var1.multiplayerWorld) {
			return;
		}
		int l = var1.getBlockMetadata(var2, var3, var4);
		if ((l & 4) != 0) {
			byte byte0 = 4;
			int i1 = byte0 + 1;
			byte byte1 = 32;
			int j1 = byte1 * byte1;
			int k1 = byte1 / 2;
			if (field_464_c == null) {
				field_464_c = new int[byte1 * byte1 * byte1];
			}
			if (var1.checkChunksExist(var2 - i1, var3 - i1, var4 - i1, var2 + i1, var3 + i1, var4 + i1)) {
				for (int l1 = -byte0; l1 <= byte0; l1++) {
					for (int k2 = -byte0; k2 <= byte0; k2++) {
						for (int i3 = -byte0; i3 <= byte0; i3++) {
							int k3 = var1.getBlockId(var2 + l1, var3 + k2, var4 + i3);
							if (k3 == Block.wood.blockID) {
								field_464_c[(l1 + k1) * j1 + (k2 + k1) * byte1 + (i3 + k1)] = 0;
								continue;
							}
							if (k3 == Block.leaves.blockID) {
								field_464_c[(l1 + k1) * j1 + (k2 + k1) * byte1 + (i3 + k1)] = -2;
							} else {
								field_464_c[(l1 + k1) * j1 + (k2 + k1) * byte1 + (i3 + k1)] = -1;
							}
						}

					}

				}

				for (int i2 = 1; i2 <= 4; i2++) {
					for (int l2 = -byte0; l2 <= byte0; l2++) {
						for (int j3 = -byte0; j3 <= byte0; j3++) {
							for (int l3 = -byte0; l3 <= byte0; l3++) {
								if (field_464_c[(l2 + k1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] != i2 - 1) {
									continue;
								}
								if (field_464_c[((l2 + k1) - 1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] == -2) {
									field_464_c[((l2 + k1) - 1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] = i2;
								}
								if (field_464_c[(l2 + k1 + 1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] == -2) {
									field_464_c[(l2 + k1 + 1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] = i2;
								}
								if (field_464_c[(l2 + k1) * j1 + ((j3 + k1) - 1) * byte1 + (l3 + k1)] == -2) {
									field_464_c[(l2 + k1) * j1 + ((j3 + k1) - 1) * byte1 + (l3 + k1)] = i2;
								}
								if (field_464_c[(l2 + k1) * j1 + (j3 + k1 + 1) * byte1 + (l3 + k1)] == -2) {
									field_464_c[(l2 + k1) * j1 + (j3 + k1 + 1) * byte1 + (l3 + k1)] = i2;
								}
								if (field_464_c[(l2 + k1) * j1 + (j3 + k1) * byte1 + ((l3 + k1) - 1)] == -2) {
									field_464_c[(l2 + k1) * j1 + (j3 + k1) * byte1 + ((l3 + k1) - 1)] = i2;
								}
								if (field_464_c[(l2 + k1) * j1 + (j3 + k1) * byte1 + (l3 + k1 + 1)] == -2) {
									field_464_c[(l2 + k1) * j1 + (j3 + k1) * byte1 + (l3 + k1 + 1)] = i2;
								}
							}

						}

					}

				}

			}
			int j2 = field_464_c[k1 * j1 + k1 * byte1 + k1];
			if (j2 >= 0) {
				var1.setBlockMetadataWithNotify(var2, var3, var4, l & -5);
			} else {
				func_6360_i(var1, var2, var3, var4);
			}
		}
	}

	private void func_6360_i(World var1, int var2, int var3, int var4) {
		this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
		var1.setBlockWithNotify(var2, var3, var4, 0);
	}

	public int quantityDropped(Random var1) {
		return var1.nextInt(20) == 0 ? 1 : 0;
	}

	public int idDropped(int var1, Random var2) {
		return Block.sapling.blockID;
	}

	public boolean isOpaqueCube() {
		return !this.graphicsLevel;
	}

	public void setGraphicsLevel(boolean var1) {
		this.graphicsLevel = var1;
		this.blockIndexInTexture = this.baseIndexInPNG + (var1 ? 0 : 1);
	}

	public void onEntityWalking(World var1, int var2, int var3, int var4, Entity var5) {
		super.onEntityWalking(var1, var2, var3, var4, var5);
	}
}

