package com.example.gradetracker.domain.usecase.grade

import com.example.gradetracker.domain.model.Grade
import com.example.gradetracker.domain.repository.GradeRepository
import kotlinx.coroutines.flow.Flow

class GetAllGradesUseCase(
    private val gradeRepository: GradeRepository
) {
    operator fun invoke(): Flow<List<Grade>> = gradeRepository.getAllGrades()
}
