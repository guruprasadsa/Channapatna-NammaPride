package com.example.channapatna_namma_pride.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.channapatna_namma_pride.ui.theme.TextPrimary
import com.example.channapatna_namma_pride.ui.theme.WoodBrown

/**
 * Dark pill-shaped tag for displaying specialties, categories, etc.
 */
@Composable
fun HeritagePillTag(
    text: String,
    backgroundColor: Color = WoodBrown,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            ),
            color = textColor
        )
    }
}

/**
 * Lays out a list of pill tags in a wrapped flow layout.
 */
@Composable
fun PillTagFlow(
    items: List<String>,
    backgroundColor: Color = WoodBrown,
    textColor: Color = Color.White
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.chunked(2).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { item ->
                    HeritagePillTag(
                        text = item,
                        backgroundColor = backgroundColor,
                        textColor = textColor
                    )
                }
            }
        }
    }
}

