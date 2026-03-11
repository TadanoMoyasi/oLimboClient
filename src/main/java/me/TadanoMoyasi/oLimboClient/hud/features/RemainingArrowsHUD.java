package me.TadanoMoyasi.oLimboClient.hud.features;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.features.impl.misc.RemainingArrows;
import me.TadanoMoyasi.oLimboClient.hud.core.BaseHUD;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDConfigScreen;
import net.minecraft.client.renderer.GlStateManager;

public class RemainingArrowsHUD extends BaseHUD {
	public RemainingArrowsHUD() {
		super("RemainingArrows", 10, 25);
	}
	
	private void renderDummy() {
	    String text = "矢: 64";
	    
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
		
		if (!oLimboClientMod.config.arrowHUD || !oLimboClientMod.config.enableToggle) return;
		if (!ModCoreData.isInTheLow) return;
		
		int currentCount = RemainingArrows.countArrows();
		
		if (currentCount < 1) return;
		
		if (oLimboClientMod.config.arrowHUDOnlyHaveBow) {
			if (mc.thePlayer.getHeldItem() == null || mc.thePlayer.getHeldItem().getItem() != net.minecraft.init.Items.bow) {
	            return;
	        }
		}
		
		String text = oLimboClientMod.config.arrowHUDText ? "" : "矢: ";
		
		if (currentCount <= 20) {
			text = text + "§c" + currentCount;
		} else {
			text = text + currentCount;
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
