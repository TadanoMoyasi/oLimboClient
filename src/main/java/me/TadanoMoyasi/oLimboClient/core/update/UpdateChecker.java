package me.TadanoMoyasi.oLimboClient.core.update;

import static me.TadanoMoyasi.oLimboClient.utils.Format.*;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.utils.TheLowUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UpdateChecker {
	private static final String GITHUB_OWNER = "TadanoMoyasi";
    private static final String GITHUB_REPO = "oLimboClient";
    private static boolean checkedUpdate = false;
    
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent evnet) {
    	if (checkedUpdate) return;
    	if (evnet.entity == Minecraft.getMinecraft().thePlayer) {
    		TheLowUtil.showInChat(ModCoreData.prefix + gray +  "アップデートをチェックしています......");
    		checkUpdate();
    		checkedUpdate = true;
    		MinecraftForge.EVENT_BUS.unregister(this);
    	}
    }

    public static void checkUpdate() {
        CompletableFuture.runAsync(() -> {
            try {
                String apiUrl = String.format("https://api.github.com/repos/%s/%s/releases/latest", GITHUB_OWNER, GITHUB_REPO);
                HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "oLimboClient-UpdateChecker");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                if (conn.getResponseCode() == 200) {
                    InputStreamReader reader = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
                    GitHubJson release = new Gson().fromJson(reader, GitHubJson.class);
                    reader.close();
                    String latestVersion = release.tagName.replace("v", "");
                    if (isNewerVersion(oLimboClientMod.MOD_VERSION, latestVersion)) {
                    	AutoUpdater.sendUpdateChat(latestVersion, release.assets.get(0).browserDownloadUrl, release.assets.get(0).name);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static boolean isNewerVersion(String current, String latest) {
        return !current.equalsIgnoreCase(latest);
    }
}
