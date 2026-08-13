package me.TadanoMoyasi.oLimboClient;

import java.io.File;

import com.google.gson.Gson;

import me.TadanoMoyasi.oLimboClient.command.UpdateCommand;
import me.TadanoMoyasi.oLimboClient.command.oLimboClientCommand;
import me.TadanoMoyasi.oLimboClient.core.ClientClock;
import me.TadanoMoyasi.oLimboClient.core.api.APISender;
import me.TadanoMoyasi.oLimboClient.core.api.ChatListener;
import me.TadanoMoyasi.oLimboClient.core.api.NetworkInhibitor;
import me.TadanoMoyasi.oLimboClient.core.config.ActiveSkillColorConfig;
import me.TadanoMoyasi.oLimboClient.core.config.oLimboClientConfig;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.core.debug.DebugAPIFixer;
import me.TadanoMoyasi.oLimboClient.core.update.UpdateChecker;
import me.TadanoMoyasi.oLimboClient.features.impl.misc.wiki.Wiki;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.Manager.SkillJsonManager;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.core.SkillEvents;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDManager;
import me.TadanoMoyasi.oLimboClient.utils.CooldownManager;
import me.TadanoMoyasi.oLimboClient.utils.CustomItemName;
import me.TadanoMoyasi.oLimboClient.utils.Scheduler;
import me.TadanoMoyasi.oLimboClient.utils.WorldChange;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = oLimboClientMod.MOD_ID, version = oLimboClientMod.MOD_VERSION, name = oLimboClientMod.MOD_NAME, clientSideOnly = true , acceptedMinecraftVersions = "[1.8.9]")
public class oLimboClientMod{
    public static final String MOD_ID = "olimboclient";
    public static final String MOD_NAME = "oLimboClient";
    public static final String MOD_VERSION = "1.2";
    
    public static final Gson gson = new Gson();
    
    public static final oLimboClientConfig config = oLimboClientConfig.INSTANCE;
    public static final ActiveSkillColorConfig activeSkillConfig = ActiveSkillColorConfig.INSTANCE;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	ModCoreData.jarFile = event.getSourceFile();
        config.preload();
        activeSkillConfig.preload();
        File configDir = new File(event.getModConfigurationDirectory(), "olimboclient");
        SkillJsonManager.init(configDir);
        Wiki.init(configDir);
        CustomItemName.init(configDir);
        MinecraftForge.EVENT_BUS.register(new NetworkInhibitor());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
	        System.out.println(ModCoreData.ufprefix + "ゲームが終了したためデータを保存します...");
	        CustomItemName.end(configDir);
	    }));
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new oLimboClientCommand());
        ClientCommandHandler.instance.registerCommand(new UpdateCommand());
    	MinecraftForge.EVENT_BUS.register(new ChatListener());
    	MinecraftForge.EVENT_BUS.register(new SkillEvents());
    	MinecraftForge.EVENT_BUS.register(new DebugAPIFixer());
    	MinecraftForge.EVENT_BUS.register(new APISender());
    	MinecraftForge.EVENT_BUS.register(new ClientClock());
    	MinecraftForge.EVENT_BUS.register(new Scheduler());
    	MinecraftForge.EVENT_BUS.register(new CooldownManager());
    	MinecraftForge.EVENT_BUS.register(new WorldChange());
    	MinecraftForge.EVENT_BUS.register(new UpdateChecker());
    	MinecraftForge.EVENT_BUS.register(new CustomItemName());
    	//MinecraftForge.EVENT_BUS.register(new DebugSoundPlayEvent());
    	//MinecraftForge.EVENT_BUS.register(new DebugAPImessage());
    	//MinecraftForge.EVENT_BUS.register(new DebugEntityArrow());
        HUDManager.init();
    }
}
