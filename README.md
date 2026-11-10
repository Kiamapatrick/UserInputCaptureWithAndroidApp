# User Input Capture Android App

A simple Android application that demonstrates capturing and validating user input including name, date of birth, email address, and marital status.

## Features

- **Name Input** - Text field with validation for required entry
- **Date of Birth** - Date picker dialog with future date restriction
- **Email Input** - Email field with format validation using Android's `Patterns.EMAIL_ADDRESS`
- **Marital Status** - Radio button selection (Single/Married)
- **Form Validation** - Comprehensive client-side validation with error messages
- **Summary Dialog** - Displays captured data in an alert dialog upon successful submission

## Screenshots

| Form View | Date Picker | Validation Errors | Success Dialog |
|-----------|-------------|-------------------|----------------|
| ![Form](screenshots/form.png) | ![Date Picker](screenshots/date-picker.png) | ![Validation](screenshots/validation.png) | ![Success](screenshots/success.png) |

## Tech Stack

- **Language**: Java
- **Min SDK**: 24 (Android 7.0 Nougat)
- **Target SDK**: 36 (Android 14)
- **Build System**: Gradle (Kotlin DSL)
- **UI Framework**: Material Components (Material3)
- **Architecture**: Single Activity with ViewBinding

## Project Structure

```
UserInputCaptureWithAndroidApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/smart/userinputcapture/
│   │   │   │   └── MainActivity.java       # Main activity with form logic
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml   # Form layout with Material components
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml         # String resources
│   │   │   │   │   ├── colors.xml          # Color definitions
│   │   │   │   │   └── themes.xml          # App themes
│   │   │   │   ├── xml/
│   │   │   │   │   ├── backup_rules.xml    # Backup configuration
│   │   │   │   │   └── data_extraction_rules.xml
│   │   │   │   └── mipmap-*/               # App icons
│   │   │   └── AndroidManifest.xml         # App manifest
│   │   ├── test/                           # Unit tests
│   │   └── androidTest/                    # Instrumented tests
│   ├── build.gradle.kts                    # App-level Gradle config
│   └── keepRules/
│       └── rules.keep                      # ProGuard/R8 rules
├── gradle/
│   ├── libs.versions.toml                  # Version catalog
│   └── wrapper/
├── build.gradle.kts                        # Project-level Gradle config
├── settings.gradle.kts                     # Gradle settings
├── gradle.properties                       # Gradle properties
└── README.md                               # This file
```

## Getting Started

### Prerequisites

- Android Studio Ladybug (2024.2.1) or later
- JDK 11 or later
- Android SDK with API level 36

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Kiamapatrick/UserInputCaptureWithAndroidApp.git
   ```

2. Open the project in Android Studio:
   - Select **File > Open**
   - Navigate to the cloned directory and select it

3. Sync the project with Gradle files:
   - Click **Sync Now** in the notification bar
   - Or use **File > Sync Project with Gradle Files**

4. Run the app:
   - Connect an Android device or create an emulator (API 24+)
   - Click **Run** (▶️) or press `Shift + F10`

## Usage

1. Launch the app
2. Enter your **Full Name** in the text field
3. Tap the **Date of Birth** field to open the date picker dialog
4. Enter your **Email Address** (validated for proper format)
5. Select your **Marital Status** (Single or Married)
6. Tap **Submit Details**
7. View the summary dialog with your entered information

## Validation Rules

| Field | Rules |
|-------|-------|
| Full Name | Required, non-empty |
| Date of Birth | Required, must not be a future date |
| Email | Required, must match valid email pattern |
| Marital Status | Required, must select one option |

## Code Highlights

### Date Picker with Future Date Restriction
```java
DatePickerDialog picker = new DatePickerDialog(
    this,
    (view, selectedYear, selectedMonth, selectedDay) -> {
        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
        editTextDate.setText(date);
        layoutDate.setError(null);
    },
    year, month, day
);
picker.getDatePicker().setMaxDate(System.currentTimeMillis());
```

### Email Validation using Android Patterns
```java
if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
    layoutEmail.setError("Enter a valid email address");
    valid = false;
}
```

### Material Components TextInputLayout
The form uses `TextInputLayout` with `OutlinedBox` style for modern Material Design appearance with built-in error handling.

## Testing

Run unit tests:
```bash
./gradlew test
```

Run instrumented tests:
```bash
./gradlew connectedAndroidTest
```

## Building

Debug APK:
```bash
./gradlew assembleDebug
```

Release APK:
```bash
./gradlew assembleRelease
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

**Kiamapatrick** - [GitHub](https://github.com/Kiamapatrick)

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Acknowledgments

- [Android Developers Documentation](https://developer.android.com/)
- [Material Components for Android](https://github.com/material-components/material-components-android)
- [Android DatePickerDialog](https://developer.android.com/reference/android/app/DatePickerDialog)