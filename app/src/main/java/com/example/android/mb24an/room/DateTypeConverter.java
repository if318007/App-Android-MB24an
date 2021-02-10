package com.example.android.mb24an.room;


import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Created by gurleensethi on 05/02/18.
 */
public class DateTypeConverter {
    @TypeConverter
    public long convertDateToLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public Date convertLongToDate(long time) {
        return new Date(time);
    }
}
