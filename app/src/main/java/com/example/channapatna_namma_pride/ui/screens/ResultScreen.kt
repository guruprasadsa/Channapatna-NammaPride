package com.example.channapatna_namma_pride.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import coil.compose.AsyncImage
import com.example.channapatna_namma_pride.R
import com.example.channapatna_namma_pride.model.Resource
import com.example.channapatna_namma_pride.model.Toy
import com.example.channapatna_namma_pride.ui.components.AppTopBar
import com.example.channapatna_namma_pride.ui.theme.*
import com.example.channapatna_namma_pride.viewmodel.VerificationViewModel

/**
 * The Verify My Toy screen.
 * Refined with an artisanal narrative and premium visual hierarchy.
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
        topBar = { AppTopBar(onNavigateBack = onNavigateBack) },
        containerColor = BoneSurface
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ─── Immersive Header ───
            HeaderSection()

            // ─── Input Card ───
            InputCard(
                toyId = toyId,
                onToyIdChanged = { viewModel.onToyIdChanged(it) },
                inputError = inputError,
                isVerifying = result is Resource.Loading,
                onVerifyClick = { viewModel.verifyToy() }
            )

            // ─── Verification Result Content ───
            AnimatedContent(
                targetState = result,
                transitionSpec = {
                    (fadeIn() + scaleIn(initialScale = 0.95f)).togetherWith(fadeOut())
                },
                label = "VerificationResult"
            ) { state ->
                when (state) {
                    is Resource.Success -> {
                        VerificationResultContent(
                            toy = state.data,
                            onNavigateToArtisan = onNavigateToArtisan
                        )
                    }
                    is Resource.Error -> {
                        ErrorMessage(messageId = state.messageId, message = state.message)
                    }
                    else -> {
                        // Show nothing or a subtle placeholder
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.verify_title),
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.verify_instruction),
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun InputCard(
    toyId: String,
    onToyIdChanged: (String) -> Unit,
    inputError: Int?,
    isVerifying: Boolean,
    onVerifyClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DigitInputRow(value = toyId, onValueChange = onToyIdChanged)

            if (inputError != null) {
                Text(
                    text = stringResource(id = inputError),
                    color = LacquerRed,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onVerifyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LacquerRed),
                enabled = !isVerifying
            ) {
                if (isVerifying) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(Icons.Default.Search, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        stringResource(R.string.verify_button),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorMessage(messageId: Int?, message: String?) {
    Box(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(LacquerRed.copy(alpha = 0.1f))
            .border(1.dp, LacquerRed.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = messageId?.let { stringResource(it) } ?: message ?: stringResource(R.string.error_label),
            color = LacquerRed,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DigitInputRow(value: String, onValueChange: (String) -> Unit) {
    val placeholder = stringResource(R.string.digit_placeholder)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BoneSurface)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until 6) {
                val char = value.getOrNull(i)?.toString() ?: ""
                DigitBox(char = char, placeholder = placeholder)
                if (i < 5) Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }

    // Hidden field for input
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

@Composable
private fun DigitBox(char: String, placeholder: String) {
    Box(
        modifier = Modifier
            .size(42.dp, 52.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (char.isEmpty()) Color.White else WoodBrown.copy(alpha = 0.1f))
            .border(1.5.dp, if (char.isNotEmpty()) WoodBrown else OutlineColor, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = char.ifEmpty { placeholder },
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = if (char.isEmpty()) OutlineColor else TextPrimary
        )
    }
}

@Composable
private fun VerificationResultContent(
    toy: Toy,
    onNavigateToArtisan: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ─── Authentic Badge ───
        Surface(
            modifier = Modifier.padding(bottom = 24.dp),
            shape = RoundedCornerShape(20.dp),
            color = PrimaryGreen.copy(alpha = 0.1f),
            border = border(1.dp, PrimaryGreen.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.authentic_badge),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
                    color = PrimaryGreen,
                    letterSpacing = 1.2.sp
                )
            }
        }

        // ─── Product Card ───
        Card(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(28.dp), ambientColor = WoodBrown, spotColor = WoodBrown),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                // Product Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .background(BoneSurfaceDark.copy(alpha = 0.3f))
                ) {
                    if (toy.imageUrl.isNotEmpty()) {
                        AsyncImage(
                            model = toy.imageUrl,
                            contentDescription = toy.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.channapatna_toy),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            alpha = 0.4f
                        )
                    }

                    // GI Tag Badge (Overlay)
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = LacquerRed
                    ) {
                        Text(
                            text = stringResource(R.string.gi_tag_label, toy.giTagNumber.ifEmpty { "23" }),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }

                // Product Details
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = toy.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Artisan Link
                    ArtisanLinkRow(
                        artisanName = toy.artisanName,
                        onClick = { onNavigateToArtisan(toy.artisanId.ifEmpty { "syed_athar" }) }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Description Quote
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = BoneSurface
                    ) {
                        Text(
                            text = "\"${toy.description}\"",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontStyle = FontStyle.Italic,
                                lineHeight = 24.sp
                            ),
                            color = TextSecondary,
                            modifier = Modifier.padding(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Action Buttons
                    ActionButtons(onToyStoryClick = {})
                }
            }
        }
    }
}

@Composable
private fun ArtisanLinkRow(artisanName: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .background(BoneSurface)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            color = WoodBrown.copy(alpha = 0.1f)
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                tint = WoodBrown
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.master_maker),
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
            Text(
                text = artisanName,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
        }
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = WoodBrown
        )
    }
}

@Composable
private fun ActionButtons(onToyStoryClick: () -> Unit) {
    OutlinedButton(
        onClick = onToyStoryClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        border = border(1.5.dp, WoodBrown, RoundedCornerShape(28.dp))
    ) {
        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = WoodBrown)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(R.string.watch_toy_story),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = WoodBrown
        )
    }
}

// Helper for border
@Composable
private fun border(width: androidx.compose.ui.unit.Dp, color: Color, shape: androidx.compose.ui.graphics.Shape) = 
    androidx.compose.foundation.BorderStroke(width, color)

