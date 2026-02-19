package me.TadanoMoyasi.oLimboClient.hud.core;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import net.minecraft.client.gui.GuiScreen;

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

	    super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
