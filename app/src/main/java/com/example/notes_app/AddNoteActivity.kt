package com.example.notes_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notes_app.data.local.Notes
import com.example.notes_app.data.local.NotesDatabase
import com.example.notes_app.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db:NotesDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabase(this)
        binding.savebtn.setOnClickListener {
            val title = binding.titleEdt.text.toString()
            val content = binding.contentEdt.text.toString()
            val note = Notes(0,title,content)
            db.insert(note)
            finish()
            Toast.makeText(this,"Notes Saved",Toast.LENGTH_SHORT).show()
        }
    }
}