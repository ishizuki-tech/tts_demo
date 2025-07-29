package com.negi.ttsdemo

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import java.util.Locale

/**
 * スワヒリ語（Swahili/Kiswahili）向けの TTS ユーティリティ。
 *
 * - Google TTS など任意のエンジンで初期化し、可能なら **オフライン音声**を優先選択します。
 * - 端末の Voice が未導入の場合は、インストール画面への誘導を行います。
 * - 初期化前に `speak()` が呼ばれても、完了後に自動で再生します（簡易キュー）。
 *
 * 使用手順（例）:
 * ```
 * val tts = SwahiliTts(context)
 * tts.init(preferredEngine = "com.google.android.tts") // null で端末デフォルト
 * // 初期化完了後に speak()
 * ```
 */
class SwahiliTts(private val context: Context) : TextToSpeech.OnInitListener {

    // ---- 定数 / 共有設定 -----------------------------------------------------

    companion object {
        /** 明示指定しない場合のデフォルト TTS エンジン（端末に GoogleTTS がある前提） */
        private const val DEFAULT_ENGINE = "com.google.android.tts"

        /** デフォルトの発話テキスト（必要な場面でのフォールバック用） */
        private const val DEFAULT_TEXT = "Hujambo! Karibu kwenye programu yetu."

        /** 発話 ID の接頭辞（追跡しやすいよう時刻と併用） */
        private const val UTT_PREFIX = "utt-"

        /** 優先するロケールの順序（TZ > KE > 言語のみ） */
        private val PREFERRED_LOCALES = listOf(
            Locale("sw", "TZ"),
            Locale("sw", "KE"),
            Locale("sw")
        )
    }

    // ---- フィールド ----------------------------------------------------------

    private val appCtx = context.applicationContext
    private var tts: TextToSpeech? = null

    /** init/onInit 完了を示す内部フラグ（UI 可視化用に getter も用意） */
    private var ready: Boolean = false

    /** 初期化完了前に渡されたテキストを一時保持（完了後に自動再生） */
    private var pendingText: String? = null

    /** 優先するエンジンのパッケージ（インストール誘導時に使用） */
    private var enginePackage: String? = null

    // ---- 初期化 --------------------------------------------------------------

    /**
     * TTS を初期化。
     *
     * @param preferredEngine 例: `"com.google.android.tts"`。`null` で端末デフォルトを使用。
     */
    fun init(preferredEngine: String? = DEFAULT_ENGINE) {
        enginePackage = preferredEngine
        tts = if (preferredEngine != null) {
            TextToSpeech(appCtx, this, preferredEngine)
        } else {
            TextToSpeech(appCtx, this)
        }
    }

    /**
     * `TextToSpeech.OnInitListener` 実装。
     * - 成功時：スワヒリ語の Voice を **オフライン優先**で選択。なければ `Locale` 設定へ。
     * - 未導入の場合：音声データのインストール画面へ誘導。
     * - 以降、発話レート・ピッチ・オーディオ属性・UPL を設定。
     */
    override fun onInit(status: Int) {
        if (status != TextToSpeech.SUCCESS) {
            ready = false
            return
        }

        val engine = tts ?: run {
            ready = false
            return
        }

        // 1) Swahili の Voice をオフライン優先で選択
        val best = selectBestSwahiliVoice(engine)
        if (best != null) {
            engine.voice = best
        } else {
            // 2) Voice が得られない端末では Locale 経由で言語設定（必要ならインストール誘導）
            if (!setSwahiliByLocale(engine)) return
        }

        // 基本の読み上げパラメータ
        engine.setSpeechRate(1.0f)
        engine.setPitch(1.0f)

        // 再生先の性質（通知/アシスト/メディア等）をヒントとして付与
        engine.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
        )

