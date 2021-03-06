package com.example.notesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentCreateNoteBinding
import com.example.notesapp.globalData.Global
import com.example.notesapp.globalData.Preferences
import com.example.notesapp.repositories.NotesRepository
import com.example.notesapp.util.CreateNoteViewModelFactory
import com.example.notesapp.viewModels.CreateNotesViewModel
import kotlinx.android.synthetic.main.fragment_notes.*

class CreateNoteFragment() : Fragment() {

    private var binding: FragmentCreateNoteBinding? = null
    private lateinit var viewModel : CreateNotesViewModel
    private lateinit var factory : CreateNoteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.fab?.visibility = View.INVISIBLE
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSubmit?.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val trimmedNoteText : String? = binding?.etNewNote?.text?.toString()?.trim()
        if(trimmedNoteText?.isEmpty() != false) {
            Global.toast(getString(R.string.enter_some_text), context)
        } else {
            insertNewNoteIntoDB(trimmedNoteText)
        }
    }

    private fun insertNewNoteIntoDB(trimmedNoteText: String) {
        val repository =
            NotesRepository(
                context?.applicationContext!!
            )
        factory = CreateNoteViewModelFactory(repository)
        viewModel = ViewModelProviders.of(
            this,
            factory
        ).get(CreateNotesViewModel::class.java)
        viewModel.insertNote(Preferences.email!!, trimmedNoteText)
        Global.toast(
            getString(R.string.saved_success),
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