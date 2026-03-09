package me.TadanoMoyasi.oLimboClient.core.api;

import me.TadanoMoyasi.oLimboClient.utils.Scheduler;
import me.TadanoMoyasi.oLimboClient.utils.Scheduler.ScheduledTask;
import net.minecraft.client.Minecraft;

public class APISender {
	private static final Minecraft mc = Minecraft.getMinecraft();
	private static ScheduledTask task;
			
	public static void sendPlayerAPIChat() {
		if (mc.thePlayer == null) {
			task = Scheduler.setTimeout(APISender::sendPlayerAPIChat, 3600);
			 return;
		}
		 mc.thePlayer.sendChatMessage("/thelow_api player");
		 task = Scheduler.setTimeout(APISender::sendPlayerAPIChat, 3600);
	  }
	  
	  public static void sendAPISubscribeChat() {
		  mc.thePlayer.sendChatMessage("/thelow_api subscribe SKILL_COOLTIME");
	  }
	  
	  public static void start(int tick) {
			 task = Scheduler.setTimeout(APISender::sendPlayerAPIChat, tick);
	  }
	  
	  public static void end() {
		  task.cancel();
	  }
}
