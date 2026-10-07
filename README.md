# HaseebFlix 🎬

**HaseebFlix** is a modern, feature-rich Android Movies & TV Shows discovery and streaming application built with cutting-edge Android development technologies, Jetpack Compose, and Clean Architecture principles.

![HaseebFlix Banner](docs/images/cinemax-splash.svg)

---

## ✨ Features

- **Explore & Discover**: Browse popular, top-rated, upcoming, and airing today movies and TV shows.
- **Advanced Search**: Instantly find your favorite films and series with real-time search capabilities.
- **Detailed Views**: Dive deep into movie and TV show details, including synopses, release dates, ratings, cast & crew, and related recommendations.
- **Built-in Video Player**: Seamlessly watch trailers and media streams using the integrated player feature.
- **Wishlist & Favorites**: Save movies and TV shows to your watchlist for quick access, powered locally by Room Database.
- **Settings & Customization**: Configure app preferences and enjoy a sleek dark theme UI designed with a custom Material-based design system.

---

## 🛠️ Tech Stack & Architecture

- **UI**: 100% [Jetpack Compose](https://developer.android.com/jetpack/compose) with a custom design system.
- **Architecture**: Clean Architecture following official [Android Architecture Guidance](https://developer.android.com/topic/architecture).
- **Modularization**: Fully modularized codebase separating features, core infrastructure, data, domain, and UI layers.
- **Dependency Injection**: [Dagger Hilt](https://dagger.dev/hilt/).
- **Asynchronicity**: Kotlin Coroutines & Flow.
- **Local Storage**: [Room Database](https://developer.android.com/training/data-storage/room) & Jetpack DataStore.
- **Networking**: Retrofit with Kotlin Serialization.
- **Performance**: Baseline Profiles support for optimized app startup.

---

## 🚀 Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/haseebno1/Haseeb-Flix.git
   ```
2. **Obtain an API Key**:
   - Generate an API key from [The Movie Database (TMDB)](https://www.themoviedb.org/).
3. **Configure `local.properties`**:
   - Create or update `local.properties` in the root directory and add your API key:
     ```properties
     cinemax.apikey=YOUR_TMDB_API_KEY_HERE
     ```
   - Optionally, configure the custom player base URL:
     ```properties
     cinemax.player.baseurl=https://embed.vidrift.net
     ```
4. **Build and Run**:
   - Open the project in the latest stable version of [Android Studio](https://developer.android.com/studio).
   - Sync Gradle and run the `debug` build variant on an emulator or physical device.

---

## 📦 Project Structure

```text
HaseebFlix/
├── app/                  # Main application module & navigation graph
├── core/
│   ├── core-common/      # Common utilities and extensions
│   ├── core-data/        # Repositories and data sources implementation
│   ├── core-database/    # Room database entities and DAOs
│   ├── core-datastore/   # Preferences DataStore
│   ├── core-network/     # Retrofit client and network models
│   ├── core-domain/      # Use cases and domain models
│   ├── core-model/       # Shared models
│   ├── core-ui/          # Reusable Compose UI components
│   ├── core-designsystem/# Theme, colors, typography, and shapes
│   └── core-navigation/  # Navigation routes and graphs
└── features/
    ├── feature-home/     # Home screen feature
    ├── feature-search/   # Search screen feature
    ├── feature-wishlist/ # Watchlist screen feature
    ├── feature-settings/ # Settings screen feature
    ├── feature-list/     # Media list screen feature
    ├── feature-details/  # Media details screen feature
    └── feature-player/   # Video player feature
```

---

## 📄 License

```
Copyright 2022-2025 HaseebFlix / Afig Aliyev

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
