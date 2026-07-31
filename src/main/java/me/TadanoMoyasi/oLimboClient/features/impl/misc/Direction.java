package me.TadanoMoyasi.oLimboClient.features.impl.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class Direction {
	private static final String[] DIRECTIONS_8 = {"S", "SW", "W", "NW", "N", "NE", "E", "SE"};

	public static String get8WayFacing() {
	    if (Minecraft.getMinecraft().thePlayer == null) return "N/A";

	    float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
	    int index = MathHelper.floor_double((double)(yaw * 8.0F / 360.0F) + 0.5D) & 7; //見てくださいこの美しいコード

	    return DIRECTIONS_8[index];
	}
}
