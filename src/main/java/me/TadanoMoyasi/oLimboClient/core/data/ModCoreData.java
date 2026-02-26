package me.TadanoMoyasi.oLimboClient.core.data;

import java.time.LocalDateTime;
import java.util.Collection;

import net.minecraft.client.network.NetworkPlayerInfo;

public class ModCoreData {
	public static String prefix = "§8[§9Limbo§8]§r ";
	
	public static boolean isInTheLow = false;
	
	public static String currentJob = "";
	
	public static boolean kaihouUsed = false;
	
	  public static LocalDateTime loggedTime;
	  
	  public static LocalDateTime lastPlayerRequest;
	  
	  public static Collection<NetworkPlayerInfo> playerInfos;
	  
	  public static LocalDateTime whenArmorBecamePinch;
	  
	  public static boolean[] isArmorPinch = new boolean[] { false, false, false, false };
}
