package com.example.noteappandroidjetpack.data

import androidx.room.TypeConverter
import java.util.*

class DateConverter {  //date converter to get and set to room database
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}