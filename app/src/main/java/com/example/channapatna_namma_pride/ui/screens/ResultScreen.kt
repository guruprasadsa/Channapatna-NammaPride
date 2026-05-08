package com.example.channapatna_namma_pride.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.channapatna_namma_pride.R
import com.example.channapatna_namma_pride.model.Resource
import com.example.channapatna_namma_pride.model.Toy
import com.example.channapatna_namma_pride.ui.components.AppTopBar
import com.example.channapatna_namma_pride.ui.theme.*
import com.example.channapatna_namma_pride.viewmodel.VerificationViewModel

/**
 * The Verify My Toy screen.
 * Combines the input form and result display into a single scrollable flow.
 * All input state is managed by the [VerificationViewModel].
 */
@Composable
fun ResultScreen(
    viewModel: VerificationViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToArtisan: (String) -> Unit = {}
) {
    val toyId by viewModel.toyIdInput.collectAsState()
    val result by viewModel.verificationResult.collectAsState()
    val inputError by viewModel.inputError.collectAsState()

    Scaffold(
        topBar = { AppTopBar(onNavigateBack = onNavigateBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BoneSurface)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ─── Title ───
            Text(
                text = stringResource(R.string.verify_title),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
            Text(
                text = stringResource(R.string.verify_instruction),
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
            )

            // ─── Input & Action Card ───
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = BoneSurfaceDark.copy(alpha = 0.4f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // ─── 6-Digit Input Row ───
                    DigitInputRow(value = toyId, onValueChange = { viewModel.onToyIdChanged(it) })

                    // Input error
                    if (inputError != null) {
                        Text(
                            text = inputError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // ─── Verify Button ───
                    Button(
                        onClick = { viewModel.verifyToy() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(26.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LacquerRed),
                        enabled = result !is Resource.Loading
                    ) {
                        if (result is Resource.Loading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                stringResource(R.string.verify_button),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.5.sp
                                )
                            )
                        }
                    }
                }
            }

            // ─── Error from API ───
            if (result is Resource.Error) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = (result as Resource.Error).message,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            // ─── Success Result ───
            if (result is Resource.Success) {
                val toy = (result as Resource.Success<Toy>).data
                VerificationResultContent(
                    toy = toy,
                    onNavigateToArtisan = onNavigateToArtisan
                )
            }
        }
    }
}

/**
 * The 6-digit input row rendered as individual rounded boxes.
 */
@Composable
private fun DigitInputRow(value: String, onValueChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .border(1.5.dp, OutlineColor, RoundedCornerShape(32.dp))
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                for (i in 0 until 6) {
                    val char = value.getOrNull(i)?.toString() ?: ""
                    Text(
                        text = char.ifEmpty { "O" },
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = if (char.isEmpty()) OutlineColor.copy(alpha = 0.5f) else TextPrimary
                    )
                }
            }
            Icon(
                Icons.Default.Search, 
                contentDescription = null, 
                tint = OutlineColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }

    // Invisible text field to capture keyboard input
    androidx.compose.material3.OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
            keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
        ),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(0.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        )
    )
}

/**
 * The verified toy result content — badge, image, details, and action buttons.
 */
@Composable
private fun VerificationResultContent(
    toy: Toy,
    onNavigateToArtisan: (String) -> Unit
) {
    Spacer(modifier = Modifier.height(28.dp))

    // Authentic badge
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(PrimaryGreen.copy(alpha = 0.1f))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = stringResource(R.string.authentic_badge),
            tint = PrimaryGreen,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            stringResource(R.string.authentic_badge),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = PrimaryGreen
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Toy image card
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(WoodBrown.copy(alpha = 0.3f), LacquerRed.copy(alpha = 0.2f))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.channapatna_toy),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.9f
            )
            // GI Tag badge
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LacquerRed)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    stringResource(R.string.gi_tag_label, toy.giTagNumber.ifEmpty { toy.id.take(2) }),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Toy name
    Text(
        text = toy.name.replaceFirstChar { it.uppercase() },
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
        color = TextPrimary,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(12.dp))

    // Artisan row
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(WoodBrown.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.artisan_portrait),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                stringResource(R.string.master_maker),
                style = MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = TextSecondary
            )
            Text(
                toy.artisanName,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
        }
        Icon(
            androidx.compose.material.icons.Icons.Default.Build,
            contentDescription = null,
            tint = WoodBrown.copy(alpha = 0.6f),
            modifier = Modifier.size(24.dp)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Description quote
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BoneSurfaceDark.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = "\"${toy.description}\"",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontStyle = FontStyle.Italic,
                lineHeight = 22.sp
            ),
            color = TextSecondary,
            modifier = Modifier.padding(16.dp)
        )
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Action buttons
    Button(
        onClick = { onNavigateToArtisan(toy.artisanId.ifEmpty { "syed_athar" }) },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(containerColor = WoodBrown)
    ) {
        Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            stringResource(R.string.view_artisan_profile),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedButton(
        onClick = { /* TODO: Navigate to toy story */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(26.dp)
    ) {
        Icon(
            Icons.Default.PlayArrow,
            contentDescription = null,
            tint = WoodBrown,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            stringResource(R.string.watch_toy_story),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = WoodBrown
        )
    }

    Spacer(modifier = Modifier.height(32.dp))
}
