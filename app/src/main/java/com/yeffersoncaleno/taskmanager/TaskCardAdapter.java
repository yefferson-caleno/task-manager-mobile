package com.yeffersoncaleno.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class TaskCardAdapter extends RecyclerView.Adapter<TaskCardAdapter.ViewHolder> {

    private List<TaskCardActivity> taskCardData;
    private LayoutInflater inflater;
    private Context context;

    public TaskCardAdapter(List<TaskCardActivity> taskCardList, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.taskCardData = taskCardList;
    }

    @Override
    public TaskCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_task_card, null);
        return new TaskCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskCardAdapter.ViewHolder holder, int position) {
        holder.bindData(taskCardData.get(position));
        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return taskCardData.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        TextView uid, title, state;
        ImageView iconTaskCard, iconViewTaskCard, iconDeleteTaskCard;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            uid = itemView.findViewById(R.id.txtUidCardTask);
            title = itemView.findViewById(R.id.txtTitleCardTask);
            state = itemView.findViewById(R.id.txtStateCardTask);
            iconTaskCard = itemView.findViewById(R.id.iconImageView);
            iconViewTaskCard = itemView.findViewById(R.id.btnUpdateCardTask);
            iconDeleteTaskCard = itemView.findViewById(R.id.btnDeleteCardTask);
        }

        void bindData(final TaskCardActivity item) {
            uid.setText(item.getUid());
            uid.setVisibility(View.GONE);
            title.setText(item.getTitle());
            state.setText(item.getState());
            iconTaskCard.setColorFilter(Color.parseColor(goToColor(item.getState())),
                    PorterDuff.Mode.SRC_IN);
            iconViewTaskCard.setColorFilter(Color.parseColor(goToColor(item.getState())),
                    PorterDuff.Mode.SRC_IN);
            iconDeleteTaskCard.setColorFilter(Color.parseColor(
                    context.getString(R.string.warning_color)), PorterDuff.Mode.SRC_IN);
        }

        void setOnClickListeners() {
            iconViewTaskCard.setOnClickListener(this);
            iconDeleteTaskCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnUpdateCardTask:
                    Intent intent = new Intent(context, TaskActivity.class);
                    intent.putExtra("uid", uid.getText().toString());
                        context.startActivity(intent);
                    break;
                case R.id.btnDeleteCardTask:

                    break;
            }
        }
    }

    private String goToColor(String state) {
        String color = context.getString(R.string.warning_color);
        switch (state.toLowerCase()) {
            case "nueva":
                color = context.getString(R.string.new_color);
                break;
            case "activa":
                color = context.getString(R.string.active_color);
                break;
            case "impedimento":
                color = context.getString(R.string.impediment_color);
                break;
            case "cerrada":
                color = context.getString(R.string.closed_color);
                break;
        };
        return color;
    }
}
