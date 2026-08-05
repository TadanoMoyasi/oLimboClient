package me.TadanoMoyasi.oLimboClient.hud.features;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.codex.CodexSkillManager;
import me.TadanoMoyasi.oLimboClient.hud.core.BaseHUD;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDConfigScreen;
import net.minecraft.client.renderer.GlStateManager;

public class CodexSkillHUD extends BaseHUD {
	public CodexSkillHUD() {
		super("CodexSkill", 10, 25);
	}
	
	private void renderDummy() {
	    String text = "Codex:攻, 癒, 鋼";
	    
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
		
		if (!oLimboClientMod.config.codexSkillDisplay || !oLimboClientMod.config.enableToggle) return;
		if (!ModCoreData.isInTheLow) return;
		
		String text = CodexSkillManager.FormatCodex();
		
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
