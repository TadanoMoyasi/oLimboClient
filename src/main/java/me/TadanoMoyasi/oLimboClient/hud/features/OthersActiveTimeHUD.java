package me.TadanoMoyasi.oLimboClient.hud.features;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.ActiveSkill;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.ExecutionSkill;
import me.TadanoMoyasi.oLimboClient.hud.core.BaseHUD;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDConfigScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

public class OthersActiveTimeHUD extends BaseHUD {
	
	public OthersActiveTimeHUD() {
		super("OthersActiveTime", 10, 25);
	}
	
	private void renderDummy() {
	    String text = "TdMy - 覚醒: 2.25s";
	    
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
		
		if (!oLimboClientMod.config.othersSkillActiveTime && !oLimboClientMod.config.normalSkillActiveTime) return;
		if (!oLimboClientMod.config.enableToggle) return;
		if (!ModCoreData.isInTheLow) return;
		
		 int baseHeight = mc.fontRendererObj.FONT_HEIGHT;
		 int lineHeight = (int) (baseHeight * scale) + 2;
		 
		int renderY = y;
		
		 for (ActiveSkill active : ExecutionSkill.getActiveSkills()) {
		        float seconds = active.getTimerAsSecond();

		        String playerName = "Unknown";
		        EntityPlayer player = mc.theWorld.getPlayerEntityByUUID(active.getPlayerId());

		        if (player != null) {
		            playerName = player.getName();
		        }

		        String text = playerName + " - " + active.getSkill().getDisplayName() + ": " + String.format("%.2f", seconds) + "s";
		        
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

