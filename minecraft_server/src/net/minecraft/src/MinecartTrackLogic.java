package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

class MinecartTrackLogic {
	private World worldObj;
	private int field_893_c;
	private int field_892_d;
	private int field_891_e;
	private int field_890_f;
	private List field_889_g;
	final BlockMinecartTrack field_888_a;

	public MinecartTrackLogic(BlockMinecartTrack var1, World var2, int var3, int var4, int var5) {
		this.field_888_a = var1;
		this.field_889_g = new ArrayList();
		this.worldObj = var2;
		this.field_893_c = var3;
		this.field_892_d = var4;
		this.field_891_e = var5;
		this.field_890_f = var2.getBlockMetadata(var3, var4, var5);
		this.func_593_a();
	}

	private void func_593_a() {
		this.field_889_g.clear();
		if(this.field_890_f == 0) {
			this.field_889_g.add(new ChunkPosition(this.field_893_c, this.field_892_d, this.field_891_e - 1));
			this.field_889_g.add(new ChunkPosition(this.field_893_c, this.field_892_d, this.field_891_e + 1));
		} else if(this.field_890_f == 1) {
			this.field_889_g.add(new ChunkPosition(this.field_893_c - 1, this.field_892_d, this.field_891_e));
			this.field_889_g.add(new ChunkPosition(this.field_893_c + 1, this.field_892_d, this.field_891_e));
		} else if(this.field_890_f == 2) {
			this.field_889_g.add(new ChunkPosition(this.field_893_c - 1, this.field_892_d, this.field_891_e));
			this.field_889_g.add(new ChunkPosition(this.field_893_c + 1, this.field_892_d + 1, this.field_891_e));
		} else if(this.field_890_f == 3) {
			this.field_889_g.add(new ChunkPosition(this.field_893_c - 1, this.field_892_d + 1, this.field_891_e));
			this.field_889_g.add(new ChunkPosition(this.field_893_c + 1, this.field_892_d, this.field_891_e));
		} else if(this.field_890_f == 4) {
			this.field_889_g.add(new ChunkPosition(this.field_893_c, this.field_892_d + 1, this.field_891_e - 1));
			this.field_889_g.add(new ChunkPosition(this.field_893_c, this.field_892_d, this.field_891_e + 1));
		} else if(this.field_890_f == 5) {
			this.field_889_g.add(new ChunkPosition(this.field_893_c, this.field_892_d, this.field_891_e - 1));
			this.field_889_g.add(new ChunkPosition(this.field_893_c, this.field_892_d + 1, this.field_891_e + 1));
		} else if(this.field_890_f == 6) {
			this.field_889_g.add(new ChunkPosition(this.field_893_c + 1, this.field_892_d, this.field_891_e));
			this.field_889_g.add(new ChunkPosition(this.field_893_c, this.field_892_d, this.field_891_e + 1));
		} else if(this.field_890_f == 7) {
			this.field_889_g.add(new ChunkPosition(this.field_893_c - 1, this.field_892_d, this.field_891_e));
			this.field_889_g.add(new ChunkPosition(this.field_893_c, this.field_892_d, this.field_891_e + 1));
		} else if(this.field_890_f == 8) {
			this.field_889_g.add(new ChunkPosition(this.field_893_c - 1, this.field_892_d, this.field_891_e));
			this.field_889_g.add(new ChunkPosition(this.field_893_c, this.field_892_d, this.field_891_e - 1));
		} else if(this.field_890_f == 9) {
			this.field_889_g.add(new ChunkPosition(this.field_893_c + 1, this.field_892_d, this.field_891_e));
			this.field_889_g.add(new ChunkPosition(this.field_893_c, this.field_892_d, this.field_891_e - 1));
		}

	}

	private void func_591_b() {
		for(int var1 = 0; var1 < this.field_889_g.size(); ++var1) {
			MinecartTrackLogic var2 = this.func_595_a((ChunkPosition)this.field_889_g.get(var1));
			if(var2 != null && var2.func_590_b(this)) {
				this.field_889_g.set(var1, new ChunkPosition(var2.field_893_c, var2.field_892_d, var2.field_891_e));
			} else {
				this.field_889_g.remove(var1--);
			}
		}

	}

