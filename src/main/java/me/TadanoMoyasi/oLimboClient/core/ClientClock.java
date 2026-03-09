package me.TadanoMoyasi.oLimboClient.core;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientClock {
	private static int tick;
	
	public static int now() {
		return tick;
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) {
		if (e.phase == TickEvent.Phase.END) {
			tick++;
		}
	}
}
