package me.TadanoMoyasi.oLimboClient.utils;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class TheLowUtil {
	public static boolean checkedInTheLow = false;
	
	public static void setIsInTheLow(boolean isIn) {
		ModCoreData.isInTheLow = isIn;
		if (isIn) {
			checkFirstLoad();
		}
	}
	
	private static void checkFirstLoad() {
		if (!oLimboClientMod.config.firstTime) return;
		showInChat("§9=================================================");
		showInChat("§ka§r§b§lWelcome to oLimboClient!§r§ka§r");
	    showInChat("§b§lThe current mod version is §f§o"+ oLimboClientMod.MOD_VERSION);
	    showInChat("§3/tc to open config!");
	    showInChat("§3/tc help to show help!");
	    showInChat("§3if you find any bugs, please notify me!");
	    showInChat("§3discord: TadanoMoyasi");
	    showInChat("§9=================================================");
		oLimboClientMod.config.firstTime = false;
	}
	
	private static void showInChat(Object obj) {
	    if (obj == null) {
	      (Minecraft.getMinecraft()).thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText("null"));
	    } else {
	      (Minecraft.getMinecraft()).thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText(obj.toString()));
	    } 
	  }
	
	public static void sendDungeonAPIChat() {
	    (Minecraft.getMinecraft()).thePlayer.sendChatMessage("/thelow_api dungeon");
	  }
	  public static void sendPlayerAPIChat() {
	    (Minecraft.getMinecraft()).thePlayer.sendChatMessage("/thelow_api player");
	  }
	  
	  public static void sendAPISubscribeChat() {
	    (Minecraft.getMinecraft()).thePlayer.sendChatMessage("/thelow_api subscribe SKILL_COOLTIME");
	  }
}
