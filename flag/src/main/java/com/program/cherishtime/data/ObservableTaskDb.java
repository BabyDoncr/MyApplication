package com.program.cherishtime.data;

import android.content.Context;
import android.database.Cursor;

import com.program.cherishtime.bean.Task;
import com.program.cherishtime.utils.LogUtil;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ObservableTaskDb {
    private PublishSubject<ArrayList<Task>> mPublishSubject;

    private PublishSubject<Task> mTaskPublishSubject;

    private TaskDbHelper mDBHelper;

    public ObservableTaskDb(Context context) {
        mDBHelper = new TaskDbHelper(context);
        mPublishSubject = PublishSubject.create();
        mTaskPublishSubject = PublishSubject.create();
    }

    public Observable<ArrayList<Task>> getObservable(int belong) {
        Observable<ArrayList<Task>> observable = Observable.fromCallable(() -> getPickedTaskList(belong));
        return observable.concatWith(mPublishSubject);
    }

    public Observable<Task> getTask(int tid, int belong) {
        Observable<Task> observable = Observable.fromCallable(() -> getTaskByTid(tid, belong));
        return observable.concatWith(mTaskPublishSubject);
    }

    private Task getTaskByTid(int tid, int belong) {
        Task task = new Task();
        mDBHelper.open();
        Cursor c = mDBHelper.getTaskByTidAndBelong(tid, belong);
        if (!c.moveToFirst()) {
            return task;
        } else {
            task.setId(c.getInt(TaskDbHelper.TASK_ID_COLUMN_POSITION));
            task.setStartTime(c.getString(TaskDbHelper.TASK_STARTTIME_COLUMN_POSITION));
            task.setEndTime(c.getString(TaskDbHelper.TASK_ENDTIME_COLUMN_POSITION));
            task.setTitle(c.getString(TaskDbHelper.TASK_TITLE_COLUMN_POSITION));
            task.setContent(c.getString(TaskDbHelper.TASK_CONTENT_COLUMN_POSITION));
            task.setType(c.getInt(TaskDbHelper.TASK_TYPE_COLUMN_POSITION));
            task.setTaskDate(c.getInt(TaskDbHelper.TASK_TASKDATE_COLUMN_POSITION));
            task.setDel(c.getInt(TaskDbHelper.TASK_DEL_COLUMN_POSITION));
        }
        c.close();
        mDBHelper.close();
        return task;
    }


    private ArrayList<Task> getPickedTaskList(int belong) {
        mDBHelper.open();
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor cursor = mDBHelper.getTasksByBelong(belong);
        if (!cursor.moveToFirst()) {
            LogUtil.d("Test", "未查询到数据");
            return tasks;
        }
        do {
            Task task = new Task();
            task.setId(cursor.getInt(TaskDbHelper.TASK_ID_COLUMN_POSITION));
            task.setStartTime(cursor.getString(TaskDbHelper.TASK_STARTTIME_COLUMN_POSITION));
            task.setEndTime(cursor.getString(TaskDbHelper.TASK_ENDTIME_COLUMN_POSITION));
            task.setTitle(cursor.getString(TaskDbHelper.TASK_TITLE_COLUMN_POSITION));
            task.setContent(cursor.getString(TaskDbHelper.TASK_CONTENT_COLUMN_POSITION));
            task.setType(cursor.getInt(TaskDbHelper.TASK_TYPE_COLUMN_POSITION));
            task.setTaskDate(cursor.getInt(TaskDbHelper.TASK_TASKDATE_COLUMN_POSITION));
            task.setDel(cursor.getInt(TaskDbHelper.TASK_DEL_COLUMN_POSITION));
            tasks.add(task);
            LogUtil.d("Test", task.toString());
        } while (cursor.moveToNext());
        cursor.close();
        mDBHelper.close();
        return tasks;
    }

    public void insertPickedTaskList(ArrayList<Task> tasks, int belong) {
        mDBHelper.open();
        mDBHelper.removeTasksByBelong(belong);
        for (Task task : tasks) {
            mDBHelper.addTask(
                    task.getId(),
                    task.getStartTime(),
                    task.getEndTime(),
                    task.getTitle(),
                    task.getContent(),
                    task.getType(),
                    task.getTaskDate(),
                    task.getDel(),
                    belong
            );
        }
        mDBHelper.close();
        mPublishSubject.onNext(tasks);
    }

    public void insertTask(Task task, int belong) {
        mDBHelper.open();
        mDBHelper.removeTaskByTidAndBelong(task.getId(), belong);
        mDBHelper.addTask(
                task.getId(),
                task.getStartTime(),
                task.getEndTime(),
                task.getTitle(),
                task.getContent(),
                task.getType(),
                task.getTaskDate(),
                task.getDel(),
                belong
        );
        mDBHelper.close();
        mTaskPublishSubject.onNext(task);
    }
}
