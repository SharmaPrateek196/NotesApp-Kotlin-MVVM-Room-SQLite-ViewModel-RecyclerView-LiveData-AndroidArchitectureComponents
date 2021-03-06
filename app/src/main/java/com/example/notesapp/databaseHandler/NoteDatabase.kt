package com.example.notesapp.databaseHandler

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.entities.Note

//Room automatically inherits this abstract class
@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    //Room implements this method
    abstract fun getNotesDao() : NotesDAO

    companion object {

        private const val DB_NAME = "note_database"
        @Volatile private var instance : NoteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            DB_NAME
        ).build()

    }

}