package me.TadanoMoyasi.oLimboClient.features.impl.misc.wiki;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.utils.CooldownManager;
import me.TadanoMoyasi.oLimboClient.utils.JsonUtil;
import me.TadanoMoyasi.oLimboClient.utils.Scheduler;
import me.TadanoMoyasi.oLimboClient.utils.TheLowUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;

public class Wiki {
	private static final List<DungeonData> DUNGEON_LIST = new ArrayList<>();
	private static final List<DungeonData> LOWLEVEL_DUNGEON_LIST = new ArrayList<>();

    private static BlockPos lastPos;
    private static boolean lastInDungeon;
    
	public static void init(File configDir) {
		File configFile = new File(configDir, "dungeons.json");
		Type type = new TypeToken<ArrayList<DungeonData>>() {}.getType();
		ArrayList<DungeonData> loadedData = JsonUtil.loadOrCopyDefault(configFile, "/dungeons.json", type);
		if (loadedData != null) {
			DUNGEON_LIST.addAll(loadedData);
		}
        for (DungeonData data : DUNGEON_LIST) {
            if (data.level == null) continue;
            String clearLevel = data.level.replace("+", "");
            if (clearLevel.isEmpty()) continue;
            try {
                int level = Integer.parseInt(clearLevel.trim());
                if (level >= 66) continue;
            } catch (NumberFormatException e) {
                continue;
            }
            LOWLEVEL_DUNGEON_LIST.add(data);
        }
    }

    /*@SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        if (player != null && event.world == mc.theWorld) {
        	if (player.posX == 8.5 && player.posY == 65.0 && player.posZ == 8.5) return;
            lastX = player.posX;
            lastY = player.posY;
            lastZ = player.posZ;
            
             lastPos = new BlockPos(lastX, lastY, lastZ);
             lastInDungeon = ModCoreData.inDungeon;
        }
    }*/
    
    public static void onTP() {
    	Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (player == null) return;
        if (player.posX == 8.5 && player.posY == 65.0 && player.posZ == 8.5) return;
        if (!CooldownManager.checkAndReset("wiki", 40)) return;
        lastPos = new BlockPos(player.posX, player.posY, player.posZ);
        lastInDungeon = ModCoreData.inDungeon;
        if (!oLimboClientMod.config.Wiki) return;
        Scheduler.setTimeout(() -> {
        	if (mc.thePlayer == null) return;
        	DungeonData data = getNearestWithinRange(lastPos);
        	if (data == null) return;
        	if (!ModCoreData.inDungeon) return;
        	if (data.inDungeon) {
        		if (!lastInDungeon) return;
        	}
        	if (oLimboClientMod.config.WikiHighLevel && LOWLEVEL_DUNGEON_LIST.contains(data)) return;
        	TheLowUtil.sendClickableUrl(ModCoreData.prefix + data.name +"のWiki: " + data.url, data.url);
         }, 60);
    }
    
    /*public static void init(File configDir) {
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        File configFile = new File(configDir, "dungeons.json");
        if (!configFile.exists()) {
            copyDefaultJson(configFile);
        }
        loadDungeonsFromFile(configFile);
    }

    private static void copyDefaultJson(File targetFile) {
        try (InputStream in = Wiki.class.getResourceAsStream("/dungeons.json");
             OutputStream out = new FileOutputStream(targetFile)) {
            if (in != null) {
                IOUtils.copy(in, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /*private static void loadDungeonsFromFile(File file) {
    	DUNGEON_LIST.clear();
        try (
        	Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            Type type = new TypeToken<ArrayList<DungeonData>>() {}.getType();
            ArrayList<DungeonData> loadedData = oLimboClientMod.gson.fromJson(reader, type);
            if (loadedData != null) {
            	DUNGEON_LIST.addAll(loadedData);
            }
        } catch (com.google.gson.JsonSyntaxException e) {
            // JSONの構文エラー
            System.err.println("[oLimboClient] dungeons.json の書き方が間違っています。 JSONの構文を確認してください。");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (DungeonData data : DUNGEON_LIST) {
        	String clearLevel = data.level.replace("+", "");
        	if (clearLevel == null || clearLevel.isEmpty()) continue;
        	try {
        		int level = Integer.parseInt(clearLevel.trim());
        		if (level >= 66) continue;
        	} catch (NumberFormatException e) {
        		continue;
        	}
        	LOWLEVEL_DUNGEON_LIST.add(data);
        }
    }*/
    
    public static DungeonData getNearestWithinRange(BlockPos playerPos) {
    	if (DUNGEON_LIST.isEmpty() || playerPos == null) return null;
        DungeonData nearestData = null;
        double minDistanceSq = Double.MAX_VALUE;
        
        for (DungeonData data : DUNGEON_LIST) {
            double distSq = playerPos.distanceSq(data.getBlockPos());
            if (distSq < minDistanceSq) {
                minDistanceSq = distSq;
                nearestData = data;
            }
        }
        
        if (nearestData != null) {
        	double effectiveRadius = nearestData.radius > 0 ? nearestData.radius : 7.0;
            double maxRangeSq = effectiveRadius * effectiveRadius;

            if (minDistanceSq <= maxRangeSq) {
                return nearestData;
            }
        }
        return null;
    }
}
