plugins {
    // Version Catalog（libs.versions.toml）のプラグイン定義を利用
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose) // Compose plugin（K2/Compose Compilerの整合を自動で担保）
}

android {
    namespace = "com.negi.ttsdemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.negi.ttsdemo"
        minSdk = 25
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // デモ用途のため minify 無効（本番は有効化＋ProGuard設定推奨）
            isMinifyEnabled = false

            // 既定のProGuard最適化 + プロジェクト用ルール
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // ひとまず debug 証明書で署名（デモ/社内配布想定）
            // 本番では release 用 keystore を設定してください
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    // Java/Kotlin のターゲットを統一（Compose/Kotlin 2.x 系と相性良い 11）
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    // Compose を有効化
    buildFeatures {
        compose = true
    }

    // ※ kotlin.compose プラグインを使っているため、通常は composeOptions の個別指定は不要
    //   （Catalog で Compose Compiler が管理されます）
    // composeOptions { kotlinCompilerExtensionVersion = "..." }
}

dependencies {
    // ---------------------------------------------------------------------------------------------
    // Compose は「BOMを1つだけ」使って各モジュールのバージョン整合性を取る
    // Version Catalog の BOM を採用（重複定義を避けるため、手書きのBOMは削除）
    // ---------------------------------------------------------------------------------------------
    implementation(platform(libs.androidx.compose.bom))

    // ─────────────────────────────
    // 基本UI（Version Catalog）
    // ─────────────────────────────
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)                    // compose-ui
    implementation(libs.androidx.ui.graphics)           // 描画まわり
    implementation(libs.androidx.ui.tooling.preview)    // Preview 用
    implementation(libs.androidx.material3)             // Material3 コンポーネント

    // ─────────────────────────────
    // Compose 追加モジュール（Catalogに無ければ座標を直書き）
    // ─────────────────────────────
    // KeyboardOptions / KeyboardType などを提供（import androidx.compose.ui.text.input.*）
    implementation("androidx.compose.ui:ui-text")
    // スクロール・レイアウト基盤等（必要に応じて）
    implementation("androidx.compose.foundation:foundation")
    // rememberSaveable のため（import androidx.compose.runtime.saveable.*）
    implementation("androidx.compose.runtime:runtime-saveable")

    // ─────────────────────────────
    // Activity / Lifecycle / Coroutines
    // ─────────────────────────────
    implementation("androidx.activity:activity-ktx:1.9.3")                 // 標準 Activity 拡張
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // Lifecycle（Composeと併用する場合は runtime-compose も）
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")

    // ─────────────────────────────
    // テスト
    // ─────────────────────────────
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // AndroidTest でも Compose の BOM を共有
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // ツール系（デバッグ時のみ）
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
