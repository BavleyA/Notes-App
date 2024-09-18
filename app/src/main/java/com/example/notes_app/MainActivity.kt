package com.example.notes_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes_app.data.local.NotesDatabase
import com.example.notes_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindigng : ActivityMainBinding
    private lateinit var db : NotesDatabase
    private lateinit var notesAdapter: NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindigng = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindigng.root)
        db = NotesDatabase(this)
        notesAdapter= NotesAdapter(db.GetAllNotes(),this)

        bindigng.notesRecyclerView.layoutManager=LinearLayoutManager(this)
        bindigng.notesRecyclerView.adapter=notesAdapter

        bindigng.addbutton.setOnClickListener {
            var intent = Intent(this,AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.GetAllNotes())
    }
}