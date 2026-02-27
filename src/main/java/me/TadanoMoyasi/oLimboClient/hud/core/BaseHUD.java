package me.TadanoMoyasi.oLimboClient.hud.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public abstract class BaseHUD {
	
	protected Minecraft mc = Minecraft.getMinecraft();
	
	protected final String id;
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected float scale = 1.0F;
	
	protected boolean enabled = true;
	
	public BaseHUD(String id, int defaultX, int defaultY) {
		this.id = id;
		this.x = defaultX;
		this.y = defaultY;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public float getScale() { return scale; }

	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setScale(float scale) { this.scale = scale; }

	public boolean isEnabled() { return enabled; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; }
	
	public abstract void render();
	
	public void renderEditor() {
		render();

		GlStateManager.pushMatrix();
		
	    GlStateManager.translate(x, y, 0);
	    
	    drawOutline();
	    
	    GlStateManager.popMatrix();
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
	
	private void drawOutline() {
		//nazeka sirankedo byouga dekin kara ittan akirameru kotonisuru. maa nakutemo komaransi kigamuitara naosu#気が向いたら直す
		int borderColor = 0xFFFFFF;
		int backgroundColor = 0x55000000;
		
		Gui.drawRect(0, 0, width, height, backgroundColor);
	    
	    Gui.drawRect(0, 0, width, 1, borderColor); // ue
	    Gui.drawRect(0, height - 1, width, height, borderColor); // sita
	    Gui.drawRect(0, 0, 1, height, borderColor); // hida
	    Gui.drawRect(width - 1, 0, width, height, borderColor); // migi
	}
}
