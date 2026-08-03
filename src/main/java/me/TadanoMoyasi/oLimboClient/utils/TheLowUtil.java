package me.TadanoMoyasi.oLimboClient.utils;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.DifficultyInstance;

public class TheLowUtil {
	public static boolean checkedInTheLow = false;
	
	public static void setIsInTheLow(boolean isIn) {
		ModCoreData.isInTheLow = isIn;
		if (isIn) {
			checkFirstLoad();
			checkLastVersion();
		}
	}
	
	private static void checkFirstLoad() {
		if (!oLimboClientMod.config.firstTime) return;
		showInChat("§9=================================================");
		showInChat("§ka§r§b§lWelcome to oLimboClient!§r§ka§r");
	    showInChat("§b§lThe current mod version is §f§o"+ oLimboClientMod.MOD_VERSION);
	    showInChat("§3/lc to open config!");
	    showInChat("§3/lc help to show help!");
	    showInChat("§3if you find any bugs, please notify me!");
	    showInChat("§3discord: TadanoMoyasi");
	    showInChat("§9=================================================");
		oLimboClientMod.config.firstTime = false;
	}
	
	private static void checkLastVersion() {
		if (!oLimboClientMod.MOD_VERSION.equals(oLimboClientMod.config.lastVersion)) {
			oLimboClientMod.config.lastVersion = oLimboClientMod.MOD_VERSION;
		}
	}
	
	public static void showInChat(Object obj) {
		if (Minecraft.getMinecraft().thePlayer == null) return;
	    if (obj == null) {
	      (Minecraft.getMinecraft()).thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText("null"));
	    } else {
	      (Minecraft.getMinecraft()).thePlayer.addChatComponentMessage((IChatComponent)new ChatComponentText(obj.toString()));
	    } 
	  }
	
	public static void sendClickableUrl(String message, String url) {
		IChatComponent chatComponent = new ChatComponentText(message);
		ChatStyle style = new ChatStyle();
		style.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
		style.setChatHoverEvent(new HoverEvent(
				HoverEvent.Action.SHOW_TEXT,
				new ChatComponentText(EnumChatFormatting.YELLOW + "クリックしてリンクを開く: " + url)
		));
		style.setColor(EnumChatFormatting.BLUE);
		style.setUnderlined(true);
		chatComponent.setChatStyle(style);
		if (Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(chatComponent);
        }
	}
	
	public static boolean isInLobby() {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.getCurrentServerData() !=null) return true;
		if (!mc.getCurrentServerData().serverIP.toLowerCase().contains("exim")) return true;
		if (mc.theWorld == null) return true;
		Scoreboard scoreboard = mc.theWorld.getScoreboard();
		ScoreObjective sidebar = scoreboard.getObjectiveInDisplaySlot(1);
		if (sidebar == null) return true;
		return false;
	}
	
	public static float getLocalDifficultyValue() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) return 0.0f;
        BlockPos playerPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        DifficultyInstance difficultyInstance = mc.theWorld.getDifficultyForLocation(playerPos);
        return difficultyInstance.getAdditionalDifficulty();
    }
}
