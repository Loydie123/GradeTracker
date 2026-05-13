package com.example.gradetracker.domain.model

data class SubjectWithGrades(
    val subject: Subject,
    val grades: List<Grade>,
    val categories: List<GradeCategory>
) {
    val weightedAverage: Double
        get() {
            if (grades.isEmpty() || categories.isEmpty()) return 0.0
            
            var totalWeightedScore = 0.0
            var totalWeight = 0.0
            
            categories.forEach { category ->
                val categoryGrades = grades.filter { it.category == category.name }
                if (categoryGrades.isNotEmpty()) {
                    val categoryAverage = categoryGrades.sumOf { it.percentage } / categoryGrades.size
                    totalWeightedScore += categoryAverage * (category.weight / 100)
                    totalWeight += category.weight
                }
            }
            
            return if (totalWeight > 0) (totalWeightedScore / totalWeight) * 100 else 0.0
        }
    
    val gradePoint: Double
        get() = when {
            weightedAverage >= 97 -> 1.00
            weightedAverage >= 94 -> 1.25
            weightedAverage >= 91 -> 1.50
            weightedAverage >= 88 -> 1.75
            weightedAverage >= 85 -> 2.00
            weightedAverage >= 82 -> 2.25
            weightedAverage >= 79 -> 2.50
            weightedAverage >= 76 -> 2.75
            weightedAverage >= 75 -> 3.00
            else -> 5.00
        }
    
    val letterGrade: String
        get() = when {
            weightedAverage >= 97 -> "A+"
            weightedAverage >= 94 -> "A"
            weightedAverage >= 91 -> "A-"
            weightedAverage >= 88 -> "B+"
            weightedAverage >= 85 -> "B"
            weightedAverage >= 82 -> "B-"
            weightedAverage >= 79 -> "C+"
            weightedAverage >= 76 -> "C"
            weightedAverage >= 75 -> "C-"
            else -> "F"
        }
}
