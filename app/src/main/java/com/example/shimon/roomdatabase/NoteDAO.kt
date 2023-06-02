package com.example.shimon.roomdatabase

import androidx.room.*

@Dao
interface NoteDAO {

    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note:Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("Select * From Note")
    fun getAllNote(): List<Note>

    @Query("Select * From Note Where noteID IN(:id)")
    fun getNoteById(id : List<Int>): List<Note>


}