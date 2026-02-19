package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import java.util.UUID;

import me.TadanoMoyasi.oLimboClient.features.impl.skills.ExecutionSkill.Skill;

public class ActiveSkill {
	private final UUID playerId;
	private final Skill skill;
	private final TimedSkill timer;
	private int ritsumaiWindow = 0;
	private boolean ritsumaiUsed = false;
	//読み方わかんなくて調べたんですけど、そつぶだったりりつぶだったりでわかんなかったので、
	//一番それっぽくてわかりやすいりつまいになってます。
		
	public ActiveSkill(UUID playerId, Skill skill) {
		this.playerId = playerId;
		this.skill = skill;
		this.timer  = new TimedSkill(skill.getActiveTime());
		this.timer.start();
	}
	
	public void setDurationTicks(int ticks) {
		timer.setDurationTicks(ticks);
	}
	
	public UUID getPlayerId() {
        return playerId;
	}

    public Skill getSkill() {
    	return skill;
    }
    
    public boolean canStartRitsumai() {
    	return timer.isActive() && !ritsumaiUsed;
    }
    
    public void startRitsumaiWindow() {
    	if (ritsumaiUsed) return;
    	ritsumaiWindow = 100;//tick
    	ritsumaiUsed = true;
    }
    
    public boolean canExtendFromRitsumai() {
    	return ritsumaiWindow > 0;
    }
    
    public void tick() {
    	timer.tick();
        
        if (ritsumaiWindow > 0) {
        	ritsumaiWindow--;
        }
        if (!timer.isActive()) {
        	ExecutionSkill.isTenkaDisable();
        	ritsumaiUsed = false;
        	ritsumaiWindow = 0;
        }
    }

    public boolean isExpired() {
        return !timer.isActive();
    }

    public float getTimer() {
        return timer.getTimer();
    }
    
    public void addTime(int extra) {
        timer.addTime(extra);
    }
    
    public float getTimerAsSecond( ) {
    	return timer.getTimerAsSeconds();
    }
}