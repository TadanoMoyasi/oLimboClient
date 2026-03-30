package me.TadanoMoyasi.oLimboClient.core.debug;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class DebugEntityArrow {
	private final List<EntityArrow> pending = new ArrayList<>();

	@SubscribeEvent
	public void onEntitySpawn(EntityJoinWorldEvent event) {
		if (!event.world.isRemote) return;
		
		if (event.entity instanceof EntityArrow) {
	        pending.add((EntityArrow) event.entity);
	    }
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {

	    if (event.phase != TickEvent.Phase.END) return;

	    Minecraft mc = Minecraft.getMinecraft();
	    if (mc.theWorld == null) {
	        pending.clear();
	        return;
	    }

	    Iterator<EntityArrow> it = pending.iterator();

	    while (it.hasNext()) {
	        EntityArrow arrow = it.next();

	        if (arrow.isDead) {
	            it.remove();
	            continue;
	        }

	        Entity shooter = arrow.shootingEntity;

	        if (shooter instanceof EntityPlayer) {
	            EntityPlayer player = (EntityPlayer) shooter;

	            System.out.println("arrow: " + player.getName());

	            it.remove();
	        }
	    }
	}
}
