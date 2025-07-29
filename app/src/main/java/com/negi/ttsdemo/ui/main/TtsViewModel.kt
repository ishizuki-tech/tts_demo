package com.negi.ttsdemo.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.negi.ttsdemo.SwahiliTts
import com.negi.ttsdemo.tts.DEFAULT_TTS_TEXT
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * スワヒリ語 TTS を扱う ViewModel。
 *
 * - `SwahiliTts`（アプリ内ユーティリティ）を保持し、UI からの操作（発話・停止）に応答します。
 * - TTS 初期化は非同期なため、`isReady` は「UI が操作してよい目安」を示す簡易フラグです。
 *   実際の初期化完了はエンジン側のコールバックに依存します。
 *
 * **設計メモ:**
 * 本実装では `SwahiliTts` が初期化完了を外部通知しない前提のため、
 * 少し遅延して `refreshVoiceStatus()` を呼び、状態を UI に反映しています。
 * より厳密にするなら、`SwahiliTts` 側で `onReady` コールバックや `Flow` を公開すると堅牢です。
 */
class TtsViewModel(
    application: Application
) : AndroidViewModel(application) {

    companion object {
        /** 既定で使用するエンジン（端末に Google TTS がある前提） */
        private const val DEFAULT_ENGINE = "com.google.android.tts"

        /** 初期化直後に Voice 一覧が安定して取得できるまでの待ち時間（目安） */
        private const val INIT_VOICE_CHECK_DELAY_MS = 300L
    }

    // アプリケーションスコープで TTS を保持（ViewModel のライフサイクルで管理）
    private val swTts = SwahiliTts(application)

    // 内部更新用の MutableStateFlow と、UI 公開用の読み取り専用 StateFlow
    private val _uiState = MutableStateFlow(TtsUiState())
    val uiState: StateFlow<TtsUiState> = _uiState.asStateFlow()

    init {
        // 必要に応じて端末デフォルトへ委ねてもよい（init(null)）。ここでは Google TTS を明示指定。
        swTts.init(preferredEngine = DEFAULT_ENGINE)

        // 初期化は非同期：少し遅延してからオフライン音声の有無をチェックし、UI に反映する。
        viewModelScope.launch {
            delay(INIT_VOICE_CHECK_DELAY_MS)
            refreshVoiceStatus()
            _uiState.update { it.copy(isReady = true) }
        }
    }

    /** テキスト入力の変更を UI から受け取り、状態を更新します。 */
    fun onTextChange(newText: String) {
        _uiState.update { it.copy(text = newText) }
    }

    /**
     * 現在のテキストを発話します。
     *
     * - 空文字の場合は `DEFAULT_TTS_TEXT` を使用します。
     * - 発話の戻り値は `lastSpeakResult` に記録され、UI で成否を参照できます。
     */
    fun speak() {
        val toSpeak = _uiState.value.text.ifBlank { DEFAULT_TTS_TEXT }

        // flush=true: 以前の発話キューをクリアして即座に最新のテキストを発話
        val res = swTts.speak(toSpeak, flush = true)
        _uiState.update { it.copy(lastSpeakResult = res) }
    }

    /** 現在の発話を停止します。 */
    fun stop() {
        swTts.stop()
    }

    /**
     * 端末内の **スワヒリ語オフライン音声**の有無を再評価し、UI 状態へ反映します。
     * - 設定アプリ等で Voice を追加/削除した後の最新化にも利用します。
     */
    fun refreshVoiceStatus() {
        val hasEmbedded = swTts.hasEmbeddedSwahili()
        _uiState.update { it.copy(hasOffline = hasEmbedded) }
    }

    /** ViewModel 破棄時に TTS リソースを必ず解放します。 */
    override fun onCleared() {
        swTts.release()
        super.onCleared()
    }
}
