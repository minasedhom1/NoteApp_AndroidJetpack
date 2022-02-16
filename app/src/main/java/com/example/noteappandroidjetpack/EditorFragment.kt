package com.example.noteappandroidjetpack

import android.app.Activity
import android.app.AppComponentFactory
import android.content.res.Configuration
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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
import com.example.noteappandroidjetpack.data.NoteEntity
import com.example.noteappandroidjetpack.databinding.EditorFragmentBinding

class EditorFragment : Fragment() {

    private lateinit var viewModel: EditorViewModel
    private val args: EditorFragmentArgs by navArgs() // generated by the safeargs library
    private lateinit var binding: EditorFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_check)
        }
        setHasOptionsMenu(true)


        requireActivity().title =
            if (args.noteId == NEW_NOTE_ID) {
                "New note"
            } else {
                "Edit note"
            }

        viewModel = ViewModelProvider(this).get(EditorViewModel::class.java)

        binding = EditorFragmentBinding.inflate(inflater, container,false)
        binding.editor.setText("")

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }
            })
        //make observe data in LiveData object. Observer{} lambda will be triggered once changed
        viewModel.currentNote.observe(viewLifecycleOwner, Observer {
            val savedString = savedInstanceState?.getString(NOTE_TEXT_KEY)
            val cursorPos = savedInstanceState?.getInt(CURSOR_POSITION_KEY) ?: 0


            binding.editor.setText(savedString ?: it.text) //if no saved string, use the one from the note entity object
            binding.editor.setSelection(cursorPos)
        })

        viewModel.getNoteById(args.noteId) // sent from itemclick
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveAndReturn(): Boolean {
        val inm = requireActivity()
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inm.hideSoftInputFromWindow(binding.root.windowToken, 0)


        viewModel.currentNote.value?.text = binding.editor.text.toString()
        viewModel.updateNote()

        findNavController().navigateUp()  //navigate back to the main fragment
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        with(binding.editor) {
            outState.putString(NOTE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {

        super.onConfigurationChanged(newConfig)
    }
}