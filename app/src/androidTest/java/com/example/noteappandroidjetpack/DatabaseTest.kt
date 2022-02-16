package com.example.noteappandroidjetpack

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.noteappandroidjetpack.data.AppDatabase
import com.example.noteappandroidjetpack.data.NoteDao
import com.example.noteappandroidjetpack.data.NoteEntity
import com.example.noteappandroidjetpack.data.SampleDataProvider
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var doa: NoteDao
    private lateinit var database: AppDatabase


    @Before //got called with each test separatly
    fun createDb() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
            .allowMainThreadQueries().build() // allowMainThreadQueries only in testing. easier to test synchronaously
        doa = database.noteDoa()!! // has to be not null
    }

    @Test
    fun createNotes() {
        doa.insertAll(SampleDataProvider.getNotes())
        val count = doa.getCount()
        assertEquals(count, SampleDataProvider.getNotes().size)
    }

    @Test
    fun insertNote() {
        val note = NoteEntity()
        note.text = "Some Value test"
        doa.insertNote(note)
        val savedNote = doa.getNoteById(1)
        assertEquals(savedNote?.text, note.text)
        assertEquals(savedNote?.id ?: 0, 1)
    }

    @After
    fun closeDb() {
        database.close()
    }
}