package com.example.notesapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings


@Entity(tableName = "note_table")
data class Note(
    @ColumnInfo(name = "email") var email : String = "",
    @ColumnInfo(name = "note_content") var note_content : String = ""
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id") var note_id : Int = 0
}