package com.example.perludilindungi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FaskesEntity::class], version = 1)
abstract class FaskesDatabase : RoomDatabase() {
    abstract val faskesDao: FaskesDao
    companion object {
        @Volatile
        private var INSTANCE: FaskesDatabase? = null
        fun getInstance(context: Context): FaskesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FaskesDatabase::class.java,
                        "bookmarked_faskes")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}