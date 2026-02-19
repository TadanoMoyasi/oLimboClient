package me.TadanoMoyasi.oLimboClient.core.api;

import com.google.gson.Gson;

import me.TadanoMoyasi.oLimboClient.core.api.analyzer.ChatDungeonAnalyzer;
import me.TadanoMoyasi.oLimboClient.core.api.analyzer.ChatPlayerAnalyzer;
import me.TadanoMoyasi.oLimboClient.core.api.analyzer.ChatSkillCTAnalyzer;
import me.TadanoMoyasi.oLimboClient.core.api.types.Response;

public class APIListener {
	public static void distribute(String text) {
	    Gson gson = new Gson();
		Response<?> response = (Response)gson.fromJson(text, Response.class);
	    if (response.version.intValue() != 1) {
	      return;
	    }
	    if (response.apiType.equals("dungeon")) {
	      ChatDungeonAnalyzer.analyzeDungeon(text);
	    } else if (response.apiType.equals("player_status")) {
	      ChatPlayerAnalyzer.analyzePlayer(text);
	    } else if (response.apiType.equals("skill_cooltime")) {
	      ChatSkillCTAnalyzer.analyzeCT(text);
	    } 
	  }
}
