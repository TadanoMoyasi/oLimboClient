package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import me.TadanoMoyasi.oLimboClient.core.ClientClock;

public class JerezStack {
	private static int stack = 0;
	private static int ticks = 0;
	private static int EXPIRE_TICK = 30 * 20; //600
	
	public static int getStack() {
		return stack;
	}
	
	public static void clearStack() {
		stack = 0;
	}
	
	public static void onUseSkill() {
		int now = ClientClock.now();
		if (now > ticks) {
			stack = 0;
		}
		ticks = now + EXPIRE_TICK;
		stack++;
	}
	
	public static void onTick() {
		if (ticks == 0) return;
		int now = ClientClock.now();
		if (now > ticks) {
			stack = 0;
			ticks = 0;
		}
	}
}
