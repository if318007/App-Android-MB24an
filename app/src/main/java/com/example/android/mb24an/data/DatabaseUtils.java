package com.example.android.mb24an.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Siriporn on 7/24/2017.
 */

public class DatabaseUtils {

    private static final String TAG = "dbUtils";

    public static Cursor getAllTask(SQLiteDatabase db) {
        Cursor cursor = db.query(
                Contract.TABLE_TASK.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    public static Cursor getAllProject(SQLiteDatabase db) {
        Cursor cursor = db.query(
                Contract.TABLE_PROJECT.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    public static Cursor getAllProjectWithTaskCount(SQLiteDatabase db){

        String query =
                "select count( " +
                        Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID + ") as " +
                        Contract.TABLE_PROJECT.COLUMN_NAME_NUMBER_OF_TASKS +
                        ", " + Contract.TABLE_PROJECT.TABLE_NAME + "." + Contract.TABLE_PROJECT.COLUMN_NAME_TITLE +
                        ", " + Contract.TABLE_PROJECT.TABLE_NAME + "." + Contract.TABLE_PROJECT._ID +
                        " from " + Contract.TABLE_TASK.TABLE_NAME +
                        " inner join " + Contract.TABLE_PROJECT.TABLE_NAME +
                        " on (" + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID + " = " + Contract.TABLE_PROJECT.TABLE_NAME + "." + Contract.TABLE_SUBJECT._ID+  ")" +
                        " group by " + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID + " , "  + Contract.TABLE_PROJECT.TABLE_NAME + "." + Contract.TABLE_PROJECT._ID +
                        " order by " +  Contract.TABLE_PROJECT.TABLE_NAME + "." + Contract.TABLE_PROJECT.COLUMN_NAME_TITLE ;


        Log.d(TAG, "Select table SQL: " + query);
        Cursor cursor = db.rawQuery(query, null);
        Log.v("Cursor Object", android.database.DatabaseUtils.dumpCursorToString(cursor));

        return cursor;
    }

    public static Cursor getAllTasksOfProject(SQLiteDatabase db, int projectId){

        String query =
                "select " +
                        Contract.TABLE_TASK.COLUMN_NAME_DATE +
                        ", " + Contract.TABLE_TASK.COLUMN_NAME_START_HOUR +
                        ", " + Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE +
                        ", " + Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY +
                        ", " + Contract.TABLE_TASK.COLUMN_NAME_END_HOUR +
                        ", " + Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE +
                        ", " + Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY +
                        ", " + Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES +
                        " from " + Contract.TABLE_TASK.TABLE_NAME +
                        " where "+ Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID + " = "+ String.valueOf(projectId) +
                        " order by " +  Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_DATE ;


        Log.d(TAG, "Select table SQL: " + query);
        Cursor cursor = db.rawQuery(query, null);
        Log.v("Cursor Object", android.database.DatabaseUtils.dumpCursorToString(cursor));

        return cursor;
    }

    public static Cursor getAllTasksOfSubject(SQLiteDatabase db, int subjectId){

        String query =
                "select " +
                        Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_DATE +
                        ", " + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_START_HOUR +
                        ", " + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE +
                        ", " + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY +
                        ", " + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_END_HOUR +
                        ", " + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE +
                        ", " + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY +
                        ", " + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES +
                        ", " + Contract.TABLE_PROJECT.TABLE_NAME + "." + Contract.TABLE_PROJECT.COLUMN_NAME_TITLE + " as " + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_TITLE +
                        " from " + Contract.TABLE_TASK.TABLE_NAME +
                        " inner join " + Contract.TABLE_PROJECT.TABLE_NAME +
                        " on (" + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID + " = " + Contract.TABLE_PROJECT.TABLE_NAME + "." + Contract.TABLE_PROJECT._ID+  ")" +
                        " where " + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID + " = " + String.valueOf(subjectId) +
                        " order by " +  Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_DATE ;


        Log.d(TAG, "Select table SQL: " + query);
        Cursor cursor = db.rawQuery(query, null);
        Log.v("Cursor Object", android.database.DatabaseUtils.dumpCursorToString(cursor));

        return cursor;
    }


    public static Cursor getAllSubjectsWithTaskCount(SQLiteDatabase db){

        String query =
                "select count( " +
                        Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID + ") as " +
                        Contract.TABLE_SUBJECT.COLUMN_NAME_NUMBER_OF_TASKS +
                        ", " + Contract.TABLE_SUBJECT.TABLE_NAME + "." + Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE +
                        ", " + Contract.TABLE_SUBJECT.TABLE_NAME + "." + Contract.TABLE_SUBJECT._ID +
                        " from " + Contract.TABLE_TASK.TABLE_NAME +
                        " inner join " + Contract.TABLE_SUBJECT.TABLE_NAME +
                        " on (" + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID + " = " + Contract.TABLE_SUBJECT.TABLE_NAME + "." + Contract.TABLE_SUBJECT._ID+  ")" +
                        " group by " + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID + " , "  + Contract.TABLE_SUBJECT.TABLE_NAME + "." + Contract.TABLE_SUBJECT._ID +
                        " order by " +  Contract.TABLE_SUBJECT.TABLE_NAME + "." + Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE ;


        Log.d(TAG, "Select table SQL: " + query);
        Cursor cursor = db.rawQuery(query, null);
        Log.v("Cursor Object", android.database.DatabaseUtils.dumpCursorToString(cursor));

        return cursor;
    }

    public static Cursor getAllSubject(SQLiteDatabase db) {
        Cursor cursor = db.query(
                Contract.TABLE_SUBJECT.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    public static Cursor getTodaysTask(SQLiteDatabase db)
    {
        return getDaysTask(db, 0);
    }

    public static Cursor getDaysTask(SQLiteDatabase db, int dayOffset) {
        Calendar c = GregorianCalendar.getInstance();
        c.add(Calendar.DATE, dayOffset);
        SimpleDateFormat dbDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String startDate = dbDateFormat.format(c.getTime());

        return db.query(
                Contract.TABLE_TASK.TABLE_NAME,
                null,
                Contract.TABLE_TASK.COLUMN_NAME_DATE + " = ?",
                new String[]{startDate},
                null,
                null,
                null);
    }
    public static String getDay(int dayOffset)
    {
        Calendar c = GregorianCalendar.getInstance();
        c.add(Calendar.DATE, dayOffset);
        SimpleDateFormat dbDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dbDateFormat.format(c.getTime());
    }

    public static Cursor getThisWeeksTask(SQLiteDatabase db)
    {
        return getWeeksTask(db, 0);
    }

    public static Cursor getWeeksTask(SQLiteDatabase db, int weekOffset) {

        Calendar c = GregorianCalendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        DateFormat dbDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        c.add(Calendar.DATE, 7 * weekOffset);
        String startDate = dbDateFormat.format(c.getTime());
        c.add(Calendar.DATE, 6);
        String endDate = dbDateFormat.format(c.getTime());

        return db.query(
                Contract.TABLE_TASK.TABLE_NAME,
                null,
                Contract.TABLE_TASK.COLUMN_NAME_DATE + " BETWEEN ? AND ?",
                new String[]{startDate, endDate},
                null,
                null,
                Contract.TABLE_TASK.COLUMN_NAME_DATE);
    }

    public static String getWeek(int weekOffset)
    {
        Calendar c = GregorianCalendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        DateFormat dbDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        c.add(Calendar.DATE, 7 * weekOffset);
        String startDate = dbDateFormat.format(c.getTime());
        c.add(Calendar.DATE, 6);
        String endDate = dbDateFormat.format(c.getTime());

        return startDate + " - " + endDate;
    }

    public static ArrayList<Cursor> getThisMonthTask(SQLiteDatabase db)
    {
        return getMonthTask(db, 0);
    }

    public static ArrayList<Cursor> getMonthTask(SQLiteDatabase db, int monthOffset)
    {
        ArrayList<Cursor> weeksCursor = new ArrayList<>();
        ///*debug: tracks what is currently in the weeksCursor by date*/ ArrayList<String> weekTracker = new ArrayList<>();
        DateFormat dbDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        Calendar c = Calendar.getInstance();

        int month = c.get(Calendar.MONTH) + monthOffset;
        int year = c.get(Calendar.YEAR);

        while(month < 0)
        {
            month += 12;
            year -= 1;
        }

        int dayOfMonth = 1;
        c.set(year, month, dayOfMonth);
        //*debug: corner case for this method, April 2017 [two day ahead in the next month]*/c.set(c.get(Calendar.YEAR), Calendar.APRIL, dayOfMonth);
        int thisMonth = c.get(Calendar.MONTH);

        String startDate;
        String endDate;
        startDate = dbDateFormat.format(c.getTime());
        do {
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            {
                endDate = dbDateFormat.format(c.getTime());

                {// NOTE[Philip]: this bracket is just for readability for me
                    Cursor cursor = db.query(
                            Contract.TABLE_TASK.TABLE_NAME,
                            null,
                            Contract.TABLE_TASK.COLUMN_NAME_DATE + " BETWEEN ? AND ?",
                            new String[]{startDate, endDate},
                            null,
                            null,
                            Contract.TABLE_TASK.COLUMN_NAME_DATE);
                    weeksCursor.add(cursor);
                    ///*debug: tracks what is currently in the weeksCursor by date*/ weekTracker.add(startDate + " - " + endDate);
                }

                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), ++dayOfMonth);
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

                //IMPORTANT[philip]: startDate is only null when this loop detect that the current
                // month is a new month.
                if(thisMonth == c.get(Calendar.MONTH))
                {
                    startDate = dbDateFormat.format(c.getTime());
                }
                else
                {
                    startDate = null;
                }

            }
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), ++dayOfMonth);
            dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        }
        while (thisMonth == c.get(Calendar.MONTH));

        if(startDate != null)
        {
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), --dayOfMonth);
            while(thisMonth != c.get(Calendar.MONTH))
            {c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), --dayOfMonth);}
            endDate = dbDateFormat.format(c.getTime());

