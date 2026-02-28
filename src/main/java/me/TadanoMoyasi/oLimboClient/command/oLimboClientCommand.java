package me.TadanoMoyasi.oLimboClient.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.api.APISender;
import me.TadanoMoyasi.oLimboClient.core.config.oLimboClientConfig;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.core.debug.DebugAPIHide;
import me.TadanoMoyasi.oLimboClient.core.debug.DebugPlaySound;
import me.TadanoMoyasi.oLimboClient.core.debug.DebugSoundPlayEvent;
import me.TadanoMoyasi.oLimboClient.features.impl.presets.PresetManager;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.CodexCache;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.PriestManager;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDConfigScreen;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDManager;
import me.TadanoMoyasi.oLimboClient.utils.JobChanger;
import me.TadanoMoyasi.oLimboClient.utils.OthersJobManager;
import me.TadanoMoyasi.oLimboClient.utils.UUIDFetcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
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
		return "/olimboclient <config/hud/help/codex/preset>";
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
		case "settings":
		case "setting":
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
		case "huds":
			if (args.length == 1) {
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
			} else if (args.length == 2) {
				if ("reset".equals(args[1])) {
					HUDManager.resetPosition();
				}
			}
			break;
		case "priest":
		case "puri": {
			if (args.length < 2) {
				send(sender,ModCoreData.prefix +  "/lc priest <reset>");
				break;
			}
			if ("reset".equals(args[1])) {
				PriestManager.clearPriests();
				send(sender, ModCoreData.prefix + "プリーストをリセットしました。");
			}
		}
			break;
		case "codex":
		case "code":
			if (args.length < 2) {
				send(sender,ModCoreData.prefix +  "MCIDがありません");
				break;
			}
			if (args.length < 3) {
				String mcid = args[1];
				new Thread(new Runnable() {
		            @Override
		            public void run() {
		                try {
		                    UUID uuid = UUIDFetcher.getPlayerDataName(mcid).uuid;
		                    if (uuid == null) {
		                        send(sender, ModCoreData.prefix + "プレイヤーが見つかりませんでした。");
		                        return;
		                    }

		                    int ticks = CodexCache.get(uuid).ticks;
		                    float seconds = ticks / 20.0f;
		                    if (ticks == 0) {
		                        send(sender, ModCoreData.prefix + "キャッシュされた値が存在しません。");
		                    } else {
		                        send(sender, ModCoreData.prefix + mcid + "のCodexの秒数: " + String.format("%.2f", seconds) + "秒(" + ticks + "tick)");
		                    }
		                } catch (Exception e) {
		                    e.printStackTrace();
		                    send(sender, ModCoreData.prefix + "エラーが発生しました。");
		                }
		            }
		        }).start();
		        break;
			}
			String mcid = args[1];
			String dmg = args[2];
			try {
		        CodexCache.changeCache(mcid, Double.parseDouble(dmg));
		    } catch (NumberFormatException e) {
		        send(sender, ModCoreData.prefix + "ダメージ値が不正です。数字を入力してください。");
		    }
			break;
		case "presets":
		case "preset": {
			presetCommands(sender, args);
		}
			break;
		case "resendapi":
			APISender.sendPlayerAPIChat();
			APISender.sendPlayerAPIChat();
			send(sender,ModCoreData.prefix + "APIを再送信しました。");
			break;
		case "help":
			send(sender,
			        "§9======o§bLimbo§9Client  Help======",
			        "§b§lThe current mod version is §f§o" + oLimboClientMod.MOD_VERSION,
			        "§3/lc config§7- Configを表示",
			        "§3/lc hud §7- HUDConfigを表示",
			        "§3/lc hud reset §7-§c 全て§7のHUDの位置をリセット(画面外とかに吹き飛んだ時用)",
			        "§3/lc priest reset §7-プリーストをリセットします。",
			        "§3/lc codex <MCID> §7- Codexのキャッシュされている秒数を表示します。",
			        "§3/lc codex <MCID> <Codexの個体値> §7- Codexのキャッシュされている値を変更します。",
			        "§3/lc preset help§7- プリセットの説明を表示します。",
			        "§3/lc resendapi§7- APIをもう一度送信します。"
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
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "config", "hud", "priest", "codex", "preset", "help");
        }
        if (args.length == 2) {
        	if (args[0].equalsIgnoreCase("codex") || args[0].equalsIgnoreCase("code")) {
                try {
                    Collection<NetworkPlayerInfo> playerInfoMap = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
                    List<String> playerNames = new ArrayList<>();
                    
                    for (NetworkPlayerInfo info : playerInfoMap) {
                        playerNames.add(info.getGameProfile().getName());
                    }
                    
                    return getListOfStringsMatchingLastWord(args, playerNames.toArray(new String[0]));
                } catch (Exception e) {
                    return null;
                }
            }
            
            if (args[0].equalsIgnoreCase("preset")) {
                return getListOfStringsMatchingLastWord(args, "help", "save", "list", "delete", "load", "unload");
            }
        }
        return null;
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
	
	private void presetCommands(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			sendPresetHelp(sender);
			return;
		}
		String sub = args[1];
		switch(sub.toLowerCase()) {
		case "help":
			sendPresetHelp(sender);
			break;
		case "load": {
			if (args.length < 3) {
				send(sender, ModCoreData.prefix + "Presetの名前がありません。");
				return;
			}
			String name = args[2];
			PresetManager.setActivePreset(name);
		}
			break;
		case "unload": {
			PresetManager.unloadPreset();
		}
		case "save": {
			if (args.length < 3) {
				send(sender, ModCoreData.prefix + "Presetの名前がありません。");
				return;
			}
			String name = args[2];
			PresetManager.savePreset(name, JobChanger.getCurrentJob());
		}
			break;
		case "list" :
			PresetManager.sendPresetList();
			break;
		case "delete":{
			if (args.length < 3) {
				send(sender, ModCoreData.prefix + "Presetの名前がありません。");
				return;
			}
			String name = args[2];
			PresetManager.deletePreset(name);
		}
			break;
		}
	}
	
	private void sendPresetHelp(ICommandSender sender) {
		send(sender,
				"§9======o§bLimbo§9Client Preset  Help======",
				"§3/lc preset help §7- プリセットの説明を表示します。",
				"§3/lc preset load <Presetの名前> §7- プリセットをロードします。",
				"§3/lc preset unload§7- プリセットをアンロードします。",
		        "§3/lc preset save <Presetの名前> §7- 今持っているアイテム、防具、職業を保存します。",
		        "§3/lc preset list §7- 保存されているプリセットを表示します。",
		        "§3/lc preset delete <Presetの名前> §7- 保存されているプリセットを削除します。"
				);
	}
	
	private void DebugCommands(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			send(sender, "sound boolean, api boolean, playsound string float, job string");
			return;
		}
		String sub = args[1];
		switch(sub.toLowerCase()) {
			case "sound":
			case"soundevent": {
				boolean deb = Boolean.parseBoolean(args[2]);
				DebugSoundPlayEvent.toggleDebugPlaySound(deb);
				send(sender, "soundevent:" + deb);
				break;
			}
			case "api": {
				boolean deb = Boolean.parseBoolean(args[2]);
				DebugAPIHide.setAPIHide(deb);
				send(sender, "api:" + deb);
				break;
			}
			case"playsound": {
				String dev = args[2];
				float def = Float.parseFloat(args[3]);
				DebugPlaySound.playSound(dev, def);
				break;
			}
			case "job": {
				String dev = args[2];
				send(sender, ModCoreData.prefix + OthersJobManager .getJob(dev));
			}
		}
	}
}


