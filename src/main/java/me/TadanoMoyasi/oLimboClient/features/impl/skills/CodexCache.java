package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.utils.UUIDFetcher;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class CodexCache {
	
	private static final Map<UUID, Integer> skillCache = new HashMap<>();
	
	private static boolean loaded = false;
	
	public static boolean contains(UUID uuid) {
        return skillCache.containsKey(uuid);
    }

    public static int get(UUID uuid) {
        return skillCache.getOrDefault(uuid, 0);
    }

    public static void put(UUID uuid, int tick) {
        skillCache.put(uuid, tick);
        saveToFile();
    }
    
    public static void changeCache(String name, double value) {
    	new Thread(() -> {
    	    UUID uuid = UUIDFetcher.getUUIDFromName(name);
    	    if (uuid != null) {
    	        System.out.println("[Limbo]Fetched UUID: " + uuid.toString());
    	        int ticks = calculateCodexTime(value);
    	        put(uuid, ticks);
    	        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(ModCoreData.prefix + "UUID: §8" + uuid + "§rtime: §8" + ticks / 20 + "§rsで登録しました。"));
    	    } else {
    	    	Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("§c[Limbo]UUIDの取得に失敗しました。MCIDが間違っているか、APIに制限がかかっている可能性があります。"));
    	    }
    	}).start();
    }
    
    private static int calculateCodexTime(double value) {
    	double devided = value / 2;
    	double ticks = Math.floor(devided);
    	return (int) ticks;
    }
    
    public static void updateIfDifferent(UUID uuid, int newTick) {
        int currentTick = skillCache.getOrDefault(uuid, -1);

        if (currentTick != newTick) {
            skillCache.put(uuid, newTick);
            saveToFile();
        }
    }
	
	public static void saveToFile() {
	    try {
	        File file = new File("config/olimboclient/skillcache.json");
	        file.getParentFile().mkdirs();

	        Gson gson = new GsonBuilder().setPrettyPrinting().create();

	        JsonObject root = new JsonObject();

	        for (Map.Entry<UUID, Integer> entry : skillCache.entrySet()) {
	            root.addProperty(entry.getKey().toString(), entry.getValue());
	        }

	        try (FileWriter writer = new FileWriter(file)) {
	            gson.toJson(root, writer);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void loadFromFile() {
		if (loaded) return;
	    try {
	        File file = new File("config/olimboclient/skillcache.json");
	        if (!file.exists()) return;

	        JsonParser parser = new JsonParser();
	        JsonObject root = parser.parse(new FileReader(file)).getAsJsonObject();

	        for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
	            UUID uuid = UUID.fromString(entry.getKey());
	            int time = entry.getValue().getAsInt();
	            skillCache.put(uuid, time);
	        }
	        loaded = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
