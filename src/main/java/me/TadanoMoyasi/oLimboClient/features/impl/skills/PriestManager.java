package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import me.TadanoMoyasi.oLimboClient.features.impl.skills.core.Priest;
import net.minecraft.client.Minecraft;

public class PriestManager {
	private static final Minecraft mc = Minecraft.getMinecraft();
	private static List<Priest> Priests = new CopyOnWriteArrayList<>();
	private static final long EXPIRE_TIME = 10 * 60 * 1000;//ここだけtickじゃないのキモいけど諸々の事情でこうなってます
	private static final long CHECK_CT = 60_000L; //1min
	private static long checkedTime= System.currentTimeMillis();
	private static final int DEFAULT_CODEX_ACTIVE_TICK = 400;
	private static final int DEFAULT_CODEX_COOLTIME_TICK = 400;
	private static final int DEFAULT_SPRING_ACTIVE_TICK = 400;
	private static final int DEFAULT_SPRING_COOLTIME_TICK = 620;
	
	public static int getPriestSize() {
	    return Priests.size();
	}
	
	public static List<Priest> getPriests() {
		return Priests;
	}
	
	public static Priest getPriest(String name) {
		for (Priest priest: Priests) {
			if (priest.name.equals(name)) return priest;
		}
		return null;
	}
	
	/*
	 * @param String skill (泉 or Codex)
	 * */
	public static void addPriest(String mcid, int active, int cooltime, String skill, boolean isCorrect) {
		cleanupExpiredPriests();
		Priest existingPriest = getPriest(mcid);
		
		if (existingPriest != null) {
			add(existingPriest, active, cooltime, skill, isCorrect);
		} else {
			if (Priests.size() < 2) {
				Priest priest = new Priest(mcid);
				add(priest, active, cooltime, skill, isCorrect);
			} else {
				if (Priests.size() >= 2) {
					Priests.remove(0);
				}
				Priest priest = new Priest(mcid);
				add(priest, active, cooltime, skill, isCorrect);
			}
		}
	}
	
	private static void add(Priest priest, int active, int cooltime, String skill, boolean isCorrect) {
		Priests.add(priest);
		priest.skill = skill;
		priest.isCorrect = isCorrect;
		if (isCorrect) {
			priest.start(active, cooltime);
		} else {
			if ("泉".equals(skill)) priest.start(DEFAULT_SPRING_ACTIVE_TICK, DEFAULT_SPRING_COOLTIME_TICK);
			else priest.start(DEFAULT_CODEX_ACTIVE_TICK, DEFAULT_CODEX_COOLTIME_TICK);
		}
	}
	
	private static void cleanupExpiredPriests() {
		if ((System.currentTimeMillis() - checkedTime) < CHECK_CT) return;
	    long currentTime = System.currentTimeMillis();

	    Priests.removeIf(priest -> currentTime - priest.lastUpdateTime > EXPIRE_TIME);

	    checkedTime = currentTime;
	}
	
	public static void clearPriests() {
		Priests.clear();
    }
	
	public static void onTick() {
	    if (mc.theWorld == null) return;
	    cleanupExpiredPriests();
	    for (Priest priest : getPriests()) {
	        priest.tick();
	    }
	}
}
