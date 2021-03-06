package com.example.notesapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.notesapp.entities.Note
import com.example.notesapp.repositories.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateNotesViewModel(
    private val notesRepository : NotesRepository
) : ViewModel() {

    fun updateNote(note : Note) {
        CoroutineScope(Dispatchers.IO)
            .launch {
                notesRepository.updateNote(note)
            }
    }

}