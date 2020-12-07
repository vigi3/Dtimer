package com.proj.dtimer;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Tasks")

public class Tasks {

    @DatabaseField(generatedId = true)
    private int idTask;
    @DatabaseField
    private String name;
    @DatabaseField
    private int state;
    @DatabaseField
    private int priority;
    @DatabaseField
    private int idProject;

    public Tasks() {
    }

    public Tasks(String name, int state, int priority, int idProject) {
        this.name = name;
        this.state = state;
        this.priority = priority;
        this.idProject = idProject;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    @Override
    public String toString() {return name + '\n' + state + '\n' + priority + '\n' + idProject; }


}
