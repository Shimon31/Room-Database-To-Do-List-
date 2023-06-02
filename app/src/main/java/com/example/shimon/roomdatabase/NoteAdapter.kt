package com.example.shimon.roomdatabase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shimon.roomdatabase.databinding.ItemNoteBinding

class NoteAdapter(
    var noteEditListener: NoteEditListener,
    var noteDeleteListener: NoteDeleteListener
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(comparator) {

    interface NoteEditListener {
        fun onNoteEdit(note: Note)
    }

    interface NoteDeleteListener {
        fun onNoteDelete(note: Note)
    }
    class NoteViewHolder(var binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)
    companion object {

        var comparator = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.noteID == newItem.noteID
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        getItem(position).let {
            holder.binding.apply {

                when (it.priority) {
                    "High" -> {
                        cLayout.setBackgroundColor(
                            ContextCompat.getColor(
                                this.root.context,
                                R.color.red
                            )
                        )
                    }

                    "Medium" -> {
                        cLayout.setBackgroundColor(
                            ContextCompat.getColor(
                                this.root.context,
                                R.color.yellow
                            )
                        )
                    }

                    else -> {
                        cLayout.setBackgroundColor(
                            ContextCompat.getColor(
                                this.root.context,
                                R.color.green
                            )
                        )
                    }
                }

                noteTitle.text = it.title
                noteTime.text = it.time
                noteDate.text = it.date
                piorityTV.text = it.priority

            }

            holder.itemView.setOnClickListener { _ ->
                noteEditListener.onNoteEdit(it)
            }
            holder.binding.deleteButton.setOnClickListener {_ ->
                 noteDeleteListener.onNoteDelete(it)
            }
        }
    }
}