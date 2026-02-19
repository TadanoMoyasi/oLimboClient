package me.TadanoMoyasi.oLimboClient.features.impl.skills;

public class TimedSkill {

	private final int baseDuration; 
    private int duration; //伸びるやつ用(天むすとか)
    private float timer = -1;
    private boolean finished = false;

    public TimedSkill(int duration) {
        this.baseDuration = duration;
        this.duration = duration;
    }

    public void start() {
    	if (finished) return;
        if (timer == -1) {
            timer = 0;
            duration = baseDuration;
            finished = false;
        }
    }
    
    public void setDurationTicks(int ticks) {
        this.duration = ticks;
        this.timer = 0;
        this.finished = false;
    }

    public void addTime(int extra) {
        if (timer != -1) {
            duration += extra;
        }
    }

    public void stop() {
        timer = -1;
        finished = false;
    }
    
    public boolean isFinished() {
    	return finished;
    }

    public void tick() {
        if (timer >= 0) {
            timer++;
            if (timer >= duration) {
            	timer = -1;
                finished = true;
            }
        }
    }

    public boolean isActive() {
        return timer >= 0;
    }

    public float getTimer() {
        return timer;
    }
    
    public float getTimerAsSeconds() {
    	return Math.max(0, (duration - timer) / 20);
    }
}