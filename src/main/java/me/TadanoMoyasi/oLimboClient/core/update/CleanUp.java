package me.TadanoMoyasi.oLimboClient.core.update;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

public class CleanUp {
	public static void cleanOldVersions() {
		File mcDir = new File(".");
        File modsDir = new File(mcDir, "mods");
        File cleanupFile = new File(modsDir, ".oLimboClient_cleanup.txt");
        if (!cleanupFile.exists()) return;
        try {
            List<String> filesToDelete = Files.readAllLines(cleanupFile.toPath());
            StringBuilder deleteCommands = new StringBuilder();
            for (String fileName : filesToDelete) {
            	String cleanName = fileName.replace("\uFEFF", "").trim();
                if (cleanName.isEmpty()) continue;
                File oldJar = new File(modsDir, cleanName);
                deleteCommands.append("del /f /q \"").append(oldJar.getAbsolutePath()).append("\"\r\n");
                if (deleteCommands.length() == 0) {
                    cleanupFile.delete();
                    return;
                }
                File batFile = new File(modsDir, "oLimboClientUpdater.bat");
                try (PrintWriter writer = new PrintWriter(new FileWriter(batFile))) {
                    writer.println("@echo off");
                    writer.println("timeout /t 3 /nobreak > nul");
                    writer.print(deleteCommands.toString());
                    writer.println("del /f /q \"" + cleanupFile.getAbsolutePath() + "\"");
                    writer.println("(goto) 2>nul & del \"%~f0\" & exit");
                }
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                	try {
                		(new ProcessBuilder(new String[] { "cmd", "/c", "start", "oLimboClientUpdater", batFile.getAbsolutePath() })).directory(modsDir).start();
                	} catch (IOException e) {
                		e.printStackTrace();
                	}
                }));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
