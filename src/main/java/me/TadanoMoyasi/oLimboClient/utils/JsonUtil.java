package me.TadanoMoyasi.oLimboClient.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import net.minecraft.util.JsonUtils;

public class JsonUtil {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	
	public static boolean saveToFile(File file, Object object) {
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
			GSON.toJson(object, writer);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean copyDefaultResource(String resourcePath, File targetFile) {
        File parent = targetFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (InputStream in = JsonUtils.class.getResourceAsStream(resourcePath);
             OutputStream out = new FileOutputStream(targetFile)) {
            if (in == null) {
                System.err.println(ModCoreData.ufprefix + "リソースが見つかりません: " + resourcePath);
                return false;
            }
            IOUtils.copy(in, out);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	public static <T> T loadOrCopyDefault(File file, String resourcePath, Type type) {
        if (!file.exists()) {
            copyDefaultResource(resourcePath, file);
        }
        return loadFromFile(file, type);
    }

	public static <T> T loadFromFile(File file, Type type) {
        if (!file.exists()) return null;
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            return GSON.fromJson(reader, type);
        } catch (com.google.gson.JsonSyntaxException e) {
            System.err.println(ModCoreData.ufprefix + "JSON の書き方が間違っています: " + file.getName());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	public static <T> T loadOrCreate(File file, Type type, T defaultValue) {
		if (!file.exists()) {
			saveToFile(file, defaultValue);
			return defaultValue;
		}
		T loaded = loadFromFile(file, type);
		return (loaded != null) ? loaded : defaultValue;
	}
	
	public static <T> T loadOrCreate(File file, Class<T> clazz, T defaultValue) {
		if (!file.exists()) {
			saveToFile(file, defaultValue);
			return defaultValue;
		}
		T loaded = loadFromFile(file, clazz);
		return (loaded != null) ? loaded : defaultValue;
	}
}
