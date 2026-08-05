package me.TadanoMoyasi.oLimboClient.core.api;

import io.netty.channel.ChannelPipeline;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class NetworkInhibitor {
	@SubscribeEvent
	public void onClientConnected(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		String serverAddress = event.manager.getRemoteAddress().toString().toLowerCase();
		
		if (!serverAddress.contains("eximradar")) return;
		
		ChannelPipeline pipeline = event.manager.channel().pipeline();
		
		event.manager.channel().eventLoop().submit(() -> {
	        try {
	            if (pipeline.get("oLimboClient_API_Listener") == null) {
	                if (pipeline.get("packet_handler") != null) {
	                    pipeline.addBefore("packet_handler", "oLimboClient_API_Listener", new PacketHandler());
	                    System.out.println(ModCoreData.ufprefix + "Pipeline Joined Before");
	                } else {
	                    pipeline.addLast("oLimboClient_API_Listener", new PacketHandler());
	                    System.out.println(ModCoreData.ufprefix + "Pipeline Joined Last");
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	}
}