package com.example.mynotes.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mynotes.NEW_NOTE_ID
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "MyNotes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val date: Date,
    var title:String,
    var description:String
):Parcelable {
    constructor():this(NEW_NOTE_ID,Date(),"","")
    constructor(date: Date,title: String,description: String):this(NEW_NOTE_ID,date,title, description)
}