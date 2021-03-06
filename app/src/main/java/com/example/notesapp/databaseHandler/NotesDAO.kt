package com.example.notesapp.databaseHandler

import androidx.room.*
import com.example.notesapp.entities.Note

@Dao
interface NotesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note : Note)

    @Query("SELECT * FROM note_table  WHERE email = :email ORDER BY note_id DESC")
    suspend fun getAllNotes(email: String): MutableList<Note>

}