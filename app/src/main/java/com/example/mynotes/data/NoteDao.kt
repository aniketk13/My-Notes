package com.example.mynotes.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note:NoteEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(notes:List<NoteEntity>)

    @Query("SELECT * FROM MyNotes ORDER BY date ASC")
    fun getAll(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM MyNotes WHERE id=:noteId")
    fun getNoteById(noteId:Int):NoteEntity

    @Query("DELETE FROM MyNotes")
    fun deleteAll()

    @Delete
    fun deleteSelectedNotes(selectedNotes: ArrayList<NoteEntity>):Int
}