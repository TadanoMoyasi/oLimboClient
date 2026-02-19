package me.TadanoMoyasi.oLimboClient.core.api.types;

public class Player_Status {
  public String uuid;
  
  public String mcid;
  
  int mainLevel;
  
  public SubStatus swordStatus;
  
  public SubStatus bowStatus;
  
  public SubStatus magicStatus;
  
  public ClanInfo clanInfo;
  
  public int galions;
  
  public int unit;
  
  public String jobName;
  
  public static class SubStatus {
    public int leve;
    
    public int exp;
    
    public int maxLevel;
    
    public int reincCount;
  }
  
  public static class ClanInfo {
    public String clanId;
    
    public String clanName;
    
    public String clanRank;
  }
}


