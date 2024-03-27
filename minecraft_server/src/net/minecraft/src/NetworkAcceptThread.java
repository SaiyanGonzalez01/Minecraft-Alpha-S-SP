package net.minecraft.src;

import java.io.IOException;
import java.net.Socket;
import net.minecraft.server.MinecraftServer;

class NetworkAcceptThread extends Thread {
	final MinecraftServer mcServer;
	final NetworkListenThread field_985_b;

	NetworkAcceptThread(NetworkListenThread var1, String var2, MinecraftServer var3) {
		super(var2);
		this.field_985_b = var1;
		this.mcServer = var3;
	}

	public void run() {
		while(this.field_985_b.field_973_b) {
			try {
				Socket var1 = NetworkListenThread.func_713_a(this.field_985_b).accept();
				if(var1 != null) {
					NetLoginHandler var2 = new NetLoginHandler(this.mcServer, var1, "Connection #" + NetworkListenThread.func_712_b(this.field_985_b));
					NetworkListenThread.func_716_a(this.field_985_b, var2);
				}
			} catch (IOException var3) {
				var3.printStackTrace();
			}
		}

	}
}
