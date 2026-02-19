package me.TadanoMoyasi.oLimboClient.hud.core;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.hud.features.EquippedSkillHUD;
import me.TadanoMoyasi.oLimboClient.hud.features.KaihouHUD;
import me.TadanoMoyasi.oLimboClient.hud.features.OthersActiveTimeHUD;
import me.TadanoMoyasi.oLimboClient.hud.features.SkillCastTimeHUD;
import me.TadanoMoyasi.oLimboClient.hud.features.SkillCoolTimeHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HUDManager {

	private static final List<BaseHUD> huds = new ArrayList<>();
	
	public static void init() {
		register(new SkillCastTimeHUD());
		register(new OthersActiveTimeHUD());
		register(new EquippedSkillHUD());
		register(new KaihouHUD());
		register(new SkillCoolTimeHUD());
		MinecraftForge.EVENT_BUS.register(new HUDManager());
		
		Minecraft.getMinecraft().addScheduledTask(() -> {
	        loadPositions();
	    });
	}
	
	public static void register(BaseHUD hud) {
		huds.add(hud);
	}
	
	public static List<BaseHUD> getHuds() {
        return huds;
    }
	
	public static void bringToFront(BaseHUD hud) {
	    huds.remove(hud);
	    huds.add(hud);
	}
	
	public static void savePositions() {
		JsonObject root = new JsonObject();
		
		for (BaseHUD hud : huds) {
			JsonObject obj = new JsonObject();
			obj.addProperty("x", hud.x);
			obj.addProperty("y", hud.y);
			obj.addProperty("scale", hud.scale);
			obj.addProperty("enabled", hud.enabled);
			
			root.add(hud.id, obj);
		}
		
		String json = new Gson().toJson(root);
		
		oLimboClientMod.config.HUD_ALL_POSITIONS = json;
		oLimboClientMod.config.markDirty();
		
		System.out.println("Saving JSON: " + json);
		System.out.println("Config field now: " + oLimboClientMod.config.HUD_ALL_POSITIONS);
		
		/*
		 何故かStringで保存しようとしたわけわからん昨日の夜中から昼にかけての俺の産物。
		 いや、それJsonで良くね？なんでこれに8時間以上も(考える時間含め)使ったんですかね？
		 でも消すのはなんか嫌なので残しておく。まあ色々学べたし良しとしておきます。
		 StringBuilder sb = new StringBuilder();
		
		for (BaseHUD hud : huds) {
			sb.append(hud.id)
				.append(":")
				.append(hud.x)
				.append(",")
				.append(hud.y)
				.append(";");
		}
		
		TheLowCompanionMod.config.HUD_ALL_POSITIONS = sb.toString();
		TheLowCompanionMod.config.markDirty();
		TheLowCompanionMod.config.writeData();*/
	}
	
	// id:x,y;
	public static void loadPositions() {
		System.out.println("HUD JSON at load: " + oLimboClientMod.config.HUD_ALL_POSITIONS);
		String data = oLimboClientMod.config.HUD_ALL_POSITIONS;
		if (data == null || data.isEmpty()) return;
		
		JsonObject root = new JsonParser().parse(data).getAsJsonObject();
		
		for (BaseHUD hud : huds) {
			if (root.has(hud.id)) {
				JsonObject obj = root.getAsJsonObject(hud.id);
				
				if (obj.has("x")) hud.x = obj.get("x").getAsInt();
				if (obj.has("y")) hud.y = obj.get("y").getAsInt();
				if (obj.has("scale")) hud.scale = obj.get("scale").getAsFloat();
				if (obj.has("enabled")) hud.enabled = obj.get("enabled").getAsBoolean();
			}
			System.out.println("HUD JSON at loaded");
		}
		
		/*
		 何列も何列もコメント邪魔くせえなオイ誰やねん残してるやつ。俺か。 
		 String[] entries = data.split(";");
		
		for (String entry : entries) {
			if (entry.isEmpty()) continue;
			
			String[] parts = entry.split(":");
			String id = parts[0];
			
			String[] coords = parts[1].split(",");
			int x = Integer.parseInt(coords[0]);
			int y = Integer.parseInt(coords[1]);
			
			for (BaseHUD hud : huds) {
				if (hud.id.equals(id)) {
					hud.x = x;
					hud.y = y;
					break;
				}
			}
		}*/
	}
	
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Text event) {
		for (BaseHUD hud : huds) {
			if (!hud.enabled) continue;
			
			hud.render();
			
			if (HUDConfigScreen.editMode) {
				Gui.drawRect(
	                    hud.x,
	                    hud.y,
	                    hud.x + hud.width,
	                    hud.y + hud.height,
	                    0x33FFFFFF
	                );
			}
		}
	}
}
