package net.minecraft.src;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import net.PeytonPlayz585.opengl.GL11;
import net.minecraft.src.MathHelper;

public class Tessellator {

	/** The byte buffer used for GL allocation. */
	private ByteBuffer byteBuffer;
	private IntBuffer intBuffer;

	/** Raw integer array. */
	private int[] rawBuffer;

	/**
	 * The number of vertices to be drawn in the next draw call. Reset to 0 between
	 * draw calls.
	 */
	private int vertexCount = 0;

	/** The first coordinate to be used for the texture. */
	private double textureU;

	/** The second coordinate to be used for the texture. */
	private double textureV;

	/** The color (RGBA) value to be used for the following draw call. */
	private int color;

	/**
	 * Whether the current draw object for this tessellator has color values.
	 */
	private boolean hasColor = false;

	/**
	 * Whether the current draw object for this tessellator has texture coordinates.
	 */
	private boolean hasTexture = false;

	/**
	 * Whether the current draw object for this tessellator has normal values.
	 */
	private boolean hasNormals = false;

	/** The index into the raw buffer to be used for the next data. */
	private int rawBufferIndex = 0;

	/**
	 * The number of vertices manually added to the given draw call. This differs
	 * from vertexCount because it adds extra vertices when converting quads to
	 * triangles.
	 */
	private int addedVertices = 0;

	/** Disables all color information for the following draw call. */
	private boolean isColorDisabled = false;

	/** The draw mode currently being used by the tessellator. */
	private int drawMode;

	/**
	 * An offset to be applied along the x-axis for all vertices in this draw call.
	 */
	private double xOffset;

	/**
	 * An offset to be applied along the y-axis for all vertices in this draw call.
	 */
	private double yOffset;

	/**
	 * An offset to be applied along the z-axis for all vertices in this draw call.
	 */
	private double zOffset;

	/** The normal to be applied to the face being drawn. */
	private int normal;

	/** The static instance of the Tessellator. */
	public static final Tessellator instance = new Tessellator(2097152);

	/** Whether this tessellator is currently in draw mode. */
	private boolean isDrawing = false;
	
	private int bufferSize;


	private Tessellator(int par1) {
		this.bufferSize = par1;
		this.byteBuffer = GLAllocation.createDirectByteBuffer(par1 * 4);
		this.intBuffer = this.byteBuffer.asIntBuffer();
		this.rawBuffer = new int[par1];
	}

