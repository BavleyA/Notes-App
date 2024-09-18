package com.example.notes_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notes_app.data.local.Notes
import com.example.notes_app.data.local.NotesDatabase
import com.example.notes_app.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db:NotesDatabase
    private var noteId:Int =-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabase(this)
        noteId=intent.getIntExtra("note_id",-1)
        if (noteId==-1){
            finish()
            return
        }
        val note = db.GetNOteByid(noteId)
        binding.updatetitleEdt.setText(note.title)
        binding.updatecontentEdt.setText(note.content)

        binding.updatesavebtn.setOnClickListener {
            val newTitle=binding.updatetitleEdt.text.toString()
            val newContent=binding.updatecontentEdt.text.toString()
            val updateNote = Notes(noteId,newTitle,newContent)
            db.update(updateNote)
            finish()
            Toast.makeText(this,"Note Updated" ,Toast.LENGTH_SHORT).show()
        }
    }
}