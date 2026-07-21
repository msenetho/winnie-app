# Winnie the Pooh Quote Library

![Android](https://img.shields.io/badge/Android-API%2036+-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-2.2-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-6750A4?style=for-the-badge)
![Media3](https://img.shields.io/badge/AndroidX%20Media3-34A853?style=for-the-badge)

An Android application for browsing and playing a curated collection of Winnie the Pooh voice clips. Built using modern Android development practices including Jetpack Compose, AndroidX Media3, DataStore, and an MVVM-inspired architecture.

> **Note:** This project was created for educational and portfolio purposes and is not affiliated with Disney.

---

## Features

### Quote Library

- Browse a collection of Winnie the Pooh quotes
- Play audio clips instantly
- Clean Material 3 interface

### Favorites

- Mark clips as favorites
- Persistent storage using Preferences DataStore
- Favorites remain after closing the app

### Multiple Layouts

- List View
- Grid View
- Easily switch between layouts

### Audio Playback

- Powered by AndroidX Media3 (ExoPlayer)
- Plays locally stored audio assets
- Handles playback state and switching between clips

---

# Screenshots (TBD)

# Tech Stack

## Language

- Kotlin

## UI

- Jetpack Compose
- Material 3
- Navigation Compose

## Audio

- AndroidX Media3
- ExoPlayer

## Storage

- Preferences DataStore

## Build Tools

- Gradle
- Version Catalogs

---

# Project Structure

```text
app/
│
├── core/
│   └── audio/
│       ├── AudioPlayer
│       └── MediaAudioPlayer
│
├── data/
│   ├── clip/
│   │   └── AssetClipDataSource
│   ├── favourites/
│   │   ├── FavouritesLocalDataSource
│   │   └── FavouritesRepository
│   └── domain/
│       └── VoiceClip
│
├── ui/
│   ├── library/
│   │   ├── ClipLibraryScreen
│   │   ├── ClipLibraryViewModel
│   │   ├── ClipLibraryUIState
│   │   ├── ClipSectionHeader
│   │   └── ViewMode
│   ├── navigation/
│   └── theme/
│
├── assets/
│   ├── audio/
│   └── voice_clips.json
│
└── MainActivity.kt
```

---

# Running the Project

Clone the repository.

```bash
git clone https://github.com/msenetho/winnie-app.git
```

Open the project in Android Studio.

Allow Gradle to sync and build the project.

Run the application on an Android emulator or physical device.

---

# Future Improvements

- Search and filtering
- Playlist support
- Recently played section
- Material You dynamic colors
- Share favorite quotes
- Downloadable clip packs
- Custom categories
- Widget support
- Cloud synchronization

---

# What I Learned

This project helped strengthen my understanding of:

- Kotlin
- Jetpack Compose
- State management in Compose
- MVVM architecture
- AndroidX Media3
- Repository pattern
- Preferences DataStore
- Managing local assets
- Building reusable UI components

---

# Acknowledgements

- Android Jetpack
- AndroidX Media3
- Winnie the Pooh characters belong to their respective copyright holders.

---

# Author

**Matthew Senetho**

Computer Science & Computer Engineering

University of Arkansas

GitHub: https://github.com/msenetho

---

# License

This repository is intended for educational and portfolio purposes.
