package com.example.gradetracker.domain.usecase.gpa

import com.example.gradetracker.domain.model.SubjectWithGrades

class CalculateGpaUseCase {
    
    operator fun invoke(subjectsWithGrades: List<SubjectWithGrades>): Double {
        if (subjectsWithGrades.isEmpty()) return 0.0
        
        var totalWeightedGradePoints = 0.0
        var totalUnits = 0.0
        
        subjectsWithGrades.forEach { subjectWithGrades ->
            if (subjectWithGrades.grades.isNotEmpty()) {
                totalWeightedGradePoints += subjectWithGrades.gradePoint * subjectWithGrades.subject.units
                totalUnits += subjectWithGrades.subject.units
            }
        }
        
        return if (totalUnits > 0) totalWeightedGradePoints / totalUnits else 0.0
    }
}
