package com.example.gradetracker.domain.model

data class Grade(
    val id: Long = 0,
    val subjectId: Long,
    val name: String,
    val score: Double,
    val maxScore: Double,
    val category: String,
    val weight: Double,
    val createdAt: Long = System.currentTimeMillis()
) {
    val percentage: Double
        get() = if (maxScore > 0) (score / maxScore) * 100 else 0.0
}
