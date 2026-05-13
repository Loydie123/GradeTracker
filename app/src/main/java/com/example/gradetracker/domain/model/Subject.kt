package com.example.gradetracker.domain.model

data class Subject(
    val id: Long = 0,
    val name: String,
    val code: String,
    val units: Double,
    val createdAt: Long = System.currentTimeMillis()
)
