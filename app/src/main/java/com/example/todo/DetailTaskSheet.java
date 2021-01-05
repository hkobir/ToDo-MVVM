package com.example.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DetailTaskSheet extends BottomSheetDialogFragment {
    private TextView titleTV, detailTV, dateTV;
    private String title, detail, date;

    public DetailTaskSheet() {
    }

    public DetailTaskSheet(String title, String detail, String date) {
        this.title = title;
        this.detail = detail;
        this.date = date;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_task_sheet, container);
        detailTV = view.findViewById(R.id.detailTV);
        titleTV = view.findViewById(R.id.titleTV);
        dateTV = view.findViewById(R.id.dateTV);

        titleTV.setText(title);
        detailTV.setText(detail);
        dateTV.setText(date);

        return view;
    }
}
