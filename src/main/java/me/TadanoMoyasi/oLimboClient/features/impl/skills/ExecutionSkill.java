package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.sound.PlaySoundEvent;

public class ExecutionSkill {
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	public enum Skill {
		  KAKUSEI("覚醒", 400),
		  HYAKKA_RYOURAN("百火繚乱", 200),
		  YAKUSAI_KOURIN("厄災降臨", 300),
		  CHAOS_BLIZZARD("カオスブリザード", 120),
		  MEGUMI_NO_IZUMI("恵みの泉", 400),
		  STEAD_SHOCK("ステッドショック", 240),
		  TENKA_MUSOU("天下無双", 400),
		  BERSERK("バーサーク", 100),
		  TI_NO_ZANGEKI("血の斬撃", 160),
		  GRAVITY_END("グラビティエンド", 300),
		  SATELITE_CANNON("サテライトキャノン", 200),
		  SENKI_NO_GOUREI("戦姫の号令", 600),
		  ZETA_RYRAE("ゼータ・リュラエ", 400),
		  DEATH_DANCE("デスダンス", 200),
		  IMPACTION("Impaction", 800),
		  FUUMAROKU_SINTAKU_NO_KAGO("封魔録・神託の加護", 400),
		  RA_TAIBU_MEGIDO("ラータイブ：メギド", 400),//英語とかかと思って色々調べてたら元ネタを知りました。が、どう書くのかはわかりませんでした。仕方ないのでこの書き方です。
		  SEIKISHIN_NO_UTAGOE("星輝神の歌声", 400),
		  SIKI_KOUYOU("士気高揚", 600),
		  ZYUHOU("咒砲", 60),
		  SHUUTYU("集中", 200),
		  RITSUMAI("率舞", 100),
		  ONMITSU("隠密", 400),
		  TRISTAN("トリスタン", 100),
		  ICE_STAMP("アイススタンプ", 200),
		  RA_TAIBU_AREFU("ラータイブ：アレフ", 100),
		  RA_TAIBU_ZAIN("ラータイブ：ザイン", 100),
		  SPELL_DANCE("スペルダンス", 400),
		  GEKIZYOU("激情", 400),
		  TI_NO_DAISHOU("血の代償", 140),
		  LIGHTNING_BOLT("ライトニングボルト", 40),
		  SAISHOKU_KENBI("才色兼備", 60),
		  GEKKOU_RANBU("激昂乱舞", 400),
		  MARYOKU_KYUSHU("魔力吸収", 100),
		  TI_NO_KATSUBOU("血の渇望", 100),
		  LIGHTNING_ORDER("ライトニングオーダー", 200),
		  RAGE("レイジ", 100),
		  RYU_NO_ISSEN("龍の一閃", 500);

		  private final String displayName;
		  
		  private final int activeTime;

		  Skill(String displayName, int activeTime) {
		        this.displayName = displayName;
		        this.activeTime = activeTime;
		    }

		    public String getDisplayName() {
		        return displayName;
		    }
		    
		    public int getActiveTime() {
		    	return activeTime;
		    }

		    private static final Map<String, Skill> BY_NAME = new HashMap<>();

		    static {
		        for (Skill skill : values()) {
		            BY_NAME.put(skill.displayName, skill);
		        }
		    }

		    public static Skill getSkillFromName(String name) {
		        return BY_NAME.get(name);
		    }

		    public static boolean contains (String name) {
		    	for (Skill skill : Skill.values()) {
		    		if (skill.displayName.equals(name)) {
		    			return true;
		    		}
		    	}
		    	return false;
		    }
	 }
	
	private static boolean isTenkaMusouActive = false;
	private static final List<ActiveSkill> activeSkills = new ArrayList<>();
	
	public static void activate(UUID playerId, Skill skill) {
		if (playerId == null || skill == null) return;
	    activeSkills.add(new ActiveSkill(playerId, skill));
	}
	
	public static List<ActiveSkill> getActiveSkills() {
        return activeSkills;
    }
	
	public static void onClientTick() {
        Iterator<ActiveSkill> iterator = activeSkills.iterator();

        while (iterator.hasNext()) {
            ActiveSkill active = iterator.next();
            active.tick();

            if (active.isExpired()) {
            	if (active.getSkill() == Skill.RYU_NO_ISSEN) {
            		IssenReminder.issenEnd();
            	}
                iterator.remove();
            } else {
            	if (active.getSkill() == Skill.RYU_NO_ISSEN) {
            		IssenReminder.issenActive(active);
            	}
            }
        }
    }
    
    public static Skill getCurrentSkillEnum(EntityPlayer player, String skill) {
		if (mc.thePlayer == null || mc.theWorld == null) return null;
		return Skill.getSkillFromName(skill);
	}
    
