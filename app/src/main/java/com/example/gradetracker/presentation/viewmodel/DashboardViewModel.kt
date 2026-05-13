package com.example.gradetracker.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradetracker.di.AppModule
import com.example.gradetracker.domain.model.Grade
import com.example.gradetracker.domain.model.GradeCategory
import com.example.gradetracker.domain.model.Subject
import com.example.gradetracker.domain.model.SubjectWithGrades
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class DashboardUiState(
    val subjects: List<Subject> = emptyList(),
    val subjectsWithGrades: List<SubjectWithGrades> = emptyList(),
    val overallGpa: Double = 0.0,
    val totalUnits: Double = 0.0,
    val recentGrades: List<Grade> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    
    private val getAllSubjectsUseCase = AppModule.provideGetAllSubjectsUseCase(application)
    private val getAllGradesUseCase = AppModule.provideGetAllGradesUseCase(application)
    private val calculateGpaUseCase = AppModule.provideCalculateGpaUseCase()
    private val gradeCategoryRepository = AppModule.provideGradeCategoryRepository(application)
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    init {
        loadDashboardData()
    }
    
    private fun loadDashboardData() {
        viewModelScope.launch {
            combine(
                getAllSubjectsUseCase(),
                getAllGradesUseCase()
            ) { subjects, grades ->
                Pair(subjects, grades)
            }.collect { (subjects, allGrades) ->
                val subjectsWithGrades = subjects.map { subject ->
                    val subjectGrades = allGrades.filter { it.subjectId == subject.id }
                    val categories = gradeCategoryRepository.getCategoriesBySubject(subject.id).first()
                    SubjectWithGrades(subject, subjectGrades, categories)
                }
                
                val overallGpa = calculateGpaUseCase(subjectsWithGrades)
                val totalUnits = subjects.sumOf { it.units }
                val recentGrades = allGrades.sortedByDescending { it.createdAt }.take(5)
                
                _uiState.value = DashboardUiState(
                    subjects = subjects,
                    subjectsWithGrades = subjectsWithGrades,
                    overallGpa = overallGpa,
                    totalUnits = totalUnits,
                    recentGrades = recentGrades,
                    isLoading = false
                )
            }
        }
    }
}
