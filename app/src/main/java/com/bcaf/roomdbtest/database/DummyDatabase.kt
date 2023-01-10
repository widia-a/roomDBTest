package com.bcaf.roomdbtest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bcaf.roomdbtest.database.dao.DummyDao
import com.bcaf.roomdbtest.model.PostDummyData

@Database(version = 1, entities = [PostDummyData::class])
@TypeConverters(ListStringConverter::class)
abstract class DummyDatabase : RoomDatabase(){
    abstract  fun dummyDao():DummyDao

    companion object {
        var instance: DummyDatabase?=null
        fun getInstance(ctz: Context): DummyDatabase {
            if (instance == null){
                instance = Room.databaseBuilder(ctz.applicationContext, DummyDatabase::class.java, "dummy_db")
                    .fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }
}