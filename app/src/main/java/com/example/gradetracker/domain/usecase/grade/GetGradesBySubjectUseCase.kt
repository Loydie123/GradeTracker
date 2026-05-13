package com.example.gradetracker.domain.usecase.grade

import com.example.gradetracker.domain.model.Grade
import com.example.gradetracker.domain.repository.GradeRepository
import kotlinx.coroutines.flow.Flow

class GetGradesBySubjectUseCase(
    private val gradeRepository: GradeRepository
) {
    operator fun invoke(subjectId: Long): Flow<List<Grade>> = 
        gradeRepository.getGradesBySubject(subjectId)
}
