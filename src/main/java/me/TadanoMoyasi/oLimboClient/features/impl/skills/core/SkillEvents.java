package me.TadanoMoyasi.oLimboClient.features.impl.skills.core;

import me.TadanoMoyasi.oLimboClient.features.impl.presets.PresetManager;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.ExecutionSkill;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.JerezStack;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.PriestManager;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.SkillCoolTimeHandler;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.SkillHandler;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.SkillManager;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.ZangaiArrowCounter;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.codex.CodexCache;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.codex.CodexHighlight;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.codex.CodexRangeDisplay;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.codex.CodexSkillManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
		CodexCache.onTick();
		PriestManager.onTick();
		PresetManager.onTick();
		JerezStack.onTick();
		ZangaiArrowCounter.onTick();
		CodexSkillManager.onHoldCodex();
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
    public void onRenderWorld(RenderWorldLastEvent event) {
		CodexRangeDisplay.onRenderWorld(event);
		//PlayerOutlineRender.onRenderWorld(event);
    }
	
	@SubscribeEvent
	public void onEntitySpawn(EntityJoinWorldEvent event) {
		ZangaiArrowCounter.onEntitySpawn(event);
	}
	
	@SubscribeEvent
	public void onRenderGui(GuiScreenEvent.DrawScreenEvent.Post event) {
		CodexHighlight.onRenderGui();
	}
	
	@SubscribeEvent
	public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		CodexCache.loadFromFile();
		CodexCache.start();
	}
	
	@SubscribeEvent
	public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
		CodexCache.saveToFile();
	}
	
	/*@SubscribeEvent
	public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
		PlayerOutlineRender.onRenderPlayerPre(event);
	}
	
	@SubscribeEvent
	public void onRenderPlayerPost(RenderPlayerEvent.Post event) {
		PlayerOutlineRender.onRenderPlayerPost(event);
	}*/
	
	/*@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		PlayerOutlineRender.onRenderWorldLast(event);
	}*/
}
