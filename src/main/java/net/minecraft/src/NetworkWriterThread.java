package net.minecraft.src;

class NetworkWriterThread extends Thread {
	final NetworkManager netManager;

	NetworkWriterThread(NetworkManager var1, String var2) {
		super(var2);
		this.netManager = var1;
	}

	public void run() {
		++NetworkManager.numWriteThreads;
		while(true) {
			boolean var11 = false;

			try {
				var11 = true;
				if(!NetworkManager.isRunning(this.netManager)) {
					var11 = false;
					break;
				}

				NetworkManager.sendNetworkPacket(this.netManager);
			} finally {
				if(var11) {
					--NetworkManager.numWriteThreads;
				}
			}
		}

		--NetworkManager.numWriteThreads;
	}
}
