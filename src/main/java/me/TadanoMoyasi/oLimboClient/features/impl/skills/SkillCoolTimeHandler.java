package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import java.util.ArrayList;
import java.util.List;

public class SkillCoolTimeHandler {	
	private static final List<CTSkill> CTSkills = new ArrayList<>();

	public static void activate(String skill, int tick) {
		if (tick == -1 || skill == null) return;
		CTSkills.add(new CTSkill(skill, tick));
	}
	
	public static void startCT(String skill, double seconds) {
		int ticks = (int) Math.floor(seconds * 20);
		CTSkills.removeIf(s -> s.getSkill().equals(skill)); 
	    CTSkills.add(new CTSkill(skill, (int)ticks));
	}
	
	public static void onClientTick() {
        for (CTSkill skill : CTSkills) {
            skill.tick();
        }
        CTSkills.removeIf(CTSkill::isExpired);
    }
	
	public static List<CTSkill> getCoolTimeSkills() {
        return CTSkills;
    }

	public static boolean isActive(String skillName) {
        return CTSkills.stream().anyMatch(s -> s.getSkill().equals(skillName));
    }
}
