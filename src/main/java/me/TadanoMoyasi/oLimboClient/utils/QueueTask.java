package me.TadanoMoyasi.oLimboClient.utils;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class QueueTask {
	private static final List<DelayedTask> tasks = new CopyOnWriteArrayList<>();

	public static void addTask(int delay, Runnable action) {
		tasks.add(new DelayedTask(delay, action));
	}
	
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		Iterator<DelayedTask> it = tasks.iterator();
		for (DelayedTask task : tasks) {
			task.delay--;
			if (task.delay <= 0) {
				task.action.run();
			}
		}
		tasks.removeIf(task -> task.delay <= 0);
	}
	
	private static class DelayedTask {
		int delay;
		Runnable action;
		DelayedTask(int d, Runnable a) {
			delay = d;
			action = a;
		}
	}
}
