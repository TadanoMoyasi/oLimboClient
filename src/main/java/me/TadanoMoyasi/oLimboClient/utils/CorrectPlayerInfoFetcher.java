package me.TadanoMoyasi.oLimboClient.utils;

import java.util.Collection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public class CorrectPlayerInfoFetcher {
	private static final Minecraft mc = Minecraft.getMinecraft();
	public static NetworkPlayerInfo getInfo(String str) {
		Collection<NetworkPlayerInfo> playerInfoMap = mc.getNetHandler().getPlayerInfoMap();
		NetworkPlayerInfo info = null;
		for (NetworkPlayerInfo pInfo : playerInfoMap) {
	        if (pInfo.getGameProfile().getName().equalsIgnoreCase(str)) {
	            info = pInfo;
	            break;
	        }
	    }
		return info;
	}
}
