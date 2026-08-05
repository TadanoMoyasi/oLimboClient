package me.TadanoMoyasi.oLimboClient.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class SlotUtil {
	private static final Minecraft mc = Minecraft.getMinecraft();
    /**
     * 現在開いている GuiChest から指定インデックスの Slot を取得する
     * @param slotIndex 取得したいスロットのインデックス (0 ~ )
     * @return 該当する Slot オブジェクト（GUIが開いていない/範囲外の場合は null）
     */
    public static Slot getSlotFromChest(int slotIndex) {
        if (!(mc.currentScreen instanceof GuiChest)) return null;
        GuiChest guiChest = (GuiChest) mc.currentScreen;
        Container container = guiChest.inventorySlots;
        if (slotIndex >= 0 && slotIndex < container.inventorySlots.size()) {
            return container.getSlot(slotIndex);
        }
        return null;
    }
}
