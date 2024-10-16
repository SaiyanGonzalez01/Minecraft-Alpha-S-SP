package net.minecraft.src;

import net.PeytonPlayz585.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import net.PeytonPlayz585.EaglercraftRandom;

public class SoundManager {
	private int field_587_e = 0;
	private GameSettings options;
	private static boolean loaded = false;
	private EaglercraftRandom rand = new EaglercraftRandom();
	private int field_583_i = this.rand.nextInt(12000);
	
	private static List<Integer> sounds = new ArrayList<Integer>();
	private static int BgMusic = -1;
	
	static String[] music = new String[] {"music.calm1_mp3", "music.calm2_mp3", "music.calm3_mp3", "newMusic.hal1_mp3", "newMusic.hal2_mp3", "newMusic.hal3_mp3", "newMusic.hal4_mp3", "newMusic.nuance1_mp3", "newMusic.nuance2_mp3", "newMusic.piano1_mp3", "newMusic.piano2_mp3", "newMusic.piano3_mp3"};

	public void func_340_a(GameSettings var1) {
		this.options = var1;
		loaded = true;
	}

	public void onSoundOptionsChanged() {
		for(int i = 0; i < sounds.size(); i++) {
			if(!GL11.EaglerAdapterImpl2.isPlaying(sounds.get(i))) {
				sounds.remove(i);
			}
		}
		
		if(loaded) {
			if(this.options.musicVolume == 0.0F) {
				if(BgMusic != -1) {
					GL11.EaglerAdapterImpl2.endSound(BgMusic);
				}
			} else {
				if(BgMusic != -1) {
					GL11.EaglerAdapterImpl2.setVolume(BgMusic, this.options.musicVolume);
				}
			}
			
			if(this.options.soundVolume == 0.0F) {
				for(Integer i : sounds) {
					if(i != -1) {
						GL11.EaglerAdapterImpl2.endSound(i);
					}
				}
			} else {
				for(Integer i : sounds) {
					if(i != -1) {
						GL11.EaglerAdapterImpl2.setVolume(i, this.options.soundVolume);
					}
				}
			}
		}

	}

	public void closeMinecraft() {
		/*
		 * Already handled by  the EaglerAdapter
		 */
	}

	public void func_4033_c() {
		for(int i = 0; i < sounds.size(); i++) {
			if(!GL11.EaglerAdapterImpl2.isPlaying(sounds.get(i))) {
				sounds.remove(i);
			}
		}
		
		if(loaded && this.options.musicVolume != 0.0F) {
			if(!GL11.EaglerAdapterImpl2.isPlaying(BgMusic)) {
				try {
					if(this.field_583_i > 0) {
						--this.field_583_i;
						return;
					}
				
					//Apparently I DO NOT know how to use random.nextInt
					int var1 = rand.nextInt(music.length);
					BgMusic = GL11.EaglerAdapterImpl2.beginPlaybackStatic(music[var1].replace(".", "/").replace("_", "."), this.options.musicVolume, 1.0F);
					
					if(BgMusic != -1) {
						this.field_583_i = this.rand.nextInt(12000) + 12000;
					}
				} catch(Exception e) {
					BgMusic = -1;
					return;
				}
			}
		}
	}

	public void func_338_a(EntityLiving var1, float var2) {
		for(int i = 0; i < sounds.size(); i++) {
			if(!GL11.EaglerAdapterImpl2.isPlaying(sounds.get(i))) {
				sounds.remove(i);
			}
		}
		
		if(loaded && this.options.soundVolume != 0.0F) {
			if(var1 != null) {
				float var9 = var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var2;
				float var3 = var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var2;
	            double var4 = var1.prevPosX + (var1.posX - var1.prevPosX) * (double)var2;
			    double var6 = var1.prevPosY + (var1.posY - var1.prevPosY) * (double)var2;
			    double var8 = var1.prevPosZ + (var1.posZ - var1.prevPosZ) * (double)var2;
			    try {
			    	GL11.EaglerAdapterImpl2.setListenerPos((float)var4, (float)var6, (float)var8, (float)var1.motionX, (float)var1.motionY, (float)var1.motionZ, (float)var9, (float)var3);
			    } catch(Exception e) {
			    	// ???
			    }
			}
		}
	}
	
