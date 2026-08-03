package me.TadanoMoyasi.oLimboClient.core.api.analyzer;

import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.api.types.Location;
import me.TadanoMoyasi.oLimboClient.core.api.types.Response;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;

//$api {"apiType":"location","version":1,"response":{"worldName":"thelow","x":-9.0,"y":119.0,"z":-27.57712362478395}}
public class ChatLocationAnalyzer {
	public static void analyzeLocation(String text) {
		Type type = (new TypeToken<Response<Location>>() {  }).getType();
	    Response<Location> location = (Response<Location>)oLimboClientMod.gson.fromJson(text, type);
	    
	    if ("thelow".equals(location.response.worldName)) {
	    	ModCoreData.inDungeon = false;
	    	return;
	    } else {
		    ModCoreData.inDungeon = true;
		    return;
	    }
	  }
}
