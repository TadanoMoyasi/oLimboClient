package me.TadanoMoyasi.oLimboClient.hud.core;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class HUDConfigScreen extends GuiScreen {
	
	private BaseHUD draggingHUD = null;
	private int dragOffsetX;
	private int dragOffsetY;
	public static boolean editMode = false;
	
	@Override
	public void initGui() {
		editMode = true;
	}

	@Override
	public void onGuiClosed() {
		editMode = false;
		HUDManager.savePositions();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
		
	@Override
	public void handleMouseInput() throws IOException {
	    super.handleMouseInput();

	    int dWheel = Mouse.getEventDWheel();
	    if (dWheel == 0) return;

	    int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
	    int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

	    for (BaseHUD hud : HUDManager.getHuds()) {

	        if (mouseX >= hud.getX() &&
	            mouseX <= hud.getX() + hud.getWidth() &&
	            mouseY >= hud.getY() &&
	            mouseY <= hud.getY() + hud.getHeight()) {
	        	
	        	float scale = hud.getScale();

	        	if (dWheel > 0) scale += 0.05f;
	        	else scale -= 0.05f;

	        	scale = Math.max(0.5f, Math.min(3.0f, scale));

	        	hud.setScale(scale);
	        	HUDManager.savePositions();
	        	break;
	        }
	    }
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
	    super.mouseClicked(mouseX, mouseY, mouseButton);

	    if (mouseButton == 0) {
	        for (int i = HUDManager.getHuds().size() - 1; i >= 0; i--) {
	            BaseHUD hud = HUDManager.getHuds().get(i);
	            
	            if (hud.isHovered(mouseX, mouseY)) {
	            	HUDManager.bringToFront(hud);
	                draggingHUD = hud;
	                dragOffsetX = mouseX - hud.getX();
	                dragOffsetY = mouseY - hud.getY();
	                break;
	            }
	        }
	    } else if (mouseButton == 1) {
	    	for (int i = HUDManager.getHuds().size() - 1; i >= 0; i--) {
	    		BaseHUD hud = HUDManager.getHuds().get(i);
	    		
	    		if (hud.isHovered(mouseX, mouseY)) {
	    			hud.setX(10);
	    			hud.setY(10);
	    			hud.setScale(1.0f);
	    			
	    			HUDManager.savePositions();
	    			break;
	    		}
	    	}
	    }
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
	    super.mouseReleased(mouseX, mouseY, state);

	    draggingHUD = null;
	    HUDManager.savePositions();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	    this.drawDefaultBackground();

	    for (BaseHUD hud : HUDManager.getHuds()) {
	    	hud.render();
            hud.renderEditor();
	    }

	    if (draggingHUD != null && Mouse.isButtonDown(0)) {
	        draggingHUD.setX(mouseX - dragOffsetX);
	        draggingHUD.setY(mouseY - dragOffsetY);
	    }
	    
	    Minecraft mc = Minecraft.getMinecraft();
	    ScaledResolution sr = new ScaledResolution(mc);

	    int centerX = sr.getScaledWidth() / 2;
	    int centerY = sr.getScaledHeight() / 2;
	    
	    String text = "Drag to Move";
	    String text2 = "Scroll to Change Scale";
	    String text3 = "RightClick to ResetHUD";
	    
	    int textWidth1 = mc.fontRendererObj.getStringWidth(text);
	    int textWidth2 = mc.fontRendererObj.getStringWidth(text2);
	    int textWidth3 = mc.fontRendererObj.getStringWidth(text3);
	    
	    GlStateManager.pushMatrix();
	    
	    GlStateManager.translate(centerX, centerY, 0);
	    GlStateManager.scale(1, 1, 1);

	    mc.fontRendererObj.drawStringWithShadow(text, -textWidth1 / 2, -15, 0xFFFFFF);
	    mc.fontRendererObj.drawStringWithShadow(text2,  -textWidth2 / 2, -5, 0xFFFFFF);
	    mc.fontRendererObj.drawStringWithShadow(text3,  -textWidth3 / 2,  5, 0xFFFFFF);

	    GlStateManager.popMatrix();

	    super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
