package com.example.gradetracker.domain.model

data class GradeCategory(
    val id: Long = 0,
    val subjectId: Long,
    val name: String,
    val weight: Double
)

object DefaultCategories {
    val QUIZ = "Quiz"
    val ASSIGNMENT = "Assignment"
    val EXAM = "Exam"
    val PROJECT = "Project"
    val PARTICIPATION = "Participation"
    
    fun getDefaultCategories(subjectId: Long): List<GradeCategory> = listOf(
        GradeCategory(subjectId = subjectId, name = QUIZ, weight = 20.0),
        GradeCategory(subjectId = subjectId, name = ASSIGNMENT, weight = 20.0),
        GradeCategory(subjectId = subjectId, name = EXAM, weight = 40.0),
        GradeCategory(subjectId = subjectId, name = PROJECT, weight = 15.0),
        GradeCategory(subjectId = subjectId, name = PARTICIPATION, weight = 5.0)
    )
}
