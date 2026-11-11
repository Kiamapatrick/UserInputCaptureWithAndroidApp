package com.smart.userinputcapture; // change to match your own package name

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

    // Date format for parsing/displaying
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

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

        // Open the calendar when the date field is tapped
        editTextDate.setOnClickListener(v -> showDatePicker());

        // Open the calendar when the end icon is tapped
        layoutDate.setEndIconOnClickListener(v -> showDatePicker());

        // Show the summary dialog on submit
        buttonSubmit.setOnClickListener(v -> onSubmit());
    }

    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog picker = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Month is zero-based, so add 1
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    editTextDate.setText(date);
                    layoutDate.setError(null);
                },
                year, month, day);

        // Prevent choosing a future date of birth
        picker.getDatePicker().setMaxDate(System.currentTimeMillis());
        picker.show();
    }

    private void onSubmit() {
        String name = editTextName.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        if (!validate(name, date, email)) {
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

        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.dialog_title_submitted)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_button_ok, null)
                .show();
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
            valid = false;
        }

        // Validate date
        if (TextUtils.isEmpty(date)) {
            layoutDate.setError(getString(R.string.error_date_required));
            valid = false;
        } else {
            // Check if adult (18+)
            try {
                Date dob = DATE_FORMAT.parse(date);
                Calendar dobCal = Calendar.getInstance();
                dobCal.setTime(dob);
                Calendar today = Calendar.getInstance();
                if (!Validators.isAdult(dobCal, today)) {
                    layoutDate.setError(getString(R.string.error_date_required)); // reuse or add new string
                    valid = false;
                }
            } catch (Exception e) {
                layoutDate.setError(getString(R.string.error_date_required));
                valid = false;
            }
        }

        // Validate email using Validators
        if (!Validators.isValidEmail(email)) {
            layoutEmail.setError(getString(R.string.error_email_invalid));
            valid = false;
        }

        // Validate marital status
        if (radioGroupMarital.getCheckedRadioButtonId() == -1) {
            textViewMaritalError.setVisibility(TextView.VISIBLE);
            valid = false;
        }

        return valid;
    }
}