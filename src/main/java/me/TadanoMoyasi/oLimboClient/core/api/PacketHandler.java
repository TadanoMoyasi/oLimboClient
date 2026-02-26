package me.TadanoMoyasi.oLimboClient.core.api;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

public class PacketHandler extends SimpleChannelInboundHandler<S02PacketChat> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, S02PacketChat packet) throws Exception {
		try {
			IChatComponent chatComponent = packet.getChatComponent();
			if (chatComponent != null) {
				String unformatted = chatComponent.getUnformattedText();

				if (unformatted != null && unformatted.startsWith("$api")) {
					String[] split = unformatted.split(" ", 2);
					if (split.length == 2) {
						APIListener.distribute(split[1]);
					}
				}
			}
		} finally {
			ctx.fireChannelRead(packet);
		}
	}
}