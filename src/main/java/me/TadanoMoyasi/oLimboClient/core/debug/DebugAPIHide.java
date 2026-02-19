package me.TadanoMoyasi.oLimboClient.core.debug;

public class DebugAPIHide {
	private static boolean isAPIResponceHide = true;
	
	public static boolean getAPIHide() {
		return isAPIResponceHide;
	}
	
	public static void setAPIHide(boolean is) {
		isAPIResponceHide = is;
	}
}
