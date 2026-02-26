package me.TadanoMoyasi.oLimboClient.utils;

import java.util.UUID;

public class PlayerData {
	public final UUID uuid;
	public final String mcid;
	
	public PlayerData(UUID uuid, String name) {
		this.uuid = uuid;
		this.mcid = name;
	}
}
