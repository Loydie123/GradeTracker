package com.example.gradetracker.data.mapper

import com.example.gradetracker.data.local.entity.GradeCategoryEntity
import com.example.gradetracker.domain.model.GradeCategory

fun GradeCategoryEntity.toDomain(): GradeCategory = GradeCategory(
    id = id,
    subjectId = subjectId,
    name = name,
    weight = weight
)

fun GradeCategory.toEntity(): GradeCategoryEntity = GradeCategoryEntity(
    id = id,
    subjectId = subjectId,
    name = name,
    weight = weight
)

fun List<GradeCategoryEntity>.toDomainList(): List<GradeCategory> = map { it.toDomain() }

fun List<GradeCategory>.toEntityList(): List<GradeCategoryEntity> = map { it.toEntity() }
