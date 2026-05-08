# Channapatna Namma Pride

An authentic Android application designed to protect and promote the rich cultural heritage of Channapatna toys. This application serves as a bridge between master craftsmen and consumers, ensuring the authenticity of Geographical Indication (GI) tagged toys.

## Features

- **Toy Verification:** Scan or manually enter a unique 6-digit toy ID to verify the authenticity of your purchase. Authentic toys receive a "Verified Authentic" badge along with their GI tag details.
- **Meet the Maker:** Explore the profiles of master artisans. Learn about their story, specialties, and find their workshops.
- **Workshop Map:** Discover artisan workshops in Channapatna with integrated Google Maps functionality.
- **Premium Heritage Design:** A fully custom UI built in Jetpack Compose, featuring a bespoke color palette (Bone Surface, Lacquer Red, Wood Brown) that embodies the "Orbital Velocity / Premium Heritage" design system.
- **Kannada Localization:** Full support for Kannada, enabling accessibility for local users and artisans.

## Tech Stack

- **UI Framework:** Jetpack Compose (Material 3)
- **Architecture:** MVVM (Model-View-ViewModel)
- **Backend:** Firebase Firestore (for verifying toys and fetching artisan data)
- **Maps:** Google Maps Compose SDK
- **Language:** Kotlin

## Production Setup

1. **Firebase Configuration:** The project expects a `google-services.json` file in the `app/` directory. Ensure your Firebase project has Firestore enabled.
2. **Google Maps API Key:** Add your Maps API key in the `local.properties` file or `AndroidManifest.xml` via manifest placeholders.
3. **Build Types:** The `release` build type is fully configured with minification (`R8`) and resource shrinking enabled for a highly optimized APK.

## Screenshots

The app features beautiful custom-designed components like the Verification Hero Card, Quick Tiles, and dynamic Artisan Profiles with high-quality imagery.

## License

This project is intended to protect the local heritage of Channapatna. Copyright (c) 2026.
