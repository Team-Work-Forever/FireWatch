package com.example.firewatch.services.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.firewatch.domain.daos.AutarchyDao
import com.example.firewatch.domain.entities.Autarchy
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.services.persistence.converters.LocalDateTimeConverter

@Database(
    entities = [
        Burn::class,
        Autarchy::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class FireWatchDatabase : DatabaseContext, RoomDatabase() {
    abstract override fun autarcharyDao(): AutarchyDao

    companion object {
        private const val DATABASE = "fire_watch_db"

        @Volatile
        private var INSTANCE: FireWatchDatabase? = null

        fun getDatabase(context: Context): FireWatchDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FireWatchDatabase::class.java,
                    DATABASE
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}