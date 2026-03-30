package me.TadanoMoyasi.oLimboClient.features.impl.misc.chatCommands.core;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;

public class runCommand {
	public static boolean handleCommandExecution() {
		return true;
	}
	
	public static void run(String msg) {
        String[] parts = msg.split(":", 2);
        String prefix = oLimboClientMod.config.chatCommandPrefix == "" ? "!" : oLimboClientMod.config.chatCommandPrefix;
        if (!parts[1].trim().startsWith(prefix)) return;
        String com = parts[1].substring(1);
        switch (com) {
        	case "":
        }
	}
}
