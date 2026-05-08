package com.example.channapatna_namma_pride.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.channapatna_namma_pride.R
import com.example.channapatna_namma_pride.ui.components.AppTopBar
import com.example.channapatna_namma_pride.ui.theme.*

/**
 * How It's Made screen — shows the 5-step Hale Wood process as a timeline.
 */
@Composable
fun HowItsMadeScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            AppTopBar(title = stringResource(R.string.how_its_made), onNavigateBack = onNavigateBack)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BoneSurface)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(R.string.hale_wood_process),
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(R.string.hale_wood_desc),
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            val steps = listOf(
                stringResource(R.string.step_1_title) to stringResource(R.string.step_1_desc),
                stringResource(R.string.step_2_title) to stringResource(R.string.step_2_desc),
                stringResource(R.string.step_3_title) to stringResource(R.string.step_3_desc),
                stringResource(R.string.step_4_title) to stringResource(R.string.step_4_desc),
                stringResource(R.string.step_5_title) to stringResource(R.string.step_5_desc)
            )

            steps.forEachIndexed { index, (title, description) ->
                TimelineStep(
                    number = "${index + 1}",
                    title = title,
                    description = description,
                    isLast = index == steps.lastIndex
                )
            }
        }
    }
}

@Composable
private fun TimelineStep(
    number: String,
    title: String,
    description: String,
    isLast: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if (isLast) 0.dp else 8.dp)
            .height(IntrinsicSize.Min)
    ) {
        // Timeline indicator column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(48.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(LacquerRed),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f)
                        .background(LacquerRed.copy(alpha = 0.25f))
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Content card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
    }
}
