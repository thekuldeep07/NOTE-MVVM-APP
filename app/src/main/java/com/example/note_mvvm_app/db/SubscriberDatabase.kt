package com.example.note_mvvm_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class],version = 1)
abstract class SubscriberDatabase: RoomDatabase() {

    abstract val subscriberDAO:SubscriberDAO
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: SubscriberDatabase? = null

        fun getInstance(context: Context): SubscriberDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SubscriberDatabase::class.java,
                    "subscriber_data_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


}