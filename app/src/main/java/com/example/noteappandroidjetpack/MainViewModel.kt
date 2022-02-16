package com.example.noteappandroidjetpack

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappandroidjetpack.data.AppDatabase
import com.example.noteappandroidjetpack.data.NoteEntity
import com.example.noteappandroidjetpack.data.SampleDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(app: Application) : AndroidViewModel(app) {  //replaced ViewModel cuz i will need a context

    private val database = AppDatabase.getInstance(app.applicationContext)

    val notesList = database?.noteDoa()?.getAll()    //before create db MutableLiveData<List<NoteEntity>>()  //LiveData API

    fun addSampleData() { // using coroutines // all of the database operations have to be done in the background
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val sampleNotes = SampleDataProvider.getNotes()
                database?.noteDoa()?.insertAll(sampleNotes)
            }
        }

    }

    fun deleteNotes(selectedNotes: List<NoteEntity>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.noteDoa()?.deleteNotes(selectedNotes)
            }
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.noteDoa()?.deleteAll()
            }
        }
    }

    //before create db - sample data
//  init {
//        notesList.value = SampleDataProvider.getNotes()
//    }
}