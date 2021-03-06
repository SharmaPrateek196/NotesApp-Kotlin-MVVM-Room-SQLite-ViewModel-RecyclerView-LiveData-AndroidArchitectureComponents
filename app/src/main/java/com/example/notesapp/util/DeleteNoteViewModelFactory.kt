package com.example.notesapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.repositories.NotesRepository
import com.example.notesapp.viewModels.DeleteNotesViewModel
import com.example.notesapp.viewModels.ViewNotesViewModel

@Suppress("UNCHECKED_CAST")
class DeleteNoteViewModelFactory (
    private val repository: NotesRepository
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DeleteNotesViewModel(repository) as T
    }
}