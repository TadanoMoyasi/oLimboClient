package me.TadanoMoyasi.oLimboClient.hud.features;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.PriestManager;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.core.Priest;
import me.TadanoMoyasi.oLimboClient.hud.core.BaseHUD;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDConfigScreen;
import net.minecraft.client.renderer.GlStateManager;

public class PriestHUD extends BaseHUD{
	public PriestHUD() {
		super("PriestHUD", 10, 25);
	}
	
	private void renderDummy() {
		String text = "§6§lプリースト:";
	    String textP1 = "TdMy: §aReady!";
	    String textP2 = "xXx_Priest_xXx: §cCT 2.15s";
	    
	    int baseHeight = mc.fontRendererObj.FONT_HEIGHT;

	    GlStateManager.pushMatrix();
	    
	    GlStateManager.translate(getX(), getY(), 0);
	    GlStateManager.scale(getScale(), getScale(), 1);

	    mc.fontRendererObj.drawStringWithShadow(text, 0, 0, 0xAAAAAA);
	    mc.fontRendererObj.drawStringWithShadow(textP1, 0, baseHeight + 2, 0xAAAAAA);
	    mc.fontRendererObj.drawStringWithShadow(textP2, 0, baseHeight * 2 + 2, 0xAAAAAA);

	    GlStateManager.popMatrix();
	    
	    int baseWidth = Math.max(
	    		mc.fontRendererObj.getStringWidth(textP1), mc.fontRendererObj.getStringWidth(textP2)
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
		
		if (!oLimboClientMod.config.priestHUD || !oLimboClientMod.config.enableToggle) return;
		if (!ModCoreData.isInTheLow) return;
		
		if (PriestManager.getPriestSize() <= 0) return;
		
		String title = "§6§lプリースト:";
		
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
		for (Priest priest : PriestManager.getPriests()) {
			String text;
			
			String status = priest.isCorrect ? "" : "§7(?) ";

			if (priest.isActive()) {
				text = priest.name + ": " + status + "§6" + priest.skill + " §a" + String.format("%.2f", priest.getDurationRemaining()) + "s";
			} else if (priest.isCooldown()) {
				text = priest.name + ": " + status + "§cCT " + String.format("%.2f", priest.getCooldownRemaining()) + "s";
			} else {
				text = priest.name + ": " + status + "§aReady!";
			}
			
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
