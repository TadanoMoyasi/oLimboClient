package me.TadanoMoyasi.oLimboClient.features.impl.misc;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.TadanoMoyasi.oLimboClient.features.impl.skills.ActiveSkill;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.ExecutionSkill;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.ExecutionSkill.Skill;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.PriestManager;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.core.Priest;
import me.TadanoMoyasi.oLimboClient.utils.Scheduler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public class MatatabiBridge {
//	/m@ 恵みの泉 を発動 (mcid)
	private static final Minecraft mc = Minecraft.getMinecraft();
	private static final Pattern MATATABI_PATTERN = Pattern.compile("(.+) を発動 \\((.*)\\)");

	public static void onMatatabiChat(String msg) {
		if (!msg.contains("m@")) return;
        String[] parts = msg.split("m@", 2);
        if (parts.length < 2) return;
        String trimed = parts[1].trim();
    	Matcher matcher = MATATABI_PATTERN.matcher(trimed);
       	if (matcher.find()) {
    		String matchedSkill = matcher.group(1);
    		String matchedPlayer = matcher.group(2);
    		
    		Scheduler.setTimeout(() -> {
    			for (ActiveSkill active : ExecutionSkill.getActiveSkills()) {
    				if (active.getSkill() == Skill.MEGUMI_NO_IZUMI || active.getSkill() == Skill.FUUMAROKU_SINTAKU_NO_KAGO) {
    					if (active.getMCID().equals(matchedPlayer)) return;
    				}
    			}
    			
    			NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(matchedPlayer);
    			if (info == null) return;
    			UUID uuid = info.getGameProfile().getId();
        		Skill skill = ExecutionSkill.getCurrentSkillEnum(matchedSkill);
        		String skillName = "";
        		if ("恵みの泉".equals(matchedSkill)) {
        			skillName = "泉";
        		} else if ("封魔録・神託の加護".equals(matchedSkill)) {
        			skillName = "Codex";
        		}
        		if (skillName.isEmpty()) return;
        		String correctMCID = info.getGameProfile().getName();
    			ExecutionSkill.activate(uuid, skill);
    			for (Priest priest : PriestManager.getPriests()) {
    				if (priest.name.equals(correctMCID)) {
    					if (priest.isActive() || priest.isCooldown()) return;
        				PriestManager.addPriest(correctMCID, -1, -1, skillName, false);
        				return;
    				}
    			}
    		}, 1);
       	}
	}
}
