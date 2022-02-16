package com.example.noteappandroidjetpack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappandroidjetpack.data.NoteEntity
import com.example.noteappandroidjetpack.databinding.ListItemBinding

class NotesListAdapter(private val notesList: List<NoteEntity>,
    private val listener: ListItemListener):
    RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    val selectedNotes = arrayListOf<NoteEntity>()

    inner class ViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // its called everytime data is passed to one of the recyclerview rows
        val note = notesList[position]
        with(holder.binding) {
            noteText.text = note.text
            root.setOnClickListener{
                listener.editNote(note.id)
            }
            fab.setOnClickListener{
                if (selectedNotes.contains(note)) {
                    selectedNotes.remove(note)
                    fab.setImageResource(R.drawable.ic_note)
                } else {
                    selectedNotes.add(note)
                    fab.setImageResource(R.drawable.ic_check)
                }
                listener.onItemSelectionChanged() // to notify the fragment to rebuild its menu
            }
            fab.setImageResource(
                if(selectedNotes.contains(note)){
                    R.drawable.ic_check
                } else {
                    R.drawable.ic_note
                }
            )
        }
    }

    override fun getItemCount() = notesList.size

    interface ListItemListener {
        fun editNote(noteId: Int)
        fun onItemSelectionChanged()
    }
}