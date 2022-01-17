package com.example.mynotes

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.AppDatabase
import com.example.mynotes.data.NoteEntity
import com.example.mynotes.data.SampleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(app:Application) : AndroidViewModel(app) {
    private val database=AppDatabase.getInstance(app)
    val notesList=database?.noteDao()?.getAll()
    fun addSampleData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val sampleNotes=SampleData.getNotes()
                Log.i(TAG,sampleNotes.toString())
                database?.noteDao()?.insertAll(sampleNotes)
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database?.noteDao()?.deleteAll()
            }
        }
    }

    fun deleteNotes(selectedNotes: ArrayList<NoteEntity>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database?.noteDao()?.deleteSelectedNotes(selectedNotes)
            }
        }
    }
    // TODO: Implement the ViewModel
}