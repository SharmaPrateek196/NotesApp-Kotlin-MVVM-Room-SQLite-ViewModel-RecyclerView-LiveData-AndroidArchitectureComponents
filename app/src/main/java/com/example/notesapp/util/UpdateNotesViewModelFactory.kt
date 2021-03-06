package com.example.notesapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.repositories.NotesRepository
import com.example.notesapp.viewModels.UpdateNotesViewModel

@Suppress("UNCHECKED_CAST")
class UpdateNotesViewModelFactory (
    private val repository: NotesRepository
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UpdateNotesViewModel(repository) as T
    }
}