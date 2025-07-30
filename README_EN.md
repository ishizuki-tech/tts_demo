# Swahili TTS Demo (Android / Jetpack Compose)

A minimal demo that reads **Kiswahili (Swahili) text with on‑device TTS (offline‑first)**.
UI and state management are implemented with **Jetpack Compose + ViewModel (StateFlow)**.
Initialization, voice selection, and speech control for `TextToSpeech` are encapsulated in the `SwahiliTts` utility.

---

## Table of Contents

* [Features](#features)
* [Screenshots (example)](#screenshots-example)
* [Requirements](#requirements)
* [Setup](#setup)
* [Build & Run](#build--run)
* [Project Structure](#project-structure)
* [Key Classes & Design](#key-classes--design)
* [Dependencies (Compose, etc.)](#dependencies-compose-etc)
* [Installing Offline Voices](#installing-offline-voices)
* [Sample Swahili Phrases](#sample-swahili-phrases)
* [Troubleshooting](#troubleshooting)
* [FAQ](#faq)
* [Developer Notes](#developer-notes)
* [🙏 Credits](#-credits)
* [📬 Contact](#-contact)
* [License](#license)

---

## Features

* **Swahili TTS**: Automatically selects a Swahili voice with an **offline‑first** policy
  Priority: `TZ (Tanzania) > KE (Kenya) > others` → **lower‑latency** → **higher‑quality**
* **Install Flow**: If a suitable voice is missing, the app navigates to the **Install voice data** screen (on supported devices)
* **Compose UI**: Text input, status indicators (offline availability), and **Speak / Stop** buttons
* **State Management**: Exposes `TtsUiState` via `StateFlow` (`isReady`, `hasOffline`, `lastSpeakResult`, etc.)
* **UX**: IME **Done** starts speech; the keyboard closes while speaking

---

## Screenshots (example)

> Images omitted. On launch you’ll see the **Swahili TTS Demo** title, a status chip, a text field, and **Speak** / **Stop** buttons.

---

## Requirements

* **Android Studio** (latest stable)
* **JDK 17** recommended
* **minSdk 23+** (API 23 or higher)
* For offline TTS: a **TTS engine** (e.g., **Google Speech Services**) and **Swahili voice data** installed on the device

> **Note:** Availability of offline voices depends on device, OS version, and the TTS engine build.

---

## Setup

1. Clone the repository

   ```bash
   git clone <your-repo-url>
   cd <repo>
   ```

2. Open the project in Android Studio.

3. Run the initial **Gradle Sync**.
   If dependency resolution fails, see [Dependencies (Compose, etc.)](#dependencies-compose-etc).

---

## Build & Run

* Select a connected device or emulator and click **Run ▶︎**.
* On first launch, the status shows **“Initializing…”**; within a few hundred milliseconds, **offline availability** is updated.
* If offline voices are missing, the app will prompt the **TTS voice data** install screen (on supported devices).

---

## Project Structure

```
app/
 └─ src/main/java/com/negi/ttsdemo/
    ├─ MainActivity.kt                     // Compose entry point (screen & lifecycle)
    ├─ SwahiliTts.kt                       // TTS utility (selection / speak / stop / install redirect)
    ├─ tts/
    │   └─ TtsDefaults.kt                  // Shared constants (e.g., DEFAULT_TTS_TEXT)
    └─ ui/main/
        ├─ TtsUiState.kt                   // UI state (exposed via StateFlow)
        └─ TtsViewModel.kt                 // ViewModel (init / state updates / playback control)
```

> The package layout above is an example. You can place `TtsDefaults.kt` in any module‑visible package.

---

## Key Classes & Design

### `SwahiliTts`

* Creates and initializes `TextToSpeech` (defaults to `preferredEngine = "com.google.android.tts"`).
* **Voice selection** pipeline:

    1. Filter to Swahili voices → 2) **Offline‑first** → 3) `TZ > KE > others` → 4) **lower‑latency** → 5) **higher‑quality**.
* **Locale fallback**: If no voice object is found, tries `Locale("sw","TZ")` → `("sw","KE")` → `("sw")`.
  If still unavailable, opens the **Install voice data** screen.
* **Playback**: `speak(text, flush)` queues calls invoked before initialization and plays them once ready.
* **Stop / Release**: `stop()` / `release()`.
* **Offline presence**: `hasEmbeddedSwahili()` provides a quick availability check.

### `TtsViewModel`

* Calls `SwahiliTts.init()` on app start and, after a short delay (default **\~300 ms**), reflects `hasEmbeddedSwahili()`.
  Sets `isReady = true` to enable UI interactions.
* Exposes `StateFlow<TtsUiState>` (`text`, `hasOffline`, `lastSpeakResult`, `isReady`).
* Mediates UI triggers for `speak()` / `stop()`.

### `MainActivity` + `TtsScreen` (Compose)

* Subscribes to ViewModel state with `collectAsStateWithLifecycle()`.
* On `ON_RESUME`, calls `refreshVoiceStatus()` to ensure newly installed voices are detected.
* IME **Done** on `OutlinedTextField` or pressing **Speak** triggers speech and clears focus.

---

## Dependencies (Compose, etc.)

> **Tip:** Use the **Compose BOM** to keep versions consistent.
> Replace `<version>` / `<compose-bom-version>` with your version catalog or current stable releases.

```kotlin
// build.gradle.kts (Module: app)
dependencies {
    // Compose BOM (recommended)
    implementation(platform("androidx.compose:compose-bom:<compose-bom-version>"))

    // Core Compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.ui:ui-text")
    implementation("androidx.compose.runtime:runtime-saveable")

    // Activity / Lifecycle (for collectAsStateWithLifecycle, etc.)
    implementation("androidx.activity:activity-compose:<version>")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:<version>")

    // Kotlin Coroutines (Android)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:<version>")
}
```

> If you encounter **Unresolved reference** (e.g., `KeyboardOptions`), verify that `ui-text`, `foundation`, `activity-compose`, and `lifecycle-runtime-compose` are included and aligned by the BOM.
> Also ensure your Kotlin/Compose Gradle plugins (e.g., `alias(libs.plugins.kotlin.compose)`) match the chosen versions.

---

## Installing Offline Voices

Exact UI labels vary by device/OS, but generally:

1. **Settings** → **System** → **Languages & input** → **Text‑to‑speech output**
2. **Preferred engine**: choose **Google Speech Services** (or your TTS engine)
3. Tap **Install voice data**
4. Select **Swahili (Kiswahili)** and download the **offline** package (e.g., `TZ` / `KE`)
5. Return to the app and confirm the **Voice status** shows `offline available`

> Some devices block programmatic navigation to the install screen. If so, install voices manually via Settings.

---

## Sample Swahili Phrases

* `Hujambo! Karibu kwenye programu yetu.` (Hello! Welcome to our app.)
* `Habari za leo?` (How are you today?)
* `Asante sana kwa kutumia programu hii.` (Thank you very much for using this app.)
* `Tafadhali bonyeza kitufe cha kuzima ili kusitisha usomaji.` (Please press the stop button to end the reading.)
* `Sauti ya Kiswahili imepakuliwa kwenye kifaa hiki.` (The Swahili voice is installed on this device.)

---

## Troubleshooting

* **“Unresolved reference: KeyboardOptions” (or other Compose symbols)**

    * Include `androidx.compose.ui:ui-text`, `foundation`, `activity-compose`, and `lifecycle-runtime-compose`.
    * Ensure your Compose BOM aligns all Compose artifacts.
    * Try **Invalidate Caches / Restart** in Android Studio and re‑run **Gradle Sync**.

* **Offline voice not detected**

    * After installation, wait a few seconds, then return to the app (or rely on the **resume** path) so `refreshVoiceStatus()` can update state.
    * In the TTS settings, confirm the selected voice is **downloaded** and **installed**.

* **Cannot open the install screen from the app**

    * Some OEM builds restrict this. Open **Settings → Text‑to‑speech output → Install voice data** manually.

* **Speech stutters or is slow**

    * Close background apps with heavy load.
    * Check battery optimization or power‑saving settings; some devices throttle the TTS engine in the background.

---

## FAQ

**Q. Do I need special permissions?**
A. No additional app permissions are required to use the standard `TextToSpeech` API. (Online voices may use network access on the engine side.)

**Q. Is Swahili offline voice available on all devices?**
A. Availability depends on the **TTS engine version, device model, and region**. Check **Install voice data** for **Swahili** in your device settings.

**Q. What’s the difference between TZ and KE voices?**
A. Pronunciation and vocabulary can differ. This demo uses `TZ > KE > others` by default, but you can tailor the selection logic for your needs.

**Q. Can I use other TTS engines?**
A. Yes. Update `preferredEngine` and adjust voice selection to match the engine’s voice IDs and capabilities.

---

## Developer Notes

* **Architecture**: single module / MVVM (ViewModel + StateFlow) / Compose UI.
* **State flow**: `SwahiliTts` initializes → `TtsViewModel` reflects `hasEmbeddedSwahili()` → UI subscribes.
* **Why the short delay?** Immediately after engine init, voice lists can be unstable; a **few hundred ms** helps ensure accurate detection.
* **Customization ideas**:

    * Expose voice selection weights (quality vs latency) as app settings.
    * Support continuous queueing / pause / resume (`QUEUE_ADD`, etc.).
    * Extend IME actions or input validation to fit your use case.

---

## 🙏 Credits

* Android SDK, Jetpack Compose, Kotlin team
* Optimized and maintained by **[Ishizuki Tech LLC](https://ishizuki.tech)**

---

## 📬 Contact

Developed by **Ishizuki Tech LLC**
Email: **[ishizuki.tech@gmail.com](mailto:ishizuki.tech@gmail.com)**

---

## License

Unless otherwise noted, the app code in this repository is released under the **MIT License**.
(Adjust to your project’s requirements as needed.)