	public void func_331_a(String var1, float var2, float var3, float var4, float var5, float var6) {
		func_331_a(var1, var2, var3, var4, var5, var6, rand.nextInt((4 - 1) + 1) + 1);
	}

	public void func_331_a(String var1, float var2, float var3, float var4, float var5, float var6, int number) {
		for(int i = 0; i < sounds.size(); i++) {
			if(!GL11.EaglerAdapterImpl2.isPlaying(sounds.get(i))) {
				sounds.remove(i);
			}
		}
		
		if(loaded && this.options.soundVolume != 0.0F) {
			if(var1 == null) {
				return;
			}
			
			String var7;
			var7 = "sounds/" + var1.replace(".", "/") + number + ".mp3";
			if(var7 != null && var5 > 0.0F) {
				if(var5 > 1.0F) {
					var5 = 1.0F;
				}

				int i = GL11.EaglerAdapterImpl2.beginPlayback(var7.replace("0", ""), var2, var3, var4, var5 * this.options.soundVolume, var6);
				if(i != -1) {
					sounds.add(i);
				} else {
					if(number != 0) {
						func_331_a(var1, var2, var3, var4, var5, var6, number - 1);
					} else {
						System.err.println("Unknown sound: " + var7.replace("0", ""));
					}
				}
			}
		}
	}

	public void func_336_b(String var1, float var2, float var3, float var4, float var5, float var6) {
		func_336_b(var1, var2, var3, var4, var5, var6, rand.nextInt((4 - 1) + 1) + 1);
	}
	
	public void func_336_b(String var1, float var2, float var3, float var4, float var5, float var6, int number) {
		for(int i = 0; i < sounds.size(); i++) {
			if(!GL11.EaglerAdapterImpl2.isPlaying(sounds.get(i))) {
				sounds.remove(i);
			}
		}
		
		if(loaded && this.options.soundVolume != 0.0F) {
			if(var1 == null) {
				return;
			}
			
			String var7;
			var7 = "sounds/" + var1.replace(".", "/") + number + ".mp3";
			if(var7 != null && var5 > 0.0F) {
				if(var5 > 1.0F) {
					var5 = 1.0F;
				}

				int i = GL11.EaglerAdapterImpl2.beginPlayback(var7.replace("0", ""), var2, var3, var4, var5 * this.options.soundVolume, var6);
				if(i != -1) {
					sounds.add(i);
				} else {
					if(number != 0) {
						func_336_b(var1, var2, var3, var4, var5, var6, number - 1);
					} else {
						System.err.println("Unknown sound: " + var7.replace("0", ""));
					}
				}
			}
		}
	}
	
	public void func_337_a(String var1, float var2, float var3) {
		func_337_a(var1, var2, var3, rand.nextInt((4 - 1) + 1) + 1);
	}
	
	public void func_337_a(String var1, float var2, float var3, int number) {
		for(int i = 0; i < sounds.size(); i++) {
			if(!GL11.EaglerAdapterImpl2.isPlaying(sounds.get(i))) {
				sounds.remove(i);
			}
		}
		
		if(loaded && this.options.soundVolume != 0.0F) {
			String var4 = "sounds/" + var1.replace(".", "/") + number + ".mp3";
			if(var4 != null) {
				if(var2 > 1.0F) {
					var2 = 1.0F;
				}

				int i = GL11.EaglerAdapterImpl2.beginPlaybackStatic(var4.replace("0", ""), var2 * this.options.soundVolume, var3);
				if(i != -1) {
					sounds.add(i);
				} else {
					if(number != 0) {
						func_337_a(var1, var2, var3, number - 1);
					} else {
						System.err.println("Unknown sound: " + var4.replace("0", ""));
					}
				}
			}

		}
	}
}