	private boolean func_589_a(int var1, int var2, int var3) {
		return this.worldObj.getBlockId(var1, var2, var3) == this.field_888_a.blockID ? true : (this.worldObj.getBlockId(var1, var2 + 1, var3) == this.field_888_a.blockID ? true : this.worldObj.getBlockId(var1, var2 - 1, var3) == this.field_888_a.blockID);
	}

	private MinecartTrackLogic func_595_a(ChunkPosition var1) {
		return this.worldObj.getBlockId(var1.field_846_a, var1.field_845_b, var1.field_847_c) == this.field_888_a.blockID ? new MinecartTrackLogic(this.field_888_a, this.worldObj, var1.field_846_a, var1.field_845_b, var1.field_847_c) : (this.worldObj.getBlockId(var1.field_846_a, var1.field_845_b + 1, var1.field_847_c) == this.field_888_a.blockID ? new MinecartTrackLogic(this.field_888_a, this.worldObj, var1.field_846_a, var1.field_845_b + 1, var1.field_847_c) : (this.worldObj.getBlockId(var1.field_846_a, var1.field_845_b - 1, var1.field_847_c) == this.field_888_a.blockID ? new MinecartTrackLogic(this.field_888_a, this.worldObj, var1.field_846_a, var1.field_845_b - 1, var1.field_847_c) : null));
	}

	private boolean func_590_b(MinecartTrackLogic var1) {
		for(int var2 = 0; var2 < this.field_889_g.size(); ++var2) {
			ChunkPosition var3 = (ChunkPosition)this.field_889_g.get(var2);
			if(var3.field_846_a == var1.field_893_c && var3.field_847_c == var1.field_891_e) {
				return true;
			}
		}

		return false;
	}

	private boolean func_599_b(int var1, int var2, int var3) {
		for(int var4 = 0; var4 < this.field_889_g.size(); ++var4) {
			ChunkPosition var5 = (ChunkPosition)this.field_889_g.get(var4);
			if(var5.field_846_a == var1 && var5.field_847_c == var3) {
				return true;
			}
		}

		return false;
	}

	private int func_594_c() {
		int var1 = 0;
		if(this.func_589_a(this.field_893_c, this.field_892_d, this.field_891_e - 1)) {
			++var1;
		}

		if(this.func_589_a(this.field_893_c, this.field_892_d, this.field_891_e + 1)) {
			++var1;
		}

		if(this.func_589_a(this.field_893_c - 1, this.field_892_d, this.field_891_e)) {
			++var1;
		}

		if(this.func_589_a(this.field_893_c + 1, this.field_892_d, this.field_891_e)) {
			++var1;
		}

		return var1;
	}

	private boolean func_597_c(MinecartTrackLogic var1) {
		if(this.func_590_b(var1)) {
			return true;
		} else if(this.field_889_g.size() == 2) {
			return false;
		} else if(this.field_889_g.size() == 0) {
			return true;
		} else {
			ChunkPosition var2 = (ChunkPosition)this.field_889_g.get(0);
			return var1.field_892_d == this.field_892_d && var2.field_845_b == this.field_892_d ? true : true;
		}
	}

	private void func_598_d(MinecartTrackLogic var1) {
		this.field_889_g.add(new ChunkPosition(var1.field_893_c, var1.field_892_d, var1.field_891_e));
		boolean var2 = this.func_599_b(this.field_893_c, this.field_892_d, this.field_891_e - 1);
		boolean var3 = this.func_599_b(this.field_893_c, this.field_892_d, this.field_891_e + 1);
		boolean var4 = this.func_599_b(this.field_893_c - 1, this.field_892_d, this.field_891_e);
		boolean var5 = this.func_599_b(this.field_893_c + 1, this.field_892_d, this.field_891_e);
		byte var6 = -1;
		if(var2 || var3) {
			var6 = 0;
		}

		if(var4 || var5) {
			var6 = 1;
		}

		if(var3 && var5 && !var2 && !var4) {
			var6 = 6;
		}

		if(var3 && var4 && !var2 && !var5) {
			var6 = 7;
		}

		if(var2 && var4 && !var3 && !var5) {
			var6 = 8;
		}

		if(var2 && var5 && !var3 && !var4) {
			var6 = 9;
		}

		if(var6 == 0) {
			if(this.worldObj.getBlockId(this.field_893_c, this.field_892_d + 1, this.field_891_e - 1) == this.field_888_a.blockID) {
				var6 = 4;
			}

			if(this.worldObj.getBlockId(this.field_893_c, this.field_892_d + 1, this.field_891_e + 1) == this.field_888_a.blockID) {
				var6 = 5;
			}
		}

		if(var6 == 1) {
			if(this.worldObj.getBlockId(this.field_893_c + 1, this.field_892_d + 1, this.field_891_e) == this.field_888_a.blockID) {
				var6 = 2;
			}

			if(this.worldObj.getBlockId(this.field_893_c - 1, this.field_892_d + 1, this.field_891_e) == this.field_888_a.blockID) {
				var6 = 3;
			}
		}

		if(var6 < 0) {
			var6 = 0;
		}

		this.worldObj.setBlockMetadataWithNotify(this.field_893_c, this.field_892_d, this.field_891_e, var6);
	}

