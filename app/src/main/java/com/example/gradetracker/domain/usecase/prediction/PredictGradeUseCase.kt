package com.example.gradetracker.domain.usecase.prediction

import com.example.gradetracker.domain.model.Grade
import com.example.gradetracker.domain.model.GradeCategory

class PredictGradeUseCase {
    
    data class PredictionResult(
        val currentAverage: Double,
        val predictedFinalGrade: Double,
        val requiredScoreForTarget: Double,
        val targetGrade: Double,
        val remainingWeight: Double
    )
    
    operator fun invoke(
        grades: List<Grade>,
        categories: List<GradeCategory>,
        targetGrade: Double = 75.0
    ): PredictionResult {
        if (categories.isEmpty()) {
            return PredictionResult(0.0, 0.0, 0.0, targetGrade, 100.0)
        }
        
        var currentWeightedScore = 0.0
        var completedWeight = 0.0
        
        categories.forEach { category ->
            val categoryGrades = grades.filter { it.category == category.name }
            if (categoryGrades.isNotEmpty()) {
                val categoryAverage = categoryGrades.sumOf { it.percentage } / categoryGrades.size
                currentWeightedScore += categoryAverage * (category.weight / 100)
                completedWeight += category.weight
            }
        }
        
        val currentAverage = if (completedWeight > 0) {
            (currentWeightedScore / completedWeight) * 100
        } else 0.0
        
        val remainingWeight = 100.0 - completedWeight
        
        val predictedFinal = if (remainingWeight > 0) {
            currentWeightedScore + (currentAverage / 100) * (remainingWeight / 100) * 100
        } else {
            currentWeightedScore * 100
        }
        
        val requiredScore = if (remainingWeight > 0) {
            ((targetGrade / 100) - currentWeightedScore) / (remainingWeight / 100) * 100
        } else {
            0.0
        }
        
        return PredictionResult(
            currentAverage = currentAverage,
            predictedFinalGrade = predictedFinal,
            requiredScoreForTarget = requiredScore.coerceIn(0.0, 100.0),
            targetGrade = targetGrade,
            remainingWeight = remainingWeight
        )
    }
}
