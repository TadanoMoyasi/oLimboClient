package me.TadanoMoyasi.oLimboClient.core.api.analyzer;

import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.api.types.Response;
import me.TadanoMoyasi.oLimboClient.core.api.types.SkillCoolTime;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.ExecutionSkill;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.JerezStack;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.SkillCoolTimeHandler;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.SkillHandler;


public class ChatSkillCTAnalyzer{
  public static boolean uniqueSkills(SkillCoolTime sc) {
	  if ("開放".equals(sc.name)) {
	      ModCoreData.kaihouUsed = true;
	      SkillCoolTimeHandler.startCT("開放タイマー", 30);
	      return true;
	    } else if ("覚醒".equals(sc.name)) {
	      ModCoreData.kaihouUsed = false;
	    }
	  if ("ステッドショック".equals(sc.name) && sc.cooltime.doubleValue() > 60.0D) {
	      SkillCoolTimeHandler.startCT(sc.name, 30);
	      return true;
	    }
	  if ("メテオストライク".equals(sc.name) || "マジックボール".equals(sc.name) || "ライトニングボルト".equals(sc.name)) {
	    	SkillHandler.resetEISHOU();
	    }
	  if (ExecutionSkill.Skill.contains(sc.name) && "NORMAL_SKILL".equals(sc.type)) {
	    	ExecutionSkill.onNormalSkillUse(sc.name);
	    }
	  if ("ラータイブ：ザイン".equals(sc.name) || "ラータイブ：アレフ".equals(sc.name)) {
		  JerezStack.onUseSkill();
	  }
	  return false;
  }
  
  public static void analyzeCT(String text) {
    Type type = (new TypeToken<Response<SkillCoolTime>>() {  }).getType();
    Response<SkillCoolTime> ct = (Response<SkillCoolTime>)oLimboClientMod.gson.fromJson(text, type);
    SkillCoolTime skill = ct.response;
    
    if (!uniqueSkills(skill)) {
        if (!SkillCoolTimeHandler.isActive(skill.name)) {
            SkillCoolTimeHandler.startCT(skill.name, skill.cooltime);
        }
    }
  }
}