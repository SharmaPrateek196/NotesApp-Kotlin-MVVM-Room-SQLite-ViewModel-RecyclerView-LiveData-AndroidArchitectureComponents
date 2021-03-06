package com.example.notesapp.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notesapp.R
import com.example.notesapp.entities.Note
import com.example.notesapp.globalData.Global
import com.example.notesapp.repositories.NotesRepository
import com.example.notesapp.util.DeleteNoteViewModelFactory
import com.example.notesapp.util.ViewNotesViewModelFactory
import com.example.notesapp.viewModels.DeleteNotesViewModel
import com.example.notesapp.viewModels.ViewNotesViewModel
import kotlinx.android.synthetic.main.content_notes.*
import kotlinx.android.synthetic.main.fragment_notes.*


class NotesFragment() : Fragment() {

    private lateinit var viewNotesViewModel : ViewNotesViewModel
    private lateinit var deleteNotesViewModel : DeleteNotesViewModel
    private lateinit var viewNotesFactory : ViewNotesViewModelFactory
    private lateinit var deleteNotesFactory: DeleteNoteViewModelFactory
    private lateinit var createNoteFragment : CreateNoteFragment
    private lateinit var repository : NotesRepository
    private lateinit var swipeDeleteHelper: ItemTouchHelper
    private var trasaction : FragmentTransaction? = null
    private lateinit var adapter : NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.show()
        return inflater.inflate(
            R.layout.fragment_notes,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository =
            NotesRepository(
                context?.applicationContext!!
            )
        viewNotesFactory = ViewNotesViewModelFactory(repository)
        deleteNotesFactory = DeleteNoteViewModelFactory(repository)
        showNotes(viewNotesFactory)
        addOnSwipeDeleteFunctionality(deleteNotesFactory)
        fab.setOnClickListener {
            goToCreateNoteFragment()
        }
    }

    private fun addOnSwipeDeleteFunctionality(factoryView: DeleteNoteViewModelFactory) {

        swipeDeleteHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder, target: ViewHolder
                ) = false

                override fun onSwiped(
                    viewHolder: ViewHolder,
                    direction: Int
                ) {
                    val position = viewHolder.getAdapterPosition()
                    val note = viewNotesViewModel.listOfNotes.value?.get(position)
                    deleteNote(factoryView, note)
                }
            })
        swipeDeleteHelper.attachToRecyclerView(recyclerViewNotes)
    }

    private fun deleteNote(
        factoryView: DeleteNoteViewModelFactory,
        note: Note?
    ) {
        deleteNotesViewModel = ViewModelProviders
            .of(this, factoryView)
            .get(DeleteNotesViewModel::class.java)
        deleteNotesViewModel.deleteNote(note!!)
        viewNotesViewModel.listOfNotes.value?.remove(note)
        showNotes(viewNotesFactory)
    }

    private fun showNotes(factoryView: ViewNotesViewModelFactory) {
        viewNotesViewModel = ViewModelProviders.of(
            requireActivity(),
            factoryView
            ).get(
            ViewNotesViewModel::class.java
        )
        viewNotesViewModel.getAllNotes()
        viewNotesViewModel.listOfNotes.observe(viewLifecycleOwner, Observer { listOfNotes ->
            recyclerViewNotes.also {
                it.setOnFlingListener(null)
                it.layoutManager = LinearLayoutManager(context)
                adapter = NotesAdapter(listOfNotes, context)
                it.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun goToCreateNoteFragment() {
        createNoteFragment = CreateNoteFragment()
        trasaction = activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.frame_main_activity, createNoteFragment)
            ?.addToBackStack(null)
        trasaction?.commit()
    }

    override fun onDetach() {
        super.onDetach()
        if(Global.mGoogleSignInClient != null) {
            requireActivity().moveTaskToBack(true);
            requireActivity().finish()
        }
    }

}

