package me.TadanoMoyasi.oLimboClient.features.impl.skills.core;

public class CTSkill {
	private final String skill;
	private final TimedSkill timer;
	
	public CTSkill(String skill, int tick) {
		this.skill = skill;
		this.timer  = new TimedSkill(tick);
		this.timer.start();
	}
	
	public String getSkill() {
    	return skill;
    }
	
	public synchronized void tick() {
    	timer.tick();
    }
	
	public synchronized boolean isExpired() {
        return !timer.isActive();
    }
	
	public synchronized float getTimerAsSecond( ) {
    	return timer.getTimerAsSeconds();
    }
}
