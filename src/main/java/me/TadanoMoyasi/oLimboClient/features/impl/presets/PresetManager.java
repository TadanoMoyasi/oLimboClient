package me.TadanoMoyasi.oLimboClient.features.impl.presets;

import java.util.Map;
import java.util.Set;

import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.utils.JobChanger;
import me.TadanoMoyasi.oLimboClient.utils.TheLowUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PresetManager {
	private static final Minecraft mc = Minecraft.getMinecraft();
	private static Preset activePreset = null;
	
	public static void setActivePreset(String name) {
		Preset loaded = loadPreset(name);
	    if (loaded != null) {
	        loaded.name = name;
	        activePreset = loaded;
	        if (!loaded.job.equals(JobChanger.getCurrentJob())) {
	        	TheLowUtil.showInChat(ModCoreData.prefix + "職業が違います : " + loaded.job);
	        }
	    }
	}
	
	public static boolean isPresetActive() {
		if (activePreset == null) return false;
		return true;
	}
	
	public static Preset getActivePreset() {
		return activePreset;
	}
	
	public static void unloadPreset() {
		activePreset = null;
	}
	
	public static void resetActivePreset() {
		activePreset = null;
		TheLowUtil.showInChat(ModCoreData.prefix + "全てのアイテムが揃った為、表示を終了しました。");
	}
	
	public static void sendPresetList() {
		Map<String, Preset> allData = PresetFileManager.loadAll();
		Set<String> names = allData.keySet();
		TheLowUtil.showInChat(ModCoreData.prefix + "Presets");
		for (String name : names) {
			TheLowUtil.showInChat(name);
		}
	}
	
	public static void deletePreset(String name) {
		Map<String, Preset> allData = PresetFileManager.loadAll();
		if (allData.containsKey(name)) {
	        allData.remove(name);
	        PresetFileManager.saveAll(allData);
	        TheLowUtil.showInChat(ModCoreData.prefix + "§aプリセット '" + name + "' を削除しました。");
	        System.out.println("プリセット '" + name + "' を削除しました。");
	    } else {
	        TheLowUtil.showInChat(ModCoreData.prefix + "§cエラー: '" + name + "' は見つかりません。");
	        System.out.println("エラー: '" + name + "' は見つかりません。");
	    }
	}
	
	public static void savePreset(String name, String jobName) {
		Map<String, Preset> allData = PresetFileManager.loadAll();
		Preset preset = new Preset();
		preset.name = name;
		preset.job = jobName;
		
		EntityPlayer player = mc.thePlayer;

		for (ItemStack stack : player.inventory.mainInventory) {
	        if (stack != null) {
	            preset.items.add(createRequirement(stack));
	        }
	    }

		for (ItemStack armor : player.inventory.armorInventory) {
	        if (armor != null) {
	            preset.items.add(createRequirement(armor));
	        }
	    }
		allData.put(name, preset);
	    PresetFileManager.saveAll(allData);
	}
	
	private static Preset.ItemRequirement createRequirement(ItemStack stack) {
	    String id = Item.itemRegistry.getNameForObject(stack.getItem()).toString();
	    String displayName = stack.getChatComponent().getUnformattedText();
	    
	    if (displayName.startsWith("[") && displayName.endsWith("]")) {
	        displayName = displayName.substring(1, displayName.length() - 1);
	    }
	    
	    String nbt = "";
	    
	    if (stack.hasTagCompound()) {
	        nbt = stack.getTagCompound().toString();
	    }
	    
	    Preset.ItemRequirement req = new Preset.ItemRequirement(id, displayName, nbt);
	    
	    return req;
	}
	
	public static Preset loadPreset(String name) {
		Map<String, Preset> allData = PresetFileManager.loadAll();
		Preset preset = allData.get(name);
		
		if (preset == null) {
			TheLowUtil.showInChat(ModCoreData.prefix + "§cプリセットが見つかりません: " + name);
			return null;
		}
		
		return preset;
	}
	
	public static boolean hasAllItems(Preset preset) {
	    EntityPlayer player = mc.thePlayer;
	    if (player == null || preset == null) return false;

	    for (Preset.ItemRequirement req : preset.items) {
	        boolean found = false;
	        
	        for (ItemStack stack : player.inventory.mainInventory) {
	            if (stack != null && isSameItem(stack, req)) {
	                found = true;
	                break;
	            }
	        }
	        
	        if (!found) {
	            for (ItemStack armor : player.inventory.armorInventory) {
	                if (armor != null && isSameItem(armor, req)) {
	                    found = true;
	                    break;
	                }
	            }
	        }
	        
	        if (!found) return false;
	    }
	    
	    return true; 
	}
	
	public static boolean isItemInInventory(Preset.ItemRequirement req) {
		EntityPlayer player = mc.thePlayer;
		if (player == null || req == null) return false;
		
		for (ItemStack stack : player.inventory.mainInventory) {
	        if (stack != null && isSameItem(stack, req)) return true;
	    }

	    for (ItemStack armor : player.inventory.armorInventory) {
	        if (armor != null && isSameItem(armor, req)) return true;
	    }

	    return false;
	}
	
	private static boolean isSameItem(ItemStack stack, Preset.ItemRequirement req) {
		if (stack == null || stack.getItem() == null) return false;
	    String stackId = Item.itemRegistry.getNameForObject(stack.getItem()).toString();
	    if (!stackId.equals(req.id)) return false;
	    
	    String currentName = net.minecraft.util.EnumChatFormatting.getTextWithoutFormattingCodes(stack.getDisplayName());
	    String requiredName = net.minecraft.util.EnumChatFormatting.getTextWithoutFormattingCodes(req.displayName);
	    
	    return currentName.equals(requiredName);
	}
}
