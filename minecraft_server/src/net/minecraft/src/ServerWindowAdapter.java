package net.minecraft.src;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import net.minecraft.server.MinecraftServer;

final class ServerWindowAdapter extends WindowAdapter {
	final MinecraftServer mcServer;

	ServerWindowAdapter(MinecraftServer var1) {
		this.mcServer = var1;
	}

	public void windowClosing(WindowEvent var1) {
		this.mcServer.func_6016_a();

		while(!this.mcServer.field_6032_g) {
			try {
				Thread.sleep(100L);
			} catch (InterruptedException var3) {
				var3.printStackTrace();
			}
		}

		System.exit(0);
	}
}
