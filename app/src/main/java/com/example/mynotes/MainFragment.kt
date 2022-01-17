package com.example.mynotes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.data.NoteEntity
import com.example.mynotes.databinding.MainFragmentBinding

class MainFragment : Fragment(),NotesListAdapter.ListItemListener {

    private lateinit var binding:MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: NotesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= MainFragmentBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        with(binding.recyclerView){
            setHasFixedSize(true)
            val divider= DividerItemDecoration(context,LinearLayoutManager(context).orientation)
            addItemDecoration(divider)
        }
        setHasOptionsMenu(true)
        binding.addNotesButton.setOnClickListener {
//            addSampleData()
            editNote(NEW_NOTE_ID)
        }
//        addSampleData()

        viewModel.notesList?.observe(viewLifecycleOwner, Observer {
            adapter= NotesListAdapter(it,this@MainFragment)
            Log.i(TAG,it.toString())
            binding.recyclerView.adapter=adapter
            binding.recyclerView.layoutManager=LinearLayoutManager(activity)

            val selectedNotes=savedInstanceState?.getParcelableArrayList<NoteEntity>(SELECTED_NOTES_KEY)
            adapter.selectedNotes.addAll(selectedNotes?: emptyList())
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuId=if(this::adapter.isInitialized && adapter.selectedNotes.isNotEmpty()){
            R.menu.menu_main_selected_items
        }
        else{
            R.menu.menu_main
        }
        inflater.inflate(menuId,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteAll->deleteAll()
            R.id.addSampleData->addSampleData()
            R.id.action_delete->deleteSelectedNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteSelectedNotes():Boolean {
        viewModel.deleteNotes(adapter.selectedNotes)
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.selectedNotes.clear()
            requireActivity().invalidateOptionsMenu()
        }, 100)
        return true
    }

    private fun deleteAll(): Boolean {
        viewModel.deleteAll()
        return true
    }

    private fun addSampleData():Boolean {
        viewModel.addSampleData()
        return true
    }

    override fun editNote(noteid: Int) {
        val action:NavDirections=MainFragmentDirections.actionEditNote(noteid)
        findNavController().navigate(action)
    }

    override fun onItemSelectionChanged() {
        requireActivity().invalidateOptionsMenu()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if(this::adapter.isInitialized){
            outState.putParcelableArrayList(SELECTED_NOTES_KEY,adapter.selectedNotes)
        }
        super.onSaveInstanceState(outState)
    }

}