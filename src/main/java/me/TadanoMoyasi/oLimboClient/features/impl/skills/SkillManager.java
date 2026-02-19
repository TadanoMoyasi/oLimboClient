package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import java.util.HashMap;
import java.util.Map;

import me.TadanoMoyasi.oLimboClient.features.impl.skills.SkillHandler.Skill;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SkillManager {
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	private static ItemStack lastHeldItem = null;

	private static final Map<Skill, TimedSkill> skills = new HashMap<>();

	static {
		skills.put(Skill.ACE, new TimedSkill(120)); //6
		skills.put(Skill.EISYOU, new TimedSkill(200)); //10
		skills.put(Skill.KETSUI, new TimedSkill(200)); //10
		skills.put(Skill.YAMI_KAIHOU, new TimedSkill(120)); //6
	}

	public static void activate(Skill skill) {
		skills.get(skill).start();
	}

	public static void deactivate(Skill skill) {
		skills.get(skill).stop();
	}

	public static boolean isActive(Skill skill) {
		return skills.get(skill).isActive();
	}
	
	public static float getCastTimeTimer(Skill skill) {
		return skills.get(skill).getTimer();
	}

	public static float getCastTime(Skill skill) {
		return skills.get(skill).getTimerAsSeconds();
	}

	public static TimedSkill getTimedSkill(Skill skill) {
		return skills.get(skill);
	}

	public static void onClientTick() {
		if (mc.thePlayer == null) return;
		
		ItemStack current = mc.thePlayer.getHeldItem();
		
		if (!ItemStack.areItemStacksEqual(current, lastHeldItem)) {
			for (TimedSkill skill : skills.values()) {
				skill.stop();
			}
			
			lastHeldItem = current == null ? null : current.copy();
		}
		
		for (TimedSkill skill : skills.values()) {
			skill.tick();
		}
	}

	public enum SkillType {
		PASSIVE, NORMAL, SPECIAL
	}

	public static String getCurrentSkill(EntityPlayer player, SkillType type) {
		if (mc.thePlayer == null || mc.theWorld == null)
			return null;
		ItemStack stack = player.getHeldItem();
		if (stack == null || !stack.hasTagCompound())
			return null;
		NBTTagCompound nbt = stack.getTagCompound();

		switch (type) {
		case PASSIVE:
			return SkillHandler.getPassiveSkillName(SkillHandler.getPassive(nbt));

		case NORMAL:
			return SkillHandler.getName(SkillHandler.getNormal(nbt));

		case SPECIAL:
			return SkillHandler.getName(SkillHandler.getSpecial(nbt));

		default:
			return null;
		}
	}
	
	public static Skill getCurrentSkillEnum(EntityPlayer player, SkillType type) {
		if (mc.thePlayer == null || mc.theWorld == null) return null;
		String name = getCurrentSkill(player, SkillType.PASSIVE);
		
		return Skill.getSkillFromName(name);
	}
}