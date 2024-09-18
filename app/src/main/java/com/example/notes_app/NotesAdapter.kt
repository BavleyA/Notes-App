package com.example.notes_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notes_app.data.local.Notes
import com.example.notes_app.data.local.NotesDatabase

class NotesAdapter (private var notes: List<Notes>,context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

private val db :NotesDatabase = NotesDatabase(context)

    class NoteViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView : TextView = itemView.findViewById(R.id.titleTv)
        val contentTextView : TextView = itemView.findViewById(R.id.contebtTv)
        val updateButton : ImageView = itemView.findViewById(R.id.updaateBtn)
        val deleteButton : ImageView = itemView.findViewById(R.id.deleteBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text=note.title
        holder.contentTextView.text=note.content
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context,UpdateActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener {
            db.DeleteNote(note.id)
            refreshData(db.GetAllNotes())
            Toast.makeText(holder.itemView.context, "Note Deleted" , Toast.LENGTH_SHORT).show()
        }
    }
    fun refreshData(newNotes : List<Notes>){
        notes = newNotes
        notifyDataSetChanged()
    }
}