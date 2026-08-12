package me.TadanoMoyasi.oLimboClient.core.update;

import static me.TadanoMoyasi.oLimboClient.utils.Format.*;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.utils.TheLowUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;

public class AutoUpdater {
	public static void downloadAndScheduleCleanup(String downloadUrl, String newFileName, File currentJarFile) {
        new Thread(() -> {
            try {
                File modsDir = currentJarFile.getParentFile();
                File newJarFile = new File(modsDir, newFileName);
                URL url = new URL(downloadUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("User-Agent", "oLimboClient-AutoUpdater");
                try (InputStream in = conn.getInputStream()) {
                    Files.copy(in, newJarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                File cleanupFile = new File(modsDir, ".oLimboClient_cleanup.txt");
                try (FileWriter writer = new FileWriter(cleanupFile, true)) {
                    writer.write(currentJarFile.getName() + "\n");
                }
                System.out.println(ModCoreData.ufprefix + "新バージョンダウンロード完了。次回起動時に適用されます。");
                TheLowUtil.showInChat(ModCoreData.prefix + "新バージョンダウンロード完了。次回起動時に適用されます。");
                CleanUp.cleanOldVersions();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
	
	public static void sendUpdateChat(String newVersion, String downloadUrl, String newJarName) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            if (Minecraft.getMinecraft().thePlayer == null) return;
            ChatComponentText message = new ChatComponentText(ModCoreData.prefix + green + "新しいバージョン (" + newVersion + ") が利用可能です！ ");
            ChatComponentText clickLink = new ChatComponentText(aqua + "[今すぐアップデート]");
            clickLink.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/olimboclientupdate " + downloadUrl + " " + newJarName));
            clickLink.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(yellow + "クリックしてアップデートを開始")));
            message.appendSibling(clickLink);
            Minecraft.getMinecraft().thePlayer.addChatMessage(message);
        });
    }
}
