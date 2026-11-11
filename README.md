# User Input Capture Android App

A simple Android application that demonstrates capturing and validating user input including name, date of birth, email, and marital status.

## Features

- **Full Name Input** - Text field with validation
- **Date of Birth** - Date picker dialog with future date prevention
- **Email Address** - Email format validation using Android's `Patterns.EMAIL_ADDRESS`
- **Marital Status** - Radio buttons (Single/Married) with selection validation
- **Input Validation** - Real-time error messages for invalid/missing fields
- **Summary Dialog** - Displays all captured data in an alert dialog on successful submission

## Screenshots

| Input Form | Date Picker | Validation Error | Success Dialog |
|------------|-------------|------------------|----------------|
| ![Form](docs/screenshots/form.png) | ![Date Picker](docs/screenshots/date_picker.png) | ![Validation](docs/screenshots/validation.png) | ![Success](docs/screenshots/success.png) |

## Tech Stack

- **Language**: Java
- **Min SDK**: 24 (Android 7.0 Nougat)
- **Target SDK**: 34 (Android 14)
- **Build System**: Gradle (Kotlin DSL)
- **UI Components**: Material Components (TextInputLayout, TextInputEditText, RadioGroup)
- **Architecture**: Single Activity with View Binding

## Project Structure

```
UserInputCaptureWithAndroidApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/smart/userinputcapture/
│   │   │   └── MainActivity.java          # Main activity with form logic
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml      # UI layout with ScrollView
│   │   │   ├── values/
│   │   │   │   ├── strings.xml            # App name
│   │   │   │   ├── colors.xml             # Color definitions
│   │   │   │   └── themes.xml             # Material theme
│   │   │   └── xml/
│   │   │       ├── backup_rules.xml       # Backup configuration
│   │   │       └── data_extraction_rules.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Getting Started

### Prerequisites

- Android Studio Flamingo (2022.2.1) or later
- JDK 17 or later
- Android SDK 34

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Kiamapatrick/UserInputCaptureWithAndroidApp.git
   ```

2. Open the project in Android Studio:
   - File → Open → Select the cloned directory

3. Let Gradle sync complete, then run the app on an emulator or physical device.

### Building from Command Line

```bash
# Debug build
./gradlew assembleDebug

# Release build (requires signing config)
./gradlew assembleRelease
```

## Usage

1. Launch the app
2. Enter your **Full Name**
3. Tap **Date of Birth** field to open the date picker (future dates are disabled)
4. Enter your **Email Address**
5. Select **Marital Status** (Single or Married)
6. Tap **Submit Details**
7. View the summary dialog with all entered information

## Validation Rules

| Field | Rule |
|-------|------|
| Full Name | Required, non-empty |
| Date of Birth | Required, must select a date (not future) |
| Email | Required, valid email format |
| Marital Status | Required, must select one option |

## Key Implementation Details

### Date Picker (`MainActivity.java`)
```java
private void showDatePicker() {
    Calendar cal = Calendar.getInstance();
    DatePickerDialog picker = new DatePickerDialog(
        this,
        (view, year, month, day) -> {
            String date = day + "/" + (month + 1) + "/" + year;
            editTextDate.setText(date);
        },
        year, month, day);
    picker.getDatePicker().setMaxDate(System.currentTimeMillis()); // No future dates
    picker.show();
}
```

### Email Validation
Uses Android's built-in `Patterns.EMAIL_ADDRESS.matcher(email).matches()`

### Form Validation
All fields validated on submit with error messages displayed via `TextInputLayout.setError()`

## Dependencies

```kotlin
// app/build.gradle.kts
dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

**Kiamapatrick**
- GitHub: [@Kiamapatrick](https://github.com/Kiamapatrick)

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request