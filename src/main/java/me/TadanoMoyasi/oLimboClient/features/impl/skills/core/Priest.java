package me.TadanoMoyasi.oLimboClient.features.impl.skills.core;

public class Priest {
	public String name;
	public String skill;
	public boolean isCorrect;
	
	public TimedSkill durationTimer;
    public TimedSkill cooldownTimer;
    
	public long lastUpdateTime;
	
	public Priest() {
		this.lastUpdateTime = System.currentTimeMillis();
	};
	
	public Priest(String name) {
		this.name = name;
		this.lastUpdateTime = System.currentTimeMillis();
	}
	
	public void start(int activeTick, int cooltimeTick) {
		durationTimer = new TimedSkill(activeTick);
		durationTimer.start();
		cooldownTimer = new TimedSkill(cooltimeTick);
		lastUpdateTime = System.currentTimeMillis();
	}
	
	public void tick() {
		if (durationTimer != null) {
			durationTimer.tick();
			if (durationTimer.isFinished() && cooldownTimer != null && !cooldownTimer.isActive() && !cooldownTimer.isFinished()) {
	            cooldownTimer.start();
	        }
		}
		if (cooldownTimer != null && cooldownTimer.isActive()) {
        	cooldownTimer.tick();
        }
    }
	
	public boolean isActive() {
		return durationTimer != null && durationTimer.isActive();
	 }

	public boolean isCooldown() {
		return cooldownTimer != null && cooldownTimer.isActive();
	}

	public boolean isReady() {
		return cooldownTimer != null && cooldownTimer.isFinished();
	}

	public float getDurationRemaining() {
		return durationTimer != null ? durationTimer.getTimerAsSeconds() : 0;
	}

	public float getCooldownRemaining() {
		return cooldownTimer != null ? cooldownTimer.getTimerAsSeconds() : 0;
	}
}