package com.example.gradetracker.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gradetracker.presentation.components.AddGradeDialog
import com.example.gradetracker.presentation.components.GradeCard
import com.example.gradetracker.presentation.components.StatCard
import com.example.gradetracker.presentation.viewmodel.SubjectDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectDetailScreen(
    viewModel: SubjectDetailViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddGradeDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.subject?.name ?: "Subject Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            if (uiState.categories.isNotEmpty()) {
                FloatingActionButton(
                    onClick = { showAddGradeDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Grade")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        uiState.subject?.let { subject ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = subject.code,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${subject.units} units",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                    
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            StatCard(
                                title = "Average",
                                value = String.format("%.1f%%", uiState.subjectWithGrades?.weightedAverage ?: 0.0),
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                title = "Grade",
                                value = uiState.subjectWithGrades?.letterGrade ?: "N/A",
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                title = "GPA",
                                value = String.format("%.2f", uiState.subjectWithGrades?.gradePoint ?: 0.0),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    
                    uiState.prediction?.let { prediction ->
                        if (prediction.remainingWeight > 0) {
                            item {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = "Grade Prediction",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Predicted Final: ${String.format("%.1f%%", prediction.predictedFinalGrade)}"
                                        )
                                        Text(
                                            text = "To pass (75%): Score ${String.format("%.1f%%", prediction.requiredScoreForTarget)} on remaining ${prediction.remainingWeight.toInt()}%"
                                        )
                                    }
                                }
                            }
                        }
                    }
                    
                    item {
                        Text(
                            text = "Grades",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    
                    if (uiState.grades.isEmpty()) {
                        item {
                            Text(
                                text = "No grades recorded yet. Tap + to add one.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        items(uiState.grades) { grade ->
                            GradeCard(
                                grade = grade,
                                onDelete = { viewModel.deleteGrade(grade.id) }
                            )
                        }
                    }
                }
            }
        }
    }
    
    if (showAddGradeDialog && uiState.categories.isNotEmpty()) {
        AddGradeDialog(
            categories = uiState.categories,
            onDismiss = { showAddGradeDialog = false },
            onConfirm = { name, score, maxScore, category, weight ->
                viewModel.addGrade(name, score, maxScore, category, weight)
                showAddGradeDialog = false
            }
        )
    }
}
