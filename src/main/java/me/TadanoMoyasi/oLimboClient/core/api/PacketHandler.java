package me.TadanoMoyasi.oLimboClient.core.api;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.TadanoMoyasi.oLimboClient.features.impl.misc.wiki.Wiki;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.IChatComponent;

public class PacketHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
		try {
			if (packet instanceof S02PacketChat) {
				S02PacketChat packetChat = (S02PacketChat) packet;
				IChatComponent chatComponent = packetChat.getChatComponent();
				if (chatComponent != null) {
					String unformatted = chatComponent.getUnformattedText();

					if (unformatted != null && unformatted.startsWith("$api")) {
						String[] split = unformatted.split(" ", 2);
						if (split.length == 2) {
							Minecraft.getMinecraft().addScheduledTask(() -> {
							    APIListener.distribute(split[1]);
							});
						}
					}
				}
			} else if (packet instanceof S08PacketPlayerPosLook) {
				//S08PacketPlayerPosLook packetPlayerPosLook = (S08PacketPlayerPosLook) packet;
				Minecraft.getMinecraft().addScheduledTask(() -> {
					Wiki.onTP();
				});
			}
		} finally {
			ctx.fireChannelRead(packet);
		}
	}
}