package me.TadanoMoyasi.oLimboClient.core.api;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class APISender {
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	private static int ticks = -1;
	
	private static boolean APITick = false;
	
	public static void sendPlayerAPIChat() {
		 mc.thePlayer.sendChatMessage("/thelow_api player");
	  }
	  
	  public static void sendAPISubscribeChat() {
		  mc.thePlayer.sendChatMessage("/thelow_api subscribe SKILL_COOLTIME");
	  }
	  
	  public static void start(int tick) {
		  APITick = true;
		  setTicks(tick);
	  }
	  
	  public static void end() {
		  APITick = false;
		  setTicks(-1);
	  }
	  
	  public static void setTicks(int tick) {
		  ticks = tick;
	  }
	
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		if (mc.thePlayer == null) return;
		if (APITick && ticks >= 0) {
			ticks--;
		} else {
			ticks = 3600; 
			sendPlayerAPIChat();
		}
	}
}