    public static boolean isSkillActive(Skill skill) {
    	for ( ActiveSkill active : activeSkills) {
    		if (active.getSkill() == skill) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean isSkillActiveByName(String skillname) {
    	Skill skill = Skill.getSkillFromName(skillname);
    	if ( skill == null) return false;
    	return isSkillActive(skill);
    }
    
    public static void onChat(String msg) {
    	if (!oLimboClientMod.config.othersSkillActiveTime) return;
    	Pattern pattern = Pattern.compile("\\[武器スキル\\] (.+)が(.+)を発動");
    	Matcher matcher = pattern.matcher(msg);
    	
    	if (matcher.find()) {
    		String matchedPlayer = matcher.group(1);
    		String matchedSkill = matcher.group(2);
    		
    		EntityPlayer player = Minecraft.getMinecraft().theWorld.getPlayerEntityByName(matchedPlayer);
    		if (player == null) return;
    		UUID uuid = player.getUniqueID();
    		Skill skill = getCurrentSkillEnum(mc.thePlayer, matchedSkill);
    		activate(uuid, skill);
    		
    		if ("天下無双".equals(matchedSkill)) {
    			isTenkaEnable();
    		}
    		
    		if ("封魔録・神託の加護".equals(matchedSkill)) {
    			onCodex(uuid);
    		}
    	}
    }
    
    public static void onNormalSkillUse(String msg) {
    	if (!oLimboClientMod.config.normalSkillActiveTime) return;
    	if (oLimboClientMod.config.onlyIssen && !"龍の一閃".equals(msg)) return;
    	EntityPlayer player = mc.thePlayer;
    	if (player == null) return;
    	UUID uuid = mc.thePlayer.getUniqueID();
    	Skill skill = getCurrentSkillEnum(mc.thePlayer, msg);
    	activate(uuid, skill);
    }
    
    public static void isTenkaEnable() {
    	isTenkaMusouActive = true;
    	return;
    }
    
    public static void isTenkaDisable() {
    	isTenkaMusouActive = false;
    	return;
    }
    
    public static void onPlaySound(PlaySoundEvent event) {
    	if (!"random.orb".equals(event.name) || event.sound.getPitch() < 0.5) return;
    	if (!isTenkaMusouActive) return;
    	for (ActiveSkill active : ExecutionSkill.getActiveSkills()) {
    	    if (active.getSkill() == Skill.TENKA_MUSOU) {
    	    	if (!active.canExtendFromRitsumai()) {
    	    		if (active.canStartRitsumai()) {
    	    		    active.startRitsumaiWindow();
    	    		}
    	    	}
    	    	if (!active.canExtendFromRitsumai()) return;
    	        UUID uuid = active.getPlayerId();
    	        EntityPlayer player = mc.theWorld.getPlayerEntityByUUID(uuid);
    	        if (player == null) continue;

    	        double distance = player.getDistance(event.sound.getXPosF(), event.sound.getYPosF(), event.sound.getZPosF());

    	        if (distance < 3.3) {
    	        	active.addTime(6);
    	        }
    	    }
    	}
    }
    
    private static void onCodex(UUID uuid) {
    	EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    	
    	PotionEffect effect = null;
    	int ticks = -1;
    	
    	if (CodexCache.contains(uuid)) {
    		int tick = CodexCache.get(uuid);
    		ticks = tick;
    	}
    	
    	if (player.isPotionActive(Potion.damageBoost) && ticks == -1) {
    		PotionEffect e = player.getActivePotionEffect(Potion.damageBoost);
    		if (e.getAmplifier() == 2) {
    			if (e.getDuration() >200 && e.getDuration() < 600) {
    				effect = e;
    			}
    		}
    	}
    	
    	if (effect == null && player.isPotionActive(Potion.resistance) && ticks == -1) {
    		PotionEffect e = player.getActivePotionEffect(Potion.resistance);
    		if (e.getAmplifier() == 1) {
    			if (e.getDuration() > 200 && e.getDuration() < 600) {
    				effect = e;
    			}
    		}
    	}
    	
    	if (effect == null && player.isPotionActive(Potion.regeneration) && ticks == -1) {
    		PotionEffect e = player.getActivePotionEffect(Potion.regeneration);
    		if (e.getAmplifier() == 2) {
    			if (e.getDuration() > 200 && e.getDuration() < 600) {
    				effect = e;
    			}
    		}
    	}
    	
    	for (ActiveSkill active : ExecutionSkill.getActiveSkills()) {
    	    if (active.getSkill() == Skill.FUUMAROKU_SINTAKU_NO_KAGO) {
    	    	if (ticks != -1) {
    	    		active.setDurationTicks(ticks);
    	    		CodexCache.updateIfDifferent(uuid, ticks);
    	    		return;
    	    	}
    	    	if (effect != null && ticks == -1) {
    	    		ticks = effect.getDuration();
    	    		active.setDurationTicks(ticks);
    	    		if (!CodexCache.contains(uuid)) {
        	    		CodexCache.put(uuid, ticks);
        	    	}
    	    	}
    	    }
    	}
    }
}
