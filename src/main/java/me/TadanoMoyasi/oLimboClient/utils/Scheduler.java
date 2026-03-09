package me.TadanoMoyasi.oLimboClient.utils;

import java.util.Comparator;
import java.util.PriorityQueue;

import me.TadanoMoyasi.oLimboClient.core.ClientClock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Scheduler {
	private static final PriorityQueue<ScheduledTask> tasks = new PriorityQueue<>(Comparator.comparingInt(t -> t.executeTick));
	
	public static ScheduledTask setTimeout(Runnable action, int delayTick) {
        if (action == null || delayTick < 0) return null;
        ScheduledTask task = new ScheduledTask(ClientClock.now() + delayTick, action);
		tasks.add(task);
		return  task;
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) {
		if (e.phase != TickEvent.Phase.END) return;
		
		int now = ClientClock.now();
		
		while (!tasks.isEmpty()) {
			ScheduledTask next = tasks.peek();

            if (next.executeTick > now) break;
            
            ScheduledTask task = tasks.poll();
            
            if (task.cancelled) continue;
            
            try {
                task.action.run();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
	}
	
	public static class ScheduledTask  {
		int executeTick;
		Runnable action;
		boolean cancelled = false;
		
		ScheduledTask(int tick, Runnable action) {
			this.executeTick = tick;
			this.action = action;
		}
		
		public void cancel() {
            this.cancelled = true;
        }
	}
}
