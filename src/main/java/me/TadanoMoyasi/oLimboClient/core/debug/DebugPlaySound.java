package me.TadanoMoyasi.oLimboClient.core.debug;

import net.minecraft.client.Minecraft;

public class DebugPlaySound {
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	public static void playSound(String type, float pitch) {
		mc.thePlayer.playSound(type, 1.0F, pitch);
	}
}
