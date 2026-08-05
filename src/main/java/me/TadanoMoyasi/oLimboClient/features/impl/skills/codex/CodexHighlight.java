package me.TadanoMoyasi.oLimboClient.features.impl.skills.codex;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.utils.ItemHighlightUtil;
import me.TadanoMoyasi.oLimboClient.utils.SlotUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class CodexHighlight {
	private static final Minecraft mc = Minecraft.getMinecraft();
	public static boolean isSpecificChestOpen(String titleName) {
        if (mc.currentScreen instanceof GuiChest) {
            GuiChest guiChest = (GuiChest) mc.currentScreen;
            ContainerChest container = (ContainerChest) guiChest.inventorySlots;
            IInventory lowerChestInventory = container.getLowerChestInventory();

            if (lowerChestInventory != null && lowerChestInventory.hasCustomName()) {
                return lowerChestInventory.getDisplayName().getUnformattedText().contains(titleName);
            }
        }
        return false;
    }
	
	public static boolean isMatchingChest(int slotIndex, String itemName) {
        if (!(mc.currentScreen instanceof GuiChest)) return false;
        GuiChest guiChest = (GuiChest) mc.currentScreen;
        ContainerChest container = (ContainerChest) guiChest.inventorySlots;
        IInventory inv = container.getLowerChestInventory();
        if (slotIndex < 0 || slotIndex >= inv.getSizeInventory()) return false;
        ItemStack stack = inv.getStackInSlot(slotIndex);
        if (stack == null) return false;
        if (itemName != null) {
            String cleanDisplayName = stack.getDisplayName().replaceAll("§[0-9a-fk-or]", "");
            if (!cleanDisplayName.contains(itemName)) return false;
        }
        return true;
    }
	
	public static void onRenderGui() {
		if (!oLimboClientMod.config.codexSkillHighlight) return;
	    if (!isSpecificChestOpen("weapon skill")) return;
	    if (!isMatchingChest(11, "烈撃の紋章")) return;

	    highlightIf(!CodexSkillManager.stre, 11, 0xC0FF0000);
	    highlightIf(!CodexSkillManager.rege, 13, 0xC0FF0000);
	    highlightIf(!CodexSkillManager.regi, 15, 0xC0FF0000);
	}
	
	private static void highlightIf(boolean condition, int slotIndex, int color) {
	    if (condition) {
	        ItemHighlightUtil.highlightSlot(SlotUtil.getSlotFromChest(slotIndex), color);
	    }
	}
}
