package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class CodexRangeDisplay {
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	private static final double[] cosCache = new double[360];
	private static final double[] sinCache = new double[360];

	static {
	    for (int i = 0; i < 360; i++) {
	        cosCache[i] = Math.cos(Math.toRadians(i));
	        sinCache[i] = Math.sin(Math.toRadians(i));
	    }
	}
	
	public static void onRenderWorld(RenderWorldLastEvent event) {
		if (!oLimboClientMod.config.codexRange || !oLimboClientMod.config.enableToggle) return;
		if (!ModCoreData.isInTheLow) return;
		if (mc.thePlayer == null || mc.thePlayer.getHeldItem() == null) return;
		
		if (!mc.thePlayer.getHeldItem().getDisplayName().contains("Codex")) return;
		
		Entity player = Minecraft.getMinecraft().getRenderViewEntity();
		if (player == null) return;
        
        double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.partialTicks;
        double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.partialTicks;
        double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.partialTicks;
        
        Color color;
        if (oLimboClientMod.config.codexRangeChroma) {
            float hue = (System.currentTimeMillis() % 3000L) / 3000.0F;
            color = Color.getHSBColor(hue, 0.8F, 1.0F); 
        } else {
            color = oLimboClientMod.config.codexRangeColor;
        }
        
        float alpha;
        float lineWidth;
        
        if (player.isSneaking()) {
            alpha = 0.8F;
            lineWidth = 6.0F; 
        } else {
            alpha = 0.3F;
            lineWidth = 4.0F;
        }
        
        drawCircleInWorld(x, y + 0.1, z, 7.0, color, alpha, lineWidth);
	}
	
	private static void drawCircleInWorld(double x, double y, double z, double radius, Color color, float alpha, float lineWidth) {
        Minecraft mc = Minecraft.getMinecraft();
        double renderX = x - mc.getRenderManager().viewerPosX;
        double renderY = y - mc.getRenderManager().viewerPosY;
        double renderZ = z - mc.getRenderManager().viewerPosZ;
        
        boolean isChroma = oLimboClientMod.config.codexRangeChroma;
        
        GlStateManager.pushMatrix();
        GlStateManager.translate(renderX, renderY, renderZ);
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        
        GL11.glLineWidth(lineWidth);
        
        if (isChroma) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        }
        
        drawLoop(radius, alpha * 0.2F, isChroma, color);
        
        GlStateManager.enableDepth();
        drawLoop(radius, alpha, isChroma, color);
        
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
	
	private static void drawLoop(double radius, float alpha, boolean isChroma, Color staticColor) {
	    Tessellator tessellator = Tessellator.getInstance();
	    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
	    
	    if (isChroma) {
	        worldrenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
	        
	        float hueOffset = (System.currentTimeMillis() % 3000L) / 3000.0F;
	        for (int i = 0; i <= 360; i++) {
	            int index = i % 360;
	            float hue = (hueOffset + (i / 360.0F)) % 1.0F;
	            int rgb = Color.HSBtoRGB(hue, 0.8F, 1.0F);
	            
	            worldrenderer.pos(sinCache[index] * radius, 0, cosCache[index] * radius)
	                         .color((rgb >> 16 & 0xFF) / 255F, (rgb >> 8 & 0xFF) / 255F, (rgb & 0xFF) / 255F, alpha)
	                         .endVertex();
	        }
	    } else {
	        float r = staticColor.getRed() / 255.0F;
	        float g = staticColor.getGreen() / 255.0F;
	        float b = staticColor.getBlue() / 255.0F;
	        GlStateManager.color(r, g, b, alpha);
	        
	        worldrenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
	        
	        /*for (int i = 0; i <= 360; i++) {
            double radians = Math.toRadians(i);
            worldrenderer.pos(Math.sin(radians) * radius, 0, Math.cos(radians) * radius).endVertex();
        }*/
	        
	        for (int i = 0; i < 360; i++) {
	            worldrenderer.pos(sinCache[i] * radius, 0, cosCache[i] * radius).endVertex(); // ちょっとくらい軽量化になる...かな？たぶん。
	        }
	    }
	    
	    tessellator.draw();
	}
}
