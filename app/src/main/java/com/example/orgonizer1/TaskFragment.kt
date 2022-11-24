package com.example.orgonizer1

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import java.util.*
private const val ARG_TASK_ID = "task_id"
private const val TAG = "TaskFragment"
class TaskFragment: Fragment() {
    private lateinit var task: Task
    private lateinit var _task_title: EditText
    private lateinit var taskText: EditText
    private lateinit var dateButton: Button
    private val taskDetailViewModel:TaskDetailViewModel by lazy{
        ViewModelProviders.of(this).get(TaskDetailViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = Task()
        val taskId: UUID = arguments?.getSerializable(ARG_TASK_ID) as UUID
        taskDetailViewModel.loadTask(taskId)
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState:
        Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task, container, false)
        _task_title = view.findViewById(R.id.title_task) as EditText
        taskText = view.findViewById(R.id.desc_task) as EditText
        /*dateButton = view.findViewById(R.id.date_task) as Button*/

        /*dateButton.apply {
            text = task.date.toString()
            isEnabled = false
        }*/
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        taskDetailViewModel.taskLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer() { tasks ->
                task?.let {
                    this.task = task
                    updateUI()
                }
            })
    }
    override fun onStop() {
        super.onStop()
        taskDetailViewModel.saveTask(task)
    }
    override fun onStart() {
        super.onStart()
        val titleWatcher = object : TextWatcher
        {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                task.title = sequence.toString()
            }
            override fun
                    afterTextChanged(sequence: Editable?) {
            }
        }
        val titleWatcher2 = object : TextWatcher
        {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                task.title = sequence.toString()
            }
            override fun
                    afterTextChanged(sequence: Editable?) {
            }
        }
        val titleWatcher3 = object : TextWatcher
        {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                task.title = sequence.toString()
            }
            override fun
                    afterTextChanged(sequence: Editable?) {
            }
        }
        _task_title.addTextChangedListener(titleWatcher)
       taskText.addTextChangedListener(titleWatcher2)
        dateButton.addTextChangedListener(titleWatcher3)


    }
    private fun updateUI() {
        _task_title.setText(task.title).toString()
        dateButton.text = task.date.toString()
        taskText.setText(task.task)
    }
    companion object {
        fun newInstance(crimeId: UUID):
                TaskFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TASK_ID, crimeId)
            }
            return TaskFragment().apply {
                arguments = args
            }
        }
    }

}