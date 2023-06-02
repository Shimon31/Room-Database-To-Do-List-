package com.example.shimon.roomdatabase

import android.icu.text.CaseMap.Title
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(

    @PrimaryKey(autoGenerate = true)
    var noteID: Int=0,

    var title: String,
    var time: String,
    var date: String,
    var priority: String

)
