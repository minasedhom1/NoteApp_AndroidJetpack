package com.example.noteappandroidjetpack

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappandroidjetpack.data.AppDatabase
import com.example.noteappandroidjetpack.data.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditorViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    val currentNote = MutableLiveData<NoteEntity>()

    fun getNoteById(noteId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val note =
                    if (noteId != NEW_NOTE_ID) {
                        database?.noteDoa()?.getNoteById(noteId)
                    } else {
                        NoteEntity()
                    }
                currentNote.postValue(note!!) // to assign to livedata from a background thread
            }
        }
    }

    fun updateNote() {
        // the entire block will be excuted if i have a not null note object
        currentNote.value?.let {
            it.text = it.text.trim()
            if (it.id == NEW_NOTE_ID && it.text.isEmpty()) {
                return
            }
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    if(it.text.isEmpty()) {
                        database?.noteDoa()?.deleteNote(it)
                    } else {
                        database?.noteDoa()?.insertNote(it)  // insertNote() used because it has an onConflict strategy in the query which will update if exist
                    }
                }
            }
        }
    }
}