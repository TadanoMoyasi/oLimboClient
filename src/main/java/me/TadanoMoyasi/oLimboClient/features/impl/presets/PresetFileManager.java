package me.TadanoMoyasi.oLimboClient.features.impl.presets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.utils.TheLowUtil;

public class PresetFileManager {
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final File file = new File("config/olimboclient/preset.json");
	
	public static void saveAll(Map<String, Preset> map) {
		if (!file.getParentFile().exists()) {
	        file.getParentFile().mkdirs();
	    }
        try (OutputStreamWriter writer = new OutputStreamWriter(
        		new FileOutputStream(file), StandardCharsets.UTF_8)) {
            gson.toJson(map, writer);
            System.out.println("[Limbo] Saved Preset");
            TheLowUtil.showInChat(ModCoreData.prefix + "§aプリセットを保存しました。");
        } catch (IOException e) {
        	e.printStackTrace();
            System.err.println("[Limbo] failed save preset");
            TheLowUtil.showInChat(ModCoreData.prefix + "§c保存エラーが発生しました。");
        }
    }
	
	public static Map<String, Preset> loadAll() {
        if (!file.exists()) return new HashMap<>();
        
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8)) {
            Type type = new TypeToken<Map<String, Preset>>(){}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
