# Customer Satisfaction Terminal (Android)

An Android kiosk-style application built with Kotlin and Jetpack Compose that lets customers quickly submit satisfaction feedback using a row of expressive emoji buttons. Each vote is immediately sent to your existing Next.js backend (`POST /api/votes`).

## Feature Highlights

- Full-screen Compose UI optimized for unattended kiosks
- Пет емотикона за оценка (от много недоволен до много доволен)
- Instant vote submission through Retrofit + Coroutines
- Интерфейс изцяло на български език
- Automatic per-device token + API base URL handled via SharedPreferences
- On-device admin panel for updating credentials, base URL, or exiting kiosk mode
- Допълнителна форма за обратна връзка (име, телефон, имейл, коментар, съгласие за маркетинг)
- MVVM architecture with a clean repository layer
- Dependency injection powered by Hilt
- Success toast and error dialog feedback for staff monitoring

## Project Structure

```
app/
 ├─ src/main/java/com/erankup/customersatisfaction/
 │   ├─ data/remote/…            # Retrofit API and DTOs
 │   ├─ data/repository/…        # Repository contracts + implementation
 │   ├─ di/…                     # Hilt modules (network, dispatchers, repos)
 │   ├─ domain/model/…           # VoteType enum shared across layers
 │   ├─ ui/theme/…               # Compose theme definitions
 │   ├─ ui/vote/…                # Compose screens + ViewModel
 │   ├─ util/DeviceConfigManager.kt # SharedPreferences-backed device credentials
 │   ├─ MainActivity.kt          # @AndroidEntryPoint host
 │   └─ CustomerSatisfactionApplication.kt
 └─ build.gradle.kts             # Module configuration
```

## Prerequisites

- Android Studio Giraffe (AGP 8.1) or newer
- JDK 17
- An Android device or emulator running API 24+
- Active internet connection to reach your Next.js backend

## Getting Started

1. **Clone or copy the project** into your Android Studio workspace.
2. **Open the project** in Android Studio and let it download the required Gradle dependencies.  
   If the Gradle wrapper is missing, generate it once from a terminal:
   ```shell
   gradle wrapper
   ```
3. **Configure the backend base URL**:
   - Update the default value in `app/build.gradle.kts` (this will be pre-filled in the device setup form and can later be changed from the admin panel):
     ```kotlin
     buildConfigField("String", "API_BASE_URL", "\"https://YOUR_DOMAIN/\"")
     ```
   - Be sure to keep the trailing slash. Example: `https://feedback.example.com/`.
4. **Sync Gradle** and build the project.
5. **Deploy to a device or emulator** using the _Run_ action in Android Studio.

## Runtime Behavior

- On first launch, the app requests the device token, API base URL, and optional assigned user through an on-screen setup form and persists them via `SharedPreferences`.
- Tapping any emoji instantly calls `POST {BASE_URL}/api/votes` with payload:
  ```json
  {
    "token": "<DEVICE_TOKEN>",
    "vote": "<superlike|like|neutral|dislike|superdislike>",
    "name": "<optional>",
    "phone": "<optional>",
    "email": "<optional>",
    "comment": "<optional>",
    "marketingConsent": <true|false>
  }
  ```
- Success responses trigger a brief toast (`Thanks for your feedback!` by default).
- API failures show a dismissible dialog with the returned message.
- Any attempt to leave the kiosk view prompts for the admin password (`1234` by default, configurable in `ui/vote/VoteScreen.kt`). Once authenticated you can update the stored device credentials, adjust the API base URL, or exit the app.

## Testing the Backend Connection

1. Deploy or start your Next.js backend that exposes `/api/votes`.
2. Run the Android app and tap different emoji to send votes.
3. Inspect your backend logs or database to verify incoming vote records.

## Customization Ideas

- Swap the toast for a custom `Snackbar` or on-screen confirmation.
- Add analytics or offline queuing in the repository layer.
- Lock the device into kiosk mode using Android's pinned screen feature.
- Localize strings via additional resource files.
- Change the admin password by editing `ADMIN_PASSWORD` in `ui/vote/VoteScreen.kt`.

## License

This project is delivered as-is for integration with your proprietary backend. Adjust licensing terms as needed for your deployment.
