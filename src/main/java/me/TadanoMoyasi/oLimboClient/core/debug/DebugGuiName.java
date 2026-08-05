package me.TadanoMoyasi.oLimboClient.core.debug;

import me.TadanoMoyasi.oLimboClient.utils.Scheduler;
import me.TadanoMoyasi.oLimboClient.utils.TheLowUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;

public class DebugGuiName {
	private static final Minecraft mc = Minecraft.getMinecraft();
	public static void debugName(int delayTick) {
		Scheduler.setTimeout(() -> {
			if (mc.currentScreen instanceof GuiChest) {
	            GuiChest guiChest = (GuiChest) mc.currentScreen;
	            ContainerChest container = (ContainerChest) guiChest.inventorySlots;
	            IInventory lowerChestInventory = container.getLowerChestInventory();

	            if (lowerChestInventory != null) {
	                TheLowUtil.showInChat(lowerChestInventory.getDisplayName().getUnformattedText());
	            }
	        }
		}, delayTick);
    }
}
