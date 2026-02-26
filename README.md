<p align="center">
  <img src="https://github.com/user-attachments/assets/a2cccf0d-93bf-488b-a6ee-dfe0d091333e" width="200" alt="oLimboClient Logo">
  <h1 align="center">oLimboClient</h1>
  <p align="center">
    Minecraft 1.8.9 TheLow用多機能ユーティリティMod。
    (何とは言いませんが、チートではありません)
  </p>
</p>

---

## 🌟 Features

* **スキルCT表示** 自分のスキルのクールダウンを表示。
* **キャストタイム表示** 詠唱やアムル等の時間を表示
* **実行時間（バフ・効果時間）表示** 覚醒や百花繚乱など、自分や周囲のプレイヤーのスキル持続時間を表示。
* **プリーストHUD** スキルが使用可能かどうかを一目で判別できるインジケーター。
### 📑 Preset & Equipment
* **装備スキル表示**: 現在武器にセットされているスキルを表示。
* **解放HUD**: 解放の状況を表示。
* **プリセットHUD**: ダンジョンに行く前にアイテムを確認出来ます。
### 🛠 Quality of Life (QoL)
* **一閃リマインダー**: 龍の一閃が終わりそうな時に音で通知。（※設定で秒数調整可能。26でオフ）
* **リマインダー**: 
  - **Tell通知**: Tellが届いた際に音で通知。
  - **メンション通知**: チャット内で自分の名前が呼ばれた際に音で通知。
## ⌨️ Usage
* `/lc` : 設定画面を表示
* `/lc hud` : HUD設定画面を表示
* `/lc help` : コマンドヘルプを表示
## ⚠️ Disclaimer
* 本Modの使用によって生じたあらゆる不利益（アカウント制限等を含む）について、開発者は一切の責任を負いません。
* サーバーのルールを遵守し、各自の判断でご利用ください。

## 📩 Contact / Bug Report
バグ報告や機能要望は、以下のいずれかまでお願いします。
* **Discord**: `TadanoMoyasi`
* **GitHub Issues**: [こちらから報告](https://github.com/TadanoMoyasi/oLimboClient/issues)

## 🏗 For Developers

### 📚 使用ライブラリ
このModは以下のライブラリを使用して開発されています。
* **[Vigilance](https://github.com/EssentialGG/Vigilance)**: 
  * 高機能な設定画面（GUI）を構築するために使用しています。
  * 本Modの動作およびビルドには、VigilanceおよびEssentialのライブラリが必要です。

### 📂 データ構造
Modの設定やキャッシュデータは、実行時に以下のディレクトリへ保存されます。
* `.minecraft/config/oLimboClient/`
  * `config.toml`: 設定データ
  * `skillCache.json`: Codexの時間、スロットのキャッシュ
  * `preset.json`: Preset情報
---
<p align="center">
Developed by TadanoMoyasi
</p>
