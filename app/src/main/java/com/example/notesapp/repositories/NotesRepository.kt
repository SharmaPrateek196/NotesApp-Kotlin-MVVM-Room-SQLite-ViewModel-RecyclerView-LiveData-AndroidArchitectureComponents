package com.example.notesapp.repositories

import android.content.Context
import com.example.notesapp.globalData.Preferences
import com.example.notesapp.databaseHandler.NoteDatabase
import com.example.notesapp.databaseHandler.NotesDAO
import com.example.notesapp.entities.Note

class NotesRepository(
    val context : Context
) {
    private var notesDAO : NotesDAO? = null

    init {
        notesDAO = NoteDatabase
            .invoke(context)
            .getNotesDao()

        Preferences.initialize(context)
    }

    suspend fun getAllNotes() = notesDAO?.getAllNotes(Preferences.email!!)

    suspend fun insertNote(note : Note) = notesDAO?.insertNote(note)

    suspend fun deleteNote(note : Note) = notesDAO?.deleteNote(note)

    suspend fun updateNote(note : Note) = notesDAO?.updateNote(note)

}