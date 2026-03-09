package me.TadanoMoyasi.oLimboClient.features.impl.misc;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mojang.authlib.GameProfile;

import me.TadanoMoyasi.oLimboClient.features.impl.skills.ActiveSkill;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.CodexCache;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.ExecutionSkill;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.ExecutionSkill.Skill;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.PriestManager;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.core.Codex;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.core.MagicStoneManager;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.core.Priest;
import me.TadanoMoyasi.oLimboClient.utils.CorrectPlayerInfoFetcher;
import me.TadanoMoyasi.oLimboClient.utils.Scheduler;
import net.minecraft.client.network.NetworkPlayerInfo;

public class MatatabiBridge {
//	/m@ 恵みの泉 を発動 (mcid)
	// /clan msg m@ 恵みの泉 を発動 (tdmy)
	// /clan msg m@ 封魔録・神託の加護 を発動 (tdmy)
	private static final Pattern MATATABI_PATTERN = Pattern.compile("(.+) を発動 \\((.*)\\)");
	private static final double CODEX_BASE_COOLTIME = 60.0; //pa-ku no hangen komi(pa-kuha torenainore)
	private static final int CODEX_BASE_ACTIVETIME_TICK = 400;

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
    			NetworkPlayerInfo info = CorrectPlayerInfoFetcher.getInfo(matchedPlayer);
    			if (info == null) return;
    			GameProfile profile = info.getGameProfile();
        		String correctMCID = profile.getName();
        		UUID uuid = profile.getId();
    			for (ActiveSkill active : ExecutionSkill.getActiveSkills()) {
    				if (active.getSkill() == Skill.MEGUMI_NO_IZUMI || active.getSkill() == Skill.FUUMAROKU_SINTAKU_NO_KAGO) {
    					if (!active.getMCID().equals(correctMCID)) {
    		        		Skill skill = ExecutionSkill.getCurrentSkillEnum(matchedSkill);
    		    			ExecutionSkill.activate(uuid, skill);
    					}
    				}
    			}
    			
        		String skillName = "";
        		if ("恵みの泉".equals(matchedSkill)) {
        			skillName = "泉";
        		} else if ("封魔録・神託の加護".equals(matchedSkill)) {
        			skillName = "Codex";
        		}
        		if (skillName.isEmpty()) return;
    			for (Priest priest : PriestManager.getPriests()) {
    				if (priest.name.equals(correctMCID)) {
    					if (priest.isActive() || priest.isCooldown()) return;
    					if (skillName.equals("Codex")) {
    						int activeTicks = -1;
    		        		int calclatedCoolTimeTicks = -1;
    		        		if (CodexCache.contains(uuid)) {
    		        			Codex codex = CodexCache.get(uuid);
    		        			String slot = codex.slot;
    		        			activeTicks = codex.ticks;
    		        			int cooltimeTicks = (int) (MagicStoneManager.calclateCooltimeReduction(CODEX_BASE_COOLTIME, slot) * 20) + CODEX_BASE_ACTIVETIME_TICK;
    		            		calclatedCoolTimeTicks = cooltimeTicks - activeTicks;
    		            		PriestManager.addPriest(correctMCID, activeTicks, calclatedCoolTimeTicks, "Codex", true);
    		        		} else {
    		            		PriestManager.addPriest(correctMCID, activeTicks, calclatedCoolTimeTicks, "Codex", false);
    		        		}
    					} else {
            				PriestManager.addPriest(correctMCID, -1, -1, skillName, false);
    					}
        				return;
    				}
    			}
    		}, 1);
       	}
	}
}
