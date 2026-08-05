package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CodexSkillManager {
	public static boolean stre = false;
	public static boolean rege = false;
	public static boolean regi = false;
	
	private static final Minecraft mc = Minecraft.getMinecraft();
	/*
	 * wskill188_shintaku_buffs_id
	 * 0 false false false
	 * 1 false false true
	 * 2 false true false
	 * 3 false true true
	 * 4 true false false
	 * 5 true false true
	 * 6 true true flase 
	 * 7 true true ture
	 * */
	
	public static void onHoldCodex() {
		if (mc.thePlayer == null) return;
		if (mc.thePlayer.getHeldItem() == null) return;
		if (!mc.thePlayer.getHeldItem().getDisplayName().contains("Codex")) return;
		ItemStack stack = mc.thePlayer.getHeldItem();
		if (stack == null || !stack.hasTagCompound()) return;
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) return;
		if (!nbt.hasKey("wskill188_shintaku_buffs_id")) return;
		int num = nbt.getInteger("wskill188_shintaku_buffs_id");
		stre = (num & 4) != 0; // 3ビット目 (4)
		rege = (num & 2) != 0; // 2ビット目 (2)
		regi = (num & 1) != 0; // 1ビット目 (1)
		//これ思いついたのガチのマジで気持ちいい。
	}
	
	public static String FormatCodex() {
		List<String> list = new ArrayList<>();
	    if (stre) list.add("攻");
	    if (rege) list.add("癒");
	    if (regi) list.add("鋼");
	    if (list.isEmpty()) return "";
	    return "Codex: " + String.join(", ", list);
	}
}
