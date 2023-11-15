package com.sk.autotp.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.UUID

@Entity(tableName = "rule")
data class Rule(
    @PrimaryKey
    val id: UUID,
    val createdAt: Instant,
    val enabled: Boolean,
    val title: String,
    val keywords: String,
    val prefix: String,
    val notify: Boolean,
    @Embedded
    val contact: Contact,
)