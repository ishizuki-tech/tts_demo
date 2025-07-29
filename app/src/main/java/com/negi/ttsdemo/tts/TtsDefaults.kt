package com.negi.ttsdemo.tts

/**
 * TTS 周りで共有する既定値。
 *
 * - `internal` は「同一モジュール内のみ可視」を意味します。
 *   app モジュール内でしか使わない想定なら `internal` のままで OK です。
 *   複数モジュールから参照するなら `internal` を外す or object にすることを検討してください。
 */
internal const val DEFAULT_TTS_TEXT: String = "Hujambo! Karibu kwenye programu yetu."
