package com.smart.userinputcapture;

import org.junit.Test;

import java.util.Calendar;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Unit tests for Validators class.
 */
public class ValidatorsTest {

    // isValidName tests
    @Test
    public void isValidName_validTwoWords_returnsTrue() {
        assertTrue(Validators.isValidName("John Doe"));
        assertTrue(Validators.isValidName("Mary Jane Smith"));
    }

    @Test
    public void isValidName_validWithHyphen_returnsTrue() {
        assertTrue(Validators.isValidName("Jean-Paul Sartre"));
    }

    @Test
    public void isValidName_validWithApostropheInTwoWords_returnsTrue() {
        assertTrue(Validators.isValidName("O'Connor Smith"));
    }

    @Test
    public void isValidName_singleWordWithApostrophe_returnsFalse() {
        assertFalse(Validators.isValidName("O'Connor"));
    }

    @Test
    public void isValidName_singleWord_returnsFalse() {
        assertFalse(Validators.isValidName("John"));
    }

    @Test
    public void isValidName_empty_returnsFalse() {
        assertFalse(Validators.isValidName(""));
        assertFalse(Validators.isValidName("   "));
        assertFalse(Validators.isValidName(null));
    }

    @Test
    public void isValidName_withNumbers_returnsFalse() {
        assertFalse(Validators.isValidName("John123 Doe"));
    }

    @Test
    public void isValidName_withSpecialChars_returnsFalse() {
        assertFalse(Validators.isValidName("John@Doe"));
        assertFalse(Validators.isValidName("John#Doe"));
    }

    // isValidEmail tests
    @Test
    public void isValidEmail_validSimple_returnsTrue() {
        assertTrue(Validators.isValidEmail("test@example.com"));
    }

    @Test
    public void isValidEmail_validWithSubdomain_returnsTrue() {
        assertTrue(Validators.isValidEmail("user.name@sub.domain.com"));
    }

    @Test
    public void isValidEmail_validWithPlus_returnsTrue() {
        assertTrue(Validators.isValidEmail("user+tag@example.com"));
    }

    @Test
    public void isValidEmail_invalidNoAt_returnsFalse() {
        assertFalse(Validators.isValidEmail("testexample.com"));
    }

    @Test
    public void isValidEmail_invalidNoDomain_returnsFalse() {
        assertFalse(Validators.isValidEmail("test@"));
    }

    @Test
    public void isValidEmail_invalidNoTld_returnsFalse() {
        assertFalse(Validators.isValidEmail("test@domain"));
    }

    @Test
    public void isValidEmail_empty_returnsFalse() {
        assertFalse(Validators.isValidEmail(""));
        assertFalse(Validators.isValidEmail("   "));
        assertFalse(Validators.isValidEmail(null));
    }

    @Test
    public void isValidEmail_invalidDoubleAt_returnsFalse() {
        assertFalse(Validators.isValidEmail("test@@example.com"));
    }

    // isAdult tests
    @Test
    public void isAdult_exactly18YearsOld_returnsTrue() {
        Calendar today = Calendar.getInstance(Locale.getDefault());
        Calendar dob = (Calendar) today.clone();
        dob.add(Calendar.YEAR, -18);
        assertTrue(Validators.isAdult(dob, today));
    }

    @Test
    public void isAdult_olderThan18_returnsTrue() {
        Calendar today = Calendar.getInstance(Locale.getDefault());
        Calendar dob = (Calendar) today.clone();
        dob.add(Calendar.YEAR, -25);
        assertTrue(Validators.isAdult(dob, today));
    }

    @Test
    public void isAdult_under18_returnsFalse() {
        Calendar today = Calendar.getInstance(Locale.getDefault());
        Calendar dob = (Calendar) today.clone();
        dob.add(Calendar.YEAR, -17);
        assertFalse(Validators.isAdult(dob, today));
    }

    @Test
    public void isAdult_under18ByDays_returnsFalse() {
        Calendar today = Calendar.getInstance(Locale.getDefault());
        Calendar dob = (Calendar) today.clone();
        dob.add(Calendar.YEAR, -18);
        dob.add(Calendar.DAY_OF_MONTH, 1); // 1 day after 18th birthday
        assertFalse(Validators.isAdult(dob, today));
    }

    @Test
    public void isAdult_customMinAge_returnsCorrect() {
        Calendar today = Calendar.getInstance(Locale.getDefault());
        Calendar dob = (Calendar) today.clone();
        dob.add(Calendar.YEAR, -20);

        // Min age 21 - should be false (20 < 21)
        assertFalse(Validators.isAdult(dob, today, 21));

        // Min age 18 - should be true (20 >= 18)
        assertTrue(Validators.isAdult(dob, today, 18));
    }

    @Test
    public void isAdult_nullDob_returnsFalse() {
        Calendar today = Calendar.getInstance(Locale.getDefault());
        assertFalse(Validators.isAdult(null, today));
    }

    @Test
    public void isAdult_nullToday_returnsFalse() {
        Calendar dob = Calendar.getInstance(Locale.getDefault());
        dob.add(Calendar.YEAR, -25);
        assertFalse(Validators.isAdult(dob, null));
    }
}