package me.TadanoMoyasi.oLimboClient.hud.features;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.SkillManager;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.SkillManager.SkillType;
import me.TadanoMoyasi.oLimboClient.hud.core.BaseHUD;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDConfigScreen;
import net.minecraft.client.renderer.GlStateManager;

public class EquippedSkillHUD extends BaseHUD {
	public EquippedSkillHUD() {
		super("EquippedSkill", 10, 25);
	}
	
	private void renderDummy() {
	    String textS = "Special: カオスブリザード";
	    String textN = "Normal: 雪柱";
	    
	    int baseHeight = mc.fontRendererObj.FONT_HEIGHT;

	    GlStateManager.pushMatrix();
	    
	    GlStateManager.translate(getX(), getY(), 0);
	    GlStateManager.scale(getScale(), getScale(), 1);

	    mc.fontRendererObj.drawStringWithShadow(textS, 0, 0, 0xAAAAAA);
	    mc.fontRendererObj.drawStringWithShadow(textN, 0, baseHeight + 2, 0xAAAAAA);

	    GlStateManager.popMatrix();
	    
	    int baseWidth = Math.max(
	    	    mc.fontRendererObj.getStringWidth(textS),
	    	    mc.fontRendererObj.getStringWidth(textN)
	    	);
	    
	    width = (int) (baseWidth * scale);
	    height = (int) ((baseHeight * 2 + 2) * scale);
	}
	
	@Override
	public  void render() {
		if (HUDConfigScreen.editMode) {
	        renderDummy();
	        return;
	    }
		
		if (!oLimboClientMod.config.equippedSkill || !oLimboClientMod.config.enableToggle) return;
		if (!ModCoreData.isInTheLow) return;
		
		String specialSkill = SkillManager.getCurrentSkill(mc.thePlayer, SkillType.SPECIAL);
		String normalSkill  = SkillManager.getCurrentSkill(mc.thePlayer, SkillType.NORMAL);
		
		if (specialSkill == null && normalSkill == null) return;
		
		String textS = "Special: " + (specialSkill != null ? specialSkill : "unselected");
		String textN = "Normal: " + (normalSkill  != null ? normalSkill  : "unselected");
	    
	    int baseHeight = mc.fontRendererObj.FONT_HEIGHT;

	    GlStateManager.pushMatrix();
	    
	    GlStateManager.translate(getX(), getY(), 0);
	    GlStateManager.scale(getScale(), getScale(), 1);

	    mc.fontRendererObj.drawStringWithShadow(textS, 0, 0, 0xFFFFFF);
	    mc.fontRendererObj.drawStringWithShadow(textN, 0, baseHeight + 2, 0xFFFFFF);

	    GlStateManager.popMatrix();
	    
	    int baseWidth = Math.max(
	    	    mc.fontRendererObj.getStringWidth(textS),
	    	    mc.fontRendererObj.getStringWidth(textN)
	    	);
	    
	    width = (int) (baseWidth * scale);
	    height = (int) ((baseHeight * 2 + 2) * scale);
	}
}
