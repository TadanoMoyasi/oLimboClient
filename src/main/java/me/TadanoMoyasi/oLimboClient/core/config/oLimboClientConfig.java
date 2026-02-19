package me.TadanoMoyasi.oLimboClient.core.config;

import java.io.File;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import me.TadanoMoyasi.oLimboClient.hud.core.HUDConfigScreen;
import net.minecraft.client.Minecraft;

public class oLimboClientConfig extends Vigilant {	
	public static final oLimboClientConfig INSTANCE = new oLimboClientConfig();
	
	@Property(
			type = PropertyType.SWITCH,
	        name = "機能を有効化",
	        description = "このMODをON/OFFします",
	        category = " General",
	        subcategory = "General"
	    )
	    public boolean enableToggle = true;
	
	@Property(
			type = PropertyType.SWITCH,
	        name = "FirstTimeMessage",
	        category = " General",
	        hidden = true
	    )
	    public boolean firstTime = true;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "スキルのクールタイムを表示",
			description = "スキルが使用できるようになるまでの時間を表示します。",
			category = "Features",
			subcategory = "CoolTime"
			)
	public boolean skillCoolTime = true;
	
	
	@Property(
			type = PropertyType.SWITCH,
			name = "スキルのキャストタイムを表示",
			description = "ROA詠唱やアムル等、パッシブが発動できるようになるまでの時間を表示します。",
			category = "Features",
			subcategory = "CoolTime"
			)
	public boolean skillCastTime = true;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "スキルの実行時間を表示",
			description = "覚醒や百花等のスキルを誰かが使用した際、そのスキルが終わるまでの時間を表示します。",
			category = "Features",
			subcategory = "ActiveTime"
			)
	public boolean othersSkillActiveTime = true;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "ノーマルスキルの実行時間を表示",
			description = "実行時間HUDにノーマルスキルの実行時間も表示します。",
			category = "Features",
			subcategory = "ActiveTime"
			)
	public boolean normalSkillActiveTime = true;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "龍の一閃だけ表示",
			description = "ノーマルスキルの実行時間を龍の一閃だけ表示します。(ノーマル表示オンの状態でオンにしてください)",
			category = "Features",
			subcategory = "ActiveTime"
			)
	public boolean onlyIssen = false;
	
	@Property(
			type = PropertyType.SLIDER,
			name = "一閃リマインダー",
			description = "一閃が終わりそうな時に音を鳴らします。(26にしたらオフになります。キモくてごめんね)",
			category = "Features",
			subcategory = "ActiveTime",
			min = 0,
			max = 26
			)
	public int  issenReminderTime = 3;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "Codexの時間をキャッシュ",
			description = "実行時間表示用に、誰かからCodexを受けたらその時間をキャッシュします。キャッシュすると、Codexを受けていなくても実行時間の表示がその人のCodexの時間になります。",
			category = "Features",
			subcategory = "ActiveTime"
			)
	public boolean codexCache = true;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "つけているスキルを表示",
			description = "その武器が現在つけているスキルを表示します。スキルを付けていないとunselectedになります。",
			category = "Features",
			subcategory = "Quality of Life"
			)
	public boolean equippedSkill = true;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "解放表示",
			description = "解放したかどうかを表示します。",
			category = "Features",
			subcategory = "Quality of Life"
			)
	public boolean kaihouDisplay = true;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "Tellリマインダー",
			description = "tellが来た際に音を鳴らします。",
			category = "Miscellaneous",
			subcategory = "Quality of Life"
			)
	public boolean tellReminder = true;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "チャットメンションリマインダー",
			description = "チャットで名前を呼ばれた際に音を鳴らします",
			category = "Miscellaneous",
			subcategory = "Quality of Life"
			)
	public boolean chatMentionReminder = true;
	
	@Property(
			type = PropertyType.BUTTON,
			name = "HUD表示位置変更",
			category = "HUD",
			subcategory = "Positions"
			)
	public void openHUDPositionGUI() {
		Minecraft.getMinecraft().displayGuiScreen(new HUDConfigScreen());
	}
	
	@Property(
			type = PropertyType.TEXT,
			name = "HUD_ALL_POSITIONS",
			category = "HUD",
			subcategory = "Positions",
			hidden = true
			)
	public String HUD_ALL_POSITIONS = "";

	public oLimboClientConfig() {
		super(new File("config/olimboclient/config.toml"), "oLimboClient Config");
		
		initialize();
	 }
}
