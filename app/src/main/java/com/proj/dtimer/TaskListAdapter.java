package com.proj.dtimer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
            convertedView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertedView.getTag();
        }

        Tasks tasks = this.tasksList.get(position);
        holder.nameView.setText(tasks.getName());

        return convertedView;
    }

    static class ViewHolder {
        TextView nameView;
    }
}
