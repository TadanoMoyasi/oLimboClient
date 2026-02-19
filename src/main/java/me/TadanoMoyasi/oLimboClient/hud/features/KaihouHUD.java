package me.TadanoMoyasi.oLimboClient.hud.features;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.hud.core.BaseHUD;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDConfigScreen;
import net.minecraft.client.renderer.GlStateManager;

public class KaihouHUD extends BaseHUD {
	public KaihouHUD() {
		super("Kaihou", 10, 25);
	}
	
	private void renderDummy() {
	    String text = "解放: §a済";
	    
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
		
		if (!oLimboClientMod.config.kaihouDisplay || !oLimboClientMod.config.enableToggle) return;
		if (!ModCoreData.isInTheLow) return;
		
		String text;
		
		if (ModCoreData.kaihouUsed) {
			text = "解放: §a済";
		} else {
			text = "解放: §c未";
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
