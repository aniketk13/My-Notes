package com.example.mynotes.data

import java.util.*

class SampleData {
    companion object{
        private val title1="Note 1"
        private val title2="Note 2"
        private val title3="Note 3"
        private val description1="Text 1"
        private val description2="Text 2"
        private val description3="Text 3"

        fun getNotes()= arrayListOf<NoteEntity>(
            NoteEntity(getDate(0), title1, description1),
            NoteEntity(getDate(1), title2, description2),
            NoteEntity(getDate(2), title3, description3)
        )

        private fun getDate(diff: Long): Date {
            return Date(Date().time+diff)

        }
    }
}