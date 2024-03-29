package net.minecraft.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChunkProviderServer implements IChunkProvider {
	private Set field_725_a = new HashSet();
	private Chunk field_724_b;
	private IChunkProvider field_730_c;
	private IChunkLoader field_729_d;
	private Map field_728_e = new HashMap();
	private List field_727_f = new ArrayList();
	private WorldServer field_726_g;

	public ChunkProviderServer(WorldServer var1, IChunkLoader var2, IChunkProvider var3) {
		this.field_724_b = new Chunk(var1, new byte[-Short.MIN_VALUE], 0, 0);
		this.field_724_b.field_678_q = true;
		this.field_724_b.field_679_p = true;
		this.field_726_g = var1;
		this.field_729_d = var2;
		this.field_730_c = var3;
	}

	public boolean chunkExists(int var1, int var2) {
		ChunkCoordinates var3 = new ChunkCoordinates(var1, var2);
		return this.field_728_e.containsKey(var3);
	}

	public void func_374_c(int var1, int var2) {
		int var3 = var1 * 16 + 8 - this.field_726_g.spawnX;
		int var4 = var2 * 16 + 8 - this.field_726_g.spawnZ;
		short var5 = 128;
		if(var3 < -var5 || var3 > var5 || var4 < -var5 || var4 > var5) {
			this.field_725_a.add(new ChunkCoordinates(var1, var2));
		}

	}

	public Chunk loadChunk(int var1, int var2) {
		ChunkCoordinates var3 = new ChunkCoordinates(var1, var2);
		this.field_725_a.remove(new ChunkCoordinates(var1, var2));
		Chunk var4 = (Chunk)this.field_728_e.get(var3);
		if(var4 == null) {
			var4 = this.func_4063_e(var1, var2);
			if(var4 == null) {
				if(this.field_730_c == null) {
					var4 = this.field_724_b;
				} else {
					var4 = this.field_730_c.func_363_b(var1, var2);
				}
			}

			this.field_728_e.put(var3, var4);
			this.field_727_f.add(var4);
			var4.func_4053_c();
			if(var4 != null) {
				var4.func_358_c();
			}

			if(!var4.isTerrainPopulated && this.chunkExists(var1 + 1, var2 + 1) && this.chunkExists(var1, var2 + 1) && this.chunkExists(var1 + 1, var2)) {
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

		return var4;
	}

	public Chunk func_363_b(int var1, int var2) {
		ChunkCoordinates var3 = new ChunkCoordinates(var1, var2);
		Chunk var4 = (Chunk)this.field_728_e.get(var3);
		return var4 == null ? (this.field_726_g.field_9209_x ? this.loadChunk(var1, var2) : this.field_724_b) : var4;
	}

	private Chunk func_4063_e(int var1, int var2) {
		if(this.field_729_d == null) {
			return null;
		} else {
			try {
				Chunk var3 = this.field_729_d.func_659_a(this.field_726_g, var1, var2);
				if(var3 != null) {
					var3.field_676_s = this.field_726_g.worldTime;
				}

				return var3;
			} catch (Exception var4) {
				var4.printStackTrace();
				return null;
			}
		}
	}

	private void func_375_a(Chunk var1) {
		if(this.field_729_d != null) {
			try {
				this.field_729_d.func_4104_b(this.field_726_g, var1);
			} catch (Exception var3) {
				var3.printStackTrace();
			}

		}
	}

	private void func_373_b(Chunk var1) {
		if(this.field_729_d != null) {
			try {
				var1.field_676_s = this.field_726_g.worldTime;
				this.field_729_d.func_662_a(this.field_726_g, var1);
			} catch (IOException var3) {
				var3.printStackTrace();
			}

		}
	}

	public void populate(IChunkProvider var1, int var2, int var3) {
		Chunk var4 = this.func_363_b(var2, var3);
		if(!var4.isTerrainPopulated) {
			var4.isTerrainPopulated = true;
			if(this.field_730_c != null) {
				this.field_730_c.populate(var1, var2, var3);
				var4.func_336_e();
			}
		}

	}

	public boolean saveWorld(boolean var1, IProgressUpdate var2) {
		int var3 = 0;

		for(int var4 = 0; var4 < this.field_727_f.size(); ++var4) {
			Chunk var5 = (Chunk)this.field_727_f.get(var4);
			if(var1 && !var5.field_679_p) {
				this.func_375_a(var5);
			}

			if(var5.func_347_a()) {
				this.func_373_b(var5);
				var5.isModified = false;
				++var3;
				if(var3 == 32 && !var1) {
					return false;
				}
			}
		}

		if(var1) {
			if(this.field_729_d == null) {
				return true;
			}

			this.field_729_d.func_660_b();
		}

		return true;
	}

	public boolean func_361_a() {
		if(!this.field_726_g.field_816_A) {
			for(int var1 = 0; var1 < 100; ++var1) {
				if(!this.field_725_a.isEmpty()) {
					ChunkCoordinates var2 = (ChunkCoordinates)this.field_725_a.iterator().next();
					Chunk var3 = this.func_363_b(var2.field_529_a, var2.field_528_b);
					var3.func_331_d();
					this.func_373_b(var3);
					this.func_375_a(var3);
					this.field_725_a.remove(var2);
					this.field_728_e.remove(var2);
					this.field_727_f.remove(var3);
				}
			}

			if(this.field_729_d != null) {
				this.field_729_d.func_661_a();
			}
		}

		return this.field_730_c.func_361_a();
	}

	public boolean func_364_b() {
		return !this.field_726_g.field_816_A;
	}
}
