package com.example.notesapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.ItemNoteBinding
import com.example.notesapp.entities.Note

class NotesAdapter(
    private val notes: MutableList<Note>,
    private val context: Context?
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(){

    private lateinit var updateNoteFragment: UpdateNoteFragment
    private lateinit var transaction: FragmentTransaction

    override fun getItemCount() = notes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = NotesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_note,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemNoteBinding.note = notes[position]
        holder.itemView.setOnClickListener {
            goToUpdateFragment(holder.itemView, holder.adapterPosition)
        }
    }

    inner class NotesViewHolder(
        val itemNoteBinding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(itemNoteBinding.root)

    private fun goToUpdateFragment(itemView: View, position: Int) {
        updateNoteFragment = UpdateNoteFragment(position)
        val hostActivity = itemView.context as AppCompatActivity
        transaction = hostActivity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main_activity, updateNoteFragment)
            .addToBackStack(null)
        transaction.commit()
    }

}