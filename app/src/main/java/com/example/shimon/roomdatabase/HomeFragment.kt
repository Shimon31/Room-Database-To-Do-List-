package com.example.shimon.roomdatabase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.shimon.roomdatabase.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), NoteAdapter.NoteEditListener, NoteAdapter.NoteDeleteListener {
    lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        NoteDataBase.getDB(requireContext()).getNoteDao().getAllNote()?.let {

            var adapter: NoteAdapter = NoteAdapter(this, this)
            adapter.submitList(it)

            //Log.d("NoteDatabaseDebug ", "$it")

            binding.noteRCV.adapter = adapter


        }


        binding.addNote.setOnClickListener {



           findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)

        }

        return binding.root
    }

    override fun onNoteEdit(note: Note) {
        var bundle = Bundle()
        bundle.putInt(AddNoteFragment.Note_Id, note.noteID)



        findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment, bundle)
    }

    override fun onNoteDelete(note: Note) {

    }

}