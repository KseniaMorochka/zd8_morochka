package com.example.orgonizer1

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import database.TaskDatabase
import database.migration_1_2
import java.util.*
import java.util.concurrent.Executors

private  const val DATABASE_NAME ="taskdatabase"

class TaskRepository private constructor(context:Context){

    private val database : TaskDatabase = Room.databaseBuilder(
        context.applicationContext,
        TaskDatabase::class.java,
        DATABASE_NAME
    ).addMigrations(migration_1_2)
        .build()
    private val taskDao = database.taskDao()
    private val executor = Executors.newSingleThreadExecutor()
    fun getTasks() : LiveData<List<Task>> = taskDao.getTasks()
    fun getCrime(id: UUID): LiveData<Task?> = taskDao.getTask(id)

    fun updateCrime(crime: Task) {
        executor.execute {
            taskDao.updateTask(crime)
        }
    }
    fun addTask(crime: Task) {
        executor.execute {
            taskDao.addTask(crime)
        }
    }
    fun deleteFilm(film: Task)
    {
        executor.execute {
            taskDao.deleteFilm(film)
        }
    }
    companion object{
        private  var INSTANCE :TaskRepository? = null
        fun initialize(context: Context){
            if(INSTANCE ==null){
                INSTANCE = TaskRepository(context)
            }
        }
        fun get() :TaskRepository{
            return INSTANCE?:
            throw IllegalStateException("TaskRepository must be initialized")
        }
    }
}

