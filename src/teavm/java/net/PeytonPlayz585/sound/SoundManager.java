package net.PeytonPlayz585.sound;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Random;

import net.PeytonPlayz585.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;

public class SoundManager {
	
	static String[] music = new String[] {"calm1", "calm2", "calm3"};
	static String[] newMusic = new String[] {"hal1", "hal2", "hal3", "hal4", "nuance1", "nuance2", "piano1", "piano2", "piano3"};
	
	private static int song = 0;
	private static String prevSong = "";
	
	/*
	 * Will be set to true when a song begins playback
	 * This will initiate the timer for the next song
	 */
	private boolean musicFlag = false;
	private boolean musicTimerSet = false;
	private int musicTimer = 0;
	
	public void func_336_b(String s, float f, float g, float h, float i, float j) {
		String file = s.replace(".", "/");
		String[] integers = new String[] {"1", "2", "3", "4"};
		boolean flag = false;
		
		for(String s1 : integers) {
			if(file.endsWith(s1)) {
				flag = true;
			}
		}
		
		if(i > 1.0F) {
			i = 1.0F;
		}
		
		file = "sounds/" + file;
		if(flag) {
			int i1 = GL11.beginPlayback(file + ".mp3", f, g, h, i * Minecraft.getMinecraft().gameSettings.soundVolume, j);
			if(i1 == -1) {
				try {
					throw new FileNotFoundException("Audio file " + file + " not found!");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			Random rand = new Random();
			int i1 = rand.nextInt(4 - 1 + 1) + 1;
			int i2 = GL11.beginPlayback(file + i1 + ".mp3", f, g, h, i * Minecraft.getMinecraft().gameSettings.soundVolume, j);
			if(i2 == -1) {
				int i3 = 0;
				if(i1 == 4) {
					i3 = GL11.beginPlayback(file + 3 + ".mp3", f, g, h, i * Minecraft.getMinecraft().gameSettings.soundVolume, j);
					if(i3 == -1) {
						i3 = GL11.beginPlayback(file + 2 + ".mp3", f, g, h, i * Minecraft.getMinecraft().gameSettings.soundVolume, j);
						if(i3 == -1) {
							i3 = GL11.beginPlayback(file + 1 + ".mp3", f, g, h, i * Minecraft.getMinecraft().gameSettings.soundVolume, j);
						}
					}
				}
				
				if(i1 == 3) {
					i3 = GL11.beginPlayback(file + 2 + ".mp3", f, g, h, i * Minecraft.getMinecraft().gameSettings.soundVolume, j);
					if(i3 == -1) {
						i3 = GL11.beginPlayback(file + 1 + ".mp3", f, g, h, i * Minecraft.getMinecraft().gameSettings.soundVolume, j);
					}
				}
				
				if(i1 == 2) {
					i3 = GL11.beginPlayback(file + 1 + ".mp3", f, g, h, i * Minecraft.getMinecraft().gameSettings.soundVolume, j);
				}
				
				if(i1 == 1) {
					i3 = GL11.beginPlayback(file + ".mp3", f, g, h, i * Minecraft.getMinecraft().gameSettings.soundVolume, j);
				}
				
				if(i3 == -1) {
					int i4 = 0;
					i4 = GL11.beginPlayback(file + ".mp3", f, g, h, i * Minecraft.getMinecraft().gameSettings.soundVolume, j);
					if(i4 == -1) {
						try {
							throw new FileNotFoundException("Audio file " + file + " not found!");
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public void func_338_a(EntityPlayer player, float f) {
		if(player == null) {
			GL11.setListenerPos(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
		} else {
			double x = player.prevPosX + (player.posX - player.prevPosX) * f;
			double y = player.prevPosY + (player.posY - player.prevPosY) * f;
			double z = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
			double pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
			double yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
			
			try {
				GL11.setListenerPos((float)x, (float)y, (float)z, (float)player.motionX, (float)player.motionY, (float)player.motionZ, (float)pitch, (float)yaw);
			} catch(Exception e) {
				
			}
		}
	}
	
	public void func_337_a(String file, float volume, float pitch) {
		file = file.replace(".", "/");
		String sound = "sounds/" + file;
		if(!sound.endsWith(".mp3")) {
			sound = sound + ".mp3";
		}
		
		if(volume > 1.0F) {
			volume = 1.0F;
		}
		
		volume *= 0.25F;
		int i = GL11.beginPlaybackStatic(sound, volume * Minecraft.getMinecraft().gameSettings.soundVolume, pitch);
		if(i == - 1) {
			try {
				throw new FileNotFoundException("Audio file " + file + " not found!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void onSoundOptionsChanged() {
		for (Map.Entry<Integer, GL11.AudioBufferSourceNodeX> entry : GL11.activeSoundEffects.entrySet()) {
			int i = entry.getKey();
			
			if(GL11.isPlaying(i)) {
				if(i != song) {
					GL11.setVolume(i, GL11.getVolume(i) * Minecraft.getMinecraft().gameSettings.soundVolume);
				} else {
					GL11.setVolume(i, 1.0F * Minecraft.getMinecraft().gameSettings.musicVolume);
				}
			}
		}
	}
	
	int ticks = 0;

	public void musicTick() throws FileNotFoundException {
		if(Minecraft.getMinecraft().gameSettings.musicVolume == 0.0F || Minecraft.getMinecraft().theWorld == null) {
			if(GL11.isPlaying(song)) {
				GL11.endSound(song);
			}
			musicFlag = true;
			musicTimer = 0;
			musicTimerSet = false;
			song = 0;
			return;
		}
		
		if(!GL11.isPlaying(song) && musicFlag && !musicTimerSet) {
			Random rand = new Random();
			musicTimer = rand.nextInt(6 - 3 + 1) + 3;
			musicTimer = musicTimer * 60;
			musicTimer = musicTimer * 20;
			musicTimerSet = true;
			song = 0;
			ticks = Minecraft.getMinecraft().ticksRan + musicTimer;
			return;
		}
		
		if(!GL11.isPlaying(song) && musicFlag && musicTimerSet) {
			if(Minecraft.getMinecraft().ticksRan >= ticks) {
				musicFlag = false;
				musicTimer = 0;
				musicTimerSet = false;
				song = 0;
				return;
			}
		}
		
		if(!GL11.isPlaying(song) && !musicFlag) {
			Random rand = new Random();
			int i = rand.nextInt(2);
			
			if(i == 0) {
		        int i1 = rand.nextInt((music.length - 1) + 1) + 1;
		        String songToPlay = music[i1 - 1];
		        if(songToPlay.equals(prevSong)) {
		        	return;
		        }
		        prevSong = songToPlay;
		        song = GL11.beginPlaybackStatic("/music/" + songToPlay + ".mp3", Minecraft.getMinecraft().gameSettings.musicVolume, 1.0F);
		        if(song == -1) {
		        	throw new FileNotFoundException("Audio file " + songToPlay + ".mp3 not found!");
		        }
			} else {
				int i1 = rand.nextInt((newMusic.length - 1) + 1) + 1;
		        String songToPlay = newMusic[i1 - 1];
		        if(songToPlay.equals(prevSong)) {
		        	return;
		        }
		        prevSong = songToPlay;
		        song = GL11.beginPlaybackStatic("/newMusic/" + songToPlay + ".mp3", Minecraft.getMinecraft().gameSettings.musicVolume, 1.0F);
		        if(song == -1) {
		        	throw new FileNotFoundException("Audio file " + songToPlay + ".mp3 not found!");
		        }
			}
			musicFlag = true;
		}
	}
}
