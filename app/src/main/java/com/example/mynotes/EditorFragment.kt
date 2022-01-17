package com.example.mynotes

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mynotes.databinding.EditorFragmentBinding
import kotlinx.android.synthetic.main.list_item.*

class EditorFragment : Fragment() {

    private lateinit var viewModel: EditorViewModel
private lateinit var binding:EditorFragmentBinding
private val args:EditorFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(EditorViewModel::class.java)
        binding= EditorFragmentBinding.inflate(inflater,container,false)
        viewModel.getNoteById(args.noteId)
        viewModel.note.observe(viewLifecycleOwner, Observer {
            with(binding){
                val savedString=savedInstanceState?.getString(NOTE_TEXT_KEY)
                val cursorPosition=savedInstanceState?.getInt(CURSOR_POSITION_KEY)?:0
                val cursorPosition2nd=savedInstanceState?.getInt(CURSOR_POSITION_SECOND_KEY)?:0
                val savedDescription=savedInstanceState?.getString(NOTE_DESC_KEY)

                editorTitle.setText(savedString?:it.title)
                editorTitle.setSelection(cursorPosition)
                editorDescription.setText(savedDescription?:it.description)
                editorDescription.setSelection(cursorPosition2nd)
            }
        })

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_check)
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,object: OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }
            }
        )
        requireActivity().title=if(args.noteId== NEW_NOTE_ID){
            "Create Note"}
        else {
            "Edit Note"
        }
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->saveAndReturn()
            else-> super.onOptionsItemSelected(item)
        }
    }
    private fun saveAndReturn():Boolean{
        val imm=requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken,0)
        viewModel.note.value?.title=binding.editorTitle.text.toString()
        viewModel.note.value?.description=binding.editorDescription.text.toString()
        viewModel.updateNote()
        findNavController().navigateUp()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        with(binding){
            outState.putString(NOTE_TEXT_KEY,editorTitle.text.toString())
            outState.putString(NOTE_DESC_KEY,editorDescription.text.toString())
            outState.putInt(CURSOR_POSITION_KEY,editorTitle.selectionStart)
            outState.putInt(CURSOR_POSITION_SECOND_KEY,editorDescription.selectionStart)
        }
        super.onSaveInstanceState(outState)
    }

}