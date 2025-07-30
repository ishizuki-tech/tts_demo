以下は、いただいた **Credits / Contact** セクションを追加した最新版の **README.md** です。
（前回版の構成・文言は維持しつつ、重複していた「著者」セクションは **Contact** に統合しました。）

---

# Swahili TTS Demo (Android / Jetpack Compose)

スワヒリ語（Kiswahili）のテキストを \*\*端末内 TTS（オフライン優先）\*\*で読み上げる、最小構成のデモアプリです。
Jetpack Compose + ViewModel（StateFlow）で UI と状態管理を実装し、`TextToSpeech` の初期化・音声選択・発話制御を `SwahiliTts` ユーティリティにカプセル化しています。

---

## 目次

* [機能](#機能)
* [スクリーンショット（例）](#スクリーンショット例)
* [要件](#要件)
* [セットアップ](#セットアップ)
* [ビルド & 実行](#ビルド--実行)
* [プロジェクト構成](#プロジェクト構成)
* [主要クラスと設計](#主要クラスと設計)
* [依存関係（Compose など）](#依存関係compose-など)
* [オフライン音声のインストール手順](#オフライン音声のインストール手順)
* [動作確認用：スワヒリ語サンプル文](#動作確認用スワヒリ語サンプル文)
* [トラブルシューティング](#トラブルシューティング)
* [Credits / 謝辞](#-credits--謝辞)
* [Contact / お問い合わせ](#-contact--お問い合わせ)
* [ライセンス](#ライセンス)

---

## 機能

* **スワヒリ語 TTS**：Swahili の Voice を **オフライン優先**で自動選択（`TZ > KE > その他` の順で優先）
* **インストール誘導**：該当 Voice が未導入なら、TTS データのインストール画面へ遷移
* **Compose UI**：入力テキスト、状態表示（オフライン可否）、Speak / Stop 操作
* **状態管理**：`StateFlow` による `TtsUiState` の公開（`isReady`、`hasOffline`、`lastSpeakResult` など）
* **UX**：IME の Done で発話、発話時はキーボードを閉じる

---

## スクリーンショット（例）

> 画像は省略。アプリ起動後、`Swahili TTS Demo` タイトル、状態チップ、テキスト入力、`Speak` / `Stop` ボタンが表示されます。

---

## 要件

* **Android Studio**（最新の安定版）
* **JDK 17** 推奨
* **minSdk 23** 以上（API 23+）
* オフライン TTS を使う場合は、端末に **Google Speech Services** などの TTS エンジンと **Swahili 音声データ**が必要

---

## セットアップ

1. リポジトリをクローン

   ```bash
   git clone <your-repo-url>
   cd <repo>
   ```

2. Android Studio でプロジェクトを開く

3. 最初の同期（Gradle Sync）を実行

    * 依存関係が解決できない場合は、下記の [依存関係（Compose など）](#依存関係compose-など) を確認

---

## ビルド & 実行

* 接続済みデバイスまたはエミュレータを選択し、**Run ▶︎** をクリック
* 初回起動時に **「Initializing…」** と表示され、その後に **オフライン可否** が反映されます
* オフライン音声が未導入の場合、アプリが **TTS データのインストール画面**へ誘導します（対応端末のみ）

---

## プロジェクト構成

```
app/
 └─ src/main/java/com/negi/ttsdemo/
    ├─ MainActivity.kt                     // Compose ルート（画面、ライフサイクル連携）
    ├─ SwahiliTts.kt                       // TTS ユーティリティ（Voice選択/発話/停止/誘導）
    ├─ tts/
    │   └─ TtsDefaults.kt                  // 共有定数（DEFAULT_TTS_TEXT など）
    └─ ui/main/
        ├─ TtsUiState.kt                   // UI ステート定義（StateFlow で公開）
        └─ TtsViewModel.kt                 // ViewModel（初期化・状態更新・発話制御）
```

> パッケージ階層は一例です。`TtsDefaults.kt` はモジュール内で参照できる任意パッケージに置けます。

---

## 主要クラスと設計

### `SwahiliTts`

* `TextToSpeech` の生成と初期化（`preferredEngine = "com.google.android.tts"` を既定）
* **Voice 選択**：`voices` から Swahili のみ抽出 → `オフライン優先` → `TZ > KE > その他` → `低レイテンシ` → `高品質`
* **Locale 設定**：Voice が取得できない場合は `Locale("sw", "TZ")` → `("sw", "KE")` → `("sw")` の順で設定
  未導入なら **TTS データのインストール画面**へ誘導
* **発話**：`speak(text, flush)`（初期化前コールはキューして、初期化後に再生）
* **停止 / 解放**：`stop()` / `release()`
* **オフライン有無**：`hasEmbeddedSwahili()` で簡易チェック

### `TtsViewModel`

* アプリ起動時に `SwahiliTts.init()` を呼び、\*\*少し遅延（既定 300ms）\*\*の後に `hasEmbeddedSwahili()` を反映
  → `isReady = true` として UI 操作を許可
* `StateFlow<TtsUiState>` を公開（`text`, `hasOffline`, `lastSpeakResult`, `isReady`）
* `speak()` / `stop()` の UI トリガーを仲介

### `MainActivity` + `TtsScreen` (Compose)

* `collectAsStateWithLifecycle()` で ViewModel の状態を購読
* 画面復帰（`ON_RESUME`）で `refreshVoiceStatus()` を再実行
* `OutlinedTextField` の IME Done または `Speak` ボタンで発話・フォーカス解除

---

## 依存関係（Compose など）

> **ポイント**：Compose は **BOM** でバージョン整合性を取るのが安全です。
> ここでは **概念的な指定**を記載します。実際の `<version>` はプロジェクトのバージョンカタログや最新安定版に合わせてください。

```kotlin
// build.gradle.kts (Module: app) — dependencies
dependencies {
    // Compose BOM（推奨）
    implementation(platform("androidx.compose:compose-bom:<compose-bom-version>"))

    // Compose 基本
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.ui:ui-text")
    implementation("androidx.compose.runtime:runtime-saveable")

    // Activity / Lifecycle（collectAsStateWithLifecycle など）
    implementation("androidx.activity:activity-compose:<version>")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:<version>")

    // Kotlin Coroutines（Android）
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:<version>")
}
```

> `KeyboardOptions` などが **Unresolved reference** になる場合は、
> `ui-text` / `foundation` / `activity-compose` / `lifecycle-runtime-compose` の導入や BOM の整合を確認してください。

---

## オフライン音声のインストール手順

端末や OS バージョンで表記が異なりますが、概ね以下で導入できます：

1. **設定** → **システム** → **言語と入力** → **テキスト読み上げ出力**
2. **優先エンジン**：**Google Speech Services**（または使用中の TTS エンジン）を選択
3. **音声データのインストール**（Install voice data）
4. **Swahili（Kiswahili）** を選択 → **オフライン用**音声データをダウンロード
5. アプリに戻り、**Voice 状態**が `offline available` になっているか確認

> 一部端末では、アプリ側からインストール画面を直接開けない場合があります。
> その際は設定アプリから手動で導入してください。

---

## 動作確認用：スワヒリ語サンプル文

* `Hujambo! Karibu kwenye programu yetu.`（こんにちは！私たちのアプリへようこそ。）
* `Habari za leo?`（今日はどうですか？）
* `Asante sana kwa kutumia programu hii.`（このアプリをご利用いただきありがとうございます。）
* `Tafadhali bonyeza kitufe cha kuzima ili kusitisha usomaji.`（停止ボタンを押して読み上げを止めてください。）
* `Sauti ya Kiswahili imepakuliwa kwenye kifaa hiki.`（この端末にはスワヒリ語音声がインストールされています。）

---

## 🙏 Credits / 謝辞

* Android SDK, Jetpack Compose, Kotlin team
* Optimized and maintained by [Ishizuki Tech LLC](https://ishizuki.tech)

---

## 📬 Contact / お問い合わせ

Developed by **Ishizuki Tech LLC**
Email: [ishizuki.tech@gmail.com](mailto:ishizuki.tech@gmail.com)

---

## ライセンス

本リポジトリ内のアプリコードは、特に断りがない場合、MIT License を想定しています。
