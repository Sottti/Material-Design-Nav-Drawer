# Material Design Navigation Drawer

> **Article companion — inactive.** This repository contains the sample code for
> [Material Design Navigation Drawer series](https://medium.com/android-news/navigation-drawer-styling-according-material-design-5306190da08f) on Medium.
> It is preserved for reference and is not under active development.

![Java](https://img.shields.io/badge/Java-Android-007396?logo=openjdk&logoColor=white)
![Android](https://img.shields.io/badge/Android%20Support-25.3.1-3DDC84?logo=android&logoColor=white)
![Material](https://img.shields.io/badge/Material%20Design-navigation%20drawer-4285F4)
![Status](https://img.shields.io/badge/status-inactive%20article%20companion-FFB000)
![License](https://img.shields.io/badge/license-MIT-blue)

A small Java Android sample showing how to build a Material Design navigation
drawer with `DrawerLayout`, `NavigationView`, AppCompat, and the Android Design
Support Library.

The app demonstrates the classic pre-AndroidX navigation drawer pattern: a
toolbar with a drawer affordance, a branded drawer header, grouped menu items,
checked navigation state, delayed content changes while the drawer closes, and
secondary Account, Help, and About screens.

## ✨ What It Demonstrates

- `DrawerLayout` as the root layout for content plus drawer.
- `NavigationView` with a header layout and menu resource.
- AppCompat `Toolbar` configured as the activity action bar.
- Home/up affordance opening the drawer from the toolbar.
- Checked single-selection drawer items for primary destinations.
- Non-checkable drawer actions for Help and About.
- Fragment replacement after a short delay to avoid jank while closing the
  drawer.
- State restoration for the toolbar title after configuration changes.
- Alternative branches comparing Design Support Library and manual
  implementations.

## 🌿 Branches

| Branch | Approach |
| --- | --- |
| [`using_design_support_library`](https://github.com/Sottti/Material-Design-Nav-Drawer/tree/using_design_support_library) | Design Support Library implementation using `NavigationView`. |
| [`not_using_design_support_library`](https://github.com/Sottti/Material-Design-Nav-Drawer/tree/not_using_design_support_library) | Older/manual drawer implementation without `NavigationView`. |

## ⚙️ Tech Stack

- Java
- Android Gradle Plugin 3.0.0 alpha03
- Gradle 4.0 milestone 2
- compile SDK 25, target SDK 25, min SDK 16
- Android Support Library 25.3.1
- AppCompat, Design Support Library, Percent Support Library
- XML layouts and vector drawables

## 🚀 Run It

This repo is pinned to 2017 Android tooling. Use **JDK 8**, Android SDK Platform
25, and Android SDK Build Tools 25.0.3. Point `JAVA_HOME` at JDK 8 and set
`ANDROID_HOME` to your SDK installation (or configure `sdk.dir` in your local
`local.properties`). The Gradle wrapper downloads the original Gradle version;
it is not compatible with current JDK releases.

```bash
./gradlew :app:assembleDebug
./gradlew :app:installDebug
```

## 🧪 Verification

No automated test suite is included in this historical sample. The practical
sanity check is to build the debug app:

```bash
./gradlew :app:assembleDebug
```

## 📌 Status

This is a historical sample, not a modern AndroidX/Material Components app. Its
value today is as a compact reference for how the Design Support Library era
implemented Material navigation drawers.

## 📜 License

The original README declared this repository to be MIT licensed. A standalone
`LICENSE` file is not currently present in the repository.
