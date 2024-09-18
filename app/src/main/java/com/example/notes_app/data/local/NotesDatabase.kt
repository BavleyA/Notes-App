package com.example.notes_app.data.local

import android.content.ContentValues
import android.content.Context
import android.content.LocusId
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract.CommonDataKinds.Note

class NotesDatabase (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION){
    companion object{
        private const val DATABASE_NAME = "notes.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "notes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
       val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT , $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun insert (notes: Notes){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,notes.title)
            put(COLUMN_CONTENT,notes.content )
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun GetAllNotes():List<Notes>{

        val noteList = mutableListOf<Notes>()
        val db = readableDatabase
        val getnotes = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(getnotes,null)
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val note = Notes(id,title,content)
            noteList.add(note)
        }
        cursor.close()
        db.close()
        return noteList
    }

    fun update(notes:Notes){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,notes.title)
            put(COLUMN_CONTENT,notes.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(notes.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }
    fun GetNOteByid(noteId: Int):Notes{
        val db =readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor =  db.rawQuery(query,null)
        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
        cursor.close()
        db.close()
        return Notes(id,title,content)

    }

    fun DeleteNote(noteId:Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }

}