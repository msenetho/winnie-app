<div align="center">

# Winnie Quotes

A small Android app for browsing, playing, and favouriting Winnie the Pooh voice clips.

![Android](https://img.shields.io/badge/Android-minSdk%2024-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-2.4-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-6750A4?style=for-the-badge)
![Media3](https://img.shields.io/badge/AndroidX%20Media3-34A853?style=for-the-badge)

</div>

> **Disclaimer:** This is a personal learning project and is not affiliated with Disney. Winnie the Pooh and the audio clips belong to their respective copyright holders.

---

## About

Winnie Quotes is an Android app that provides a library of short Winnie the Pooh voice clips. Users can tap a clip to play it and star clips to keep them as favourites.

The app is built on the modern Android stack: Jetpack Compose for the UI, Kotlin Flows for state management, AndroidX Media3 for audio playback, and Preferences DataStore for local persistence. The scope is intentionally small, which keeps the codebase easy to follow and extend.

## What it does today

- **Clip library.** Loads a list of clips from a bundled JSON file and shows them with a title for each.
- **Playback.** Tap a clip to play it, and stop it with the stop button. The clip that is playing is highlighted, and the highlight clears when the clip ends.
- **Favourites.** Star a clip to favourite it. Favourites are saved with Preferences DataStore, so they persist between launches.
- **List and grid layouts.** Switch between them with a segmented button at the top of the screen.
- **Loading and error states.** The screen shows a loading state while clips load and an error message if loading fails.
- **Pooh-themed Material 3 styling.** The palette uses honey gold, Pooh red, Hundred Acre Wood green, and warm parchment.

## Architecture

The app follows a layered, MVVM-style structure. The ViewModel combines several `StateFlow`s (the clips, the favourite IDs, the clip that is playing, the loading and error state, and the layout mode) into a single UI state. The screen only renders that state and reports user actions back up.

```text
UI (Compose)  ─►  ViewModel  ─►  Repository / data sources  ─►  assets, DataStore
     ▲               │
     └── UI state ◄──┘
                     └─►  AudioPlayer (Media3 / ExoPlayer)
```

### Project structure

```text
app/src/main/
├── java/com/msenetho/winnie_app/
│   ├── core/audio/
│   │   ├── AudioPlayer              interface for playback
│   │   └── MediaAudioPlayer         ExoPlayer implementation
│   ├── data/
│   │   ├── clip/
│   │   │   └── AssetClipDataSource  reads voice_clips.json from assets
│   │   └── favourites/
│   │       ├── FavouritesLocalDataSource   DataStore storage
│   │       └── FavouritesRepository
│   ├── domain/model/
│   │   └── VoiceClip
│   ├── ui/
│   │   ├── library/                 screen, ViewModel, UI state, layout mode
│   │   ├── navigation/              AppNavHost and bottom navigation
│   │   └── theme/                   colours, typography, theme
│   └── MainActivity.kt
└── assets/
    ├── audio/                       the .wav clips
    └── voice_clips.json             clip IDs, titles, and file paths
```

## Tech stack

| Area | Tools |
| --- | --- |
| Language | Kotlin |
| UI | Jetpack Compose, Material 3, Navigation Compose |
| Audio | AndroidX Media3 (ExoPlayer) |
| State | ViewModel, Kotlin Coroutines and Flow |
| Storage | Preferences DataStore |
| Build | Gradle (Kotlin DSL) with a version catalog |

## Running the project

1. Clone the repository:

   ```bash
   git clone https://github.com/msenetho/winnie-app.git
   ```

2. Open it in Android Studio and let Gradle sync.
3. Run it on an emulator or a physical device (Android 7.0 / API 24 or higher).

## Roadmap

This is where I'd like to take the app next. I'll pick these up one at a time as I learn the pieces they need.

**Done**

- [x] Clip library with playback
- [x] Favourites that persist
- [x] List and grid layouts
- [x] Themed Material 3 UI and app icon

**In progress**

- [ ] Favourites section

**Coming soon**

- [ ] "About" page, added to the bottom navigation bar
- [ ] Tests for the ViewModel and repositories

## What I'm learning

- Building screens in Jetpack Compose and keeping them stateless
- Modelling UI state with `StateFlow` and `combine`
- ViewModels and a simple repository layer
- Playing audio with Media3 and tying playback state back to the UI
- Persisting data with Preferences DataStore
- Splitting an app into `core`, `data`, `domain`, and `ui` packages

## Screenshots

Coming soon.

## Author

**Matthew Senetho**
Computer Science & Computer Engineering, University of Arkansas
GitHub: [@msenetho](https://github.com/msenetho)

## License

This repository is for learning and portfolio purposes.
