package com.example.gradetracker.domain.usecase.grade

import com.example.gradetracker.domain.repository.GradeRepository

class DeleteGradeUseCase(
    private val gradeRepository: GradeRepository
) {
    suspend operator fun invoke(id: Long) = gradeRepository.deleteGradeById(id)
}
