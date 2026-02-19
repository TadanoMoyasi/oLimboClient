package me.TadanoMoyasi.oLimboClient.core.api.analyzer;

import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;

import me.TadanoMoyasi.oLimboClient.oLimboClientMod;
import me.TadanoMoyasi.oLimboClient.core.api.types.Response;
import me.TadanoMoyasi.oLimboClient.core.api.types.SkillCoolTime;
import me.TadanoMoyasi.oLimboClient.core.data.ModCoreData;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.ExecutionSkill;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.SkillCoolTimeHandler;
import me.TadanoMoyasi.oLimboClient.features.impl.skills.SkillHandler;


public class ChatSkillCTAnalyzer{
  public static boolean uniqueSkills(SkillCoolTime sc) {
	  System.out.println(sc.type);
	  if (sc.name.equals("開放")) {
	      ModCoreData.kaihouUsed = true;
	      SkillCoolTimeHandler.startCT("開放タイマー", 30);
	      return true;
	    } else if (sc.name.equals("覚醒")) {
	      ModCoreData.kaihouUsed = false;
	      return false;
	    } else if (sc.name.equals("ステッドショック") && sc.cooltime.doubleValue() > 60.0D) {
	      SkillCoolTimeHandler.startCT(sc.name, 30);
	      return true;
	    } else if (sc.name.equals("メテオストライク") || sc.name.equals("マジックボール") || sc.name.equals("ライトニングボルト")) {
	    	SkillHandler.resetEISHOU();
	    	return false;
	    } else if (ExecutionSkill.Skill.contains(sc.name) && "NORMAL_SKILL".equals(sc.type)) {
	    	ExecutionSkill.onNormalSkillUse(sc.name);
	    	return false;
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


