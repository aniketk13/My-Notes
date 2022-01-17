package com.example.mynotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.data.NoteEntity
import com.example.mynotes.databinding.ListItemBinding

class NotesListAdapter(private val notesList:List<NoteEntity>, private val listener: ListItemListener):RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    val selectedNotes= arrayListOf<NoteEntity>()

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding=ListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListAdapter.ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesListAdapter.ViewHolder, position: Int) {
        val note = notesList[position]
        with(holder.binding) {
            title.text = note.title
            description.text = note.description
            root.setOnClickListener {
                listener.editNote(note.id)
            }
            floatingActionButton.setOnClickListener {
                if(selectedNotes.contains(note)){
                    selectedNotes.remove(note)
                    floatingActionButton.setImageResource(R.drawable.ic_note)
                }
                else{
                    selectedNotes.add(note)
                    floatingActionButton.setImageResource(R.drawable.ic_check)
                }
                listener.onItemSelectionChanged()
            }
            floatingActionButton.setImageResource(
                if(selectedNotes.contains(note)){
                    R.drawable.ic_check
                }
            else
                {
                    R.drawable.ic_note
                }
            )
        }
    }
    override fun getItemCount()=notesList.size
    interface ListItemListener {
        fun editNote(noteid: Int)
        fun onItemSelectionChanged()
    }



}
