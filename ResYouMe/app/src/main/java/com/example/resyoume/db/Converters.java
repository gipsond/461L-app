package com.example.resyoume.db;

import java.util.Date;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value != null ? new Date(value) : null;
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date != null ? date.getTime() : null;
    }
}
