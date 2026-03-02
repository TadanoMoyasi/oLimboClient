package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.core.Codex;
import me.TadanoMoyasi.oLimboClient.utils.PlayerData;
import me.TadanoMoyasi.oLimboClient.utils.UUIDFetcher;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class CodexCache {
	private static final Minecraft mc = Minecraft.getMinecraft();
	private static final Map<UUID, Codex> skillCache = new java.util.concurrent.ConcurrentHashMap<>();
	
	private static int autoSaveTicks = -1;
	
	private static boolean loaded = false;
	
	public static void start() {
		autoSaveTicks = 36000;
	}
	
	public static void onTick() {
		if (autoSaveTicks >= 0) {
			autoSaveTicks--;
		} else {
			autoSaveTicks = 36000;
		}
	}
	
	public static boolean contains(UUID uuid) {
        return skillCache.containsKey(uuid);
    }

    public static Codex get(UUID uuid) {
    	if (!skillCache.containsKey(uuid)) return null;
    	Codex codex = skillCache.get(uuid);
        return codex;
    }

    public static void put(UUID uuid, int tick, String mcid, String slot) {
    	if (skillCache.containsKey(uuid)) {
            Codex codex = skillCache.get(uuid);
            synchronized (codex) {
                if (tick != 0) codex.ticks = tick;
                if (!mcid.equals("")) codex.mcid = mcid;
                if (!slot.equals("")) codex.slot = slot;
            }
        } else {
            Codex newCodex = new Codex(mcid, tick, slot);
            skillCache.put(uuid, newCodex);
        }
    }
    
    public static void changeCache(String name, double value) {
    	new Thread(() -> {
    		PlayerData player = UUIDFetcher.getPlayerDataName(name);
    	    UUID uuid = player.uuid;
    	    String mcid = player.mcid;
    	    if (uuid == null) {
    	    	sendChatMessage(ModCoreData.prefix + "§cUUIDの取得に失敗しました。MCIDが間違っているか、APIに制限がかかっている可能性があります。");
    	    }
    	    System.out.println("[Limbo]Fetched UUID: " + uuid.toString());
	        int ticks = calculateCodexTime(value);
	        put(uuid, ticks, mcid, "");
	        String timeStr = String.format("%.2f", ticks / 20.0f);
	        sendChatMessage(ModCoreData.prefix + "UUID:§8" + uuid + "時間: " + timeStr  + "§r登録完了");
	        saveToFile();
    	}).start();
    }
    
    private static void sendChatMessage(String text) {
    	if (mc.thePlayer != null) {
    		mc.addScheduledTask(() -> mc.thePlayer.addChatComponentMessage(new ChatComponentText(text)));
    	}
    }
    
    public static int calculateCodexTime(double value) {
    	double devided = value / 2;
    	double ticks = Math.floor(devided);
    	return (int) ticks;
    }
    
    public static void updateIfDifferent(UUID uuid, int newTick) {
    	Codex currentCodex = skillCache.get(uuid);
    	if (currentCodex == null) return;

        synchronized (currentCodex) {
            if (currentCodex.ticks != newTick) {
                currentCodex.ticks = newTick;
                new Thread(CodexCache::saveToFile).start(); 
            }
        }
    }
	
	public static void saveToFile() {
	    try {
	        File file = new File("config/olimboclient/skillcache.json");
	        file.getParentFile().mkdirs();

	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        
	        Map<UUID, Codex> copy;
	        synchronized (skillCache) {
	            copy = new java.util.HashMap<>(skillCache);
	        }

	        try (FileWriter writer = new FileWriter(file)) {
	            gson.toJson(copy, writer);//ここでskillCacheが変わるとマイクラが吹き飛ぶのでそれ対策のcopy
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
	            JsonObject obj = entry.getValue().getAsJsonObject();
	            int ticks = obj.get("ticks").getAsInt();
	            String mcid = obj.has("mcid") ? obj.get("mcid").getAsString() : "";
	            String slot = obj.has("slot") ? obj.get("slot").getAsString() : "";

	            skillCache.put(uuid, new Codex(mcid, ticks, slot));
	        }
	        loaded = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
