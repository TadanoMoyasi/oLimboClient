package me.TadanoMoyasi.oLimboClient.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JobChanger {
	private static String currentJob = "";
	private static Pattern pattern = Pattern.compile("職業「(.+)」を選択しました。");
	
	public static void onChangeJob(String msg) {
		Matcher matcher = pattern.matcher(msg);
		if (matcher.find()) {
			currentJob = matcher.group(1);
		}
	}
	
	public static String getCurrentJob() {
		return currentJob;
	}
	
	public static void setCurrentJob(String job) {
		currentJob = job;
	}
}
