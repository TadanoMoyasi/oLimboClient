package me.TadanoMoyasi.oLimboClient.features.impl.skills.Data;

import java.util.HashMap;
import java.util.Map;

public class SkillData {
	private static Map<String, String> skillMap = new HashMap<>();
	
	public static Map<String, String> getSkillMap() {
        return skillMap;
    }
	
	public static void putAll(Map<String, String> newData) {
        if (newData != null) {
            skillMap.putAll(newData);
        }
    }
	
	public static void clearMap() {
		skillMap.clear();
	}
	
	public static String getSkillName(String id) {
		return skillMap.get(id);
	}
	
	public static String getSkillNameDef(String id) {
		return skillMap.getOrDefault(id, "unknown");
	}
}
