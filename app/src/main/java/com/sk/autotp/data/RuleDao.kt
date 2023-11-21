package com.sk.autotp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface RuleDao {

    @Insert
    suspend fun insert(rule: Rule)

    @Update
    suspend fun update(rule: Rule)

    @Delete
    suspend fun delete(rule: Rule)

    @Query("SELECT * FROM rule WHERE id = :ruleId")
    suspend fun get(ruleId: UUID): Rule

    @Query("SELECT * FROM rule ORDER BY createdAt DESC")
    fun getAll(): Flow<List<Rule>>

    @Query("SELECT * FROM rule WHERE enabled IS 1")
    suspend fun getAllEnabled(): List<Rule>

    @Query("UPDATE rule SET enabled = :enabled WHERE id = :ruleId")
    fun updateStatus(ruleId: UUID, enabled: Boolean)
}