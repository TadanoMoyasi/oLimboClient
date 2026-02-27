package me.TadanoMoyasi.oLimboClient.core.api;

import com.google.gson.Gson;

import me.TadanoMoyasi.oLimboClient.core.api.analyzer.ChatPlayerAnalyzer;
import me.TadanoMoyasi.oLimboClient.core.api.analyzer.ChatSkillCTAnalyzer;
import me.TadanoMoyasi.oLimboClient.core.api.types.Response;
import net.minecraft.client.Minecraft;

public class APIListener {
	private static final Gson gson = new Gson();
	
	public static void distribute(String text) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
            try {
                Response<?> response = gson.fromJson(text, Response.class);
                if (response == null || response.version == null || response.version.intValue() != 1) {
                    return;
                }
                if ("player_status".equals(response.apiType)) {
                    ChatPlayerAnalyzer.analyzePlayer(text);
                } else if ("skill_cooltime".equals(response.apiType)) {
                    ChatSkillCTAnalyzer.analyzeCT(text);
                }
            } catch (Exception e) {
                System.err.println("[Limbo] APIパースエラー: " + e.getMessage());
            }
        });
	}
}
