package me.TadanoMoyasi.oLimboClient.features.impl.skills.core;

public class MagicStoneManager {
	public static double calclateCooltimeReduction(double baseTime, String slot) {
		double multiplier = 1.0;
		
		String[] items = slot.split(",");
		
		for (String item : items) {
			String trimmedItem = item.trim();
			if (trimmedItem.startsWith("reduce_cooltime_magic_stone")) {
				if (item.endsWith("LEVEL5")) multiplier *= 0.60;
				else if (item.endsWith("LEVEL4_5")) multiplier *= 0.72;
				else if (item.endsWith("LEVEL4")) multiplier *= 0.77;
				else if (item.endsWith("LEVEL3")) multiplier *= 0.84;
				else if (item.endsWith("LEVEL2")) multiplier *= 0.90;
				else if (item.endsWith("LEVEL1")) multiplier *= 0.95;
			}
		}
		return baseTime * multiplier;
	}
}
