package me.TadanoMoyasi.oLimboClient.core.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DebugAPIFixer {
    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onChatReceived(ClientChatReceivedEvent event) {
        String unformatted = event.message.getUnformattedText();
        if (unformatted.startsWith("$api")) {
        	if (!DebugAPIHide.getAPIHide()) {
                final IChatComponent message = event.message;
                
                new Thread(() -> {
                    try {
                        Thread.sleep(1);
                        Minecraft.getMinecraft().addScheduledTask(() -> {
                            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(message);
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
}