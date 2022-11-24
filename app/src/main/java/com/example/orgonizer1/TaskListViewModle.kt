package com.example.orgonizer1

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModel
import java.util.*

class TaskListViewModle: ViewModel() {

    private val taskRepository = TaskRepository.get()
    val taskListLiveData = taskRepository.getTasks()

    fun addTask(task:Task){
        taskRepository.addTask(task)
    }

    fun deleteFilm(film :Task){
        taskRepository.deleteFilm(film)
    }
}