package com.example.gradetracker.domain.usecase.grade

import com.example.gradetracker.domain.model.Grade
import com.example.gradetracker.domain.repository.GradeRepository

class UpdateGradeUseCase(
    private val gradeRepository: GradeRepository
) {
    suspend operator fun invoke(grade: Grade) = gradeRepository.updateGrade(grade)
}
