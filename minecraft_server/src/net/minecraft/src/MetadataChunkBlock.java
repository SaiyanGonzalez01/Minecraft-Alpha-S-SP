package net.minecraft.src;

public class MetadataChunkBlock {
	public final EnumSkyBlock field_957_a;
	public int field_956_b;
	public int field_962_c;
	public int field_961_d;
	public int field_960_e;
	public int field_959_f;
	public int field_958_g;

	public MetadataChunkBlock(EnumSkyBlock var1, int var2, int var3, int var4, int var5, int var6, int var7) {
		this.field_957_a = var1;
		this.field_956_b = var2;
		this.field_962_c = var3;
		this.field_961_d = var4;
		this.field_960_e = var5;
		this.field_959_f = var6;
		this.field_958_g = var7;
	}

	public void func_4107_a(World var1) {
		int var2 = this.field_960_e - this.field_956_b + 1;
		int var3 = this.field_959_f - this.field_962_c + 1;
		int var4 = this.field_958_g - this.field_961_d + 1;
		int var5 = var2 * var3 * var4;
		if(var5 <= -Short.MIN_VALUE) {
			for(int var6 = this.field_956_b; var6 <= this.field_960_e; ++var6) {
				for(int var7 = this.field_961_d; var7 <= this.field_958_g; ++var7) {
					if(var1.func_530_e(var6, 0, var7)) {
						for(int var8 = this.field_962_c; var8 <= this.field_959_f; ++var8) {
							if(var8 >= 0 && var8 < 128) {
								int var9 = var1.getSavedLightValue(this.field_957_a, var6, var8, var7);
								boolean var10 = false;
								int var11 = var1.getBlockId(var6, var8, var7);
								int var12 = Block.lightOpacity[var11];
								if(var12 == 0) {
									var12 = 1;
								}

								int var13 = 0;
								if(this.field_957_a == EnumSkyBlock.Sky) {
									if(var1.canExistingBlockSeeTheSky(var6, var8, var7)) {
										var13 = 15;
									}
								} else if(this.field_957_a == EnumSkyBlock.Block) {
									var13 = Block.lightValue[var11];
								}

								int var14;
								int var20;
								if(var12 >= 15 && var13 == 0) {
									var20 = 0;
								} else {
									var14 = var1.getSavedLightValue(this.field_957_a, var6 - 1, var8, var7);
									int var15 = var1.getSavedLightValue(this.field_957_a, var6 + 1, var8, var7);
									int var16 = var1.getSavedLightValue(this.field_957_a, var6, var8 - 1, var7);
									int var17 = var1.getSavedLightValue(this.field_957_a, var6, var8 + 1, var7);
									int var18 = var1.getSavedLightValue(this.field_957_a, var6, var8, var7 - 1);
									int var19 = var1.getSavedLightValue(this.field_957_a, var6, var8, var7 + 1);
									var20 = var14;
									if(var15 > var14) {
										var20 = var15;
									}

									if(var16 > var20) {
										var20 = var16;
									}

									if(var17 > var20) {
										var20 = var17;
									}

									if(var18 > var20) {
										var20 = var18;
									}

									if(var19 > var20) {
										var20 = var19;
									}

									var20 -= var12;
									if(var20 < 0) {
										var20 = 0;
									}

									if(var13 > var20) {
										var20 = var13;
									}
								}

								if(var9 != var20) {
									var1.setLightValue(this.field_957_a, var6, var8, var7, var20);
									var14 = var20 - 1;
									if(var14 < 0) {
										var14 = 0;
									}

									var1.neighborLightPropagationChanged(this.field_957_a, var6 - 1, var8, var7, var14);
									var1.neighborLightPropagationChanged(this.field_957_a, var6, var8 - 1, var7, var14);
									var1.neighborLightPropagationChanged(this.field_957_a, var6, var8, var7 - 1, var14);
									if(var6 + 1 >= this.field_960_e) {
										var1.neighborLightPropagationChanged(this.field_957_a, var6 + 1, var8, var7, var14);
									}

									if(var8 + 1 >= this.field_959_f) {
										var1.neighborLightPropagationChanged(this.field_957_a, var6, var8 + 1, var7, var14);
									}

									if(var7 + 1 >= this.field_958_g) {
										var1.neighborLightPropagationChanged(this.field_957_a, var6, var8, var7 + 1, var14);
									}
								}
							}
						}
					}
				}
			}

		}
	}

	public boolean func_692_a(int var1, int var2, int var3, int var4, int var5, int var6) {
		if(var1 >= this.field_956_b && var2 >= this.field_962_c && var3 >= this.field_961_d && var4 <= this.field_960_e && var5 <= this.field_959_f && var6 <= this.field_958_g) {
			return true;
		} else {
			byte var7 = 1;
			if(var1 >= this.field_956_b - var7 && var2 >= this.field_962_c - var7 && var3 >= this.field_961_d - var7 && var4 <= this.field_960_e + var7 && var5 <= this.field_959_f + var7 && var6 <= this.field_958_g + var7) {
				int var8 = this.field_960_e - this.field_956_b;
				int var9 = this.field_959_f - this.field_962_c;
				int var10 = this.field_958_g - this.field_961_d;
				if(var1 > this.field_956_b) {
					var1 = this.field_956_b;
				}

				if(var2 > this.field_962_c) {
					var2 = this.field_962_c;
				}

				if(var3 > this.field_961_d) {
					var3 = this.field_961_d;
				}

				if(var4 < this.field_960_e) {
					var4 = this.field_960_e;
				}

				if(var5 < this.field_959_f) {
					var5 = this.field_959_f;
				}

				if(var6 < this.field_958_g) {
					var6 = this.field_958_g;
				}

				int var11 = var4 - var1;
				int var12 = var5 - var2;
				int var13 = var6 - var3;
				int var14 = var8 * var9 * var10;
				int var15 = var11 * var12 * var13;
				if(var15 - var14 <= 2) {
					this.field_956_b = var1;
					this.field_962_c = var2;
					this.field_961_d = var3;
					this.field_960_e = var4;
					this.field_959_f = var5;
					this.field_958_g = var6;
					return true;
				}
			}

			return false;
		}
	}
}
