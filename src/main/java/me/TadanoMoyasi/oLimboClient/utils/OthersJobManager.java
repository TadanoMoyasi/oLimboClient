package me.TadanoMoyasi.oLimboClient.utils;

import java.util.HashMap;
import java.util.Map;

import me.TadanoMoyasi.oLimboClient.core.api.types.Player_Status;
import net.minecraft.client.Minecraft;

public class OthersJobManager {
	private static final Map<String, String> jobs = new HashMap<>();
	
	public static void onAPIReceived(Player_Status stats) {
		jobs.put(stats.mcid, stats.jobName);
		if (stats.mcid.equals(Minecraft.getMinecraft().thePlayer.getName())) {
			JobChanger.setCurrentJob(stats.jobName);
		}
	}
	
	public static String getJob(String mcid) {
		return jobs.get(mcid);
	}
}
