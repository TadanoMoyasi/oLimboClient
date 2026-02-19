package me.TadanoMoyasi.oLimboClient.hud.features;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.CTSkill;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.SkillCoolTimeHandler;
import me.TadanoMoyasi.oLimboClient.hud.core.BaseHUD;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDConfigScreen;
import net.minecraft.client.renderer.GlStateManager;

public class SkillCoolTimeHUD extends BaseHUD {
	public SkillCoolTimeHUD() {
		super("SkillCoolTime", 10, 25);
	}
	
	private void renderDummy() {
	    String text = "雪柱 0:18";
	    
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
		
		if (!oLimboClientMod.config.skillCoolTime || !oLimboClientMod.config.enableToggle) return;
		if (!ModCoreData.isInTheLow) return;
		
		 int baseHeight = mc.fontRendererObj.FONT_HEIGHT;
		 int lineHeight = (int) (baseHeight * scale) + 2;
		 
		int renderY = y;
		
		 for (CTSkill cooltime : SkillCoolTimeHandler.getCoolTimeSkills()) {
		        float seconds = cooltime.getTimerAsSecond();

		        String text = cooltime.getSkill() + String.format("%.2f", seconds) + "s";
		        
		        int baseWidth = mc.fontRendererObj.getStringWidth(text);
			    
			    float centerX = x + (baseWidth * scale) / 2f;
			    float centerY = renderY + (baseHeight * scale) / 2f;
				
			    GlStateManager.pushMatrix();
				
			    GlStateManager.translate(centerX, centerY, 0);
			    GlStateManager.scale(scale, scale, 1F);
			    
			    GlStateManager.translate(-baseWidth / 2f, -baseHeight / 2f, 0);
				
				mc.fontRendererObj.drawStringWithShadow(text, 0, 0, 0xFFFFFF);
				
				GlStateManager.popMatrix();
				
				width = (int) (baseWidth * scale);
				height = (int) (baseHeight * scale);
				
				renderY += lineHeight;
		    }
	}
}
