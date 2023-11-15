package com.sk.autotp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {

    @Insert
    suspend fun insert(log: Log)

    @Query("SELECT * FROM log ORDER BY sentOn DESC")
    fun getAll(): Flow<Log>
}