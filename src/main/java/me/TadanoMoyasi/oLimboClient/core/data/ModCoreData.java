package me.TadanoMoyasi.oLimboClient.core.data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.TadanoMoyasi.oLimboClient.core.api.types.DungeonData;
import me.TadanoMoyasi.oLimboClient.core.api.types.Player_Status;
import net.minecraft.client.network.NetworkPlayerInfo;

public class ModCoreData {
	public static String prefix = "§8[§9Limbo§8]§r";
	
	public static boolean isInTheLow = false;
	
	public static String currentJob = "";
	
	public static boolean kaihouUsed = false;
	
	  public static LocalDateTime loggedTime;
	  
	  public static LocalDateTime lastPlayerRequest;
	  
	  public static DungeonData[] dungeonData = null;
	  
	  public static Map<String, Player_Status> player_status = new ConcurrentHashMap<String, Player_Status>();
	  	  
	  public static String[] skillPlace = new String[6];
	  
	  public static Collection<NetworkPlayerInfo> playerInfos;
	  
	  public static LocalDateTime whenArmorBecamePinch;
	  
	  public static boolean[] isArmorPinch = new boolean[] { false, false, false, false };
}
