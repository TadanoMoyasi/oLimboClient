package me.TadanoMoyasi.oLimboClient.hud.features;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.features.impl.presets.Preset;
import me.TadanoMoyasi.oLimboClient.features.impl.presets.PresetManager;
import me.TadanoMoyasi.oLimboClient.hud.core.BaseHUD;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDConfigScreen;
import net.minecraft.client.renderer.GlStateManager;

public class PresetHUD extends BaseHUD{	
	public PresetHUD() {
		super("Preset", 10, 25);
	}
	
	private void renderDummy() {
	    String text = "§lRequired Items (vaaasa)";
	    String text2 = "§c ~ Rune of Arcadia ~";
	    String text3 = "§a✔ §l♥トワの手作りバレンタインチョコ♥ ";
	    
	    int baseWidth = mc.fontRendererObj.getStringWidth(text);
	    int baseHeight = mc.fontRendererObj.FONT_HEIGHT;
	    int lineHeight = (int) (baseHeight * scale) + 2;

	    GlStateManager.pushMatrix();
	    
	    GlStateManager.translate(getX(), getY(), 0);
	    GlStateManager.scale(getScale(), getScale(), 1);

	    mc.fontRendererObj.drawStringWithShadow(text, 0, 0, 0xAAAAAA);
	    mc.fontRendererObj.drawStringWithShadow(text2, 0, 0 + lineHeight, 0xAAAAAA);
	    mc.fontRendererObj.drawStringWithShadow(text3, 0, 0 + lineHeight * 2, 0xAAAAAA);

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
		
		if (!oLimboClientMod.config.presetHUD) return;
		if (!PresetManager.isPresetActive()) return;
		if (!oLimboClientMod.config.enableToggle) return;
		if (!ModCoreData.isInTheLow) return;
		
		Preset currentPreset = PresetManager.getActivePreset();
		if (currentPreset == null) return;
		
		if (PresetManager.hasAllItems(currentPreset)) {
			
			return;
		}
		
		String title = "§lRequired Items (" + currentPreset.name + ")";
		
		 int baseHeight = mc.fontRendererObj.FONT_HEIGHT;
		 int lineHeight = (int) (baseHeight * scale) + 2;
		 
		int renderY = y;
	    
	    GlStateManager.pushMatrix();
	    
	    GlStateManager.translate(getX(), getY(), 0);
	    GlStateManager.scale(getScale(), getScale(), 1.0f);
	    	    
	    int currentY = 0;
		
	    mc.fontRendererObj.drawStringWithShadow(title, 0, currentY, 0xFFFFFF);
	    
	    GlStateManager.popMatrix();
	    
	    renderY += lineHeight;
	    
	    for (Preset.ItemRequirement req : currentPreset.items) {
	    	String cleanName = req.displayName.replace("§r", "");
	    	String plainName = net.minecraft.util.EnumChatFormatting.getTextWithoutFormattingCodes(req.displayName);
	        String displayText = "§c ✘" + cleanName;
	        int color = 0xFFFFFF;
	        
	        if (PresetManager.isItemInInventory(req)) {
	        	displayText = "§a✔§m " + plainName;
	            color = 0x55FF55;
	        }
	        
	        int baseWidth = mc.fontRendererObj.getStringWidth(displayText);
		    
		    float centerX = x + (baseWidth * scale) / 2f;
		    float centerY = renderY + (baseHeight * scale) / 2f;
		    
		    GlStateManager.pushMatrix();
			
		    GlStateManager.translate(centerX, centerY, 0);
		    GlStateManager.scale(scale, scale, 1F);
		    
		    GlStateManager.translate(-baseWidth / 2f, -baseHeight / 2f, 0);
			
			mc.fontRendererObj.drawStringWithShadow(displayText, 0, 0, color);
			
			GlStateManager.popMatrix();
			
			width = (int) (baseWidth * scale);
			height = (int) (baseHeight * scale);
			
			renderY += lineHeight;
	    }
	}
}
