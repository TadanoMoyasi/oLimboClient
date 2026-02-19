package me.TadanoMoyasi.oLimboClient.hud.features;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.SkillManager;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.TimedSkill;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.SkillHandler.Skill;
import me.TadanoMoyasi.oLimboClient.hud.core.BaseHUD;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDConfigScreen;
import net.minecraft.client.renderer.GlStateManager;

public class SkillCastTimeHUD extends BaseHUD {
	public SkillCastTimeHUD() {
		super("SkillCastTime", 10, 25);
	}
	
	private void renderDummy() {
	    String text = "詠唱: 1.5s";
	    
	    int baseWidth = mc.fontRendererObj.getStringWidth(text);
	    int baseHeight = mc.fontRendererObj.FONT_HEIGHT;

	    GlStateManager.pushMatrix();
	    
	    GlStateManager.translate(getX(), getY(), 0);
	    GlStateManager.scale(getScale(), getScale(), 1);

	    mc.fontRendererObj.drawStringWithShadow(text, 0, 0, 0xAAAAAA);

	    GlStateManager.popMatrix();
	    
	    width = (int) (baseWidth * scale);
	    height = (int) (baseHeight * scale);
	}
	
	@Override
	public void render() {
		if (HUDConfigScreen.editMode) {
	        renderDummy();
	        return;
	    }
		
		if (!oLimboClientMod.config.skillCastTime || !oLimboClientMod.config.enableToggle) return;
		if (!ModCoreData.isInTheLow) return;
		
		if (mc.thePlayer == null) return;
		String currentSkillStr = SkillManager.getCurrentSkill(mc.thePlayer, SkillManager.SkillType.PASSIVE);
		if (currentSkillStr == null) return;
		Skill currentSkill = Skill.getSkillFromName(currentSkillStr);
		if (currentSkill == null) return;
		TimedSkill timed = SkillManager.getTimedSkill(currentSkill);
		if (timed == null ) return;
		float castTime = SkillManager.getCastTime(currentSkill);
		if (castTime < 0 ) return;
		float timer = SkillManager.getCastTimeTimer(currentSkill);
		
		String text;
		
		if (timer > 0) {
			text = currentSkill.getDisplayName() +  ": " + String.format("%.2f", castTime) + "s";
		} else if (timed.isFinished()) {
			text = currentSkill.getDisplayName() + ": §a使用可能";
		} else {
			return;
		}
		
		int baseWidth = mc.fontRendererObj.getStringWidth(text);
	    int baseHeight = mc.fontRendererObj.FONT_HEIGHT;
	    
	    float centerX = x + (baseWidth * scale) / 2f;
	    float centerY = y + (baseHeight * scale) / 2f;
		
	    GlStateManager.pushMatrix();
		
	    GlStateManager.translate(centerX, centerY, 0);
	    GlStateManager.scale(scale, scale, 1F);
		
	    GlStateManager.translate(-baseWidth / 2f, -baseHeight / 2f, 0);
		
		mc.fontRendererObj.drawStringWithShadow(text, 0, 0, 0xFFFFFF);
		
		GlStateManager.popMatrix();
		
		width = (int) (baseWidth * scale);
		height = (int) (baseHeight * scale);
	}
}
