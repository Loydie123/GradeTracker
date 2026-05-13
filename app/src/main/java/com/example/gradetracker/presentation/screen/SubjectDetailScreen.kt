package com.example.gradetracker.presentation.screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gradetracker.domain.model.Grade
import com.example.gradetracker.presentation.components.AddGradeDialog
import com.example.gradetracker.presentation.viewmodel.SubjectDetailViewModel
import com.example.gradetracker.ui.theme.AccentGreen
import com.example.gradetracker.ui.theme.AccentOrange
import com.example.gradetracker.ui.theme.AccentRed
import com.example.gradetracker.ui.theme.PrimaryBlue
import com.example.gradetracker.ui.theme.PrimaryBlueDark
import com.example.gradetracker.ui.theme.White

@Composable
fun SubjectDetailScreen(
    viewModel: SubjectDetailViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddGradeDialog by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = PrimaryBlue
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header with Subject Info
                item {
                    SubjectDetailHeader(
                        subjectName = uiState.subject?.name ?: "",
                        subjectCode = uiState.subject?.code ?: "",
                        units = uiState.subject?.units ?: 0.0,
                        onNavigateBack = onNavigateBack
                    )
                }
                
                // Stats Cards
                item {
                    SubjectStatsRow(
                        average = uiState.subjectWithGrades?.weightedAverage ?: 0.0,
                        letterGrade = uiState.subjectWithGrades?.letterGrade ?: "N/A",
                        gradePoint = uiState.subjectWithGrades?.gradePoint ?: 0.0,
                        hasGrades = uiState.grades.isNotEmpty()
                    )
                }
                
                // Prediction Card
                uiState.prediction?.let { prediction ->
                    if (prediction.remainingWeight > 0 && uiState.grades.isNotEmpty()) {
                        item {
                            PredictionCard(
                                predictedFinal = prediction.predictedFinalGrade,
                                requiredScore = prediction.requiredScoreForTarget,
                                remainingWeight = prediction.remainingWeight
                            )
                        }
                    }
                }
                
                // Grades Section
                item {
                    Text(
                        text = "Grades",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2D3748),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    )
                }
                
                if (uiState.grades.isEmpty()) {
                    item {
                        EmptyGradesState()
                    }
                } else {
                    items(uiState.grades) { grade ->
                        ModernGradeCard(
                            grade = grade,
                            onDelete = { viewModel.deleteGrade(grade.id) }
                        )
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
        
        // FAB
        if (uiState.categories.isNotEmpty()) {
            FloatingActionButton(
                onClick = { showAddGradeDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
                    .shadow(8.dp, CircleShape),
                containerColor = PrimaryBlue,
                contentColor = White,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Grade",
                    modifier = Modifier.size(28.dp)
                )
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

@Composable
fun SubjectDetailHeader(
    subjectName: String,
    subjectCode: String,
    units: Double,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PrimaryBlue,
                        PrimaryBlueDark
                    )
                )
            )
            .padding(bottom = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Back Button
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(White.copy(alpha = 0.2f))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = subjectName,
                        style = MaterialTheme.typography.headlineSmall,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = subjectCode,
                            style = MaterialTheme.typography.bodyMedium,
                            color = White.copy(alpha = 0.8f)
                        )
                        Text(
                            text = " • ",
                            color = White.copy(alpha = 0.8f)
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(White.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "${units.toInt()} units",
                                style = MaterialTheme.typography.labelSmall,
                                color = White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SubjectStatsRow(
    average: Double,
    letterGrade: String,
    gradePoint: Double,
    hasGrades: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatBox(
            value = if (hasGrades) String.format("%.1f%%", average) else "--",
            label = "Average",
            color = when {
                !hasGrades -> Color.Gray
                average >= 85 -> AccentGreen
                average >= 75 -> AccentOrange
                else -> AccentRed
            },
            modifier = Modifier.weight(1f)
        )
        StatBox(
            value = if (hasGrades) letterGrade else "--",
            label = "Grade",
            color = PrimaryBlue,
            modifier = Modifier.weight(1f)
        )
        StatBox(
            value = if (hasGrades) String.format("%.2f", gradePoint) else "--",
            label = "GPA",
            color = AccentOrange,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatBox(
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun PredictionCard(
    predictedFinal: Double,
    requiredScore: Double,
    remainingWeight: Double
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AccentOrange.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = AccentOrange,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Grade Prediction",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5D4E37)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Predicted Final: ${String.format("%.1f%%", predictedFinal)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF7D6E57)
                )
                Text(
                    text = "Need ${String.format("%.0f%%", requiredScore)} on remaining ${remainingWeight.toInt()}% to pass",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF7D6E57)
                )
            }
        }
    }
}

@Composable
fun ModernGradeCard(
    grade: Grade,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Grade Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        when {
                            grade.percentage >= 85 -> AccentGreen.copy(alpha = 0.1f)
                            grade.percentage >= 75 -> AccentOrange.copy(alpha = 0.1f)
                            else -> AccentRed.copy(alpha = 0.1f)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = when {
                        grade.percentage >= 85 -> AccentGreen
                        grade.percentage >= 75 -> AccentOrange
                        else -> AccentRed
                    },
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Grade Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = grade.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2D3748)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(PrimaryBlue.copy(alpha = 0.1f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = grade.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = PrimaryBlue
                    )
                }
            }
            
            // Score
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${grade.score.toInt()}/${grade.maxScore.toInt()}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D3748)
                )
                Text(
                    text = String.format("%.0f%%", grade.percentage),
                    style = MaterialTheme.typography.bodySmall,
                    color = when {
                        grade.percentage >= 85 -> AccentGreen
                        grade.percentage >= 75 -> AccentOrange
                        else -> AccentRed
                    }
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // Delete Button
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = AccentRed.copy(alpha = 0.6f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun EmptyGradesState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(PrimaryBlue.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier.size(40.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "No Grades Yet",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2D3748),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Start adding your grades by tapping the + button",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}
