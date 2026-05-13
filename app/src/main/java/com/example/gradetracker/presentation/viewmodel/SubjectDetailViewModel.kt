package com.example.gradetracker.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gradetracker.di.AppModule
import com.example.gradetracker.domain.model.Grade
import com.example.gradetracker.domain.model.GradeCategory
import com.example.gradetracker.domain.model.Subject
import com.example.gradetracker.domain.model.SubjectWithGrades
import com.example.gradetracker.domain.usecase.prediction.PredictGradeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class SubjectDetailUiState(
    val subject: Subject? = null,
    val grades: List<Grade> = emptyList(),
    val categories: List<GradeCategory> = emptyList(),
    val subjectWithGrades: SubjectWithGrades? = null,
    val prediction: PredictGradeUseCase.PredictionResult? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

class SubjectDetailViewModel(
    application: Application,
    private val subjectId: Long
) : AndroidViewModel(application) {
    
    private val getSubjectByIdUseCase = AppModule.provideGetSubjectByIdUseCase(application)
    private val getGradesBySubjectUseCase = AppModule.provideGetGradesBySubjectUseCase(application)
    private val getCategoriesBySubjectUseCase = AppModule.provideGetCategoriesBySubjectUseCase(application)
    private val addGradeUseCase = AppModule.provideAddGradeUseCase(application)
    private val deleteGradeUseCase = AppModule.provideDeleteGradeUseCase(application)
    private val updateSubjectUseCase = AppModule.provideUpdateSubjectUseCase(application)
    private val predictGradeUseCase = AppModule.providePredictGradeUseCase()
    
    private val _uiState = MutableStateFlow(SubjectDetailUiState())
    val uiState: StateFlow<SubjectDetailUiState> = _uiState.asStateFlow()
    
    init {
        loadSubjectDetails()
    }
    
    private fun loadSubjectDetails() {
        viewModelScope.launch {
            val subject = getSubjectByIdUseCase(subjectId)
            _uiState.value = _uiState.value.copy(subject = subject)
            
            combine(
                getGradesBySubjectUseCase(subjectId),
                getCategoriesBySubjectUseCase(subjectId)
            ) { grades, categories ->
                Pair(grades, categories)
            }.collect { (grades, categories) ->
                val subjectWithGrades = subject?.let {
                    SubjectWithGrades(it, grades, categories)
                }
                
                val prediction = predictGradeUseCase(grades, categories)
                
                _uiState.value = _uiState.value.copy(
                    grades = grades,
                    categories = categories,
                    subjectWithGrades = subjectWithGrades,
                    prediction = prediction,
                    isLoading = false
                )
            }
        }
    }
    
    fun addGrade(name: String, score: Double, maxScore: Double, category: String, weight: Double) {
        viewModelScope.launch {
            val grade = Grade(
                subjectId = subjectId,
                name = name,
                score = score,
                maxScore = maxScore,
                category = category,
                weight = weight
            )
            addGradeUseCase(grade)
        }
    }
    
    fun deleteGrade(id: Long) {
        viewModelScope.launch {
            deleteGradeUseCase(id)
        }
    }
    
    fun updateSubject(name: String, code: String, units: Double) {
        viewModelScope.launch {
            _uiState.value.subject?.let { currentSubject ->
                val updatedSubject = currentSubject.copy(
                    name = name,
                    code = code,
                    units = units
                )
                updateSubjectUseCase(updatedSubject)
                _uiState.value = _uiState.value.copy(subject = updatedSubject)
            }
        }
    }
}
