package me.TadanoMoyasi.oLimboClient.features.impl.skills.core;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class NBTParsing {
	public static double getPlayerWeaponDamage(UUID uuid) {
        EntityPlayer player = Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(uuid);
        if (player != null && player.getHeldItem() != null) {
            NBTTagCompound nbt = player.getHeldItem().getTagCompound();
            if (nbt != null && nbt.hasKey("thelow_item_damage")) {
                return nbt.getDouble("thelow_item_damage");
            }
        }
        return 0.0;
    }
	
	public static String getPlayerSlot(UUID uuid) {
        EntityPlayer player = Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(uuid);
        if (player != null && player.getHeldItem() != null) {
            NBTTagCompound nbt = player.getHeldItem().getTagCompound();
            if (nbt != null && nbt.hasKey("thelow_item_slot_list")) {
                return nbt.getString("thelow_item_slot_list");
            }
        }
        return "";
    }
}
