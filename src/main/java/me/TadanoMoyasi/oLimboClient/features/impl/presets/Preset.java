package me.TadanoMoyasi.oLimboClient.features.impl.presets;

import java.util.ArrayList;
import java.util.List;

public class Preset {
	public String name;
	public String job;
	public List<ItemRequirement> items = new ArrayList<>();
	
	public static class ItemRequirement {
		public String id;
		public String displayName;
		public String nbt;
		
		public ItemRequirement(String id, String displayName, String nbt) {
	        this.id = id;
	        this.displayName = displayName;
	        this.nbt = nbt;
	    }
	}
}
