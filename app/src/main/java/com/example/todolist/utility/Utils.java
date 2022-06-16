package com.example.todolist.utility;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.todolist.model.Priority;
import com.example.todolist.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
 public  static String formatDate(Date date){
     SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
     simpleDateFormat.applyPattern("EEE,MMM,d");
     return  simpleDateFormat.format(date);
 }

 public static void hideKeyboard(View view){
     InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
     inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
 }

 public static int priorityColor(Task task){
     int color;
     if (task.getPriority() == Priority.HIGH){
         color = Color.argb(200,231,65,65);
     }
     else if (task.getPriority() == Priority.MEDIUM){
         color = Color.argb(200,67,164,7);
     }
     else if (task.getPriority() == Priority.LOW){
         color = Color.argb(200,93,163,243);
     }
     else {
         color = Color.argb(60,0,0,0);
     }
     return color;
 }
}
