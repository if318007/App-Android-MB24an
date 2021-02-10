package com.example.android.mb24an.data;

import android.provider.BaseColumns;

/**
 * Created by Vahedi on 7/19/17.
 */

public class Contract {

    public static class TABLE_TASK implements BaseColumns {
        public static final String TABLE_NAME = "tasks";

        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_SUBJECT_ID = "subjectId";
        public static final String COLUMN_NAME_PROJECT_ID = "projectId";
        public static final String COLUMN_NAME_START_HOUR = "startHour";
        public static final String COLUMN_NAME_START_MINUTE = "startMinute";
        public static final String COLUMN_NAME_START_MID_DAY = "startMidDay";
        public static final String COLUMN_NAME_END_HOUR = "endHour";
        public static final String COLUMN_NAME_END_MINUTE = "endMinute";
        public static final String COLUMN_NAME_END_MID_DAY = "endMidDay";
        public static final String COLUMN_NAME_TASK_TOTAL_MINUTES = "taskTotalMinutes";

        //for join column
        public static final String COLUMN_NAME_SUBJECT_TITLE = "subjectTitle";
        public static final String COLUMN_NAME_PROJECT_TITLE = "projectTitle";
    }

    public static class TABLE_SUBJECT implements BaseColumns{
        public static final String TABLE_NAME = "subjects";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_NUMBER_OF_TASKS = "numberOfTasks";
    }

    public static class TABLE_PROJECT implements BaseColumns{
        public static final String TABLE_NAME = "projects";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_NUMBER_OF_TASKS = "numberOfTasks";
    }
}