        // 発話進捗のコールバック（必要に応じて外部にコールバックで中継可）
        engine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) { /* no-op */ }
            override fun onDone(utteranceId: String?) { /* no-op */ }
            override fun onError(utteranceId: String?) { /* no-op */ }
        })

        ready = true

        // 初期化前に要求された発話をここで実行
        pendingText?.let {
            speak(it)
            pendingText = null
        }
    }

    // ---- Voice / Locale 選択 -------------------------------------------------

    /**
     * スワヒリ語の Voice を **オフライン優先**で選択。
     * - オフライン > 国（TZ > KE > それ以外）> 低レイテンシ > 高品質 の順で優先。
     */
    private fun selectBestSwahiliVoice(engine: TextToSpeech): Voice? {
        val voices = engine.voices ?: return null
        return voices.asSequence()
            .filter { it.locale.language.equals("sw", ignoreCase = true) }
            .sortedWith(
                compareByDescending<Voice> { !it.isNetworkConnectionRequired } // オフライン優先
                    .thenByDescending {
                        when (it.locale.country.uppercase(Locale.ROOT)) {
                            "TZ" -> 2
                            "KE" -> 1
                            else -> 0
                        }
                    }
                    .thenBy { it.latency }            // 小さいほど良い（低レイテンシ優先）
                    .thenByDescending { it.quality }  // 大きいほど良い（高品質優先）
            )
            .firstOrNull()
    }

    /**
     * Locale ベースで Swahili を設定。必要なら音声データのインストールを誘導。
     *
     * @return true: 設定成功 / false: 未導入のため誘導した・または設定不可
     */
    private fun setSwahiliByLocale(engine: TextToSpeech): Boolean {
        for (loc in PREFERRED_LOCALES) {
            val avail = engine.isLanguageAvailable(loc)
            if (avail >= TextToSpeech.LANG_AVAILABLE) {
                val r = engine.setLanguage(loc)
                if (r >= TextToSpeech.LANG_AVAILABLE) return true
                if (r == TextToSpeech.LANG_MISSING_DATA) {
                    promptInstall(engine)
                    return false
                }
            } else if (avail == TextToSpeech.LANG_MISSING_DATA) {
                promptInstall(engine)
                return false
            }
        }
        return false
    }

    /**
     * 音声データのインストール画面へ誘導。
     * - 可能なら現在使用中のエンジンのパッケージを明示指定。
     * - 一部端末/エンジンでは UI が用意されていない場合があるため try-catch。
     */
    private fun promptInstall(engine: TextToSpeech) {
        val pkg = enginePackage ?: engine.defaultEngine ?: DEFAULT_ENGINE
        val intent = Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            setPackage(pkg)
        }
        try {
            appCtx.startActivity(intent)
        } catch (_: Exception) {
            // 一部端末は UI 非対応（サイレントに無視）
        }
    }

    // ---- 状態確認 ------------------------------------------------------------

    /**
     * スワヒリ語の **オフライン（埋め込み）音声**が利用可能かの簡易判定。
     */
    fun hasEmbeddedSwahili(): Boolean {
        val engine = tts ?: return false
        val voices = engine.voices ?: return false
        return voices.any { v ->
            v.locale.language.equals("sw", ignoreCase = true) &&
                    !v.isNetworkConnectionRequired
        }
    }

    // ---- 再生制御 ------------------------------------------------------------

    /**
     * 発話要求。初期化前に呼ばれた場合は内部キューに保持し、初期化完了後に再生。
     *
     * @param text 読み上げる文字列（空文字の場合はフォールバック文を使用）
     * @param flush true: 直前のキューを破棄して即時再生 / false: キューに追加
     * @return `TextToSpeech.SUCCESS` または `TextToSpeech.ERROR`
     */
    fun speak(text: String, flush: Boolean = true): Int {
        val engine = tts ?: return TextToSpeech.ERROR

        // 初期化がまだなら pending に格納して成功扱い（後で onInit から実行）
        if (!ready) {
            pendingText = text.ifBlank { DEFAULT_TEXT }
            return TextToSpeech.SUCCESS
        }

        val mode = if (flush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
        val toSpeak = text.ifBlank { DEFAULT_TEXT }

        return engine.speak(toSpeak, mode, /* params */ null, UTT_PREFIX + System.currentTimeMillis())
    }

    /** 現在の発話を停止します。 */
    fun stop() {
        tts?.stop()
    }

    /** TTS リソースを解放します（必ずライフサイクルに応じて呼び出すこと）。 */
    fun release() {
        ready = false
        tts?.shutdown()
        tts = null
    }
}