	private boolean func_592_c(int var1, int var2, int var3) {
		MinecartTrackLogic var4 = this.func_595_a(new ChunkPosition(var1, var2, var3));
		if(var4 == null) {
			return false;
		} else {
			var4.func_591_b();
			return var4.func_597_c(this);
		}
	}

	public void func_596_a(boolean var1) {
		boolean var2 = this.func_592_c(this.field_893_c, this.field_892_d, this.field_891_e - 1);
		boolean var3 = this.func_592_c(this.field_893_c, this.field_892_d, this.field_891_e + 1);
		boolean var4 = this.func_592_c(this.field_893_c - 1, this.field_892_d, this.field_891_e);
		boolean var5 = this.func_592_c(this.field_893_c + 1, this.field_892_d, this.field_891_e);
		byte var6 = -1;
		if((var2 || var3) && !var4 && !var5) {
			var6 = 0;
		}

		if((var4 || var5) && !var2 && !var3) {
			var6 = 1;
		}

		if(var3 && var5 && !var2 && !var4) {
			var6 = 6;
		}

		if(var3 && var4 && !var2 && !var5) {
			var6 = 7;
		}

		if(var2 && var4 && !var3 && !var5) {
			var6 = 8;
		}

		if(var2 && var5 && !var3 && !var4) {
			var6 = 9;
		}

		if(var6 == -1) {
			if(var2 || var3) {
				var6 = 0;
			}

			if(var4 || var5) {
				var6 = 1;
			}

			if(var1) {
				if(var3 && var5) {
					var6 = 6;
				}

				if(var4 && var3) {
					var6 = 7;
				}

				if(var5 && var2) {
					var6 = 9;
				}

				if(var2 && var4) {
					var6 = 8;
				}
			} else {
				if(var2 && var4) {
					var6 = 8;
				}

				if(var5 && var2) {
					var6 = 9;
				}

				if(var4 && var3) {
					var6 = 7;
				}

				if(var3 && var5) {
					var6 = 6;
				}
			}
		}

		if(var6 == 0) {
			if(this.worldObj.getBlockId(this.field_893_c, this.field_892_d + 1, this.field_891_e - 1) == this.field_888_a.blockID) {
				var6 = 4;
			}

			if(this.worldObj.getBlockId(this.field_893_c, this.field_892_d + 1, this.field_891_e + 1) == this.field_888_a.blockID) {
				var6 = 5;
			}
		}

		if(var6 == 1) {
			if(this.worldObj.getBlockId(this.field_893_c + 1, this.field_892_d + 1, this.field_891_e) == this.field_888_a.blockID) {
				var6 = 2;
			}

			if(this.worldObj.getBlockId(this.field_893_c - 1, this.field_892_d + 1, this.field_891_e) == this.field_888_a.blockID) {
				var6 = 3;
			}
		}

		if(var6 < 0) {
			var6 = 0;
		}

		this.field_890_f = var6;
		this.func_593_a();
		this.worldObj.setBlockMetadataWithNotify(this.field_893_c, this.field_892_d, this.field_891_e, var6);

		for(int var7 = 0; var7 < this.field_889_g.size(); ++var7) {
			MinecartTrackLogic var8 = this.func_595_a((ChunkPosition)this.field_889_g.get(var7));
			if(var8 != null) {
				var8.func_591_b();
				if(var8.func_597_c(this)) {
					var8.func_598_d(this);
				}
			}
		}

	}

	static int func_600_a(MinecartTrackLogic var0) {
		return var0.func_594_c();
	}
}
