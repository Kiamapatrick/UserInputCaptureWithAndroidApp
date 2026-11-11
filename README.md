# User Input Capture Android App

A simple Android application that demonstrates capturing and validating user input including name, date of birth, email, and marital status.

## Features

- **Full Name Input** - Text field with validation (min 2 words, letters/spaces/hyphens/apostrophes only)
- **Date of Birth** - Date picker dialog with calendar end icon, future date prevention, min date 1900, default 18 years ago
- **Email Address** - Email format validation using custom regex
- **Marital Status** - Radio buttons (Single/Married) with inline error validation
- **Input Validation** - Real-time error clearing on text change, focus first invalid field on submit
- **Confirmation Dialog** - AlertDialog with Confirm/Edit buttons, Snackbar on confirm, form reset

## Tech Stack

- **Language**: Java
- **Min SDK**: 24 (Android 7.0 Nougat)
- **Target SDK**: 36 (Android 16)
- **Build System**: Gradle (Kotlin DSL)
- **UI Components**: Material3 (TextInputLayout, TextInputEditText, RadioGroup, Snackbar)
- **Architecture**: Single Activity with findViewById (no ViewBinding)

## Project Structure

```
UserInputCaptureWithAndroidApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/smart/userinputcapture/
│   │   │   ├── MainActivity.java          # Main activity with form logic
│   │   │   └── Validators.java            # Plain Java validation logic (no Android deps)
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml      # UI layout with ScrollView
│   │   │   ├── values/
│   │   │   │   ├── strings.xml            # All UI text
│   │   │   │   ├── colors.xml             # Color definitions
│   │   │   │   └── themes.xml             # Material3 theme
│   │   │   └── xml/
│   │   │       ├── backup_rules.xml       # Backup configuration
│   │   │       └── data_extraction_rules.xml
│   │   └── AndroidManifest.xml
│   ├── src/test/
│   │   └── java/com/smart/userinputcapture/
│   │       └── ValidatorsTest.java        # JUnit tests for Validators
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Getting Started

### Prerequisites

- Android Studio Flamingo (2022.2.1) or later
- JDK 17 or later
- Android SDK 36

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

# Run unit tests
./gradlew test
```

## Usage

1. Launch the app
2. Enter your **Full Name** (minimum 2 words, letters/spaces/hyphens/apostrophes only)
3. Tap **Date of Birth** field or calendar icon to open the date picker (future dates disabled, min 1900, defaults to 18 years ago)
4. Enter your **Email Address**
5. Select **Marital Status** (Single or Married)
6. Tap **Submit Details**
7. Confirm dialog appears - tap **Confirm** to submit (shows Snackbar, resets form) or **Edit** to return

## Validation Rules

| Field | Rule |
|-------|------|
| Full Name | Required, min 2 words, letters/spaces/hyphens/apostrophes only |
| Date of Birth | Required, not future, not before 1900, must be 18+ years old |
| Email | Required, valid email format (custom regex) |
| Marital Status | Required, must select one option |

## Key Implementation Details

### Validators (`Validators.java`)
Plain Java class with no Android dependencies:
```java
public static boolean isValidName(String name) {
    // min 2 words, letters/spaces/hyphens/apostrophes only
}

public static boolean isValidEmail(String email) {
    // custom regex pattern
}

public static boolean isAdult(Calendar dob, Calendar today, int minAge) {
    // age calculation
}
```

### Date Picker (`MainActivity.java`)
```java
private void showDatePicker() {
    Calendar today = Calendar.getInstance();
    Calendar initialDate = (selectedDob != null) ? (Calendar) selectedDob.clone() : today;
    if (selectedDob == null) initialDate.add(Calendar.YEAR, -18);

    DatePickerDialog picker = new DatePickerDialog(this, (view, y, m, d) -> {
        selectedDob = Calendar.getInstance();
        selectedDob.set(y, m, d);
        editTextDate.setText(DATE_FORMAT.format(selectedDob.getTime()));
    }, year, month, day);

    picker.getDatePicker().setMaxDate(today.getTimeInMillis());
    Calendar minDate = Calendar.getInstance();
    minDate.set(1900, Calendar.JANUARY, 1);
    picker.getDatePicker().setMinDate(minDate.getTimeInMillis());
    picker.show();
}
```

### Form Validation & UX
- Errors cleared on text change via `TextWatcher`
- On submit, first invalid field focused and keyboard hidden
- Confirmation dialog with `setCancelable(false)`, Confirm/Edit buttons
- Snackbar shown on confirm, form reset

### Rotation Support
- `onSaveInstanceState` / restore in `onCreate`
- Saves selected date, error states, marital error visibility

## Dependencies

```kotlin
// app/build.gradle.kts
dependencies {
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}
```

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