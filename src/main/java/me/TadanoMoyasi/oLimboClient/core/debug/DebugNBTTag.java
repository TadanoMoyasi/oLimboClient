package me.TadanoMoyasi.oLimboClient.core.debug;

import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.utils.TheLowUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DebugNBTTag {
	private static final Minecraft mc = Minecraft.getMinecraft();

	public static void sendNBTTag(String str) {
		if (mc.thePlayer.getHeldItem() == null) return;
		if (mc.thePlayer == null) return;
		ItemStack stack = mc.thePlayer.getHeldItem();
		if (stack == null || !stack.hasTagCompound()) return;
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) return;
		
		if (!nbt.hasKey(str)) return;
		TheLowUtil.showInChat(ModCoreData.prefix + nbt.getString(str));
	}
}
