package me.TadanoMoyasi.oLimboClient.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.config.oLimboClientConfig;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.core.debug.DebugAPIHide;
import me.TadanoMoyasi.oLimboClient.core.debug.DebugPlaySound;
import me.TadanoMoyasi.oLimboClient.core.debug.DebugSoundPlayEvent;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.CodexCache;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

public class oLimboClientCommand extends CommandBase {
	@Override
	public String getCommandName() {
		return "olimboclient";
	}
	
	@Override
	public List<String> getCommandAliases() {
		return Arrays.asList("olc", "lc","olimbo");
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/olimboclient <config/hud/help/codex>";
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length == 0) {
			new Thread(() -> {
		        try {
		            // 100ms待機。なんかしらんけどこれしないとGUI開かん。開くけど一瞬で消える？みたいな
		            Thread.sleep(100);
		            
		            Minecraft.getMinecraft().addScheduledTask(() -> {
		                Minecraft.getMinecraft().displayGuiScreen(
		                    oLimboClientConfig.INSTANCE.gui()
		                );
		            });
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		    }).start();
			return;
		}
		String sub = args[0];
		
		switch (sub.toLowerCase()) {
		case "config":
		case "openconfig":
			new Thread(() -> {
		        try {
		            Thread.sleep(100);
		            
		            Minecraft.getMinecraft().addScheduledTask(() -> {
		                Minecraft.getMinecraft().displayGuiScreen(oLimboClientConfig.INSTANCE.gui());
		            });
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		    }).start();
		    break;
		case "hud":
			new Thread(() -> {
		        try {
		            Thread.sleep(100);
		            
		            Minecraft.getMinecraft().addScheduledTask(() -> {
		            	Minecraft.getMinecraft().displayGuiScreen(new HUDConfigScreen());
		            });
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		    }).start();
			break;
		case "codex":
			String mcid = args[1];
			String dmg = args[2];
			if (mcid == null || dmg == null) {
				send(sender,ModCoreData.prefix +  "MCIDか個体値がありません");
				break;
			}
			CodexCache.changeCache(mcid, Double.parseDouble(dmg));
			break;
		case "help":
			send(sender,
			        "§9======o§bLimbo§9Client  Help======",
			        "§b§lThe current mod version is §f§o" + oLimboClientMod.MOD_VERSION,
			        "§3/lc config§7- open config",
			        "§3/lc hud §7- open hud config",
			        "§3/lc codex <MCID> <Codexの個体値> §7- codexのキャッシュされている値を変更します。"
			);
			break;
		case "debug":
			DebugCommands(sender, args);
			break;
		default:
			new Thread(() -> {
		        try {
		            Thread.sleep(100);
		            
		            Minecraft.getMinecraft().addScheduledTask(() -> {
		                Minecraft.getMinecraft().displayGuiScreen(oLimboClientConfig.INSTANCE.gui());
		            });
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		    }).start();
		}
	}
	
	@Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("config");
            completions.add("hud");
            completions.add("codex");
            completions.add("help");
        }
        return completions;
    }
	
	@Override
	public int getRequiredPermissionLevel() {
	    return 0;
	  }
	
	private void send(ICommandSender sender, String... lines) {
	    for (String line : lines) {
	        sender.addChatMessage(new ChatComponentText(line));
	    }
	}
	
	private void DebugCommands(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			send(sender, "sound boolean, api boolean, playsound string float");
		}
		String sub = args[1];
		String dev = args[2];
		boolean deb = Boolean.parseBoolean(args[2]);
		float def = Float.parseFloat(args[3]);
		switch(sub.toLowerCase()) {
		case "sound":
		case"soundevent":
			DebugSoundPlayEvent.toggleDebugPlaySound(deb);
			send(sender, "soundevent:" + deb);
			break;
		case "api":
			DebugAPIHide.setAPIHide(deb);
			send(sender, "api:" + deb);
			break;
		case"playsound":
			DebugPlaySound.playSound(dev, def);
			break;
		}
	}
}


