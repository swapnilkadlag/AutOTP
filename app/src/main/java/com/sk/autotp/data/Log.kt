package com.sk.autotp.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.UUID

@Entity(tableName = "log")
data class Log(
    @PrimaryKey
    val id: UUID,
    val message: String,
    val sentOn: Instant,
    @Embedded
    val contact: Contact,
)