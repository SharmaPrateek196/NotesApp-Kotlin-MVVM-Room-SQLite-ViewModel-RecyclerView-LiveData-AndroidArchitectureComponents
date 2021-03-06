package com.example.notesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentUpdateNoteBinding
import com.example.notesapp.entities.Note
import com.example.notesapp.globalData.Global
import com.example.notesapp.repositories.NotesRepository
import com.example.notesapp.util.UpdateNotesViewModelFactory
import com.example.notesapp.util.ViewNotesViewModelFactory
import com.example.notesapp.viewModels.UpdateNotesViewModel
import com.example.notesapp.viewModels.ViewNotesViewModel

class UpdateNoteFragment(private val position: Int) : Fragment() {

    private var binding : FragmentUpdateNoteBinding? = null
    //Creating viewmodel of ViewNote itself because we want to extract the data of the item clicked from the preinitializad activity contexed viewmodel
    private lateinit var viewNoteViewModel : ViewNotesViewModel
    private lateinit var viewNotesFactory : ViewNotesViewModelFactory
    private lateinit var updateNoteViewModel : UpdateNotesViewModel
    private lateinit var updateNotesFactory : UpdateNotesViewModelFactory
    private var currentNote : Note? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repository =
            NotesRepository(
                context?.applicationContext!!
            )
        viewNotesFactory = ViewNotesViewModelFactory(repository)
        viewNoteViewModel = ViewModelProviders.of(
            requireActivity(),
            viewNotesFactory
        ).get(
            ViewNotesViewModel::class.java
        )
        currentNote = viewNoteViewModel.listOfNotes.value?.get(position)
        binding?.etUpdateNote?.setText(currentNote?.note_content)
        binding?.btnUpdateSubmit?.setOnClickListener { saveUpdatedNote() }
    }

    private fun saveUpdatedNote() {
        val trimmedNoteText : String? = binding?.etUpdateNote?.text?.toString()?.trim()
        if(trimmedNoteText?.isEmpty() != false) {
            Global.toast(getString(R.string.enter_some_text), context)
        } else {
            updateNoteInDB(trimmedNoteText)
        }
    }

    private fun updateNoteInDB(trimmedNoteText: String) {
        val repository =
            NotesRepository(
                context?.applicationContext!!
            )
        updateNotesFactory = UpdateNotesViewModelFactory(repository)
        updateNoteViewModel = ViewModelProviders.of(
            this,
            updateNotesFactory
        ).get(UpdateNotesViewModel::class.java)
        currentNote?.note_content = trimmedNoteText
        updateNoteViewModel.updateNote(currentNote!!)
        Global.toast(
            getString(R.string.update_success),
            this.context
        )
        killCurrentFragment()
    }

    private fun killCurrentFragment() {
        activity
            ?.supportFragmentManager
            ?.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}