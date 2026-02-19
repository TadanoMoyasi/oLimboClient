package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class SkillEvents {
	Minecraft mc = Minecraft.getMinecraft();
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		if (mc.thePlayer == null) return;
		
		SkillHandler.onClientTick();
		SkillHandler.attackCheckOnClientTick();
		SkillHandler.aceCheckOnClientTick();
		SkillManager.onClientTick();
		SkillCoolTimeHandler.onClientTick();
		ExecutionSkill.onClientTick();
	}
	
	@SubscribeEvent
	  public void onAttackEntity(AttackEntityEvent event) {
	    SkillHandler.onAttackEntity(event);
	  }
	
	@SubscribeEvent
	  public void onDamage(LivingHurtEvent event) {
		 SkillHandler.onDamage(event);
	  }
	
	 @SubscribeEvent
	  public void onActionBar(ClientChatReceivedEvent event) {
		SkillHandler.onActionBar(event);
	  }
	 
	 @SubscribeEvent
	    public void onPlaySound(PlaySoundEvent event) {
	    	ExecutionSkill.onPlaySound(event);
	    }
	
	@SubscribeEvent
	public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
	    CodexCache.loadFromFile();
	}
	
	@SubscribeEvent
	public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
		CodexCache.saveToFile();
	}
}
