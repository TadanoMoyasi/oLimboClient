package me.TadanoMoyasi.oLimboClient.core.debug;

import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DebugAPImessage {
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onChat(ClientChatReceivedEvent event) {
	    String raw = event.message.getFormattedText();
	    System.out.println("Type: " + event.type);
	    System.out.println("Class: " + event.message.getClass().getName());
	    System.out.println("JSON: " + IChatComponent.Serializer.componentToJson(event.message));
	    if (raw.trim().equals("§r") || raw.trim().isEmpty()) {
	        System.out.println("[oLimbo-Debug] only have  empty or §r");
	        
	        for (IChatComponent sibling : event.message.getSiblings()) {
	            System.out.println("  - Sibling Part: " + sibling.getUnformattedText());
	            if (sibling.getChatStyle().getChatHoverEvent() != null) {
	                System.out.println("  - Hover Content: " + sibling.getChatStyle().getChatHoverEvent().getValue().getUnformattedText());
	            }
	        }
	    } else {
	        System.out.println("[oLimbo-Debug] Received: " + raw);
	    }
	}
}
