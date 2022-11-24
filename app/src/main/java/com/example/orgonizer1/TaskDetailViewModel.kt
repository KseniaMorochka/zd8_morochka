package com.example.orgonizer1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class TaskDetailViewModel() : ViewModel() {

    private val taskRepository = TaskRepository.get()
    private val taskIdLiveData = MutableLiveData<UUID>()
    var taskLiveData: LiveData<Task?> =
        Transformations.switchMap(taskIdLiveData){
            taskRepository.getCrime(it)
        }
    fun loadTask(crimeId: UUID) {
        taskIdLiveData.value = crimeId
    }
    fun saveTask(crime: Task) {
        taskRepository.updateCrime(crime)
    }
}