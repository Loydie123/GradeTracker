package com.example.gradetracker.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradetracker.di.AppModule
import com.example.gradetracker.domain.model.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SubjectListUiState(
    val subjects: List<Subject> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class SubjectListViewModel(application: Application) : AndroidViewModel(application) {
    
    private val getAllSubjectsUseCase = AppModule.provideGetAllSubjectsUseCase(application)
    private val addSubjectUseCase = AppModule.provideAddSubjectUseCase(application)
    private val deleteSubjectUseCase = AppModule.provideDeleteSubjectUseCase(application)
    
    private val _uiState = MutableStateFlow(SubjectListUiState())
    val uiState: StateFlow<SubjectListUiState> = _uiState.asStateFlow()
    
    init {
        loadSubjects()
    }
    
    private fun loadSubjects() {
        viewModelScope.launch {
            getAllSubjectsUseCase().collect { subjects ->
                _uiState.value = _uiState.value.copy(
                    subjects = subjects,
                    isLoading = false
                )
            }
        }
    }
    
    fun addSubject(name: String, code: String, units: Double) {
        viewModelScope.launch {
            val subject = Subject(
                name = name,
                code = code,
                units = units
            )
            addSubjectUseCase(subject)
        }
    }
    
    fun deleteSubject(id: Long) {
        viewModelScope.launch {
            deleteSubjectUseCase(id)
        }
    }
}
