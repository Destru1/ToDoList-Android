package com.example.todolist.utility;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todolist.TaskDao;
import com.example.todolist.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class},version = 1,exportSchema = false)
public abstract class TaskRoomDatabase  extends RoomDatabase{
    public static final int NUMBER_OF_THREADS = 4;
    public static final String DATABASE = "toDoList_database";
    public static volatile TaskRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriterExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static final RoomDatabase.Callback sRoomDataBaseCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseWriterExecutor.execute(() -> {

                TaskDao taskDao = INSTANCE.taskDao();
                taskDao.deleteAll();
            });
        }
    };

    public static TaskRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (TaskRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDatabase.class,DATABASE).addCallback(sRoomDataBaseCallBack).build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract TaskDao taskDao();
}
