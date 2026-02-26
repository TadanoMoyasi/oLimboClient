package me.TadanoMoyasi.oLimboClient.core.api;

import io.netty.channel.ChannelPipeline;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class NetworkInhibitor {
	@SubscribeEvent
	public void onClientConnected(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		try {
			ChannelPipeline pipeline = event.manager.channel().pipeline();
			if (pipeline.get("oLimboClient_API_Listener") == null) {
				pipeline.addBefore("packet_handler", "oLimboClient_API_Listener", new PacketHandler());
			}
		} catch (Exception e) {
			System.err.println("[Limbo]APIパケットハンドラーの挿入に失敗しました: " + e.getMessage());
		}
	}
}