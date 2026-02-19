package me.TadanoMoyasi.oLimboClient.core.api;

import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.core.debug.DebugAPIHide;
import me.TadanoMoyasi.oLimboClient.features.impl.misc.ChatMentionReminder;
import me.TadanoMoyasi.oLimboClient.features.impl.misc.TellReminder;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.ExecutionSkill;
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
			TheLowUtil.sendAPISubscribeChat();
		}
		if (unformatted.contains("Welcome to the EXR-NETWORK")) {
			TheLowUtil.setIsInTheLow(false);
			ModCoreData.kaihouUsed = false;
		}
		if (unformatted.matches("\\[武器スキル\\]\\s(?<name>.+)が(?<skill>.+)を発動")) {
			ExecutionSkill.onChat(unformatted);
		}
		if (unformatted.contains("からささやかれました：") || unformatted.contains("whispers to you:")) {
			TellReminder.tellSound();
		}
		if (unformatted.matches("(\\[.+\\]\\s)?.+(\\s\\[.+\\])?\\s:\\s(?<message>.+)")) {
			ChatMentionReminder.mentionSound(unformatted);
		}
		
		if (unformatted.startsWith("$api")) {
			String[] split = unformatted.split(" ", 2);
			if (split.length == 2) {
				APIListener.distribute(split[1]);
			}
			if (DebugAPIHide.getAPIHide()) {
				event.setCanceled(true);
			}
		}
	}
}
