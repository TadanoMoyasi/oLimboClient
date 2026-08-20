package me.TadanoMoyasi.oLimboClient.core.debug;

import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.utils.TheLowUtil;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DebugChat {
	private static boolean isEnabled = false;
	private static final DebugChat INSTANCE = new DebugChat();
	
	public static void ToggleDebugChat() {
		isEnabled = !isEnabled;
		if (isEnabled) {
			MinecraftForge.EVENT_BUS.register(INSTANCE);
			TheLowUtil.showInChat(ModCoreData.prefix + "debugchat:" + isEnabled);
		} else {
			MinecraftForge.EVENT_BUS.unregister(INSTANCE);
			TheLowUtil.showInChat(ModCoreData.prefix + "debugchat:" + isEnabled);
		}
	}
	
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		String formatted = event.message.getFormattedText();
		System.out.println(formatted);
	}
}
