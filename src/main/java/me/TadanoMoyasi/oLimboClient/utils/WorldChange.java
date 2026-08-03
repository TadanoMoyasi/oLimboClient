package me.TadanoMoyasi.oLimboClient.utils;

import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldChange {
	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.getCurrentServerData() !=null) return;
		if (!mc.getCurrentServerData().serverIP.toLowerCase().contains("exim")) return;
    	if (event.entity == null || event.entity != mc.thePlayer) return;
    	if (!CooldownManager.checkAndReset("worldChange", 5));
    	onJoin();
    	Scheduler.setTimeout(() -> {onJoin();}, 20);
    }
	
	public void onJoin() {
		float localDiff = TheLowUtil.getLocalDifficultyValue();
		System.out.println("loc" + localDiff);
		if (localDiff == 0.75) {
			ModCoreData.isInTheLow = true;
			ModCoreData.inDungeon = true;
		} else if (localDiff == 0.0) {
			ModCoreData.isInTheLow = false;
			ModCoreData.inDungeon = false;
			ModCoreData.kaihouUsed = false;
		}
	}
}
