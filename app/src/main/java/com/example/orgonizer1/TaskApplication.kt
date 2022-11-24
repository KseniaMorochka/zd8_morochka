package com.example.orgonizer1

import android.app.Application

class TaskApplication : Application()
{
    override fun onCreate() {
        super.onCreate()
        TaskRepository.initialize(this)
    }
}