package me.TadanoMoyasi.oLimboClient.features.impl.misc;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import net.minecraft.client.Minecraft;

public class ChatMentionReminder {
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	public static void mentionSound(String msg) {
		if (!oLimboClientMod.config.chatMentionReminder) return;
		if (msg.startsWith("$api")) return;
		String playerName = mc.thePlayer.getName();
        if (!msg.toLowerCase().contains(playerName.toLowerCase())) return;
        String[] parts = msg.split(":", 2);
        if (parts.length == 2) {
        	String sender = parts[0].toLowerCase();
            String message = parts[1].toLowerCase();
            if (!sender.contains(playerName) && message.contains(playerName.toLowerCase())) {
                mc.thePlayer.playSound("random.orb", 1.0F, 1.7F);
            }
        }
	}
}
