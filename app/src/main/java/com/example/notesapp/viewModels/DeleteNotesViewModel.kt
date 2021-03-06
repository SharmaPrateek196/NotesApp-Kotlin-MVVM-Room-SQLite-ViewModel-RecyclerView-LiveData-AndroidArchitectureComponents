package com.example.notesapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.notesapp.entities.Note
import com.example.notesapp.repositories.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteNotesViewModel(
    private val notesRepository: NotesRepository
) : ViewModel() {

    fun deleteNote(note : Note) {
        CoroutineScope(Dispatchers.IO)
            .launch {
                notesRepository.deleteNote(note)
            }
    }

}