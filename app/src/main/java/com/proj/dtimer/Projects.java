package com.proj.dtimer;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Tasks")

public class Projects {

    @DatabaseField(generatedId = true)
    private int idProject;
    @DatabaseField
    private String name;
    @DatabaseField
    private int percentageCompletion;


    public Projects() {
    }

    public Projects(String name, int percentageCompletion, int idProject) {
        this.name = name;
        this.percentageCompletion = percentageCompletion;
        this.idProject = idProject;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercentageCompletion() {
        return percentageCompletion;
    }

    public void setPercentageCompletion(int percentageCompletion) {
        this.percentageCompletion = percentageCompletion;
    }
}
