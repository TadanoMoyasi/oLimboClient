package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.utils.QueueTask;
import net.minecraft.client.Minecraft;

public class IssenReminder {
	private static final Minecraft mc = Minecraft.getMinecraft();
	private static boolean reminded = false;
	public static void issenActive(ActiveSkill skill) {
		if (oLimboClientMod.config.issenReminderTime == 26) return;
		float sec = skill.getTimerAsSecond();
		int remind = oLimboClientMod.config.issenReminderTime;
		if (sec <= remind) {
			if (reminded) return; 
			mc.thePlayer.playSound("random.orb", 1.0F, 0.8F);
			QueueTask.addTask(5, () -> {
				mc.thePlayer.playSound("random.orb", 1.0F, 0.8F);
			});
			reminded = true;
		}
	}
	
	public static void issenEnd() {
		reminded = false;
	}
}
