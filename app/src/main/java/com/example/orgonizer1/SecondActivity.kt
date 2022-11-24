package com.example.orgonizer1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*




class SecondActivity : AppCompatActivity() , TaskListFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val fragment = TaskListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
    override fun onCrimeSelected(taskId: UUID)
    {
        val fragment = TaskFragment.newInstance(taskId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}