            Cursor cursor = db.query(
                    Contract.TABLE_TASK.TABLE_NAME,
                    null,
                    Contract.TABLE_TASK.COLUMN_NAME_DATE + " BETWEEN ? AND ?",
                    new String[]{startDate, endDate},
                    null,
                    null,
                    Contract.TABLE_TASK.COLUMN_NAME_DATE);
            weeksCursor.add(cursor);
            ///*debug: tracks what is currently in the weeksCursor by date*/ weekTracker.add(startDate + " - " + endDate);
        }

        return weeksCursor;
    }

    public static String getMonth(int monthOffset)
    {
        DateFormat dbDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        Calendar c = Calendar.getInstance();

        int month = c.get(Calendar.MONTH) + monthOffset;
        int year = c.get(Calendar.YEAR);

        while(month < 0)
        {
            month += 12;
            year -= 1;
        }

        int dayOfMonth = 1;
        c.set(year, month, dayOfMonth);
        //*debug: corner case for this method, April 2017 [two day ahead in the next month]*/c.set(c.get(Calendar.YEAR), Calendar.APRIL, dayOfMonth);
        int thisMonth = c.get(Calendar.MONTH);
        String monthInString = getMonthForInt(thisMonth);
        return year + " " + monthInString;
    }

    // copy this block of code from
    // https://stackoverflow.com/questions/14832151/how-to-get-month-name-from-calendar
    private static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    public static Cursor getTask(SQLiteDatabase db, String start,String end) {
        //add some spaces in front of aliases to avoid typo
        String taskAlias = " " + "T";
        String subjectAlias = " " + "S";
        String projectAlias = " " + "P";

        //SELECT T._id, s.title AS subjectTitle, p.title AS projectTitle,
        //date, startHour, startMinute, startMidDay
        //endHour, endMinute, endMidDay, taskTotalMinutes
        //FROM tasks T
        //INNER JOIN subjects S ON T.subjectId = S._id
        //INNER JOIN projects P ON T.projectId = P._id
        //WHERE T.date = ? ;
        /*
        String query = "SELECT " + taskAlias + "." + Contract.TABLE_TASK._ID +
                ", " + subjectAlias + "." + Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE + " AS " + Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_TITLE +
                ", " + projectAlias + "." + Contract.TABLE_PROJECT.COLUMN_NAME_TITLE + " AS " + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_TITLE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_DATE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_HOUR +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_HOUR +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES +
                " FROM " + Contract.TABLE_TASK.TABLE_NAME + taskAlias +
                " INNER JOIN " + Contract.TABLE_SUBJECT.TABLE_NAME + subjectAlias + " ON " +
                taskAlias + "." + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID + " = " + subjectAlias + "." + Contract.TABLE_SUBJECT._ID +
                " INNER JOIN " + Contract.TABLE_PROJECT.TABLE_NAME + projectAlias + " ON " +
                taskAlias + "." + Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID + " = " + projectAlias + "." + Contract.TABLE_PROJECT._ID +
                " WHERE " + taskAlias + "." + Contract.TABLE_TASK.COLUMN_NAME_DATE + "  between '" + start + "'and  '" + end + "';";
        //" WHERE 0;";
        */
        String query =
                "select " +
                Contract.TABLE_SUBJECT.TABLE_NAME + "." + Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE + " as " + Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_TITLE +
                ", " + Contract.TABLE_PROJECT.TABLE_NAME + "." + Contract.TABLE_PROJECT.COLUMN_NAME_TITLE + " as " + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_TITLE +
                ", " + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK._ID +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_DATE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_HOUR +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_HOUR +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES +
                " from " + Contract.TABLE_TASK.TABLE_NAME +
                " inner join " + Contract.TABLE_SUBJECT.TABLE_NAME +
                " on (" + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID + " = " + Contract.TABLE_SUBJECT.TABLE_NAME + "." + Contract.TABLE_SUBJECT._ID+  ")" +
                " inner join " + Contract.TABLE_PROJECT.TABLE_NAME +
                " on (" + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID + " = " + Contract.TABLE_PROJECT.TABLE_NAME + "." + Contract.TABLE_PROJECT._ID+  ")" +
                " where " + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_DATE + "  between '" + start + "'and  '" + end + "'";


        Log.d(TAG, "Select table SQL: " + query);
        Cursor cursor = db.rawQuery(query, null);
        Log.v("Cursor Object", android.database.DatabaseUtils.dumpCursorToString(cursor));
/*
        try {
            String[] columnNames = cursor.getColumnNames();
            for(String name: columnNames)
                Log.d(TAG, "column "+name);
        } finally {
            cursor.close();
        }
*/
        return cursor;
    }

    public static Cursor getDailyTask(SQLiteDatabase db, String date) {
        //add some spaces in front of aliases to avoid typo
        String taskAlias = " " + "T";
        String subjectAlias = " " + "S";
        String projectAlias = " " + "P";

        //SELECT T._id, s.title AS subjectTitle, p.title AS projectTitle,
        //date, startHour, startMinute, startMidDay
        //endHour, endMinute, endMidDay, taskTotalMinutes
        //FROM tasks T
        //INNER JOIN subjects S ON T.subjectId = S._id
        //INNER JOIN projects P ON T.projectId = P._id
        //WHERE T.date = ? ;
        /*
        String query = "SELECT " + taskAlias + "." + Contract.TABLE_TASK._ID +
                ", " + subjectAlias + "." + Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE + " AS " + Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_TITLE +
                ", " + projectAlias + "." + Contract.TABLE_PROJECT.COLUMN_NAME_TITLE + " AS " + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_TITLE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_DATE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_HOUR +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_HOUR +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES +
                " FROM " + Contract.TABLE_TASK.TABLE_NAME + taskAlias +
                " INNER JOIN " + Contract.TABLE_SUBJECT.TABLE_NAME + subjectAlias + " ON " +
                taskAlias + "." + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID + " = " + subjectAlias + "." + Contract.TABLE_SUBJECT._ID +
                " INNER JOIN " + Contract.TABLE_PROJECT.TABLE_NAME + projectAlias + " ON " +
                taskAlias + "." + Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID + " = " + projectAlias + "." + Contract.TABLE_PROJECT._ID +
                " WHERE " + taskAlias + "." + Contract.TABLE_TASK.COLUMN_NAME_DATE + " = '" + date + "';";
        */
        //" WHERE 0;";
        String query =
                "select " +
                Contract.TABLE_SUBJECT.TABLE_NAME + "." + Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE + " as " + Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_TITLE +
                ", " + Contract.TABLE_PROJECT.TABLE_NAME + "." + Contract.TABLE_PROJECT.COLUMN_NAME_TITLE + " as " + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_TITLE +
                ", " + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK._ID +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_DATE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_HOUR +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_HOUR +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY +
                ", " + Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES +
                " from " + Contract.TABLE_TASK.TABLE_NAME +
                " inner join " + Contract.TABLE_SUBJECT.TABLE_NAME +
                " on (" + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID + " = " + Contract.TABLE_SUBJECT.TABLE_NAME + "." + Contract.TABLE_SUBJECT._ID+  ")" +
                " inner join " + Contract.TABLE_PROJECT.TABLE_NAME +
                " on (" + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID + " = " + Contract.TABLE_PROJECT.TABLE_NAME + "." + Contract.TABLE_PROJECT._ID+  ")" +
                " where " + Contract.TABLE_TASK.TABLE_NAME + "." + Contract.TABLE_TASK.COLUMN_NAME_DATE + " = '"  + date + "'";
        Log.d(TAG, "Select table SQL: " + query);
        Cursor cursor = db.rawQuery(query, null);
        Log.v("Cursor Object", android.database.DatabaseUtils.dumpCursorToString(cursor));
/*
        try {
            String[] columnNames = cursor.getColumnNames();
            for(String name: columnNames)
                Log.d(TAG, "column "+name);
        } finally {
            cursor.close();
        }
*/
        return cursor;
    }

    public static long addTask(SQLiteDatabase db, String date, String subject, String project
            , int startHour, int startMinute, String startMidday
            , int endHour, int endMinute, String endMidday
            , int totalMinutes) {

        //check if subject not exist then insert
        int subjectId = findData(db, Contract.TABLE_SUBJECT.TABLE_NAME, Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE, subject);
        if (subjectId < 0) {
            subjectId = addSubject(db, subject);
        }
        //check if project not exist then insert
        int projectId = findData(db, Contract.TABLE_PROJECT.TABLE_NAME, Contract.TABLE_PROJECT.COLUMN_NAME_TITLE, project);
        if (projectId < 0) {
            projectId = addProject(db, project);
        }

        //save all column
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_DATE, date);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID, subjectId);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID, projectId);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_START_HOUR, startHour);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE, startMinute);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY, startMidday);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_END_HOUR, endHour);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE, endMinute);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY, endMidday);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES, totalMinutes);

        return db.insert(Contract.TABLE_TASK.TABLE_NAME, null, cv);
    }

    public static int addSubject(SQLiteDatabase db, String subject) {
        //save all column
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE, subject);
        return (int) db.insert(Contract.TABLE_SUBJECT.TABLE_NAME, null, cv);
    }

    public static int addProject(SQLiteDatabase db, String project) {
        //save all column
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_PROJECT.COLUMN_NAME_TITLE, project);
        return (int) db.insert(Contract.TABLE_PROJECT.TABLE_NAME, null, cv);
    }

    public static int findData(SQLiteDatabase db, String tableName, String dbField, String fieldValue) {
        String[] columns = {"_id", dbField};
        String selection = dbField + " =?";
        String[] selectionArgs = {fieldValue};
        String limit = "1";
        int id = -1;

        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null, limit);
        Log.v("Cursor Object", android.database.DatabaseUtils.dumpCursorToString(cursor));
        if (cursor.moveToFirst()) {
            do
            {
                id = cursor.getInt(cursor.getColumnIndex("_id"));
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return id;

    }

    public static boolean removeTask(SQLiteDatabase db, long id) {
        Log.d(TAG, "deleting id: " + id);
        return db.delete(Contract.TABLE_TASK.TABLE_NAME, Contract.TABLE_TASK._ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public static int updateTask(SQLiteDatabase db, String date, String subject, String project
            , int startHour, int startMinute, String startMidday
            , int endHour, int endMinute, String endMidday
            , int totalMinutes, long id) {

        //check if subject not exist then insert
        int subjectId = findData(db, Contract.TABLE_SUBJECT.TABLE_NAME, Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE, subject);
        if (subjectId < 0) {
            subjectId = addSubject(db, subject);
        }
        //check if project not exist then insert
        int projectId = findData(db, Contract.TABLE_PROJECT.TABLE_NAME, Contract.TABLE_PROJECT.COLUMN_NAME_TITLE, project);
        if (projectId < 0) {
            projectId = addProject(db, project);
        }

        //save all column
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_DATE, date);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_ID, subjectId);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_PROJECT_ID, projectId);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_START_HOUR, startHour);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE, startMinute);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY, startMidday);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_END_HOUR, endHour);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE, endMinute);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY, endMidday);
        cv.put(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES, totalMinutes);

        return db.update(Contract.TABLE_TASK.TABLE_NAME, cv, Contract.TABLE_TASK._ID + "=" + id, null);
    }

    public static void dummyTask(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase dbAdd = dbHelper.getWritableDatabase();

        dbAdd.delete(Contract.TABLE_TASK.TABLE_NAME, null, null);
        dbAdd.delete(Contract.TABLE_SUBJECT.TABLE_NAME, null, null);
        dbAdd.delete(Contract.TABLE_PROJECT.TABLE_NAME, null, null);

/*
        DatabaseUtils.addTask(dbAdd, "07/01/2017", "math hw", "school",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/09/2017", "get money from aim for project", "android project",
                10, 00, "AM", 10, 10, "AM",
                ((10 - 10) * 60) + (10 - 00));

        DatabaseUtils.addTask(dbAdd, "07/16/2017", "walk for 30 min", "diet",
                8, 30, "PM", 9, 00, "PM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "07/29/2017", "reading", "school",
                7, 30, "PM", 8, 30, "PM",
                ((8 - 7) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/23/2017", "walk for 30 min", "diet",
                8, 30, "PM", 9, 00, "PM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "eat a salad", "diet",
                12, 00, "AM", 1, 00, "AM",
                ((1) * 60) + (00 - 00));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "eat a salad", "diet",
                8, 30, "AM", 9, 00, "AM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "get money from aim for project", "android project",
                10, 00, "AM", 10, 10, "AM",
                ((10 - 10) * 60) + (10 - 00));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "Hello", "meeting",
                2, 30, "PM", 6, 30, "PM",
                ((6 - 2) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "bye", "meeting",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "commit graph layout", "school",
                8, 30, "PM", 11, 30, "PM",
                ((11 - 8) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "cooking snack", "diet",
                11, 31, "PM", 11, 59, "PM",
                ((11 - 11) * 60) + (59 - 31));

        DatabaseUtils.addTask(dbAdd, "07/29/2017", "english essay", "school",
                2, 30, "PM", 6, 30, "PM",
                ((6 - 2) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/29/2017", "math hw", "school",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/28/2017", "added add task button", "android project",
                7, 30, "PM", 8, 30, "PM",
                ((8 - 7) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/28/2017", "finish graph layout", "school",
                8, 30, "PM", 11, 30, "PM",
                ((11 - 8) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/29/2017", "finish updating task", "school",
                12, 30, "AM", 3, 30, "AM",
                ((3) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/31/2017", "finish Month button", "android project",
                12, 00, "AM", 1, 30, "AM",
                ((1) * 60) + (30 - 00));

        DatabaseUtils.addTask(dbAdd, "07/31/2017", "finish Month button layout", "android project",
                1, 30, "AM", 2, 30, "AM",
                ((2 - 1) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/31/2017", "finish Week button", "android project",
                2, 30, "AM", 4, 30, "AM",
                ((4 - 2) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "math hw", "school",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "get money from aim for project", "android project",
                10, 00, "AM", 10, 10, "AM",
                ((10 - 10) * 60) + (10 - 00));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "walk for 30 min", "diet",
                8, 30, "PM", 9, 00, "PM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "reading", "school",
                7, 30, "PM", 8, 30, "PM",
                ((8 - 7) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/01/2017", "walk for 30 min", "diet",
                8, 30, "PM", 9, 00, "PM",
                ((9 - 8) * 60) + (00 - 30));
        */


        /*
        DatabaseUtils.addTask(dbAdd, "08/01/2017", "Homework 1: Connecting to the News Api", "CS5540 Course",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/06/2017", "Homework 1: Connecting to the News Api", "CS5540 Course",
                10, 00, "AM", 10, 10, "AM",
                ((10 - 10) * 60) + (10 - 00));

        DatabaseUtils.addTask(dbAdd, "08/01/2017", "Homework 2: Adding a RecyclerView to News App", "CS5540 Course",
                8, 30, "PM", 9, 00, "PM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "08/03/2017", "Homework 2: Adding a RecyclerView to News App", "CS5540 Course",
                7, 30, "PM", 8, 30, "PM",
                ((8 - 7) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "Homework 2: Adding a RecyclerView to News App", "CS5540 Course",
                8, 30, "PM", 9, 00, "PM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "08/05/2017", "Homework 1: Connecting to the News Api", "CS5540 Course",
                12, 00, "AM", 1, 00, "AM",
                ((1) * 60) + (00 - 00));

        DatabaseUtils.addTask(dbAdd, "08/08/2017", "Data Analysis", "Company Website",
                8, 30, "AM", 9, 00, "AM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "08/03/2017", "Data Analysis", "Company Website",
                10, 00, "AM", 10, 10, "AM",
                ((10 - 10) * 60) + (10 - 00));

        DatabaseUtils.addTask(dbAdd, "08/09/2017", "Designing UI", "Company Website",
                2, 30, "PM", 6, 30, "PM",
                ((6 - 2) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "Designing UI", "Company Website",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/04/2017", "Designing UI", "Company Website",
                8, 30, "PM", 11, 30, "PM",
                ((11 - 8) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "Programing", "Company Website",
                11, 31, "PM", 11, 59, "PM",
                ((11 - 11) * 60) + (59 - 31));

        DatabaseUtils.addTask(dbAdd, "07/29/2017", "Programing", "Company Website",
                2, 30, "PM", 6, 30, "PM",
                ((6 - 2) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/29/2017", "Debug & Test", "Company Website",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/28/2017", "Debug & Test", "Company Website",
                7, 30, "PM", 8, 30, "PM",
                ((8 - 7) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/28/2017", "Designing UI", "Company Website",
                8, 30, "PM", 11, 30, "PM",
                ((11 - 8) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/29/2017", "Designing UI", "Company Website",
                12, 30, "AM", 3, 30, "AM",
                ((3) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/31/2017", "Finding curren project bottlnecks", "Redesign Project",
                12, 00, "AM", 1, 30, "AM",
                ((1) * 60) + (30 - 00));

        DatabaseUtils.addTask(dbAdd, "07/31/2017", "Finding curren project bottlnecks", "Redesign Project",
                1, 30, "AM", 2, 30, "AM",
                ((2 - 1) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/31/2017", "Finding curren project bottlnecks", "Redesign Project",
                2, 30, "AM", 4, 30, "AM",
                ((4 - 2) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "Finding appropriate solution", "Redesign Project",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "Finding appropriate solution", "Redesign Project",
                10, 00, "AM", 10, 10, "AM",
                ((10 - 10) * 60) + (10 - 00));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "Research and development", "Redesign Project",
                8, 30, "PM", 9, 00, "PM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "Research and development", "Redesign Project",
                7, 30, "PM", 8, 30, "PM",
                ((8 - 7) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/01/2017", "Finding appropriate solution", "Redesign Project",
                8, 30, "PM", 9, 00, "PM",
                ((9 - 8) * 60) + (00 - 30));
        */

        DatabaseUtils.addTask(dbAdd, "08/01/2017", "CS5540 Course", "Homework 1: Connecting to the News Api",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/06/2017", "CS5540 Course", "Homework 1: Connecting to the News Api",
                10, 00, "AM", 10, 10, "AM",
                ((10 - 10) * 60) + (10 - 00));

        DatabaseUtils.addTask(dbAdd, "08/01/2017", "CS5540 Course", "Homework 2: Adding a RecyclerView to News App",
                8, 30, "PM", 9, 00, "PM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "08/03/2017", "CS5540 Course", "Homework 2: Adding a RecyclerView to News App",
                7, 30, "PM", 8, 30, "PM",
                ((8 - 7) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "CS5540 Course", "Homework 2: Adding a RecyclerView to News App",
                8, 30, "PM", 9, 00, "PM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "08/05/2017", "CS5540 Course", "Homework 1: Connecting to the News Api",
                12, 00, "AM", 1, 00, "AM",
                ((1) * 60) + (00 - 00));

        DatabaseUtils.addTask(dbAdd, "08/08/2017", "Company Website", "Data Analysis",
                8, 30, "AM", 9, 00, "AM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "08/03/2017", "Company Website", "Data Analysis",
                10, 00, "AM", 10, 10, "AM",
                ((10 - 10) * 60) + (10 - 00));

        DatabaseUtils.addTask(dbAdd, "08/09/2017", "Company Website", "Designing UI",
                2, 30, "PM", 6, 30, "PM",
                ((6 - 2) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "Company Website", "Designing UI",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/04/2017", "Company Website", "Designing UI",
                8, 30, "PM", 11, 30, "PM",
                ((11 - 8) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/30/2017", "Company Website", "Programing",
                11, 31, "PM", 11, 59, "PM",
                ((11 - 11) * 60) + (59 - 31));

        DatabaseUtils.addTask(dbAdd, "07/29/2017", "Company Website", "Programing",
                2, 30, "PM", 6, 30, "PM",
                ((6 - 2) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/29/2017", "Company Website", "Debug & Test",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/28/2017", "Company Website", "Debug & Test",
                7, 30, "PM", 8, 30, "PM",
                ((8 - 7) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/28/2017", "Company Website", "Designing UI",
                8, 30, "PM", 11, 30, "PM",
                ((11 - 8) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/29/2017", "Company Website", "Designing UI",
                12, 30, "AM", 3, 30, "AM",
                ((3) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/31/2017", "Redesign Project", "Finding curren project bottlnecks",
                12, 00, "AM", 1, 30, "AM",
                ((1) * 60) + (30 - 00));

        DatabaseUtils.addTask(dbAdd, "07/31/2017", "Redesign Project", "Finding curren project bottlnecks",
                1, 30, "AM", 2, 30, "AM",
                ((2 - 1) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "07/31/2017", "Redesign Project", "Finding curren project bottlnecks",
                2, 30, "AM", 4, 30, "AM",
                ((4 - 2) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "Redesign Project", "Finding appropriate solution",
                6, 30, "PM", 7, 30, "PM",
                ((7 - 6) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "Redesign Project", "Finding appropriate solution",
                10, 00, "AM", 10, 10, "AM",
                ((10 - 10) * 60) + (10 - 00));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "Redesign Project", "Research and development",
                8, 30, "PM", 9, 00, "PM",
                ((9 - 8) * 60) + (00 - 30));

        DatabaseUtils.addTask(dbAdd, "08/02/2017", "Redesign Project", "Research and development",
                7, 30, "PM", 8, 30, "PM",
                ((8 - 7) * 60) + (30 - 30));

        DatabaseUtils.addTask(dbAdd, "08/01/2017", "Redesign Project", "Finding appropriate solution",
                8, 30, "PM", 9, 00, "PM",
                ((9 - 8) * 60) + (00 - 30));


        dbHelper.close();
        dbAdd.close();
    }
}
