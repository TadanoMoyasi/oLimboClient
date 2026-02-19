package me.TadanoMoyasi.oLimboClient.features.impl.misc;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import net.minecraft.client.Minecraft;

public class TellReminder {
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	public static void tellSound() {
		if (!oLimboClientMod.config.chatMentionReminder) return;
		mc.thePlayer.playSound("note.pling", 1.0F, 2.0F);
	}
}
