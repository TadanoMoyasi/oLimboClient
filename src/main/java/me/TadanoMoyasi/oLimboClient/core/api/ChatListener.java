package me.TadanoMoyasi.oLimboClient.core.api;

import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.features.impl.misc.ChatMentionReminder;
import me.TadanoMoyasi.oLimboClient.features.impl.misc.MatatabiBridge;
import me.TadanoMoyasi.oLimboClient.features.impl.misc.TellReminder;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.ExecutionSkill;
import me.TadanoMoyasi.oLimboClient.utils.JobChanger;
import me.TadanoMoyasi.oLimboClient.utils.TheLowUtil;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatListener {
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onChat(ClientChatReceivedEvent event) {
		if (event.message == null) return;
		String formatted = event.message.getFormattedText();
		String unformatted = event.message.getUnformattedText();
		
		if (formatted.startsWith("§r§a倉庫データを取得しました")) {
			TheLowUtil.setIsInTheLow(true);
			ModCoreData.kaihouUsed = false;
		}
		if (formatted.equals("§r§a正常にプレイヤーデータをロードしました。§r")) {
			APISender.sendAPISubscribeChat();
			APISender.start(300);
		}
		if (unformatted.contains("Welcome to the EXR-NETWORK")) {
			TheLowUtil.setIsInTheLow(false);
			APISender.end();
			ModCoreData.kaihouUsed = false;
		}
		if (unformatted.contains("[武器スキル]") && unformatted.contains("を発動")) {
		    ExecutionSkill.onChat(unformatted);
		}
		if (unformatted.contains("からささやかれました：") || unformatted.contains("whispers to you:")) {
			TellReminder.tellSound();
		}
		if (unformatted.contains(":")) {
			ChatMentionReminder.mentionSound(unformatted);
		}
		if (unformatted.matches("職業「(.+)」を選択しました。")) {
			JobChanger.onChangeJob(unformatted);
		}
		if (unformatted.contains("m@")) {
			MatatabiBridge.onMatatabiChat(unformatted);
		}
		/*if (unformatted.startsWith("$api")) {
			String[] split = unformatted.split(" ", 2);
			if (split.length == 2) {
				APIListener.distribute(split[1]);
			}
		}*/
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onAPIChat(ClientChatReceivedEvent event) {
		String unformatted = event.message.getUnformattedText();
		if (unformatted.startsWith("$api")) {
			event.setCanceled(true);
		}
	}
}
