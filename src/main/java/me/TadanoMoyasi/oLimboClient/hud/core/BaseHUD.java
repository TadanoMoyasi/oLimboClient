package me.TadanoMoyasi.oLimboClient.hud.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

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
		drawOutline();
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
	
	private void drawOutline() {
		
		int borderColor = 0xFFFFFF;
		int backgroundColor = 0x55000000;
		
		Gui.drawRect(x, y, x + width, y + height, backgroundColor);
		
		Gui.drawRect(x, y, x + width, y + 1, borderColor); //ue
		Gui.drawRect(x, y + height - 1, x + width, y + height, borderColor); //sita
		Gui.drawRect(x, y, x + 1, y + height, borderColor); //hida
		Gui.drawRect(x + width - 1, y, x + width, y + height, borderColor); //migi
	}
}
