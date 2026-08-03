package me.TadanoMoyasi.oLimboClient.utils;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CooldownManager {
    private static final Map<String, Integer> cooldowns = new HashMap<>();

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        for (String key : cooldowns.keySet()) {
            cooldowns.put(key, cooldowns.get(key) + 1);
        }
    }

    /**
     * 指定したキーのクールダウンが終わっているか判定、終わっていればリセットして true 
     * 
     * @param key 名前何でも
     * @param requiredTicks 必要な経過Tick数
     * @return 規定Tick経過true
     */
    public static boolean checkAndReset(String key, int requiredTicks) {
        int current = cooldowns.getOrDefault(key, requiredTicks);

        if (current >= requiredTicks) {
            cooldowns.put(key, 0);
            return true;
        }
        return false;
    }
}
