package me.TadanoMoyasi.oLimboClient.core.config;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.net.URI;

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
			type = PropertyType.BUTTON,
	        name = "GitHub",
	        description = "GitHubを開く",
	        category = " General",
	        subcategory = "General"
	    )
	public void openGitHub() {
		try {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				Desktop.getDesktop().browse(new URI("https://github.com/TadanoMoyasi/oLimboClient"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Property(
			type = PropertyType.BUTTON,
	        name = "暇つぶし",
	        description = "なんか暇そうですね。いいゲームありますよ",
	        category = " General",
	        subcategory = "General"
	    )
	public void openGodField() {
		try {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				Desktop.getDesktop().browse(new URI("https://godfield.net"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Property(
			type = PropertyType.SWITCH,
	        name = "FirstTimeMessage",
	        category = " General",
	        hidden = true
	    )
	    public boolean firstTime = true;
	
	@Property(
			type = PropertyType.TEXT,
	        name = "LastVersion",
	        category = " General",
	        hidden = true
	    )
	    public String lastVersion = "";
	
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
			type = PropertyType.BUTTON,
			name = "スキル別詳細設定を開く",
			description = "スキルごとの色を個別に設定します",
			category = "Features",
			subcategory = "ActiveTime"
		)
		public void openActiveSkillSettings() {
			new Thread(() -> {
				try {
					Thread.sleep(100);
					
					Minecraft.getMinecraft().addScheduledTask(() -> {
						Minecraft.getMinecraft().displayGuiScreen(ActiveSkillColorConfig.INSTANCE.gui());
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}
	
	@Property(
			type = PropertyType.SLIDER,
			name = "終わりそうなタイミングで色を変える",
			description = "実行時間が終わりそうなタイミングで色を赤に変えます。(31にしたらオフになります。)",
			category = "Features",
			subcategory = "ActiveTime",
			min = 0,
			max = 31
			)
	public int  acriveSkillEndColor = 5;
	
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
			name = "プリーストのスキルが使えるかを表示",
			description = "プリーストが泉やCodex等のスキルを使えるかどうかを表示します。",
			category = "Features",
			subcategory = "Priest"
			)
	public boolean priestHUD = true;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "Codexの範囲を表示",
			description = "Codexを持った際にCodexの範囲を表示します。",
			category = "Features",
			subcategory = "Priest"
			)
	public boolean codexRange = true;
	
	@Property(
			type = PropertyType.COLOR,
			name = "Codexの範囲の色を変更",
			description = "Codexの範囲表示の色を変更します。",
			category = "Features",
			subcategory = "Priest"
			)
	public Color codexRangeColor = new Color(255, 0, 0);
	
	@Property(
			type = PropertyType.SWITCH,
			name = "ゲーミングCodexRange",
			description = "1680万色に光る最高にゲーミングなCodexRangeをお届け！",
			category = "Features",
			subcategory = "Priest"
			)
	public boolean codexRangeChroma = false;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "ヘレスのスタック数を表示",
			description = "ヘレスのスタック数を表示します。",
			category = "Features",
			subcategory = "Quality of Life"
			)
	public boolean jerezStackHUD = true;
	
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
			name = "矢の残量を表示",
			description = "インベントリ内の矢の残り個数を表示します。",
			category = "Features",
			subcategory = "Quality of Life"
			)
	public boolean arrowHUD = true;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "矢の残量を表示のテキストを削除",
			description = "矢: 64 の 矢: の部分を消して数字のみにします。",
			category = "Features",
			subcategory = "Quality of Life"
			)
	public boolean arrowHUDText = false;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "矢の残量表示を弓の時だけ表示",
			description = "矢の残量表示を弓を持っている時のみにします。",
			category = "Features",
			subcategory = "Quality of Life"
			)
	public boolean arrowHUDOnlyHaveBow = false;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "プリセットHUD表示",
			description = "プリセットHUDを表示します。",
			category = "Features",
			subcategory = "Quality of Life"
			)
	public boolean presetHUD = true;
	
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
			type = PropertyType.SWITCH,
			name = "チャットコマンド",
			description = "チャットで名前を呼ばれた際に音を鳴らします",
			category = "Miscellaneous",
			subcategory = "Chat Command"
			)
	public boolean chatCommand = true;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "全体チャットで反応させる",
			description = "全体チャットでも反応するようにします。",
			category = "Miscellaneous",
			subcategory = "Chat Command"
			)
	public boolean chatCommandALL = false;
	
	@Property(
			type = PropertyType.TEXT,
			name = "prefix",
			description = "!dice とかの!の部分を変えます。初期値は!です。",
			category = "Miscellaneous",
			subcategory = "Chat Command"
			)
	public String chatCommandPrefix = "!";
	
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
		super(new File("config/olimboclient/config.toml"), "§lo§b§lLimbo§r§lClient Config");
		
		initialize();
	 }
}
