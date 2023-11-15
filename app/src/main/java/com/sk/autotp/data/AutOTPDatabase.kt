package com.sk.autotp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sk.autotp.utils.InstantConverter
import com.sk.autotp.utils.UUIDConverter

@Database(
    entities = [Rule::class, Log::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
    UUIDConverter::class,
)
abstract class AutOTPDatabase : RoomDatabase() {
    abstract val ruleDao: RuleDao
    abstract val logDao: LogDao
}