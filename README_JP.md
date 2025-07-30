# Swahili TTS Demo (Android / Jetpack Compose)

\*\*端末内 TTS（オフライン優先）\*\*でスワヒリ語（Kiswahili）テキストを読み上げる最小構成デモです。
UI と状態管理は **Jetpack Compose + ViewModel（StateFlow）** で実装。
`TextToSpeech` の初期化／音声選択／発話制御は `SwahiliTts` ユーティリティにカプセル化されています。

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
* [FAQ](#faq)
* [開発者向けメモ](#開発者向けメモ)
* [🙏 Credits / 謝辞](#-credits--謝辞)
* [📬 Contact / お問い合わせ](#-contact--お問い合わせ)
* [ライセンス](#ライセンス)

---

## 機能

* **スワヒリ語 TTS**：Swahili の Voice を **オフライン優先**で自動選択
  優先度：`TZ（タンザニア） > KE（ケニア） > その他` → **低レイテンシ** → **高品質**
* **インストール誘導**：該当 Voice が未導入なら、**TTS 音声データのインストール画面**へ遷移（対応端末）
* **Compose UI**：テキスト入力、状態表示（オフライン可否）、**Speak / Stop** ボタン
* **状態管理**：`StateFlow` 経由で `TtsUiState` を公開（`isReady`、`hasOffline`、`lastSpeakResult` など）
* **UX**：IME の **Done** で発話開始、発話中はキーボードを自動で閉じる

---

## スクリーンショット（例）

> 画像は省略。起動直後にタイトル「Swahili TTS Demo」、状態チップ、テキスト入力欄、**Speak** / **Stop** ボタンが表示されます。

---

## 要件

* **Android Studio（最新安定版）**
* **JDK 17** 推奨
* **minSdk 23 以上（API 23+）**
* オフライン TTS 利用時：端末に **TTS エンジン**（例：**Google Speech Services**）と **Swahili 音声データ**が必要

> 注：オフライン音声の可用性は端末・OS・TTS エンジンのバージョンに依存します。

---

## セットアップ

1. リポジトリをクローン

   ```bash
   git clone <your-repo-url>
   cd <repo>
   ```

2. Android Studio でプロジェクトを開く

3. 最初の **Gradle Sync** を実行
   依存関係エラーが出る場合は、下記 [依存関係（Compose など）](#依存関係compose-など) を参照

---

## ビルド & 実行

* 接続済みデバイスまたはエミュレータを選択し、**Run ▶︎** をクリック
* 初回はステータスが **“Initializing…”** → 数百 ms 程度で **オフライン可否** が更新
* オフライン音声が未導入の場合、アプリから **音声データのインストール画面** を開く（対応端末のみ）

---

## プロジェクト構成

```
app/
 └─ src/main/java/com/negi/ttsdemo/
    ├─ MainActivity.kt                     // Compose エントリ（画面/ライフサイクル連携）
    ├─ SwahiliTts.kt                       // TTS ユーティリティ（選択/発話/停止/誘導）
    ├─ tts/
    │   └─ TtsDefaults.kt                  // 共有定数（DEFAULT_TTS_TEXT など）
    └─ ui/main/
        ├─ TtsUiState.kt                   // UI ステート（StateFlow で公開）
        └─ TtsViewModel.kt                 // ViewModel（初期化/状態更新/発話制御）
```

> パッケージ構成は一例です。`TtsDefaults.kt` は参照できる任意パッケージで問題ありません。

---

## 主要クラスと設計

### `SwahiliTts`

* `TextToSpeech` を生成・初期化（既定 `preferredEngine = "com.google.android.tts"`）
* **Voice 選択**：

    1. Swahili のみ抽出 → 2) **オフライン優先** → 3) `TZ > KE > other` → 4) **低レイテンシ優先** → 5) **高品質**
* **Locale フォールバック**：Voice 取得不可時は `Locale("sw","TZ")` → `("sw","KE")` → `("sw")` の順に設定
  いずれも未導入なら **音声データのインストール画面** へ誘導
* **発話**：`speak(text, flush)`（初期化前の呼び出しはキューに積み、初期化完了後に再生）
* **停止/解放**：`stop()` / `release()`
* **オフライン有無**：`hasEmbeddedSwahili()` で簡易チェック

### `TtsViewModel`

* 起動時に `SwahiliTts.init()` 実行 → **既定 300ms** 程度の遅延後に `hasEmbeddedSwahili()` を反映
  → `isReady = true` にして UI 操作を許可
* `StateFlow<TtsUiState>` を公開（`text`, `hasOffline`, `lastSpeakResult`, `isReady`）
* `speak()` / `stop()` の UI トリガーを仲介

### `MainActivity` + `TtsScreen`（Compose）

* `collectAsStateWithLifecycle()` で ViewModel の状態を購読
* 画面復帰（`ON_RESUME`）で `refreshVoiceStatus()` を再実行（インストール直後の反映を確実化）
* `OutlinedTextField` の IME **Done** または **Speak** ボタンで発話・フォーカス解除

---

## 依存関係（Compose など）

> **ポイント**：Compose は **BOM（Bill of Materials）** でバージョン整合性を取るのが安全です。
> 以下は概念的な指定です。`<version>` / `<compose-bom-version>` はプロジェクトのバージョンカタログや最新安定版に合わせてください。

```kotlin
// build.gradle.kts（Module: app）
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

> **Unresolved reference**（例：`KeyboardOptions`）が出る場合：
> `ui-text` / `foundation` / `activity-compose` / `lifecycle-runtime-compose` の導入と、**BOM による整合** を確認してください。
> Kotlin/Compose のプラグイン（`alias(libs.plugins.kotlin.compose)` 等）も最新安定版に合わせます。

---

## オフライン音声のインストール手順

端末や OS バージョンにより表示は異なりますが、概ね以下で導入できます。

1. **設定** → **システム** → **言語と入力** → **テキスト読み上げ出力**
2. **優先エンジン**：**Google Speech Services**（もしくは使用中の TTS エンジン）を選択
3. **音声データのインストール**（Install voice data）
4. **Swahili（Kiswahili）** を選択し、**オフライン**音声をダウンロード（`TZ` / `KE` など）
5. アプリに戻り、**Voice 状態** が `offline available` になるか確認

> 一部端末ではアプリからインストール画面を直接開けない場合があります。その場合は設定アプリから手動で導入してください。

---

## 動作確認用：スワヒリ語サンプル文

* `Hujambo! Karibu kwenye programu yetu.`（こんにちは！私たちのアプリへようこそ。）
* `Habari za leo?`（今日はどうですか？）
* `Asante sana kwa kutumia programu hii.`（このアプリをご利用いただきありがとうございます。）
* `Tafadhali bonyeza kitufe cha kuzima ili kusitisha usomaji.`（停止ボタンを押して読み上げを止めてください。）
* `Sauti ya Kiswahili imepakuliwa kwenye kifaa hiki.`（この端末にはスワヒリ語音声がインストールされています。）

---

## トラブルシューティング

* **`KeyboardOptions` などが Unresolved reference**

    * `androidx.compose.ui:ui-text` / `foundation` / `activity-compose` / `lifecycle-runtime-compose` を導入
    * Compose BOM と各アーティファクトのバージョン整合を確認
    * IDE の **Invalidate Caches / Restart**、**Gradle Sync** の再実行

* **オフライン音声が検出されない**

    * インストール後に数秒待機 → アプリに戻って `refreshVoiceStatus()`（画面復帰で自動）
    * 選択中の Voice が **ダウンロード済み** / **インストール済み** か TTS 設定で確認

* **インストール画面が開かない**

    * 一部 OEM で制限あり。**設定アプリから手動導入**してください。

* **発話が途切れる・遅い**

    * バックグラウンド負荷の高いアプリを終了
    * 端末の省電力/電池最適化の影響を確認（TTS エンジンが制限される場合あり）

---

## FAQ

**Q. 特別な権限は必要ですか？**
A. 標準の `TextToSpeech` API の利用に追加権限は不要です（オンライン音声を使う場合はエンジン側のネットワーク使用あり）。

**Q. どの端末でもスワヒリ語のオフライン音声が使えますか？**
A. 可用性は **TTS エンジンのバージョン・端末・地域** に依存します。\*\*「音声データのインストール」\*\*で Swahili が表示されるかご確認ください。

**Q. TZ と KE の違いは？**
A. 一般に **発音・語彙**に差異があります。本デモは `TZ > KE > その他` の順で自動選択しますが、要件に応じてロジックをカスタマイズしてください。

---

## 開発者向けメモ

* **アーキテクチャ**：単一モジュール / MVVM（ViewModel + StateFlow） / Compose UI
* **状態の流れ**：`SwahiliTts` 初期化 → `TtsViewModel` が `hasEmbeddedSwahili()` を反映 → UI が購読
* **遅延の理由**：TTS エンジン初期化直後は Voice 一覧が安定しないことがあるため、**数百 ms** 待機してから判定
* **カスタマイズ例**：

    * 音声選択条件（品質／レイテンシ重み）をアプリ設定に昇格
    * IME アクション（Done 以外）や入力制御の強化
    * 連続発話キュー／一時停止・再開（`QUEUE_ADD` 等）のサポート

---

## 🙏 Credits / 謝辞

* Android SDK, Jetpack Compose, Kotlin team
* Optimized and maintained by **[Ishizuki Tech LLC](https://ishizuki.tech)**

---

## 📬 Contact / お問い合わせ

Developed by **Ishizuki Tech LLC**
Email: **[ishizuki.tech@gmail.com](mailto:ishizuki.tech@gmail.com)**

---

## ライセンス

特に断りがない場合、本リポジトリ内のアプリコードは **MIT License** とします。
（プロジェクト要件に応じて変更してください）
