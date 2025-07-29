package com.negi.ttsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.negi.ttsdemo.ui.main.TtsUiState
import com.negi.ttsdemo.ui.main.TtsViewModel

/**
 * メインアクティビティ。
 *
 * - ViewModel の状態を Compose にバインド
 * - アプリ復帰（ON_RESUME）でオフライン音声の有無を再チェック
 * - 初回表示時にも即座に状態を反映
 */
class MainActivity : ComponentActivity() {

    // Activity のライフサイクルに紐づく ViewModel
    private val vm: TtsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                // ライフサイクルに追従して StateFlow を Collect（安全なキャンセル/再開を自動処理）
                val uiState by vm.uiState.collectAsStateWithLifecycle()

                // 初回表示時に即チェック（インストール前後での表示差異を早めに反映）
                LaunchedEffect(Unit) {
                    vm.refreshVoiceStatus()
                }

                // アプリ復帰時（フォアグラウンド戻り）にオフライン音声の有無を再チェック
                val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
                DisposableEffect(lifecycleOwner) {
                    val obs = LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_RESUME) {
                            vm.refreshVoiceStatus()
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(obs)
                    onDispose { lifecycleOwner.lifecycle.removeObserver(obs) }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TtsScreen(
                        ui = uiState,
                        onTextChange = vm::onTextChange,
                        onSpeak = vm::speak,
                        onStop = vm::stop
                    )
                }
            }
        }
    }
}

/**
 * TTS 画面コンポーザブル。
 *
 * @param ui           ViewModel からの状態（テキスト・オフライン可否・準備完了など）
 * @param onTextChange ユーザー入力テキストの更新ハンドラ
 * @param onSpeak      発話トリガー（ボタン/IME Doneから呼ばれる）
 * @param onStop       停止トリガー
 */
@Composable
private fun TtsScreen(
    ui: TtsUiState,
    onTextChange: (String) -> Unit,
    onSpeak: () -> Unit,
    onStop: () -> Unit
) {
    // キーボードの Done 時にフォーカスを外すための FocusManager
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Swahili TTS Demo",
            style = MaterialTheme.typography.titleLarge
        )

        // ==== 状態表示：オフライン音声の有無 =====================================
        val statusText = when {
            !ui.isReady   -> "Initializing…"
            ui.hasOffline -> "Voice: Swahili (offline available)"
            else          -> "Voice: Swahili (online synthesis or not installed)"
        }
        AssistChipRow(statusText = statusText)

        // ==== 入力：TextFieldValue を rememberSaveAble でプロセス再生成にも耐性 ====
        // ViewModel 側の文字列と双方向同期するため、ui.text 変化時に TextFieldValue も更新
        var tfState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(ui.text))
        }
        LaunchedEffect(ui.text) {
            if (tfState.text != ui.text) {
                tfState = tfState.copy(text = ui.text)
            }
        }

        OutlinedTextField(
            value = tfState,
            onValueChange = { v ->
                tfState = v
                onTextChange(v.text)
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Text (Swahili)") },
            minLines = 3,
            // IME の Done で発話 + フォーカス解除
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onSpeak()
                }
            )
        )

        // ==== 操作ボタン =======================================================
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    focusManager.clearFocus()
                    onSpeak()
                },
                enabled = ui.isReady
            ) {
                Text("Speak")
            }

            OutlinedButton(onClick = onStop) {
                Text("Stop")
            }
        }

        // ==== デバッグ表示（必要に応じて） =====================================
        ui.lastSpeakResult?.let { code ->
            Text(
                text = "lastSpeakResult: $code",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // ==== 簡易ヘルプ =======================================================
        Text(
            text = "If Swahili voice isn't offline, the app may have prompted to install TTS data. " +
                    "After installation, return to the app to re-check.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

/**
 * 状態表示用のシンプルな AssistChip 群。
 * 現状は 1 つだけだが、将来項目が増えた場合でも Row 内に並べられる。
 */
@Composable
private fun AssistChipRow(statusText: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AssistChip(
            onClick = { /* 情報表示のみなので no-op */ },
            label = { Text(statusText) }
        )
    }
}
