package net.lax1dude.eaglercraft;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.lwjgl.opengl.Display;

import net.PeytonPlayz585.opengl.GL11;
import net.minecraft.client.Minecraft;

public final class GameWindowListener extends WindowAdapter {
	public void windowClosing(WindowEvent par1WindowEvent) {
		Minecraft.getMinecraft().shutdown();
	}
}