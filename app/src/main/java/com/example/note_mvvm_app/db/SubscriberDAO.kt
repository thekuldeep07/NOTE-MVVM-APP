package com.example.note_mvvm_app.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertSubscriber(subscriber: Subscriber):Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber):Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber):Int

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll(): Int


    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscriber():Flow<List<Subscriber>>





}