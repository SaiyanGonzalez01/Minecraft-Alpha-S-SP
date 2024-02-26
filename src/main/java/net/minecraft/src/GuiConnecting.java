package net.minecraft.src;

import java.io.IOException;

import net.PeytonPlayz585.opengl.GL11;
import net.minecraft.client.Minecraft;

public class GuiConnecting extends GuiScreen {
	private NetClientHandler clientHandler;
	private boolean cancelled = false;
	String uri;
	private int timer = 0;

	public GuiConnecting(Minecraft var1, String var2, int var3) {
		cancelled = false;
		uri = var2 + ":" + var3;
		var1.func_6261_a((World)null);
	}

	public void updateScreen() {
		if (timer > 2 && this.clientHandler == null) {
			try {
				String uria = null;
				if(uri.startsWith("ws://")) {
					uria = uri.substring(5);
				}else if(uri.startsWith("wss://")){
					uria = uri.substring(6);
				}else if(!uri.contains("://")){
					uria = uri;
					uri = "ws://" + uri;
				}else {
					this.mc.displayGuiScreen(new GuiConnectFailed("disconnect.genericReason", "invalid uri websocket protocol"));
					return;
				}
				
				int i = uria.lastIndexOf(':');
				int port = -1;
				
				if(i > 0 && uria.startsWith("[") && uria.charAt(i - 1) != ']') {
					i = -1;
				}
				
				if(i == -1) port = uri.startsWith("wss") ? 443 : 80;
				if(uria.endsWith("/")) uria = uria.substring(0, uria.length() - 1);
				
				if(port == -1) {
					try {
						int i2 = uria.indexOf('/');
						port = Integer.parseInt(uria.substring(i + 1, i2 == -1 ? uria.length() : i2 - 1));
					}catch(Throwable t) {
						this.mc.displayGuiScreen(new GuiConnectFailed("disconnect.genericReason", "invalid port number"));
					}
				}
				
				this.clientHandler = new NetClientHandler(mc, uri, 0);
				this.clientHandler.addToSendQueue(new Packet2Handshake(mc.field_6320_i.inventory));
			} catch (IOException e) {
				try {
					this.clientHandler.disconnect();
				}catch(Throwable t) {
				}
				e.printStackTrace();
				this.mc.displayGuiScreen(new GuiConnectFailed("disconnect.genericReason", e.toString()));
			}
		}
		if (clientHandler != null) {
			clientHandler.processReadPackets();
		}
		if(timer >= 1) {
			++timer;
		}
		if(timer > 5) {
			if(!GL11.connectionOpen() && this.mc.currentScreen == this) {
				this.mc.displayGuiScreen(new GuiConnectFailed("connect.failed", "disconnect.timeout"));
			}
		}
	}

	protected void keyTyped(char var1, int var2) {
	}

	public void initGui() {
		this.controlList.clear();
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.id == 0) {
			this.cancelled = true;
			if(this.clientHandler != null) {
				this.clientHandler.disconnect();
			}

			this.mc.displayGuiScreen(new GuiMainMenu());
		}

	}

	public void drawScreen(int var1, int var2, float var3) {
		if(timer == 0) {
			timer = 1;
		}
		this.drawDefaultBackground();
		if(this.clientHandler == null) {
			this.drawCenteredString(this.fontRenderer, "Connecting to the server...", this.width / 2, this.height / 2 - 50, 16777215);
			this.drawCenteredString(this.fontRenderer, "", this.width / 2, this.height / 2 - 10, 16777215);
		} else {
			this.drawCenteredString(this.fontRenderer, "Logging in...", this.width / 2, this.height / 2 - 50, 16777215);
			this.drawCenteredString(this.fontRenderer, this.clientHandler.field_1209_a, this.width / 2, this.height / 2 - 10, 16777215);
		}

		super.drawScreen(var1, var2, var3);
	}

	static NetClientHandler setNetClientHandler(GuiConnecting var0, NetClientHandler var1) {
		return var0.clientHandler = var1;
	}

	static boolean isCancelled(GuiConnecting var0) {
		return var0.cancelled;
	}

	static NetClientHandler getNetClientHandler(GuiConnecting var0) {
		return var0.clientHandler;
	}
}
