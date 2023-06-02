package com.example.shimon.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext

@Database(entities = [Note::class], version = 1)
abstract class NoteDataBase : RoomDatabase()  {
    abstract fun getNoteDao() : NoteDAO

    companion object{
         var database: NoteDataBase? = null

        fun getDB(context: Context): NoteDataBase {

            return if (database==null){
                database = Room.databaseBuilder(
                    context,
                    NoteDataBase::class.java,
                    "Note-Db"
                ).allowMainThreadQueries()
                    .build()
                database as NoteDataBase
            } else{
                database as NoteDataBase
            }

        }

    }
}