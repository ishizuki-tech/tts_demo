// =============================================================================
// settings.gradle.kts
// 役割：ビルド全体の「入口」。プラグイン取得先／依存解決先／モジュール構成を定義。
// - プラグインの取得先は pluginManagement で定義
// - 依存ライブラリの取得先は dependencyResolutionManagement で定義
// - RepositoriesMode を FAIL_ON_PROJECT_REPOS にして “各モジュールでの独自リポジトリ定義” を禁止
// =============================================================================

pluginManagement {
    repositories {
        // Google の公式リポジトリ（Android Gradle Plugin や AndroidX など）
        google {
            // content フィルタで対象グループを限定し、無駄な探索を抑制 & セキュリティ/速度を改善
            content {
                includeGroupByRegex("com\\.android.*")  // 例: com.android.tools.build:gradle
                includeGroupByRegex("com\\.google.*")   // 例: com.google.dagger, com.google.gms など
                includeGroupByRegex("androidx.*")       // 例: androidx.compose.*, androidx.activity など
            }
        }

        // Kotlin Gradle Plugin 等、Google 以外のプラグインはここから解決
        mavenCentral()

        // Gradle Plugin Portal（多くの Gradle プラグインの配布源）
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    // 各モジュール(build.gradle)での独自 repositories 定義を禁止し、ここで一元管理
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        // AndroidX / Compose / Play Services など
        google()

        // OSS ライブラリの基本
        mavenCentral()

        // （任意）GitHub上の一部ライブラリやフォークを使う場合のみ有効化
        // maven("https://jitpack.io")
    }
}

// ルートプロジェクト名（IDEの表示や生成物の既定名に影響）
rootProject.name = "TtsDemo"

// 参加させるモジュールを列挙
include(":app")
// 例：モジュールを増やす場合
// include(":core:ui", ":core:network", ":feature:tts")
