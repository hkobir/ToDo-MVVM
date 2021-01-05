package com.example.todo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.DetailTaskSheet;
import com.example.todo.MainActivity;
import com.example.todo.R;
import com.example.todo.models.Task;
import com.example.todo.utils.TaskViewModel;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    List<Task> taskList;
    Context context;
    TaskViewModel viewModel;
    private AlertDialog menuDialog, updateDialogue, deleteDialogue;

    public TaskAdapter(List<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, final int position) {

        final Task cTask = taskList.get(position);
        holder.title.setText(cTask.getTitle());
        holder.detail.setText(cTask.getDetail());
        holder.date.setText(cTask.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailTaskSheet detailTaskSheet = new DetailTaskSheet(cTask.getTitle(), cTask.getDetail(), cTask.getDate());
                detailTaskSheet.show(((FragmentActivity) context).getSupportFragmentManager(), "Task Details");
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showTaskMenu(position, cTask);
                //show task editing menu
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, detail, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTaskTV);
            detail = itemView.findViewById(R.id.detailTaskTV);
            date = itemView.findViewById(R.id.dateTV);
        }
    }


    private void showTaskMenu(final int position, final Task task) {
        viewModel = new ViewModelProvider((FragmentActivity) context).get(TaskViewModel.class);

        menuDialog = new AlertDialog.Builder(context).create();
        final View view = LayoutInflater.from(context).inflate(R.layout.task_menu_dialogue, null);
        LinearLayout editBtn = view.findViewById(R.id.editBtn);
        LinearLayout deleteBtn = view.findViewById(R.id.deleteBtn);
        Button cancelBtn = view.findViewById(R.id.cancelBtn);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTask(task);
                menuDialog.dismiss();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask(position);
                menuDialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuDialog.dismiss();
            }
        });

        menuDialog.setView(view);
        menuDialog.show();
    }

    private void editTask(final Task task) {
        updateDialogue = new AlertDialog.Builder(context).create();
        final View view = LayoutInflater.from(context).inflate(R.layout.add_task_dialogue, null);
        final EditText titleET = view.findViewById(R.id.titleET);
        final EditText detailET = view.findViewById(R.id.detailET);

        titleET.setText(task.getTitle());
        detailET.setText(task.getDetail());

        Button saveBtn = view.findViewById(R.id.saveButton);
        Button cancelBtn = view.findViewById(R.id.cancelButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleET.getText().toString().trim().equals("")) {
                    titleET.setError("Task title");
                    titleET.requestFocus();
                    return;
                }
                if (detailET.getText().toString().trim().equals("")) {
                    detailET.setError("Task detail");
                    detailET.requestFocus();
                    return;
                }

                viewModel.updateTask(
                        task.getId(),
                        titleET.getText().toString(),
                        detailET.getText().toString()
                );
                Toast.makeText(context, "Task updated", Toast.LENGTH_SHORT).show();
                updateDialogue.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDialogue.dismiss();
            }
        });
        updateDialogue.setView(view);
        updateDialogue.show();
    }

    private void deleteTask(final int position) {
        deleteDialogue = new AlertDialog.Builder(context).create();
        final View view = LayoutInflater.from(context).inflate(R.layout.delete_task_confirm_dialogue, null);
        Button deleteBtn = view.findViewById(R.id.deleteButon);
        Button cancelBtn = view.findViewById(R.id.cancelButon);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = taskList.get(position);
                viewModel.deleteTask(task);
                notifyDataSetChanged();
                deleteDialogue.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialogue.dismiss();
            }
        });

        deleteDialogue.setView(view);
        deleteDialogue.show();
    }
}
