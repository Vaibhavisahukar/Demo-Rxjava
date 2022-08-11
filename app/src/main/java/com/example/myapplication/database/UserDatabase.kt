package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.model.ResultsItem
import com.example.myapplication.utils.TypeConverter

@Database(entities = [ResultsItem::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getDataDao(): DataDao

}
