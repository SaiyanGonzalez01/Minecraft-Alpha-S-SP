package net.minecraft.src;

public class Material {
	public static final Material air = new MaterialTransparent();
	public static final Material ground = new Material();
	public static final Material wood = (new Material()).setBurning();
	public static final Material rock = new Material();
	public static final Material iron = new Material();
	public static final Material water = new MaterialLiquid();
	public static final Material lava = new MaterialLiquid();
	public static final Material field_4218_h = (new Material()).setBurning();
	public static final Material plants = new MaterialLogic();
	public static final Material sponge = new Material();
	public static final Material field_4217_k = (new Material()).setBurning();
	public static final Material fire = new MaterialTransparent();
	public static final Material sand = new Material();
	public static final Material circuits = new MaterialLogic();
	public static final Material field_4216_o = new Material();
	public static final Material tnt = (new Material()).setBurning();
	public static final Material field_4215_q = new Material();
	public static final Material ice = new Material();
	public static final Material snow = new MaterialLogic();
	public static final Material builtSnow = new Material();
	public static final Material field_4214_u = new Material();
	public static final Material clay = new Material();
	public static final Material field_4213_w = new Material();
	public static final Material field_4212_x = new Material();
	private boolean canBurn;

	public boolean getIsLiquid() {
		return false;
	}

	public boolean func_216_a() {
		return true;
	}

	public boolean getCanBlockGrass() {
		return true;
	}

	public boolean func_218_c() {
		return true;
	}

	private Material setBurning() {
		this.canBurn = true;
		return this;
	}

	public boolean getBurning() {
		return this.canBurn;
	}
}
