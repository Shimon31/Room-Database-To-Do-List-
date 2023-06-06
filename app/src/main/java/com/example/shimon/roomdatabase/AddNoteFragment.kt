package com.example.shimon.roomdatabase

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import android.widget.Toast
import androidx.room.Room
import com.example.shimon.roomdatabase.databinding.FragmentAddNoteBinding
import java.util.Calendar


class AddNoteFragment : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var binding: FragmentAddNoteBinding
    private val priorityList = listOf("Select a priority", "High", "Medium", "Low")

    private var selectedDate: String? = null
    private var time: String? = null
    private var priority: String? = null

    private var noteId = 0

    companion object {

        var Note_Id = "noteId"
    }

    lateinit var note: Note


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(layoutInflater, container, false)

        noteId = requireArguments().getInt(Note_Id,0)
        requireArguments()

        if (noteId != 0) {
            note =
                NoteDataBase.getDB(requireContext()).getNoteDao()
                    .getNoteById(listOf<Int>(noteId))[0]

            binding.apply {

                titleET.setText(note.title)
                timePickerBTN.text = note.time
                datePickerBTN.text = note.date


            }

        }





        var spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, priorityList
        )

        binding.prioritySpinner.adapter = spinnerAdapter
        binding.prioritySpinner.onItemSelectedListener = this@AddNoteFragment


        binding.datePickerBTN.setOnClickListener {

            pickADate()
        }

        binding.timePickerBTN.setOnClickListener {

            pickATime()

        }

        binding.submitBTN.setOnClickListener {
            var titlestr = binding.titleET.text.toString()
            var timeStr = time ?: "00:00"
            var dateStr = selectedDate ?: "00/00/00"
            var priorityStr = priority ?: priorityList[3]

            var note =
                Note(title = titlestr, time = timeStr, date = dateStr, priority = priorityStr)

            if (noteId == 0) {
                NoteDataBase.getDB(requireContext()).getNoteDao().insertNote(note)
            } else {
                note.noteID = noteId
                NoteDataBase.getDB(requireContext()).getNoteDao().updateNote(note)
            }

        }

        return binding.root
    }

    private fun pickATime() {

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val Minute = calendar.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->


                time = "$hourOfDay : $minute"
                binding.timePickerBTN.text = time

            }, hour, Minute, false
        )
        timePicker.show()
    }

    private fun pickADate() {
        val calender = Calendar.getInstance()

        val date = calender.get(Calendar.DAY_OF_MONTH)
        val month = calender.get(Calendar.MONTH)
        val year = calender.get(Calendar.YEAR)

        var datePicker = DatePickerDialog(
            requireActivity(),
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

                selectedDate = "$dayOfMonth/${month + 1}/$year"
                binding.datePickerBTN.text = selectedDate

            }, year, month, date

        )
        datePicker.show()

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        priority = priorityList[position]
        Toast.makeText(requireContext(), priority, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


}