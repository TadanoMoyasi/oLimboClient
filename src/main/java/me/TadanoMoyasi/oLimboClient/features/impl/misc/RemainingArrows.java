package me.TadanoMoyasi.oLimboClient.features.impl.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RemainingArrows {
	private static final Minecraft mc = Minecraft.getMinecraft();
	public static int countArrows() {
		if (mc.thePlayer == null) return -1;
		EntityPlayer player = mc.thePlayer;
	    int total = 0;
	    for (ItemStack stack : player.inventory.mainInventory) {
	        if (stack != null && stack.getItem() == Items.arrow) {
	            total += stack.stackSize;
	        }
	    }
	    return total;
	}
}
