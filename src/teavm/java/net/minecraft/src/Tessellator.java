package net.minecraft.src;

import java.nio.ByteOrder;

import org.teavm.jso.typedarrays.ArrayBuffer;
import org.teavm.jso.typedarrays.Float32Array;
import org.teavm.jso.typedarrays.Int32Array;

import net.PeytonPlayz585.opengl.GL11;

public class Tessellator {
	
	private Int32Array intBuffer;
	private Float32Array floatBuffer;
	private int vertexCount = 0;
	private double textureU;
	private double textureV;
	private int color;
	private boolean hasColor = false;
	private boolean hasTexture = false;
	private boolean hasNormals = false;
	private int rawBufferIndex = 0;
	private int addedVertices = 0;
	private boolean isColorDisabled = false;
	private int drawMode;
	private double xOffset;
	private double yOffset;
	private double zOffset;
	public static final Tessellator instance = new Tessellator(2097152);
	private boolean isDrawing = false;
	private int normal;
	private int bufferSize;

	private Tessellator(int par1) {
		this.bufferSize = par1;
		ArrayBuffer a = ArrayBuffer.create(par1 * 4);
		this.intBuffer = Int32Array.create(a);
		this.floatBuffer = Float32Array.create(a);
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
				GL11.glDrawArrays(this.drawMode, GL11.GL_POINTS, this.vertexCount, Int32Array.create(intBuffer.getBuffer(), 0, this.vertexCount * 8));
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
		if(this.isDrawing) {
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
		
		Int32Array intBuffer0 = intBuffer;
		Float32Array floatBuffer0 = floatBuffer;
		
		floatBuffer0.set(this.rawBufferIndex + 0, (float)(par1 + this.xOffset));
		floatBuffer0.set(this.rawBufferIndex + 1, (float)(par3 + this.yOffset));
		floatBuffer0.set(this.rawBufferIndex + 2, (float)(par5 + this.zOffset));

		if(this.hasTexture) {
			floatBuffer0.set(this.rawBufferIndex + 3, (float)this.textureU);
			floatBuffer0.set(this.rawBufferIndex + 4, (float)this.textureV);
		}

		if(this.hasColor) {
			intBuffer0.set(this.rawBufferIndex + 5, this.color);
		}

		if(this.hasNormals) {
			intBuffer0.set(this.rawBufferIndex + 6, this.normal);
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
		this.xOffset += (float) par1;
		this.yOffset += (float) par2;
		this.zOffset += (float) par3;
	}
}