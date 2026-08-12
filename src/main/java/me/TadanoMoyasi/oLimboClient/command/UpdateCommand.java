package me.TadanoMoyasi.oLimboClient.command;

import static me.TadanoMoyasi.oLimboClient.utils.Format.*;

import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.core.update.AutoUpdater;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class UpdateCommand extends CommandBase {
	boolean updated = false;
    @Override
    public String getCommandName() {
        return "olimboclientupdate";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/olimboclientupdate <url> <name>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0) {
            String downloadUrl = args[0];
            String newName = args[1];
            if (updated) return;
            sender.addChatMessage(new ChatComponentText(ModCoreData.prefix + green + "アップデートのダウンロードを開始します..."));
            System.out.println(ModCoreData.ufprefix + "アップデートのダウンロードを開始します。");
            AutoUpdater.downloadAndScheduleCleanup(downloadUrl, newName, ModCoreData.jarFile);
        }
    }
}
