package com.negi.ttsdemo.ui.main

import com.negi.ttsdemo.tts.DEFAULT_TTS_TEXT

/**
 * UI に渡す Text-to-Speech の状態を保持するデータクラス。
 *
 * @property text
 *   直近で発話させるテキスト（未入力の場合はデフォルトのサンプル文を使用）
 * @property hasOffline
 *   端末内に **オフライン（埋め込み）スワヒリ語音声**が存在するかどうか
 * @property lastSpeakResult
 *   直前の `TextToSpeech.speak()` の戻り値（`TextToSpeech#SUCCESS` 等）。未実行時は `null`
 * @property isReady
 *   TTS エンジンの初期化が UI 的に扱える状態になったかの簡易フラグ
 */

data class TtsUiState(
    val text: String = DEFAULT_TTS_TEXT,
    val hasOffline: Boolean = false,
    val lastSpeakResult: Int? = null,
    val isReady: Boolean = false
)
