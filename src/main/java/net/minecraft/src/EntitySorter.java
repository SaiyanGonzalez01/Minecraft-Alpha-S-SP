package net.minecraft.src;

import java.util.Comparator;

public class EntitySorter implements Comparator {
	private Entity field_1594_a;

	public EntitySorter(Entity var1) {
		this.field_1594_a = var1;
	}

	public int a(WorldRenderer var1, WorldRenderer var2) {
		float f = var1.func_1202_a(field_1594_a);
		float f1 = var2.func_1202_a(field_1594_a) ;
		return f == f1 ? 0 : f >= f1 ? 1 : -1;
	}

	public int compare(Object var1, Object var2) {
		return this.a((WorldRenderer)var1, (WorldRenderer)var2);
	}
}
