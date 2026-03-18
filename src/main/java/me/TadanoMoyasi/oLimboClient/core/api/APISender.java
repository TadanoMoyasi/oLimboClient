package me.TadanoMoyasi.oLimboClient.core.api;

import me.TadanoMoyasi.oLimboClient.core.ClientClock;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class APISender {
	private static final Minecraft mc = Minecraft.getMinecraft();
	private static int nextTick = -1;
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) {
		if (e.phase != TickEvent.Phase.END) return;
		if (nextTick == -1) return;
		if (ClientClock.now() > nextTick) {
			sendPlayerAPIChat();
		}
	}
			
	public static void sendPlayerAPIChat() {
		if (mc.thePlayer == null) {
			nextTick = ClientClock.now() + 3600;
			return;
		}
		 mc.thePlayer.sendChatMessage("/thelow_api player");
		 nextTick = ClientClock.now() + 3600;
	}
	  
	  public static void sendAPISubscribeChat() {
		  if (mc.thePlayer == null) return;
		  mc.thePlayer.sendChatMessage("/thelow_api subscribe SKILL_COOLTIME");
	  }
	  
	  public static void start(int tick) {
		  nextTick = ClientClock.now() + tick;
	  }
	  
	  public static void end() {
		  nextTick = -1;
	  }
}
