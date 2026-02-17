# Android Architecture Project

This project demonstrates a multi-module architecture with Jetpack Compose, with CI/CD (Bitrise) and Firebase App Distribution.

## Structure

*   **:app**: The main entry point. It connects features and handles the `NavHost`.
*   **:core**: The common module.
    *   **Network**: Contains `NetworkClient` (Retrofit).
    *   **Navigation**: Contains `NavigationManager` for inter-module communication.
*   **:features:home**: A sample feature module containing `HomeScreen`.

## Communication

Features communicate via the `core` module.
*   **Navigation**: Features emit commands to `NavigationManager` (in `core`). The `app` module observes these commands and performs the navigation.

## GitHub

This project is a Git repo (branch `main`). The GitHub repo is **AndroidArchitect**.

**Add the remote and push** (replace `YOUR_USERNAME` with your GitHub username):

```bash
git remote add origin https://github.com/YOUR_USERNAME/AndroidArchitect.git
git push -u origin main
```

Using SSH:

```bash
git remote add origin git@github.com:YOUR_USERNAME/AndroidArchitect.git
git push -u origin main
```

Then connect this repo to Bitrise: Add new app → GitHub → select **AndroidArchitect**.

## Setup

1.  Open this project in **Android Studio**.
2.  Sync Gradle.
3.  Run the `app` configuration.

## Testing

- **Unit tests**: `./gradlew test` (covers `:app`, `:core`).
- **UI (instrumented) tests**: `./gradlew connectedDebugAndroidTest` (requires emulator or device).

Tests are run in CI (Bitrise) and before Firebase distribution.

## CI/CD (Bitrise + Firebase)

- **Bitrise**: Use the included `bitrise.yml` in the repo root. Workflows:
  - **primary**: Unit tests → Build release APK → Deploy to Firebase App Distribution.
  - **ui-tests**: Unit tests → Build debug APK → Run instrumented (UI) tests (use an Android stack with emulator).

- **Firebase**: Replace `app/google-services.json` with your project’s file from the Firebase Console. For App Distribution, set in Bitrise:
  - `FIREBASE_APP_ID`: your Firebase Android app ID
  - `FIREBASE_TOKEN`: CI token from Firebase (Project settings → Service accounts / CI)
  - Optionally use a service account JSON and set `FIREBASE_SERVICE_CREDENTIALS_PATH` or configure the Firebase step accordingly.
