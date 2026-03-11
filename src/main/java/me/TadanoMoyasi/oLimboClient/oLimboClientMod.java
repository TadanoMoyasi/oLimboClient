package me.TadanoMoyasi.oLimboClient;

import com.google.gson.Gson;

import me.TadanoMoyasi.oLimboClient.command.oLimboClientCommand;
import me.TadanoMoyasi.oLimboClient.core.ClientClock;
import me.TadanoMoyasi.oLimboClient.core.api.ChatListener;
import me.TadanoMoyasi.oLimboClient.core.api.NetworkInhibitor;
import me.TadanoMoyasi.oLimboClient.core.config.oLimboClientConfig;
import me.TadanoMoyasi.oLimboClient.core.debug.DebugAPIFixer;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.core.SkillEvents;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDManager;
import me.TadanoMoyasi.oLimboClient.utils.Scheduler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = oLimboClientMod.MOD_ID, version = oLimboClientMod.MOD_VERSION, name = oLimboClientMod.MOD_NAME, clientSideOnly = true , acceptedMinecraftVersions = "[1.8.9]")
public class oLimboClientMod{
    public static final String MOD_ID = "olimboclient";
    public static final String MOD_NAME = "oLimboClient";
    public static final String MOD_VERSION = "1.1";
    
    public static Gson gson = new Gson();
    
    public static final oLimboClientConfig config = oLimboClientConfig.INSTANCE;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config.preload();
        MinecraftForge.EVENT_BUS.register(new NetworkInhibitor());
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new oLimboClientCommand());
    	MinecraftForge.EVENT_BUS.register(new ChatListener());
    	MinecraftForge.EVENT_BUS.register(new SkillEvents());
    	MinecraftForge.EVENT_BUS.register(new DebugAPIFixer());
    	MinecraftForge.EVENT_BUS.register(new ClientClock());
    	MinecraftForge.EVENT_BUS.register(new Scheduler());
    	//MinecraftForge.EVENT_BUS.register(new DebugSoundPlayEvent());
    	//MinecraftForge.EVENT_BUS.register(new DebugAPImessage());
        HUDManager.init();
    }
}
