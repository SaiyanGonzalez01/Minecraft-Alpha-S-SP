package net.minecraft.src;

import net.minecraft.client.Minecraft;

class ThreadConnectToServer {
	final Minecraft mc;
	final String uri;
	final GuiConnecting connectingGui;

	ThreadConnectToServer(GuiConnecting var1, Minecraft var2, String var3) {
		this.connectingGui = var1;
		this.mc = var2;
		this.uri = var3;
	}

	public void start() {
		try {
			GuiConnecting.setNetClientHandler(this.connectingGui, new NetClientHandler(this.mc, this.uri));
			if(GuiConnecting.isCancelled(this.connectingGui)) {
				return;
			}

			GuiConnecting.getNetClientHandler(this.connectingGui).handleHandshake();
		} catch (Throwable var4) {
			if(GuiConnecting.isCancelled(this.connectingGui)) {
				return;
			}

			this.mc.displayGuiScreen(new GuiConnectFailed("Failed to connect to the server", var4.toString()));
			var4.printStackTrace();
		}

	}
}