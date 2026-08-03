package me.TadanoMoyasi.oLimboClient.features.impl.skills.Manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.gson.reflect.TypeToken;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.Data.SkillData;

public class SkillJsonManager {
    public static void init(File configDir) {
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        File configFile = new File(configDir, "skills.json");
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
    }
}
