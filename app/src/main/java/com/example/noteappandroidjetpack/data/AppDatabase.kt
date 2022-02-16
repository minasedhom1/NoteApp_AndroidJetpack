package com.example.noteappandroidjetpack.data

import android.content.Context
import androidx.room.*

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false) //don't bother generating the schema docs
@TypeConverters(DateConverter::class) // thats to register the date converter class
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDoa(): NoteDao? // nullable

    companion object {  //static
        private var INSTANCE: AppDatabase? = null //can't be null but initially null

        fun getInstance(context: Context): AppDatabase? {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "plainolnotes.db"
                ).build()
            }
        }
            return INSTANCE
        }
    }
}