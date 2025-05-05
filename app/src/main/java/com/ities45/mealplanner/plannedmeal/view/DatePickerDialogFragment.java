package com.ities45.mealplanner.plannedmeal.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment {
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private static final long ONE_WEEK_MILLIS = 604800000L;

    public void setDateSetListener(DatePickerDialog.OnDateSetListener listener) {
        this.dateSetListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    Calendar selected = Calendar.getInstance();
                    selected.set(year, month, dayOfMonth);

                    if (isWithinWeek(selected)) {
                        dateSetListener.onDateSet(view, year, month, dayOfMonth);
                    } else {
                        Toast.makeText(getContext(), "Can only plan meals for the next 7 days", Toast.LENGTH_SHORT).show();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis() + ONE_WEEK_MILLIS);

        return dialog;
    }

    private boolean isWithinWeek(Calendar selectedDate) {
        Calendar now = Calendar.getInstance();
        long diff = selectedDate.getTimeInMillis() - now.getTimeInMillis();
        return diff >= 0 && diff <= ONE_WEEK_MILLIS;
    }
}
