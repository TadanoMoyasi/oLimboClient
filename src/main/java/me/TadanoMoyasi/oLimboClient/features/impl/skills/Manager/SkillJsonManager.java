package me.TadanoMoyasi.oLimboClient.features.impl.skills.Manager;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.reflect.TypeToken;

import me.TadanoMoyasi.oLimboClient.features.impl.skills.Data.SkillData;
import me.TadanoMoyasi.oLimboClient.utils.JsonUtil;

public class SkillJsonManager {
	public static void init(File configDir) {
		SkillData.clearMap();
		File configFile = new File(configDir, "skills.json");
		Type type = new TypeToken<HashMap<String, String>>() {}.getType();
		HashMap<String, String> loadedData = JsonUtil.loadOrCopyDefault(configFile, "/skills.json", type);
		if (loadedData != null) {
			SkillData.putAll(loadedData);
		}
    }
	
    /*public static void init(File configDir) {
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        File configFile = new File(configDir, "skills.json");
        SkillData.clearMap();
        if (!configFile.exists()) {
            copyDefaultJson(configFile);
        }
        loadSkillsFromFile(configFile);
    }

    private static void copyDefaultJson(File targetFile) {
        try (InputStream in = SkillJsonManager.class.getResourceAsStream("/skills.json");
             OutputStream out = new FileOutputStream(targetFile)) {
            if (in != null) {
                IOUtils.copy(in, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSkillsFromFile(File file) {
        SkillData.clearMap();
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> loadedData = oLimboClientMod.gson.fromJson(reader, type);
            if (loadedData != null) {
            	SkillData.putAll(loadedData);
            }
        } catch (com.google.gson.JsonSyntaxException e) {
            // JSONの構文エラー
            System.err.println("[oLimboClient] skills.json の書き方が間違っています。 JSONの構文を確認してください。");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
