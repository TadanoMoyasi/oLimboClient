package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.TadanoMoyasi.oLimboClient.features.impl.skills.core.CTSkill;

public class SkillCoolTimeHandler {	
	private static final List<CTSkill> CTSkills = new ArrayList<>();

	public static synchronized void activate(String skill, int tick) {
		if (tick == -1 || skill == null) return;
		CTSkills.add(new CTSkill(skill, tick));
	}
	
	public static synchronized void startCT(String skill, double seconds) {
		int ticks = (int) Math.floor(seconds * 20);
		CTSkills.removeIf(s -> s.getSkill().equals(skill));
		CTSkills.add(new CTSkill(skill, (int)ticks));
	}
	
	public static synchronized void onClientTick() {
		Iterator<CTSkill> it = CTSkills.iterator();
		while (it.hasNext()) {
            CTSkill skill = it.next();
            skill.tick(); 
            if (skill.isExpired()) {
                it.remove();
            }
        }
    }
	
	public static List<CTSkill> getCoolTimeSkills() {
        return CTSkills;
    }

	public static synchronized boolean isActive(String skillName) {
		for (CTSkill s : CTSkills) {
            if (s.getSkill().equals(skillName)) return true;
        }
        return false;
    }
}
