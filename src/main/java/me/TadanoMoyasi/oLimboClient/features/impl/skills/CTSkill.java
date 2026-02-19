package me.TadanoMoyasi.oLimboClient.features.impl.skills;

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
	
	public void tick() {
    	timer.tick();
    }
	
	public boolean isExpired() {
        return !timer.isActive();
    }
	
	public float getTimerAsSecond( ) {
    	return timer.getTimerAsSeconds();
    }
}
