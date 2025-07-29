// =============================================================================
// Root build.gradle.kts
// 役割：プロジェクト全体（全モジュール）に共通する “ビルド設定の入口”
// - ここでは「プラグインの可視化」のみ行い、実適用は各モジュール側で行います（apply false）
// - リポジトリやバージョン管理は、原則 settings.gradle.kts / libs.versions.toml に集約
// =============================================================================

plugins {
    // Version Catalog のプラグイン参照（libs.versions.toml）
    // ルートでは適用せず、各モジュール（app など）で必要時に apply
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android)      apply false
    alias(libs.plugins.kotlin.compose)      apply false
}

// =============================================================================
// 共通のタスクや将来的な共通設定（必要になったら使う）
// =============================================================================

// IDE から実行しやすいよう、ルートに clean タスクを定義
// → gradlew clean でルートの build/ を削除（各モジュールの clean は各自が持ちます）
tasks.register<Delete>("clean") {
    description = "Deletes the root project's build directory."
    delete(layout.buildDirectory)
}

// subprojects { ... } に共通設定を集約することもできますが、
// Gradle 8+ では「repositories は settings.gradle.kts へ」寄せるのが推奨です。
// Kotlin/Compose のコンパイラフラグ等も、基本は各モジュールの build.gradle.kts に置くのが安全です。
//
// 例（必要になったら雛形として使用）:
// subprojects {
//     // ここに “全モジュール共通” のタスクやチェックを追加できます。
//     // ただし設定過剰はビルド時間増の原因になるため、最小主義がベターです。
// }
