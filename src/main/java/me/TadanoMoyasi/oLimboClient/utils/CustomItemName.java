package me.TadanoMoyasi.oLimboClient.utils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.reflect.TypeToken;

import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CustomItemName {
	public static final HashMap<String, String> CHANGE_ITEMS = new HashMap<>();
	
	@SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.itemStack;
        if (stack == null || stack.getItem() == null) return;
        NBTTagCompound tag = stack.getTagCompound();
		if (!tag.hasKey("thelow_item_damage") || !tag.hasKey("display")) return;
		double attack = tag.getDouble("thelow_item_damage");
		NBTTagCompound displayTag = tag.getCompoundTag("display");
		if (!displayTag.hasKey("Name")) return;
		String nbtName = displayTag.getString("Name");
		String mapKey = Double.toString(attack) + nbtName;
        if (!CHANGE_ITEMS.containsKey(mapKey)) return;
        String customName = CHANGE_ITEMS.get(mapKey);
        event.toolTip.set(0, Format.gray + customName);
        event.toolTip.add(1, Format.gray + "(" + nbtName + Format.gray +  ")");
    }
	
	public static void addChangeItems(ItemStack stack, String name) {
		NBTTagCompound tag = stack.getTagCompound();
		if (!tag.hasKey("thelow_item_damage") || !tag.hasKey("display")) return;
		double attack = tag.getDouble("thelow_item_damage");
		NBTTagCompound displayTag = tag.getCompoundTag("display");
		if (!displayTag.hasKey("Name")) return;
		String nbtName = displayTag.getString("Name");
		String mapKey = Double.toString(attack) + nbtName;
		CHANGE_ITEMS.put(mapKey, name);
		System.out.println(ModCoreData.ufprefix + "added");
	}
	
	public static void init(File configDir) {
		File configFile = new File(configDir, "custom_items.json");
		Type type = new TypeToken<HashMap<String, String>>() {}.getType();
		HashMap<String, String> defaultMap = new HashMap<>();
		HashMap<String, String> loadedData = JsonUtil.loadOrCreate(configFile, type, defaultMap);
		if (loadedData != null) {
			CHANGE_ITEMS.putAll(loadedData);
		}
    }
	
	public static String end(File configDir) {
		File configFile = new File(configDir, "custom_items.json");
		if (!JsonUtil.saveToFile(configFile, CHANGE_ITEMS)) return "";
		return "custom_items.json";
	}
}
