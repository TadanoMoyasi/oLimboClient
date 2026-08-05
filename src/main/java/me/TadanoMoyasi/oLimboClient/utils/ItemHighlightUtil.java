package me.TadanoMoyasi.oLimboClient.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;

public class ItemHighlightUtil {
	private static Field fieldGuiLeft = null;
    private static Field fieldGuiTop = null;
    private static boolean initialized = false;
    
    public static Field getFieldFromClass(Class clazz, String fieldName) throws NoSuchFieldException {
        Field field = null;
        while (clazz != null) {
          try {
            Field[] fields = clazz.getDeclaredFields();
            if (clazz.getSimpleName().equals("GuiContainer"))
              for (int i = 0; i < fields.length; i++); 
            field = clazz.getDeclaredField(fieldName);
            break;
          } catch (NoSuchFieldException e) {
            clazz = clazz.getSuperclass();
          } 
        } 
        if (field == null)
          throw new NoSuchFieldException(); 
        return field;
      }

    private static void initFields() {
        if (initialized) return;
        initialized = true;

        List<Field> intFields = new ArrayList<>();
        // GuiContainer 内の全フィールドを検索し、int 型の非 static フィールドを順に抽出
        for (Field f : GuiContainer.class.getDeclaredFields()) {
            if (f.getType() == int.class && !java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                f.setAccessible(true);
                intFields.add(f);
            }
        }

        // GuiContainer の定義順で最初に出てくる int が guiLeft、2番目が guiTop
        if (intFields.size() >= 2) {
            fieldGuiLeft = intFields.get(0);
            fieldGuiTop = intFields.get(1);
        }
    }

    public static int getGuiLeft(GuiContainer gui) {
        initFields();
        if (fieldGuiLeft != null) {
            try {
                return fieldGuiLeft.getInt(gui);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static int getGuiTop(GuiContainer gui) {
        initFields();
        if (fieldGuiTop != null) {
            try {
                return fieldGuiTop.getInt(gui);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
	
	/**
     * 指定したスロットの背景/表面に半透明のカラーオーバーレイを描画。
     * @param slot  対象のスロット
     * @param color ARGB形式のカラーコード (例: 0x80FF0000)
     */
	public static void highlightSlot(Slot slot, int color) {
        if (slot == null) return;
        if (!(Minecraft.getMinecraft().currentScreen instanceof GuiContainer)) return;
        GuiContainer gui = (GuiContainer) Minecraft.getMinecraft().currentScreen;
        int leftX = 0;
        int topY = 0;
        Class<? extends GuiContainer> guiclazz = (Class)gui.getClass();
        try {
            Field fleftX = getFieldFromClass(guiclazz, "field_147003_i");
            Field ftopY = getFieldFromClass(guiclazz, "field_147009_r");
            fleftX.setAccessible(true);
            ftopY.setAccessible(true);
            leftX = fleftX.getInt(gui);
            topY = ftopY.getInt(gui);
          } catch (Exception exception) {}
        
        int x = leftX + slot.xDisplayPosition;
        int y = topY + slot.yDisplayPosition;
        
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(
            GL11.GL_SRC_ALPHA, 
            GL11.GL_ONE_MINUS_SRC_ALPHA, 
            GL11.GL_ONE, 
            GL11.GL_ZERO
        );

        //akithelowaddonとの干渉対策でZ調整しようと思ったけどあっちで1000まで上げてくれてるから別にこれ必要なかった。
        GlStateManager.translate(0.0F, 0.0F, 100.0F);

        Gui.drawRect(x, y, x + 16, y + 16, color);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    /**
     * 指定したスロットの周囲に枠線を描画。
     * @param slot      対象のスロット
     * @param color     RGBまたはARGB形式のカラーコード (例: 0xFFFFD700 = 金色)
     * @param thickness 枠線の太さ (px)
     */
	public static void drawSlotBorder(Slot slot, int color, int thickness) {
        if (slot == null || thickness <= 0) return;
        if ((color & 0xFF000000) == 0) {
            color |= 0xFF000000;
        }

        int x = slot.xDisplayPosition;
        int y = slot.yDisplayPosition;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(
            GL11.GL_SRC_ALPHA, 
            GL11.GL_ONE_MINUS_SRC_ALPHA, 
            GL11.GL_ONE, 
            GL11.GL_ZERO
        );

        GlStateManager.translate(0.0F, 0.0F, 100.0F);

        Gui.drawRect(x - thickness, y - thickness, x + 16 + thickness, y, color);
        Gui.drawRect(x - thickness, y + 16, x + 16 + thickness, y + 16 + thickness, color);
        Gui.drawRect(x - thickness, y, x, y + 16, color);
        Gui.drawRect(x + 16, y, x + 16 + thickness, y + 16, color);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void highlightSlotWithBorder(Slot slot, int fillColor, int borderColor, int borderThickness) {
        highlightSlot(slot, fillColor);
        drawSlotBorder(slot, borderColor, borderThickness);
    }
}
