package com.smart.userinputcapture;

import java.util.Calendar;
import java.util.regex.Pattern;

public class Validators {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^[A-Za-z\\s'-]+$"
    );

    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String trimmed = name.trim();
        // Check pattern: letters, spaces, hyphens, apostrophes only
        if (!NAME_PATTERN.matcher(trimmed).matches()) {
            return false;
        }
        // Check minimum 2 words
        String[] words = trimmed.split("\\s+");
        return words.length >= 2;
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isAdult(Calendar dob, Calendar today, int minAge) {
        if (dob == null || today == null) {
            return false;
        }
        Calendar ageCalendar = (Calendar) dob.clone();
        ageCalendar.add(Calendar.YEAR, minAge);
        return !ageCalendar.after(today);
    }

    public static boolean isAdult(Calendar dob, Calendar today) {
        return isAdult(dob, today, 18);
    }
}