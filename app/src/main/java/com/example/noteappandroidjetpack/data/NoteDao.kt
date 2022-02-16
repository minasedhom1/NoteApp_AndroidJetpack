package com.example.noteappandroidjetpack.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao { // Data Access object for note

    @Insert(onConflict = OnConflictStrategy.REPLACE) // insert new or update existing note
    fun insertNote(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(notes: List<NoteEntity>)

    @Query("SELECT * FROM notes ORDER BY date ASC")
    fun getAll(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): NoteEntity? //nullable

    @Query("SELECT COUNT(*) from notes")
    fun getCount(): Int

    @Delete
    fun deleteNotes(selectedNotes: List<NoteEntity>): Int

    @Query("DELETE FROM notes")
    fun deleteAll(): Int

    @Delete
    fun deleteNote(note: NoteEntity)
}