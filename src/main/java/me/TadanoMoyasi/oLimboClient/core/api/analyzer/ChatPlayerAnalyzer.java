package me.TadanoMoyasi.oLimboClient.core.api.analyzer;

import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.api.types.Player_Status;
import me.TadanoMoyasi.oLimboClient.core.api.types.Response;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;


public class ChatPlayerAnalyzer {
  public static void analyzePlayer(String text) {
    Type type = (new TypeToken<Response<Player_Status>>() {  }).getType();
    Response<Player_Status> playerData = (Response<Player_Status>)oLimboClientMod.gson.fromJson(text, type);
    ModCoreData.player_status.put(((Player_Status)playerData.response).mcid, playerData.response);
  }
  
  public static void addPlayerReinc() {}
}


