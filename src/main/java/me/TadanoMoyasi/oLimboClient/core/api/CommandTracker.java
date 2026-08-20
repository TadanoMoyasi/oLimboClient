package me.TadanoMoyasi.oLimboClient.core.api;

import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.utils.Scheduler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommandTracker {
	Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
    public void onClientChat(CommandEvent event) {
		if (mc.thePlayer == null) return;
        String commandName = event.command.getCommandName();
        String[] args = event.parameters;
        String joinedArgs = String.join(" ", args);
        String fullCommand = commandName + (joinedArgs.isEmpty() ? "" : " " + joinedArgs);
        if (fullCommand.equalsIgnoreCase("thelow_api location")) {
        	ModCoreData.APIChatSendedLocation = true;
        	Scheduler.setTimeout(() -> {
        		ModCoreData.APIChatSendedLocation = false;
        	}, 10);
        } else if (fullCommand.equalsIgnoreCase("thelow_api subscribe SKILL_COOLTIME")) {
        	ModCoreData.APIChatSendedSkill = true;
        	Scheduler.setTimeout(() -> {
        		ModCoreData.APIChatSendedSkill = false;
        	}, 10);
        }
    }
}
