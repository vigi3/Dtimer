package com.proj.dtimer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.util.List;

public class DataBaseManager extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NOM = "DTimer.db";
    private static final int DATABASE_VERSION = 4;

    public DataBaseManager(Context context) {
        super(context, DATABASE_NOM, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Projects.class);
            TableUtils.createTable(connectionSource, Tasks.class);
        } catch (Exception e) {
            Log.e("DATABASE", "error Database creation");
        }
    }

    //Change Database version and drop table
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Tasks.class, true);
            TableUtils.dropTable(connectionSource, Projects.class, true);
//            if all tables are dropped
            onCreate(database, connectionSource);
        } catch (Exception e) {
            Log.e("DATABASE", "Upgrade error");

        }
    }

    //Add new Task
    public int addTask (Tasks newUtil){
        try {
            Dao<Tasks,Integer> maDao = getDao(Tasks.class);
            maDao.create(newUtil);
            return newUtil.getIdTask();
        } catch (Exception e){
            Log.e ("DATABASE", "Error creating new task");
            return 0;
        }
    }

    //Add new Project
    public int addProject (Projects newUtil){
        try {
            Dao<Projects,Integer> maDao = getDao(Projects.class);
            maDao.create(newUtil);
            return newUtil.getIdProject();
        } catch (Exception e){
            Log.e ("DATABASE", "Error creating new project");
            return 0;
        }
    }

    //Show all Tasks
    public List<Tasks> showAllTasks() {
        try {
            Dao<Tasks,Integer> maDao = getDao(Tasks.class);
            List<Tasks> tasks = maDao.queryForAll();
            return tasks;
        }catch (Exception e) {
            Log.e ("DATABASE", "Error reading and showing tasks");
            return null;
        }
    }

    //Show all Projects
    public List<Projects> showAllProjects() {
        try {
            Dao<Projects,Integer> maDao = getDao(Projects.class);
            List<Projects> projects = maDao.queryForAll();
            return projects;
        }catch (Exception e) {
            Log.e ("DATABASE", "Error reading and showing projects");
            return null;
        }
    }

    //Show all Tasks for ONE PROJECT
    public List<Tasks> showAllTasksFromOneProject(int id) {
        try {
            Dao<Tasks,Integer> maDao = getDao(Tasks.class);
            List<Tasks> tasks = maDao.queryForEq("idProject", id);
            return tasks;
        }
        catch (Exception e) {
            Log.e ("DATABASE", "Error reading and showing tasks for one project. Id: " + id);
            return null;
        }
    }

    //Show one Task
    public Tasks showOneTask(int id) {
        try {
            Dao<Tasks,Integer> maDao = getDao(Tasks.class);
            Tasks task = maDao.queryForId(id);
            return task;
        }
        catch (Exception e) {
            Log.e ("DATABASE", "Error reading and showing tasks for one project. Id: " + id);
            return null;
        }
    }

    //Update Task
    public int updateTasks(Tasks tasks) {
        try {
            Dao<Tasks,Integer> maDao = getDao(Tasks.class);
            int upTask = maDao.update(tasks);
            return upTask;
        }  catch (Exception e) {
            Log.e ("DATABASE", "Error Updating Tasks");
            return 0;
        }

    }

    //Update Projects
    public int updateProject(Projects projects) {
        try {
            Dao<Projects,Integer> maDao = getDao(Projects.class);
            int upProject = maDao.update(projects);
            return upProject;
        }  catch (Exception e) {
            Log.e ("DATABASE", "Error Updating Projects");
            return 0;
        }

    }

    //Delete Task
    public int delTaskById (Tasks tasks) {
        try {
            Dao<Tasks,Integer> maDao = getDao(Tasks.class);
            String textErr = "Deleted ID " + tasks.getIdTask();
            Log.e ("DATABASE", textErr);
            int deletedTask = maDao.delete(tasks);
            return deletedTask;
        } catch (Exception e) {
            Log.e ("DATABASE", "Error deleting ID Task");
            return 0;
        }
    }

    //Delete Project
    public int delProject (Projects projects) {
        try {
            Dao<Projects,Integer> maDao = getDao(Projects.class);
            String textErr = "Deleted ID " + projects.getIdProject();
            Log.e ("DATABASE", textErr);
            int deletedProject = maDao.delete(projects);
            return deletedProject;
        } catch (Exception e) {
            Log.e ("DATABASE", "Error deleting ID Project");
            return 0;
        }
    }
}