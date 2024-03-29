package net.minecraft.src;

import java.io.IOException;

public class ChunkProviderLoadOrGenerate implements IChunkProvider {
	private Chunk field_723_c;
	private IChunkProvider field_722_d;
	private IChunkLoader field_721_e;
	private Chunk[] chunks = new Chunk[1024];
	private World worldObj;
	int field_717_a = -999999999;
	int field_716_b = -999999999;
	private Chunk field_718_h;

	public ChunkProviderLoadOrGenerate(World var1, IChunkLoader var2, IChunkProvider var3) {
		this.field_723_c = new Chunk(var1, new byte[-Short.MIN_VALUE], 0, 0);
		this.field_723_c.field_678_q = true;
		this.field_723_c.field_679_p = true;
		this.worldObj = var1;
		this.field_721_e = var2;
		this.field_722_d = var3;
	}

	public boolean chunkExists(int var1, int var2) {
		if(var1 == this.field_717_a && var2 == this.field_716_b && this.field_718_h != null) {
			return true;
		} else {
			int var3 = var1 & 31;
			int var4 = var2 & 31;
			int var5 = var3 + var4 * 32;
			return this.chunks[var5] != null && (this.chunks[var5] == this.field_723_c || this.chunks[var5].func_351_a(var1, var2));
		}
	}

	public Chunk func_363_b(int var1, int var2) {
		if(var1 == this.field_717_a && var2 == this.field_716_b && this.field_718_h != null) {
			return this.field_718_h;
		} else {
			int var3 = var1 & 31;
			int var4 = var2 & 31;
			int var5 = var3 + var4 * 32;
			if(!this.chunkExists(var1, var2)) {
				if(this.chunks[var5] != null) {
					this.chunks[var5].func_331_d();
					this.func_370_b(this.chunks[var5]);
					this.func_371_a(this.chunks[var5]);
				}

				Chunk var6 = this.func_4059_c(var1, var2);
				if(var6 == null) {
					if(this.field_722_d == null) {
						var6 = this.field_723_c;
					} else {
						var6 = this.field_722_d.func_363_b(var1, var2);
					}
				}

				this.chunks[var5] = var6;
				var6.func_4053_c();
				if(this.chunks[var5] != null) {
					this.chunks[var5].func_358_c();
				}

				if(!this.chunks[var5].isTerrainPopulated && this.chunkExists(var1 + 1, var2 + 1) && this.chunkExists(var1, var2 + 1) && this.chunkExists(var1 + 1, var2)) {
					this.populate(this, var1, var2);
				}

				if(this.chunkExists(var1 - 1, var2) && !this.func_363_b(var1 - 1, var2).isTerrainPopulated && this.chunkExists(var1 - 1, var2 + 1) && this.chunkExists(var1, var2 + 1) && this.chunkExists(var1 - 1, var2)) {
					this.populate(this, var1 - 1, var2);
				}

				if(this.chunkExists(var1, var2 - 1) && !this.func_363_b(var1, var2 - 1).isTerrainPopulated && this.chunkExists(var1 + 1, var2 - 1) && this.chunkExists(var1, var2 - 1) && this.chunkExists(var1 + 1, var2)) {
					this.populate(this, var1, var2 - 1);
				}

				if(this.chunkExists(var1 - 1, var2 - 1) && !this.func_363_b(var1 - 1, var2 - 1).isTerrainPopulated && this.chunkExists(var1 - 1, var2 - 1) && this.chunkExists(var1, var2 - 1) && this.chunkExists(var1 - 1, var2)) {
					this.populate(this, var1 - 1, var2 - 1);
				}
			}

			this.field_717_a = var1;
			this.field_716_b = var2;
			this.field_718_h = this.chunks[var5];
			return this.chunks[var5];
		}
	}

	private Chunk func_4059_c(int var1, int var2) {
		if(this.field_721_e == null) {
			return null;
		} else {
			try {
				Chunk var3 = this.field_721_e.func_659_a(this.worldObj, var1, var2);
				if(var3 != null) {
					var3.field_676_s = this.worldObj.worldTime;
				}

				return var3;
			} catch (Exception var4) {
				var4.printStackTrace();
				return null;
			}
		}
	}

	private void func_371_a(Chunk var1) {
		if(this.field_721_e != null) {
			try {
				this.field_721_e.func_4104_b(this.worldObj, var1);
			} catch (Exception var3) {
				var3.printStackTrace();
			}

		}
	}

	private void func_370_b(Chunk var1) {
		if(this.field_721_e != null) {
			try {
				var1.field_676_s = this.worldObj.worldTime;
				this.field_721_e.func_662_a(this.worldObj, var1);
			} catch (IOException var3) {
				var3.printStackTrace();
			}

		}
	}

	public void populate(IChunkProvider var1, int var2, int var3) {
		Chunk var4 = this.func_363_b(var2, var3);
		if(!var4.isTerrainPopulated) {
			var4.isTerrainPopulated = true;
			if(this.field_722_d != null) {
				this.field_722_d.populate(var1, var2, var3);
				var4.func_336_e();
			}
		}

	}

	public boolean saveWorld(boolean var1, IProgressUpdate var2) {
		int var3 = 0;
		int var4 = 0;
		int var5;
		if(var2 != null) {
			for(var5 = 0; var5 < this.chunks.length; ++var5) {
				if(this.chunks[var5] != null && this.chunks[var5].func_347_a()) {
					++var4;
				}
			}
		}

		var5 = 0;

		for(int var6 = 0; var6 < this.chunks.length; ++var6) {
			if(this.chunks[var6] != null) {
				if(var1 && !this.chunks[var6].field_679_p) {
					this.func_371_a(this.chunks[var6]);
				}

				if(this.chunks[var6].func_347_a()) {
					this.func_370_b(this.chunks[var6]);
					this.chunks[var6].isModified = false;
					++var3;
					if(var3 == 2 && !var1) {
						return false;
					}

					if(var2 != null) {
						++var5;
						if(var5 % 10 == 0) {
							var2.func_437_a(var5 * 100 / var4);
						}
					}
				}
			}
		}

		if(var1) {
			if(this.field_721_e == null) {
				return true;
			}

			this.field_721_e.func_660_b();
		}

		return true;
	}

	public boolean func_361_a() {
		if(this.field_721_e != null) {
			this.field_721_e.func_661_a();
		}

		return this.field_722_d.func_361_a();
	}

	public boolean func_364_b() {
		return true;
	}
}
