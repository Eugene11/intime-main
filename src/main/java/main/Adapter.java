package main;/*import bss.ServiceEx.Listener.PoolListeners;
import bss.ServiceEx.Process.PoolProcesses;
import bss.ServiceEx.Sender.PoolSenders;
import bss.ServiceEx.Settings.Setting;*/

import Listener.PoolListeners;
import Settings.Setting;

public class Adapter {

	private PoolListeners poolListeners = null;
	/*private PoolProcesses poolProcesses = null;
	private PoolSenders poolSenders = null;
	*/
	public Adapter(Setting setting) throws Exception {
		poolListeners = new PoolListeners(setting);
		/*poolSenders = new PoolSenders(setting);
		
		poolProcesses = new PoolProcesses(setting);		
		poolProcesses.setQueueTrIn(poolListeners.getQueue());
		poolProcesses.setQueueTrOut(poolSenders.getQueue());
		*/
	}
	
	public void start(){
		new Thread(new Runnable(){
			public void run(){

				poolListeners.Start();
				/*poolSenders.Start();
				poolProcesses.Start();*/
			}
		}).start();
	}
	
	public void stop(){
		/*
		poolListeners.Destroy();
		poolSenders.Destroy();
		poolProcesses.Destroy();
		*/
	}
	
}
