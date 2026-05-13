package com.example.gradetracker.data.mapper

import com.example.gradetracker.data.local.entity.GradeEntity
import com.example.gradetracker.domain.model.Grade

fun GradeEntity.toDomain(): Grade = Grade(
    id = id,
    subjectId = subjectId,
    name = name,
    score = score,
    maxScore = maxScore,
    category = category,
    weight = weight,
    createdAt = createdAt
)

fun Grade.toEntity(): GradeEntity = GradeEntity(
    id = id,
    subjectId = subjectId,
    name = name,
    score = score,
    maxScore = maxScore,
    category = category,
    weight = weight,
    createdAt = createdAt
)

fun List<GradeEntity>.toDomainList(): List<Grade> = map { it.toDomain() }
