package com.example.appmudanza.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appmudanza.data.dao.UserDao
import com.example.appmudanza.data.entity.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}