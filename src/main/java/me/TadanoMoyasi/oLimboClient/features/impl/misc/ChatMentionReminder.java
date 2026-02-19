package me.TadanoMoyasi.oLimboClient.features.impl.misc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import net.minecraft.client.Minecraft;

public class ChatMentionReminder {
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	public static void mentionSound(String msg) {
		if (!oLimboClientMod.config.tellReminder) return;
		if (!msg.contains(mc.thePlayer.getName())) return;
		Pattern pattern = Pattern.compile("(\\[.+\\]\\s)?.+(\\s\\[.+\\])?\\s:\\s(?<message>.+)");
    	Matcher matcher = pattern.matcher(msg);
    	
    	if (matcher.find()) {
    		String matchedMessage = matcher.group("message");
    		String msgLower = matchedMessage.toLowerCase();
    		if (!msgLower.contains(mc.thePlayer.getName().toLowerCase())) return;
    		mc.thePlayer.playSound("random.orb", 1.0F, 1.7F);
    	}
	}
}
