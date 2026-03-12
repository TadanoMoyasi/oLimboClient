package me.TadanoMoyasi.oLimboClient.core.config;

import java.awt.Color;
import java.io.File;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

public class ActiveSkillColorConfig extends Vigilant {
	public static final ActiveSkillColorConfig INSTANCE = new ActiveSkillColorConfig();
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "覚醒の表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color KAKUSEI_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "百火繚乱の表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color HYAKKA_RYOURAN_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "約再降臨の表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color YAKUSAI_KOURIN_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "カオスブリザードの表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color CHAOS_BLIZZARD_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "恵みの泉の表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color MEGUMI_NO_IZUMI_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "封魔録・神託の加護の表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color FUUMAROKU_SINTAKU_NO_KAGO_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "ステッドショックの表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color STEAD_SHOCK_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "天下無双の表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color TENKA_MUSOU_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "バーサークの表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color BERSERK_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "Impactionの表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color IMPACTION_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "ラータイブ：メギドの表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color RA_TAIBU_MEGIDO_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "星輝神の歌声の表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color SEIKISHIN_NO_UTAGOE_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "秘技：終焉の斬の表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color HIGI_SHUUEN_NO_ZAN_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "血の斬撃の表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color TI_NO_ZANGEKI_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "グラビティエンドの表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color GRAVITY_END_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "サテライトキャノンの表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color SATELITE_CANNON_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "戦姫の号令の表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color SENKI_NO_GOUREI_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "ゼータ・リュラエの表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color ZETA_RYRAE_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "デスダンスの表示色",
	        category = "SpecialSkill",
	        subcategory = "色設定"
	    )
	    public Color DEATH_DANCE_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "多段の舞の表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color TADAN_NO_MAI_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "強打の舞の表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color KYOUDA_NO_MAI_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "龍の一閃の表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color RYU_NO_ISSEN_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "士気高揚の表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color SIKI_KOUYOU_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "咒砲の表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color ZYUHOU_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "集中の表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color SHUUTYU_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "率舞の表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color RITSUMAI_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "隠密の表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color ONMITSU_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "トリスタンの表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color TRISTAN_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "アイススタンプの表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color ICE_STAMP_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "ラータイブ：アレフの表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color RA_TAIBU_AREFU_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "ラータイブ：ザインの表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color RA_TAIBU_ZAIN_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "スペルダンスの表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color SPELL_DANCE_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "激情の表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color GEKIZYOU_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "血の代償の表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color TI_NO_DAISHOU_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "ライトニングボルトの表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color LIGHTNING_BOLT_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "才色兼備の表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color SAISHOKU_KENBI_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "激昂乱舞の表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color GEKKOU_RANBU_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "魔力吸収の表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color MARYOKU_KYUSHU_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "血の渇望の表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color TI_NO_KATSUBOU_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "ライトニングオーダーの表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color LIGHTNING_ORDER_Color = new Color(255, 255, 255);
	
	@Property(
	        type = PropertyType.COLOR,
	        name = "レイジの表示色",
	        category = "NormalSkill",
	        subcategory = "色設定"
	    )
	    public Color RAGE_Color = new Color(255, 255, 255);
	
	public ActiveSkillColorConfig() {
        super(new File("config/olimboclient/activeskills.toml"), "ActiveSkill Specific Settings");
        initialize();
    }
}
