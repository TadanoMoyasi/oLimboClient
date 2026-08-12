package me.TadanoMoyasi.oLimboClient.utils;

import net.minecraft.util.EnumChatFormatting;

public class Format {
	//import static me.TadanoMoyasi.oLimboClient.utils.Format.*;
	
	// 装飾コード
    public static final String RESET = EnumChatFormatting.RESET.toString();
    public static final String BOLD = EnumChatFormatting.BOLD.toString();
    public static final String ITALIC = EnumChatFormatting.ITALIC.toString();
    public static final String UNDERLINE = EnumChatFormatting.UNDERLINE.toString();

    // カラーコード
    public static final String black = EnumChatFormatting.BLACK.toString();
    public static final String darkBlue = EnumChatFormatting.DARK_BLUE.toString();
    public static final String darkGreen = EnumChatFormatting.DARK_GREEN.toString();
    public static final String darkAqua = EnumChatFormatting.DARK_AQUA.toString();
    public static final String darkRed = EnumChatFormatting.DARK_RED.toString();
    public static final String darkPurple = EnumChatFormatting.DARK_PURPLE.toString();
    public static final String gold = EnumChatFormatting.GOLD.toString();
    public static final String gray = EnumChatFormatting.GRAY.toString();
    public static final String darkGray = EnumChatFormatting.DARK_GRAY.toString();
    public static final String blue = EnumChatFormatting.BLUE.toString();
    public static final String green = EnumChatFormatting.GREEN.toString();
    public static final String aqua = EnumChatFormatting.AQUA.toString();
    public static final String red = EnumChatFormatting.RED.toString();
    public static final String lightPurple = EnumChatFormatting.LIGHT_PURPLE.toString();
    public static final String yellow = EnumChatFormatting.YELLOW.toString();
    public static final String white = EnumChatFormatting.WHITE.toString();
    
    public static String color(String text) {
        if (text == null) return "";
        return text.replaceAll("&(?=[0-9a-fk-or])", "\u00A7");
    }
}
