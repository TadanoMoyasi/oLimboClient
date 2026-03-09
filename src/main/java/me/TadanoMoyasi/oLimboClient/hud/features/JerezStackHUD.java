package me.TadanoMoyasi.oLimboClient.hud.features;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.JerezStack;
import me.TadanoMoyasi.oLimboClient.hud.core.BaseHUD;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDConfigScreen;
import net.minecraft.client.renderer.GlStateManager;

public class JerezStackHUD extends BaseHUD {
	public JerezStackHUD() {
		super("JerezStack", 10, 25);
	}
	
	private void renderDummy() {
	    String text = "ヘレス: 3";
	    
	    int baseHeight = mc.fontRendererObj.FONT_HEIGHT;

	    GlStateManager.pushMatrix();
	    
	    GlStateManager.translate(getX(), getY(), 0);
	    GlStateManager.scale(getScale(), getScale(), 1);

	    mc.fontRendererObj.drawStringWithShadow(text, 0, 0, 0xAAAAAA);

	    GlStateManager.popMatrix();
	    
	    int baseWidth = mc.fontRendererObj.getStringWidth(text);
	    
	    width = (int) (baseWidth * scale);
	    height = (int) ((baseHeight * 2 + 2) * scale);
	}
	
	@Override
	public  void render() {
		if (HUDConfigScreen.editMode) {
	        renderDummy();
	        return;
	    }
		
		if (!oLimboClientMod.config.jerezStackHUD || !oLimboClientMod.config.enableToggle) return;
		if (!ModCoreData.isInTheLow) return;
		
		if (JerezStack.getStack() == 0) return;
		
		String text = "ヘレス: " + JerezStack.getStack();
	    
	    int baseHeight = mc.fontRendererObj.FONT_HEIGHT;

	    GlStateManager.pushMatrix();
	    
	    GlStateManager.translate(getX(), getY(), 0);
	    GlStateManager.scale(getScale(), getScale(), 1);

	    mc.fontRendererObj.drawStringWithShadow(text, 0, 0, 0xFFFFFF);

	    GlStateManager.popMatrix();
	    
	    int baseWidth = mc.fontRendererObj.getStringWidth(text);
	    
	    width = (int) (baseWidth * scale);
	    height = (int) ((baseHeight * 2 + 2) * scale);
	}
}
