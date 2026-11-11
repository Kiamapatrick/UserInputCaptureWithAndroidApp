package com.smart.userinputcapture; // change to match your own package name

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout layoutName, layoutDate, layoutEmail;
    private TextInputEditText editTextName, editTextDate, editTextEmail;
    private RadioGroup radioGroupMarital;
    private TextView textViewMaritalError;
    private Button buttonSubmit;
    private View rootLayout;

    // Date format for parsing/displaying
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    // Selected date of birth
    private Calendar selectedDob;

    // Track first invalid field for focus
    private View firstInvalidField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind views
        layoutName = findViewById(R.id.layoutName);
        layoutDate = findViewById(R.id.layoutDate);
        layoutEmail = findViewById(R.id.layoutEmail);
        editTextName = findViewById(R.id.editTextName);
        editTextDate = findViewById(R.id.editTextDate);
        editTextEmail = findViewById(R.id.editTextEmail);
        radioGroupMarital = findViewById(R.id.radioGroupMarital);
        textViewMaritalError = findViewById(R.id.textViewMaritalError);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        rootLayout = findViewById(android.R.id.content);

        // Clear errors on text change
        setupErrorClearing();

        // Open the calendar when the date field is tapped
        editTextDate.setOnClickListener(v -> showDatePicker());

        // Open the calendar when the end icon is tapped
        layoutDate.setEndIconOnClickListener(v -> showDatePicker());

        // Show the summary dialog on submit
        buttonSubmit.setOnClickListener(v -> onSubmit());
    }

    private void setupErrorClearing() {
        // Clear name error on text change
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutName.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Clear email error on text change
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutEmail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Clear marital error when selection changes
        radioGroupMarital.setOnCheckedChangeListener((group, checkedId) -> {
            textViewMaritalError.setVisibility(TextView.GONE);
        });
    }

    private void showDatePicker() {
        Calendar today = Calendar.getInstance();

        // Default to 18 years ago if no date selected yet
        Calendar initialDate;
        if (selectedDob != null) {
            initialDate = (Calendar) selectedDob.clone();
        } else {
            initialDate = (Calendar) today.clone();
            initialDate.add(Calendar.YEAR, -18);
        }

        int year = initialDate.get(Calendar.YEAR);
        int month = initialDate.get(Calendar.MONTH);
        int day = initialDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog picker = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    selectedDob = Calendar.getInstance();
                    selectedDob.set(selectedYear, selectedMonth, selectedDay);
                    String date = DATE_FORMAT.format(selectedDob.getTime());
                    editTextDate.setText(date);
                    layoutDate.setError(null);
                },
                year, month, day);

        // Set max date to today
        picker.getDatePicker().setMaxDate(today.getTimeInMillis());

        // Set min date to 1 Jan 1900
        Calendar minDate = Calendar.getInstance();
        minDate.set(1900, Calendar.JANUARY, 1);
        picker.getDatePicker().setMinDate(minDate.getTimeInMillis());

        picker.show();
    }

    private void onSubmit() {
        String name = editTextName.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        firstInvalidField = null;

        if (!validate(name, date, email)) {
            // Focus first invalid field and hide keyboard
            if (firstInvalidField != null) {
                firstInvalidField.requestFocus();
                hideKeyboard(firstInvalidField);
            }
            return;
        }

        // Read selected marital status from RadioButton's text
        int checkedId = radioGroupMarital.getCheckedRadioButtonId();
        RadioButton checkedRadioButton = findViewById(checkedId);
        String maritalStatus = checkedRadioButton != null ? checkedRadioButton.getText().toString() : "";

        String message = getString(R.string.dialog_label_name) + name
                + "\n" + getString(R.string.dialog_label_date) + date
                + "\n" + getString(R.string.dialog_label_email) + email
                + "\n" + getString(R.string.dialog_label_marital) + maritalStatus;

        // Show confirmation dialog
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.dialog_title_confirm)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_button_confirm, (dialog, which) -> {
                    // Confirm: show Snackbar and reset form
                    Snackbar.make(rootLayout, R.string.snackbar_submitted, Snackbar.LENGTH_LONG).show();
                    resetForm();
                })
                .setNegativeButton(R.string.dialog_button_edit, (dialog, which) -> {
                    // Edit: just close dialog
                    dialog.dismiss();
                })
                .show();
    }

    private void resetForm() {
        editTextName.setText("");
        editTextDate.setText("");
        editTextEmail.setText("");
        radioGroupMarital.clearCheck();
        selectedDob = null;
        layoutName.setError(null);
        layoutDate.setError(null);
        layoutEmail.setError(null);
        textViewMaritalError.setVisibility(TextView.GONE);
    }

    private boolean validate(String name, String date, String email) {
        boolean valid = true;

        layoutName.setError(null);
        layoutDate.setError(null);
        layoutEmail.setError(null);
        textViewMaritalError.setVisibility(TextView.GONE);

        // Validate name using Validators
        if (!Validators.isValidName(name)) {
            layoutName.setError(getString(R.string.error_name_required));
            if (firstInvalidField == null) firstInvalidField = editTextName;
            valid = false;
        }

        // Validate date
        if (TextUtils.isEmpty(date)) {
            layoutDate.setError(getString(R.string.error_date_required));
            if (firstInvalidField == null) firstInvalidField = editTextDate;
            valid = false;
        } else {
            // Check if adult (18+)
            try {
                Date dob = DATE_FORMAT.parse(date);
                Calendar dobCal = Calendar.getInstance();
                dobCal.setTime(dob);
                Calendar today = Calendar.getInstance();
                if (!Validators.isAdult(dobCal, today)) {
                    layoutDate.setError(getString(R.string.error_date_required));
                    if (firstInvalidField == null) firstInvalidField = editTextDate;
                    valid = false;
                }
            } catch (Exception e) {
                layoutDate.setError(getString(R.string.error_date_required));
                if (firstInvalidField == null) firstInvalidField = editTextDate;
                valid = false;
            }
        }

        // Validate email using Validators
        if (!Validators.isValidEmail(email)) {
            layoutEmail.setError(getString(R.string.error_email_invalid));
            if (firstInvalidField == null) firstInvalidField = editTextEmail;
            valid = false;
        }

        // Validate marital status
        if (radioGroupMarital.getCheckedRadioButtonId() == -1) {
            textViewMaritalError.setVisibility(TextView.VISIBLE);
            if (firstInvalidField == null) firstInvalidField = radioGroupMarital;
            valid = false;
        }

        return valid;
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}