package com.example.notesapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.repositories.NotesRepository
import com.example.notesapp.viewModels.CreateNotesViewModel
import com.example.notesapp.viewModels.DeleteNotesViewModel

@Suppress("UNCHECKED_CAST")
class CreateNoteViewModelFactory (
    private val repository: NotesRepository
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateNotesViewModel(repository) as T
    }
}