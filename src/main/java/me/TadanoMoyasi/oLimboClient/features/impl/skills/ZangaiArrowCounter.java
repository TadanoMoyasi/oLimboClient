package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.utils.Scheduler;
import me.TadanoMoyasi.oLimboClient.utils.TheLowUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class ZangaiArrowCounter {
	private static class PendingArrow {
	    EntityArrow arrow;
	    int ticks;

	    PendingArrow(EntityArrow arrow) {
	        this.arrow = arrow;
	        this.ticks = 0;
	    }
	}

	private static final List<PendingArrow> pending = new ArrayList<>();
	private static boolean inZangai = false;
	private static int zangaiArrows = 0;
	
	public static void onZangai() {
		inZangai = true;
		Scheduler.setTimeout(() -> {
			inZangai = false;
			Scheduler.setTimeout(() -> {
				TheLowUtil.showInChat(ModCoreData.prefix + "撃った本数: " + zangaiArrows);
				zangaiArrows = 0;
			}, 11);
		}, 59);
	}

	public static void onEntitySpawn(EntityJoinWorldEvent event) {
		if (!inZangai) return;
		if (!oLimboClientMod.config.zangaiArrowCount) return;
		if (!event.world.isRemote) return;
		if (event.entity instanceof EntityArrow) {
			pending.add(new PendingArrow((EntityArrow) event.entity));
	    }
	}
	
	public static void onTick() {
	    Minecraft mc = Minecraft.getMinecraft();
	    if (mc.theWorld == null) {
	        pending.clear();
	        return;
	    }
	    Iterator<PendingArrow> it = pending.iterator();
	    while (it.hasNext()) {
	    	PendingArrow pa = it.next();
	    	pa.ticks++;
	    	EntityArrow arrow = pa.arrow;
	        Entity shooter = arrow.shootingEntity;
	        if (shooter instanceof EntityPlayer) {
	        	if (shooter == mc.thePlayer) {
	                zangaiArrows++;
	            }
	            it.remove();
	            continue;
	        }
	        if (pa.ticks > 10) {
	            it.remove();
	        }
	    }
	}
}
