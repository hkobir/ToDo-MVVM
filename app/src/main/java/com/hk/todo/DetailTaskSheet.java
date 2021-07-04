package com.hk.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.hk.todo.utils.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DetailTaskSheet extends BottomSheetDialogFragment {
    private TextView titleTV, detailTV, dateTV;
    private CheckBox statusCheckBox;
    private int id;
    private String title, detail, date, status;
    TaskViewModel viewModel;

    public DetailTaskSheet() {
    }

    public DetailTaskSheet(int id, String title, String detail, String date, String status) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.date = date;
        this.status = status;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_task_sheet, container);
        detailTV = view.findViewById(R.id.detailTV);
        titleTV = view.findViewById(R.id.titleTV);
        dateTV = view.findViewById(R.id.dateTV);
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        statusCheckBox = view.findViewById(R.id.statusCB);

        titleTV.setText(title);
        detailTV.setText(detail);
        dateTV.setText(date);

        getStatus();
        statusCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    statusCheckBox.setText("Complete");
                    updateStatus("done");
                } else {
                    statusCheckBox.setText("Inomplete");
                    updateStatus("undone");
                }
            }
        });
        return view;
    }

    private void updateStatus(String status) {
        viewModel.updateTask(
                id,
                title,
                detail,
                status
        );
    }

    private void getStatus() {
        if (status.equals("done")) {
            statusCheckBox.setChecked(true);
            statusCheckBox.setText("Complete");
        } else {
            statusCheckBox.setChecked(false);
            statusCheckBox.setText("Inomplete");
        }

    }

}
