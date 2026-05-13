package com.example.gradetracker.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SubjectDetailViewModelFactory(
    private val application: Application,
    private val subjectId: Long
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubjectDetailViewModel::class.java)) {
            return SubjectDetailViewModel(application, subjectId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
