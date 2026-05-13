package com.example.gradetracker.data.mapper

import com.example.gradetracker.data.local.entity.SubjectEntity
import com.example.gradetracker.domain.model.Subject

fun SubjectEntity.toDomain(): Subject = Subject(
    id = id,
    name = name,
    code = code,
    units = units,
    createdAt = createdAt
)

fun Subject.toEntity(): SubjectEntity = SubjectEntity(
    id = id,
    name = name,
    code = code,
    units = units,
    createdAt = createdAt
)

fun List<SubjectEntity>.toDomainList(): List<Subject> = map { it.toDomain() }
