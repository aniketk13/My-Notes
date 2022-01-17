package com.example.mynotes.data

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromTimeStamp(value:Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimeStamp(date:Date):Long{
        return date.time
    }
}