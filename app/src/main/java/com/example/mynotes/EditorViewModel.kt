package com.example.mynotes

import android.app.Application
import android.graphics.DiscretePathEffect
import androidx.lifecycle.*
import com.example.mynotes.data.AppDatabase
import com.example.mynotes.data.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditorViewModel(app:Application) : AndroidViewModel(app) {
    private val database=AppDatabase.getInstance(app)
    private val _note:MutableLiveData<NoteEntity> = MutableLiveData()
    val note: LiveData<NoteEntity>
    get() = _note
    fun getNoteById(noteId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val fetchedNote=if(noteId== NEW_NOTE_ID){
                    NoteEntity()
                }
                else{
                    database?.noteDao()?.getNoteById(noteId)

                }
                _note.postValue(fetchedNote!!)
            }
        }
    }

    fun updateNote() {
        note.value?.let {
            it.title=it.title.trim()
            it.description=it.description.trim()
            if (it.id == NEW_NOTE_ID && it.title.isEmpty() && it.description.isEmpty())
                return
            viewModelScope.launch {
                withContext(Dispatchers.IO){
                    if(it.title.isEmpty()){
                        note.value?.title="Untitled Note"
                        database?.noteDao()?.insertNote(it)
                    }
                    else{
                        database?.noteDao()?.insertNote(it)
                    }
                }
            }
        }
    }
    // TODO: Implement the ViewModel
}