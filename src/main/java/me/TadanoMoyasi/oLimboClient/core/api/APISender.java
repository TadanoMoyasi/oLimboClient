package me.TadanoMoyasi.oLimboClient.core.api;

import me.TadanoMoyasi.oLimboClient.core.ClientClock;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.utils.CooldownManager;
import me.TadanoMoyasi.oLimboClient.utils.Scheduler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
	
	@SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent event) {
		if (!ModCoreData.isInTheLow) return;
    	Minecraft mc = Minecraft.getMinecraft();
    	if (event.entity == null || event.entity != mc.thePlayer) return;
		if (!CooldownManager.checkAndReset("location", 40)) return;
		Scheduler.setTimeout(() -> {
	    	mc.thePlayer.sendChatMessage("/thelow_api location");
		  }, 20);
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
		  Scheduler.setTimeout(() -> {
			  mc.thePlayer.sendChatMessage("/thelow_api subscribe SKILL_COOLTIME");
		  }, 20);
	  }
	  
	  public static void start(int tick) {
		  nextTick = ClientClock.now() + tick;
	  }
	  
	  public static void end() {
		  nextTick = -1;
	  }
}
