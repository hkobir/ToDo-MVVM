package com.example.todo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.DetailTaskSheet;
import com.example.todo.R;
import com.example.todo.models.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    List<Task> taskList;
    Context context;

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
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {

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
}
