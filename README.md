<p align="center">
  <img src="assets/logo.png" alt="Haseeb-Flix logo" width="140" />
</p>

<h1 align="center">Haseeb-Flix</h1>

<p align="center">
  A sleek, dark-themed Android app for discovering movies and TV shows, built with Kotlin and Jetpack Compose and powered by the TMDB API.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white" alt="Android" />
  <img src="https://img.shields.io/badge/Kotlin-Jetpack%20Compose-7F52FF?logo=kotlin&logoColor=white" alt="Kotlin / Jetpack Compose" />
  <img src="https://img.shields.io/badge/API-TMDB-01B4E4" alt="TMDB API" />
  <img src="https://img.shields.io/badge/Version-1.0.6-E50914" alt="Version 1.0.6" />
</p>

---

## 📱 Screenshots

<table>
  <tr>
    <td align="center"><img src="screenshots/01_home.jpg" width="200" /><br /><sub><b>Home</b></sub></td>
    <td align="center"><img src="screenshots/02_movie_details.jpg" width="200" /><br /><sub><b>Movie Details</b></sub></td>
    <td align="center"><img src="screenshots/03_discover.jpg" width="200" /><br /><sub><b>Discover</b></sub></td>
    <td align="center"><img src="screenshots/04_search_movies.jpg" width="200" /><br /><sub><b>Search – Movies</b></sub></td>
  </tr>
  <tr>
    <td align="center"><img src="screenshots/05_search_tv.jpg" width="200" /><br /><sub><b>Search – TV Shows</b></sub></td>
    <td align="center"><img src="screenshots/06_wishlist.jpg" width="200" /><br /><sub><b>Wishlist</b></sub></td>
    <td align="center"><img src="screenshots/07_tv_details.jpg" width="200" /><br /><sub><b>TV Show Details</b></sub></td>
    <td align="center"><img src="screenshots/08_settings.jpg" width="200" /><br /><sub><b>Settings & About</b></sub></td>
  </tr>
</table>

---

## ✨ Features

- **Home feed** with *Most Popular* and *Now Playing* sections, each split into Movies and TV Shows.
- **Discover** rows of trending titles with ratings, genres and poster art.
- **Search** across movies and TV shows, with a tabbed view to switch between the two.
- **Detail screens** with poster, release year, runtime, genres, rating, overview and cast.
- **TV show support** with season and episode selectors and a one-tap *Play S1 E1* button.
- **Wishlist** to save your favorite movies and shows, with a heart toggle on every details page.
- **Modern dark UI** with a red accent, rounded cards and a bottom navigation bar (Home, Search, Wishlist, Settings).
- **Settings & About** with a developer spotlight, a link to the source code, the privacy policy and the app version.

---

## 🛠️ Tech Stack

| Area | Technology |
| --- | --- |
| Language | Kotlin |
| UI | Jetpack Compose |
| Platform | Android |
| Data source | [TMDB API](https://www.themoviedb.org/documentation/api) |

---

## 🚀 Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (latest stable version recommended)
- JDK 17 or newer
- A free [TMDB API key](https://www.themoviedb.org/settings/api)

### Setup

1. **Clone the repository**

   ```bash
   git clone https://github.com/haseebno1/Haseeb-Flix.git
   cd Haseeb-Flix
   ```

2. **Add your TMDB API key**

   Add your key to `local.properties` in the project root:

   ```properties
   TMDB_API_KEY=your_api_key_here
   ```

3. **Open the project** in Android Studio and let Gradle sync.

4. **Run the app** on an emulator or a physical device (**Run ▶ Run 'app'**).

---

## 📂 Project Structure

```text
Haseeb-Flix/
├── app/            # Android application module
├── assets/         # README assets (logo)
├── screenshots/    # App screenshots used in this README
└── README.md
```

---

## 🤝 Contributing

Contributions, issues and feature requests are welcome!

1. Fork the project
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m "Add amazing feature"`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 👨‍💻 Author

**Abdul Haseeb** – Lead Mobile & App Architect

- GitHub: [@haseebno1](https://github.com/haseebno1)

---

## 🙏 Acknowledgements

- [The Movie Database (TMDB)](https://www.themoviedb.org/) for the movie and TV data.

> This product uses the TMDB API but is not endorsed or certified by TMDB.

---

<p align="center">If you like this project, give it a ⭐ on GitHub!</p>
