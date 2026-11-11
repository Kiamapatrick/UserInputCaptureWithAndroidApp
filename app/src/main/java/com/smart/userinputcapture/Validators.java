package com.smart.userinputcapture;

import java.util.Calendar;
import java.util.regex.Pattern;

public class Validators {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    // Allow Unicode letters (\p{L}), spaces, hyphens, apostrophes
    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^[\\p{L}\\s'-]+$"
    );

    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String trimmed = name.trim();
        // Check pattern: Unicode letters, spaces, hyphens, apostrophes only
        if (!NAME_PATTERN.matcher(trimmed).matches()) {
            return false;
        }
        // Check minimum 2 words
        String[] words = trimmed.split("\\s+");
        if (words.length < 2) {
            return false;
        }
        // EVERY word must contain at least one letter
        for (String word : words) {
            if (!word.matches(".*\\p{L}.*")) {
                return false;
            }
        }
        return true;
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