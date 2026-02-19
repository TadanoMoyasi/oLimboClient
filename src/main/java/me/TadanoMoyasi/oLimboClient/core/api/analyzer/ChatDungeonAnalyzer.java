package me.TadanoMoyasi.oLimboClient.core.api.analyzer;


import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.api.types.DungeonData;
import me.TadanoMoyasi.oLimboClient.core.api.types.Response;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;


public class ChatDungeonAnalyzer {
  public static void analyzeDungeon(String text) {
    Type type = (new TypeToken<Response<DungeonData[]>>() {  }).getType();
    Response<DungeonData[]> dungeonData = (Response<DungeonData[]>)oLimboClientMod.gson.fromJson(text, type);
    ModCoreData.dungeonData = (DungeonData[])dungeonData.response;
  }
}


