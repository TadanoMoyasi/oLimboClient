package me.TadanoMoyasi.oLimboClient.core.debug;

import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DebugSoundPlayEvent {
	private static boolean toggle = false;
	
	public static void toggleDebugPlaySound(boolean a) {
		toggle = a;
		System.out.println("[Debug] SoundToggle:" + toggle);
	}
	
	@SubscribeEvent
	public void onPlaySound(PlaySoundEvent event) {
		if (!toggle) return;
	    if (event.name != null) {
	        //System.out.println("Sound: " + event.name);
	    	if (!"random.orb".equals(event.name)) return;

	        System.out.println(
	            "Pitch: " + event.sound.getPitch() +
	            " Volume: " + event.sound.getVolume()
	        );
	    }
	}
}
