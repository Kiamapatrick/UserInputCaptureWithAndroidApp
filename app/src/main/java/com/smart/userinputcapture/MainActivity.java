package com.smart.userinputcapture; // change to match your own package name

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout layoutName, layoutDate, layoutEmail;
    private TextInputEditText editTextName, editTextDate, editTextEmail;
    private RadioGroup radioGroupMarital;
    private Button buttonSubmit;

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

        String maritalStatus = (radioGroupMarital.getCheckedRadioButtonId() == R.id.radioMarried)
                ? getString(R.string.radio_married) : getString(R.string.radio_single);

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

        if (name.isEmpty()) {
            layoutName.setError(getString(R.string.error_name_required));
            valid = false;
        }
        if (date.isEmpty()) {
            layoutDate.setError(getString(R.string.error_date_required));
            valid = false;
        }
        if (email.isEmpty()) {
            layoutEmail.setError(getString(R.string.error_email_required));
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            layoutEmail.setError(getString(R.string.error_email_invalid));
            valid = false;
        }
        if (radioGroupMarital.getCheckedRadioButtonId() == -1) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.error_marital_required)
                    .setMessage(R.string.error_marital_inline)
                    .setPositiveButton(R.string.dialog_button_ok, null)
                    .show();
            valid = false;
        }
        return valid;
    }
}