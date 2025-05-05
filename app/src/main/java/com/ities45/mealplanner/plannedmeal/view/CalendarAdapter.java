package com.ities45.mealplanner.plannedmeal.view;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ities45.mealplanner.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarAdapter extends ListAdapter<String, CalendarAdapter.ViewHolder> {
    private MutableLiveData<String> selectedDate = new MutableLiveData<>();
    private IOnDayClickListener listener;
    private static final long ONE_WEEK_MILLIS = 604800000L;


    private static final DiffUtil.ItemCallback<String> DIFF_CALLBACK = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    };

    public CalendarAdapter(List<String> dates, IOnDayClickListener listener) {
        super(DIFF_CALLBACK);
        submitList(dates);
        selectedDate.setValue(dates.get(0));
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Make sure this matches your actual XML file name
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_item, parent, false); // Changed to item_day
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String date = getItem(position);
        SimpleDateFormat displayFormat = new SimpleDateFormat("EEE\nd", Locale.getDefault());
        SimpleDateFormat parserFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date parsedDate = parserFormat.parse(date);
            String[] parts = displayFormat.format(parsedDate).split("\n");

            // Set both day name and date number
            holder.dayName.setText(parts[0]);
            holder.dateNumber.setText(parts[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(v -> {
            if (isDateWithinWeek(date)) {
                selectedDate.setValue(date);
                listener.onDayClick(date);
            }
        });

        // Highlight using the root view
        holder.itemView.setBackgroundColor(
                date.equals(selectedDate.getValue()) ? Color.LTGRAY : Color.TRANSPARENT
        );
    }

    // Fixed date validation method
    private boolean isDateWithinWeek(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = sdf.parse(dateString);

            Calendar now = Calendar.getInstance();
            Calendar selected = Calendar.getInstance();
            selected.setTime(date);

            // Reset time components for accurate comparison
            now.set(Calendar.HOUR_OF_DAY, 0);
            now.set(Calendar.MINUTE, 0);
            now.set(Calendar.SECOND, 0);
            now.set(Calendar.MILLISECOND, 0);
            selected.set(Calendar.HOUR_OF_DAY, 0);
            selected.set(Calendar.MINUTE, 0);
            selected.set(Calendar.SECOND, 0);
            selected.set(Calendar.MILLISECOND, 0);

            long diff = selected.getTimeInMillis() - now.getTimeInMillis();
            return diff >= 0 && diff <= ONE_WEEK_MILLIS;
        } catch (ParseException e) {
            return false;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dayName;
        public TextView dateNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayName = itemView.findViewById(R.id.dayName);
            dateNumber = itemView.findViewById(R.id.dateNumber);
        }
    }
}
