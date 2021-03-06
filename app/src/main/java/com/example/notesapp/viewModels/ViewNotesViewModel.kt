package com.example.notesapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.entities.Note
import com.example.notesapp.repositories.NotesRepository
import kotlinx.coroutines.*

class ViewNotesViewModel (
    private val notesRepository: NotesRepository
) : ViewModel() {

    private lateinit var job : Job
    private val _listOfNotes = MutableLiveData<MutableList<Note>>()
    val listOfNotes : LiveData<MutableList<Note>>
        get() = _listOfNotes

    fun getAllNotes() {

        CoroutineScope(Dispatchers.Main)
            .launch {
                _listOfNotes.value =
                    CoroutineScope(Dispatchers.IO)
                .async {
                    notesRepository.getAllNotes()
                }.await()
        }

    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }

}