	/**
	 * Draws the data set up in this tessellator and resets the state to prepare for
	 * new drawing.
	 */
	public void draw() {
		if (!this.isDrawing) {
			throw new IllegalStateException("Not tesselating!");
		} else {
			this.isDrawing = false;

			if (this.vertexCount > 0) {
				IntBuffer upload = null;
				this.intBuffer.clear();
				this.intBuffer.put(rawBuffer, 0, this.rawBufferIndex);
				this.intBuffer.flip();
				upload = this.intBuffer;

				if(this.hasTexture) {
					GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				}

				if(this.hasColor) {
					GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
				}

				if(this.hasNormals) {
					GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
				}

				GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
				GL11.glDrawArrays(this.drawMode, GL11.GL_POINTS, this.vertexCount, upload);
				GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);

				if(this.hasTexture) {
					GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				}

				if(this.hasColor) {
					GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
				}

				if(this.hasNormals) {
					GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
				}
			}

			this.reset();
		}
	}

	/**
	 * Clears the tessellator state in preparation for new drawing.
	 */
	private void reset() {
		this.vertexCount = 0;
		this.rawBufferIndex = 0;
		this.addedVertices = 0;
	}

	/**
	 * Sets draw mode in the tessellator to draw quads.
	 */
	public void startDrawingQuads() {
		this.startDrawing(7);
	}

	/**
	 * Resets tessellator state and prepares for drawing (with the specified draw
	 * mode).
	 */
	public void startDrawing(int par1) {
		if (this.isDrawing) {
			throw new IllegalStateException("Already tesselating!");
		} else {
			this.isDrawing = true;
			this.reset();
			this.drawMode = par1;
			this.hasNormals = false;
			this.hasColor = false;
			this.hasTexture = false;
			this.isColorDisabled = false;
		}
	}

	/**
	 * Sets the texture coordinates.
	 */
	public void setTextureUV(double par1, double par3) {
		this.hasTexture = true;
		this.textureU = (float) par1;
		this.textureV = (float) par3;
	}

	public void setColorOpaque_F(float var1, float var2, float var3) {
		this.setColorOpaque((int)(var1 * 255.0F), (int)(var2 * 255.0F), (int)(var3 * 255.0F));
	}

	public void setColorRGBA_F(float var1, float var2, float var3, float var4) {
		this.setColorRGBA((int)(var1 * 255.0F), (int)(var2 * 255.0F), (int)(var3 * 255.0F), (int)(var4 * 255.0F));
	}

	public void setColorOpaque(int var1, int var2, int var3) {
		this.setColorRGBA(var1, var2, var3, 255);
	}

	public void setColorRGBA(int var1, int var2, int var3, int var4) {
		if(!this.isColorDisabled) {
			if(var1 > 255) {
				var1 = 255;
			}

			if(var2 > 255) {
				var2 = 255;
			}

			if(var3 > 255) {
				var3 = 255;
			}

			if(var4 > 255) {
				var4 = 255;
			}

			if(var1 < 0) {
				var1 = 0;
			}

			if(var2 < 0) {
				var2 = 0;
			}

			if(var3 < 0) {
				var3 = 0;
			}

			if(var4 < 0) {
				var4 = 0;
			}

			this.hasColor = true;
			if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
				this.color = var4 << 24 | var3 << 16 | var2 << 8 | var1;
			} else {
				this.color = var1 << 24 | var2 << 16 | var3 << 8 | var4;
			}
		}
	}

	public void addVertexWithUV(double var1, double var3, double var5, double var7, double var9) {
		this.setTextureUV(var7, var9);
		this.addVertex(var1, var3, var5);
	}

	/**
	 * Adds a vertex with the specified x,y,z to the current draw call. It will
	 * trigger a draw() if the buffer gets full.
	 */
	public void addVertex(double par1, double par3, double par5) {
		if(this.addedVertices > 65534) return;
		++this.addedVertices;
		
		this.rawBuffer[this.rawBufferIndex + 0] = Float.floatToRawIntBits((float) (par1 + this.xOffset));
		this.rawBuffer[this.rawBufferIndex + 1] = Float.floatToRawIntBits((float) (par3 + this.yOffset));
		this.rawBuffer[this.rawBufferIndex + 2] = Float.floatToRawIntBits((float) (par5 + this.zOffset));

		if (this.hasTexture) {
			this.rawBuffer[this.rawBufferIndex + 3] = Float.floatToRawIntBits((float) this.textureU);
			this.rawBuffer[this.rawBufferIndex + 4] = Float.floatToRawIntBits((float) this.textureV);
		}

		if (this.hasColor) {
			this.rawBuffer[this.rawBufferIndex + 5] = this.color;
		}

		if (this.hasNormals) {
			this.rawBuffer[this.rawBufferIndex + 6] = this.normal;
		}

		this.rawBufferIndex += 8;
		++this.vertexCount;
		if(this.vertexCount % 4 == 0 && this.rawBufferIndex >= this.bufferSize - 32) {
			this.draw();
			this.isDrawing = true;
		}
	}

	public void setColorOpaque_I(int var1) {
		int var2 = var1 >>> 16 & 255;
		int var3 = var1 >>> 8 & 255;
		int var4 = var1 & 255;
		this.setColorOpaque(var2, var3, var4);
	}

	public void setColorRGBA_I(int var1, int var2) {
		int var3 = var1 >>> 16 & 255;
		int var4 = var1 >>> 8 & 255;
		int var5 = var1 & 255;
		this.setColorRGBA(var3, var4, var5, var2);
	}

	/**
	 * Disables colors for the current draw call.
	 */
	public void disableColor() {
		this.isColorDisabled = true;
	}

	/**
	 * Sets the normal for the current draw call.
	 */
	public void setNormal(float par1, float par2, float par3) {
		if(!this.isDrawing) {
			System.out.println("But..");
		}

		this.hasNormals = true;
		int var4 = (int)((par1) * 127.0F) + 127;
		int var5 = (int)((par2) * 127.0F) + 127;
		int var6 = (int)((par3) * 127.0F) + 127;
		this.normal = var4 & 255 | (var5 & 255) << 8 | (var6 & 255) << 16;
	}

	/**
	 * Sets the translation for all vertices in the current draw call.
	 */
	public void setTranslationD(double par1, double par3, double par5) {
		this.xOffset = par1;
		this.yOffset = par3;
		this.zOffset = par5;
	}

	/**
	 * Offsets the translation for all vertices in the current draw call.
	 */
	public void setTranslationF(float par1, float par2, float par3) {
		this.xOffset += (double) par1;
		this.yOffset += (double) par2;
		this.zOffset += (double) par3;
	}
	
	public double debugGetTranslationX() {
		return xOffset;
	}
	
	public double debugGetTranslationY() {
		return yOffset;
	}
	
	public double debugGetTranslationZ() {
		return zOffset;
	}
	
}