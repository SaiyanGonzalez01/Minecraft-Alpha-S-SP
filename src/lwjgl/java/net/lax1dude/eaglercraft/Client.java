package net.lax1dude.eaglercraft;

import javax.swing.JOptionPane;

import net.PeytonPlayz585.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Session;

public class Client {

	public static void main(String[] par0ArrayOfStr) {
		//JOptionPane.showMessageDialog(null, "Press ok to continue", "Alpha v1.2.6",
				//JOptionPane.PLAIN_MESSAGE);
		
		GL11.EaglerAdapterImpl2.initializeContext();

		Minecraft mc = new Minecraft();
		mc.session = new Session("Player");
		mc.run();

	}
}