package com.proj.dtimer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;

public class TaskListAdapter extends BaseAdapter {
    private List<Tasks> tasksList;
    private LayoutInflater layoutInflater;
    private Context context;

    public TaskListAdapter(Context context, List<Tasks> tasksList) {
        this.context = context;
        this.tasksList = tasksList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tasksList.size();
    }

    @Override
    public Object getItem(int position) {
        return tasksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertedView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertedView == null){
            convertedView = layoutInflater.inflate(R.layout.details_project_layout, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertedView.findViewById(R.id.detailName);
            holder.priorityNameView = (TextView)convertedView.findViewById(R.id.priority);
            convertedView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertedView.getTag();
        }

        Tasks tasks = this.tasksList.get(position);
        holder.nameView.setText(tasks.getName());
        switch (tasks.getPriority()) {
            case 0:
                holder.priorityNameView.setText("Normal");
                holder.priorityNameView.setTextColor(ContextCompat.getColor(context,R.color.colorWhite));
                break;
            case 1:
                holder.priorityNameView.setText("Urgent");
                holder.priorityNameView.setTextColor(ContextCompat.getColor(context,R.color.colorRed));
                break;
            case 2:
                holder.priorityNameView.setText("Important");
                holder.priorityNameView.setTextColor(ContextCompat.getColor(context,R.color.colorYellowSpice));
                break;

        }

        return convertedView;
    }

    static class ViewHolder {
        TextView nameView;
        TextView priorityNameView;
    }
}
