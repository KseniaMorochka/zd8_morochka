package com.example.orgonizer1

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class Task(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var title: String="",
                var task:String="",
                var date: Date = Date()) {
}