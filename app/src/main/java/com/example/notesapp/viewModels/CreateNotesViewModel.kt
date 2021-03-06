package com.example.notesapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.notesapp.entities.Note
import com.example.notesapp.repositories.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CreateNotesViewModel(
    private val notesRepository : NotesRepository
) : ViewModel() {

    fun insertNote(email : String, note_content : String) {
        val note = Note(email, note_content)
        CoroutineScope(Dispatchers.IO)
            .launch {
                notesRepository.insertNote(note)
            }
    }

}