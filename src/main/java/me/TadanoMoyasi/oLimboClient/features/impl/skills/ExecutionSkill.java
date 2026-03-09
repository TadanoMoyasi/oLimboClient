package me.TadanoMoyasi.oLimboClient.features.impl.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mojang.authlib.GameProfile;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.core.Codex;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.core.MagicStoneManager;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.core.NBTParsing;
import me.TadanoMoyasi.oLimboClient.utils.CorrectPlayerInfoFetcher;
import me.TadanoMoyasi.oLimboClient.utils.JobChanger;
import me.TadanoMoyasi.oLimboClient.utils.OthersJobManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.sound.PlaySoundEvent;

public class ExecutionSkill {
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	public enum Skill {
		  KAKUSEI("覚醒", 400),
		  HYAKKA_RYOURAN("百火繚乱", 240), //batasi
		  HYAKKA_RYOURAN2("百火繚乱", 200), //si-ka-
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
		    	return BY_NAME.containsKey(name);
		    }
	 }
	
	private static final Pattern SKILL_PATTERN = Pattern.compile("\\[武器スキル\\] (.+)が(.+)を発動");
	private static boolean isTenkaMusouActive = false;
	private static final List<ActiveSkill> activeSkills = new ArrayList<>();
	private static final double CODEX_BASE_COOLTIME = 60.0; //pa-ku no hangen komi(pa-kuha torenainore)
	private static final int CODEX_BASE_ACTIVETIME_TICK = 400;
	private static final double SPRING_BASE_COOLTIME = 65.0; // pa-kuno hangen + nazono purino kimoi ataino CTgenshou(90s) <- koitu nani majide wakewakaran
	private static final int SPRING_BASE_ACTIVETIME_TICK = 600;
	
	public static synchronized void activate(UUID playerId, Skill skill) {
		if (playerId == null || skill == null) return;
		for (ActiveSkill active : activeSkills) {
			if (active.getPlayerId().equals(playerId) && active.getSkill() == skill) {
				activeSkills.remove(active);
				break;
			}
		}
	    activeSkills.add(new ActiveSkill(playerId, skill));
	}
	
	public static List<ActiveSkill> getActiveSkills() {
        return activeSkills;
    }
	
	public static synchronized void onClientTick() {
		for (ActiveSkill active : activeSkills) {
	        active.tick();
	        if (active.getSkill() == Skill.RYU_NO_ISSEN) {
	        	if (active.isExpired()) {
		        	IssenReminder.issenEnd();
		        } else {
	        		IssenReminder.issenActive(active);
		        }
        	}
	    }
		
	    activeSkills.removeIf(ActiveSkill::isExpired);
    }
    
    public static Skill getCurrentSkillEnum(String skill) {
		if (mc.thePlayer == null || mc.theWorld == null) return null;
		return Skill.getSkillFromName(skill);
	}
    
    public static synchronized boolean isSkillActive(Skill skill) {
    	for (ActiveSkill active : activeSkills) {
    		if (active.getSkill() == skill) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean isSkillActiveByName(String skillname) {
    	Skill skill = Skill.getSkillFromName(skillname);
    	if (skill == null) return false;
    	return isSkillActive(skill);
    }
    
    public static void onChat(String msg) {
    	if (mc.thePlayer == null || mc.theWorld == null) return;
    	if (!oLimboClientMod.config.othersSkillActiveTime) return;
    	Matcher matcher = SKILL_PATTERN.matcher(msg);
    	
    	if (matcher.find()) {
    		String matchedPlayer = matcher.group(1);
    		String matchedSkill = matcher.group(2);
    		
    		NetworkPlayerInfo info = CorrectPlayerInfoFetcher.getInfo(matchedPlayer);
    		//EntityPlayer player = mc.theWorld.getPlayerEntityByName(matchedPlayer);
    		if (info == null) return;
    		GameProfile profile = info.getGameProfile();
    		UUID uuid = info.getGameProfile().getId();
    		Skill skill = getCurrentSkillEnum(matchedSkill);
    		if (skill == Skill.HYAKKA_RYOURAN) {
    			if (profile.getName().equals(mc.thePlayer.getName())) {
    				if (!"バタフライシーカー".equals(JobChanger.getCurrentJob())) {
        				skill = Skill.HYAKKA_RYOURAN2;
        			} 
    			} else {
    				if (!"バタフライシーカー".equals(OthersJobManager.getJob(matchedPlayer))) {
    					skill = Skill.HYAKKA_RYOURAN2;
    				}
    			}
    		}
    		activate(uuid, skill);
    		
    		if ("天下無双".equals(matchedSkill)) {
    			isTenkaEnable();
    		}
    		
    		if ("封魔録・神託の加護".equals(matchedSkill)) {
    			onCodex(uuid, profile.getName());
    			onPriestSkills(uuid, matchedPlayer, matchedSkill);
    		}
    		
    		if ("恵みの泉".equals(matchedSkill)) {
    			onPriestSkills(uuid, matchedPlayer, matchedSkill);
    		}
    		
    		if ("ラータイブ：メギド".equals(matchedSkill)) {
    			JerezStack.clearStack();
    		}
    	}
    }
    
    public static void onNormalSkillUse(String msg) {
    	if (!oLimboClientMod.config.normalSkillActiveTime) return;
    	if (oLimboClientMod.config.onlyIssen && !"龍の一閃".equals(msg)) return;
    	EntityPlayer player = mc.thePlayer;
    	if (player == null) return;
    	UUID uuid = mc.thePlayer.getUniqueID();
    	Skill skill = getCurrentSkillEnum(msg);
    	activate(uuid, skill);
    }
    
    public static void isTenkaEnable() {
    	isTenkaMusouActive = true;
    }
    
    public static void isTenkaDisable() {
    	isTenkaMusouActive = false;
    }
    
    public static synchronized void onPlaySound(PlaySoundEvent event) {
    	if (!"random.orb".equals(event.name) || event.sound.getPitch() < 0.5) return;
    	if (!isTenkaMusouActive || mc.theWorld == null) return;
    	
    	float soundX = event.sound.getXPosF();
        float soundY = event.sound.getYPosF();
        float soundZ = event.sound.getZPosF();
        
    	for (ActiveSkill active : getActiveSkills()) {
    		if (active == null || active.isExpired()) continue;
    	    if (active.getSkill() == Skill.TENKA_MUSOU) {
    	    	if (!active.canExtendFromRitsumai()) {
    	    		if (active.canStartRitsumai()) {
    	    		    active.startRitsumaiWindow();
    	    		}
    	    	}
    	    	if (!active.canExtendFromRitsumai()) continue;
    	    	EntityPlayer player = mc.theWorld.getPlayerEntityByUUID(active.getPlayerId());
    	        if (player == null) continue;

    	        double distSq = player.getDistanceSq(soundX, soundY, soundZ);
    	        //double distance = player.getDistance(event.sound.getXPosF(), event.sound.getYPosF(), event.sound.getZPosF());
    	        //ヤケクソで感知範囲を馬鹿みたいに広げたら率舞の感知安定しました。っぱ広くてなんぼよ
    	        if (distSq < 100.0) { //distance < 3.3
    	        	active.addTime(6);
    	        }
    	    }
    	}
    }
    
    private static void onPriestSkills(UUID uuid, String mcid, String skill) {
    	if (uuid == null) return;
    	EntityPlayer targetPlayer = mc.theWorld.getPlayerEntityByUUID(uuid);
    	if (targetPlayer == null) {
    		if ("封魔録・神託の加護".equals(skill)) {
        		int activeTicks = -1;
        		int calclatedCoolTimeTicks = -1;
        		if (CodexCache.contains(uuid)) {
        			Codex codex = CodexCache.get(uuid);
        			String slot = codex.slot;
        			activeTicks = codex.ticks;
        			int cooltimeTicks = (int) (MagicStoneManager.calclateCooltimeReduction(CODEX_BASE_COOLTIME, slot) * 20) + CODEX_BASE_ACTIVETIME_TICK;
            		calclatedCoolTimeTicks = cooltimeTicks - activeTicks;
            		PriestManager.addPriest(mcid, activeTicks, calclatedCoolTimeTicks, "Codex", true);
        		} else {
            		PriestManager.addPriest(mcid, activeTicks, calclatedCoolTimeTicks, "Codex", false);
        		}
        	} else if ("恵みの泉".equals(skill)) {
        		PriestManager.addPriest(mcid, -1, -1, "泉", false);
        	}
    		return;
    	}
    	ItemStack heldItem = targetPlayer.getHeldItem();
        if (heldItem == null || !heldItem.hasTagCompound()) return;
        NBTTagCompound nbt = heldItem.getTagCompound();
        if (!nbt.hasKey("thelow_item_id")) return; 
    	if ("封魔録・神託の加護".equals(skill)) {
    		String slot;
    		double damage;
    		int activeTicks;
    		if (!nbt.getString("thelow_item_id").equals("CoA")) {
    			if (!CodexCache.contains(uuid)) return;
    			Codex codex = CodexCache.get(uuid);
    			slot = codex.slot;
    			activeTicks = codex.ticks;
    		} else {
    			slot = NBTParsing.getPlayerSlot(uuid);
        		damage = NBTParsing.getPlayerWeaponDamage(uuid);
        		activeTicks = CodexCache.calculateCodexTime(damage);
    		}
    		int cooltimeTicks = (int) (MagicStoneManager.calclateCooltimeReduction(CODEX_BASE_COOLTIME, slot) * 20) + CODEX_BASE_ACTIVETIME_TICK;
    		int calclatedCoolTimeTicks = cooltimeTicks - activeTicks;
    		PriestManager.addPriest(mcid, activeTicks, calclatedCoolTimeTicks, "Codex", true);
    	} else if ("恵みの泉".equals(skill)) {
    		if (!nbt.getString("thelow_item_id").equals("mainH(223)弓")) return;
    		String slot = NBTParsing.getPlayerSlot(uuid);
    		int activeTicks = 400;
    		int cooltimeTicks = (int) (MagicStoneManager.calclateCooltimeReduction(SPRING_BASE_COOLTIME, slot) * 20) + SPRING_BASE_ACTIVETIME_TICK;
    		int calclatedCoolTimeTicks = cooltimeTicks - activeTicks;
    		PriestManager.addPriest(mcid, activeTicks, calclatedCoolTimeTicks, "泉", true);
    	}
    }
    
    private static void onCodex(UUID uuid, String mcid) {
    	if (uuid == null) return;
    	
    	double damage;
    	String slot = "";
    	EntityPlayer targetPlayer = mc.theWorld.getPlayerEntityByUUID(uuid);
        if (targetPlayer == null) {
        	damage = CodexCache.get(uuid).ticks;
        } else {
        	ItemStack heldItem = targetPlayer.getHeldItem();
            if (heldItem == null || !heldItem.hasTagCompound()) return;
            NBTTagCompound nbt = heldItem.getTagCompound();
            if (!nbt.hasKey("thelow_item_id") || !nbt.getString("thelow_item_id").equals("CoA")) return; 
            damage = NBTParsing.getPlayerWeaponDamage(uuid);
            slot = NBTParsing.getPlayerSlot(uuid);
        }
        
    	if (damage == 0.0) return;
    	for (ActiveSkill active : ExecutionSkill.getActiveSkills()) {
    	    if (active.getSkill() == Skill.FUUMAROKU_SINTAKU_NO_KAGO) {
    	    	if (damage != 0) {
    	    		int ticks = CodexCache.calculateCodexTime(damage);
    	    		active.setDurationTicks(ticks);
    	    		if (CodexCache.contains(uuid)) {
    	    			CodexCache.updateIfDifferent(uuid, ticks);
        	    	} else {
        	    		CodexCache.put(uuid, ticks, mcid, slot);
        	    	}
    	    		return;
    	    	}
    	    }
    	}
    }
    
    /*古のコデ2居ると動かないやつ。別の方法を思いついたので必要なくなりました。
	 * PotionEffect effect = null;
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
	}*/
}
