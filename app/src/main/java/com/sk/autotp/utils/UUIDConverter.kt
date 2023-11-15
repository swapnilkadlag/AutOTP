package com.sk.autotp.utils

import androidx.room.TypeConverter
import java.util.UUID

class UUIDConverter {

    @TypeConverter
    fun toUuid(value: String?): UUID? {
        return value?.let { UUID.fromString(value) }
    }

    @TypeConverter
    fun fromUuid(uuid: UUID?): String? {
        return uuid?.toString()
    }
}