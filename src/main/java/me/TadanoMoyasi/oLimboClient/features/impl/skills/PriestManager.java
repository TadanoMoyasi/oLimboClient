package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.TadanoMoyasi.oLimboClient.features.impl.skills.core.Priest;
import net.minecraft.client.Minecraft;

public class PriestManager {
	private static final Minecraft mc = Minecraft.getMinecraft();
	private static List<Priest> Priests = new ArrayList<>();
	private static final long EXPIRE_TIME = 10 * 60 * 1000;
	private static final long CHECK_CT = 60_000L; //1min
	private static long checkedTime= System.currentTimeMillis();
	
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
	
	public static void addPriest(String mcid, int active, int cooltime, String skill) {
		cleanupExpiredPriests();
		Priest existingPriest = getPriest(mcid);
		
		if (existingPriest != null) {
			existingPriest.skill = skill;
			existingPriest.start(active, cooltime);
		} else {
			if (Priests.size() < 2) {
				Priest priest = new Priest(mcid);
				Priests.add(priest);
				priest.skill = skill;
				priest.start(active, cooltime);
			} else {
				if (Priests.size() >= 2) {
					Priests.remove(0);
				}
				Priest priest = new Priest(mcid);
				Priests.add(priest);
				priest.skill = skill;
				priest.start(active, cooltime);
			}
		}
	}
	
	private static void cleanupExpiredPriests() {
		if ((System.currentTimeMillis() - checkedTime) < CHECK_CT) return;
		long currentTime = System.currentTimeMillis();
		Iterator<Priest> iterator = Priests.iterator();
		
		while (iterator.hasNext()) {
			Priest priest = iterator.next();
			if (currentTime - priest.lastUpdateTime > EXPIRE_TIME) {
				iterator.remove();
			}
		}
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
