@file:Suppress("unused")

package database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.orgonizer1.Task
import java.util.*

@Dao
interface TaskDao{
    @Query("SELECT * FROM task")
    fun getTasks(): LiveData<List<Task>>
    @Query("SELECT * FROM task WHERE id=(:id)")
    fun getTask(id: UUID): LiveData<Task?>
    @Update
    fun updateTask(crime: Task)
    @Insert
    fun addTask(crime: Task)
    @Delete
    fun deleteFilm(film: Task)
}