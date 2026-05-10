package com.example.channapatna_namma_pride.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.channapatna_namma_pride.R
import com.example.channapatna_namma_pride.ui.components.AppTopBar
import com.example.channapatna_namma_pride.ui.components.PillTagFlow
import com.example.channapatna_namma_pride.ui.theme.*

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.channapatna_namma_pride.model.Artisan
import com.example.channapatna_namma_pride.model.Resource
import com.example.channapatna_namma_pride.viewmodel.ArtisanViewModel

/**
 * Artisan Profile screen showing detailed artisan information.
 */
@Composable
fun ArtisanProfileScreen(
    artisanId: String,
    viewModel: ArtisanViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToCatalog: () -> Unit = {}
) {
    val artisanState by viewModel.artisanState.collectAsState()

    LaunchedEffect(artisanId) {
        viewModel.loadArtisan(artisanId)
    }

    Scaffold(
        topBar = { AppTopBar(onNavigateBack = onNavigateBack) }
    ) { padding ->
        when (val state = artisanState) {
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = LacquerRed)
                }
            }
            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    val msg = state.messageId?.let { stringResource(id = it) }
                        ?: state.message
                        ?: stringResource(R.string.error_label)
                    Text(text = msg, color = MaterialTheme.colorScheme.error)
                }
            }
            is Resource.Success -> {
                ArtisanProfileContent(
                    artisan = state.data,
                    padding = padding,
                    onNavigateToCatalog = onNavigateToCatalog
                )
            }
        }
    }
}

@Composable
private fun ArtisanProfileContent(
    artisan: Artisan,
    padding: PaddingValues,
    onNavigateToCatalog: () -> Unit = {}
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(BoneSurface)
            .verticalScroll(scrollState)
    ) {
        // ─── Hero Image ───
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(WoodBrown.copy(alpha = 0.4f), LacquerRed.copy(alpha = 0.3f))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.artisan_hero),
                contentDescription = stringResource(R.string.artisan_photo),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.8f
            )
            // Years badge
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(LacquerRed)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    artisan.experience.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = Color.White
                )
            }
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(24.dp))

            // ─── Title Block ───
            Text(
                text = stringResource(R.string.master_craftsman),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp
                ),
                color = TextPrimary
            )
            Text(
                text = artisan.name,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 42.sp
                ),
                color = LacquerRed
            )
            Text(
                text = artisan.workshopName,
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ─── The Story ───
            SectionCard(icon = Icons.Default.Info, title = stringResource(R.string.the_story)) {
                Text(
                    text = artisan.bio,
                    style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ─── Specialties ───
            if (artisan.specialties.isNotEmpty()) {
                SectionCard(icon = Icons.Default.Build, title = stringResource(R.string.specialties)) {
                    PillTagFlow(items = artisan.specialties.map { it.uppercase() })
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // ─── Workshop Location ───
            SectionCard(
                icon = Icons.Default.LocationOn,
                title = stringResource(R.string.workshop_location)
            ) {
                Text(
                    text = artisan.location,
                    style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = {
                        val gmmIntentUri = Uri.parse("geo:0,0?q=${artisan.workshopName}, Channapatna, Karnataka")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        context.startActivity(mapIntent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, WoodBrown),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = WoodBrown)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.get_directions))
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ─── View Products ───
            Button(
                onClick = onNavigateToCatalog,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LacquerRed)
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    stringResource(R.string.view_products),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ─── Call Artisan ───
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:08027251234") // General Channapatna Crafts center for demo
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = WoodBrown)
            ) {
                Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    stringResource(R.string.call_the_master),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

/**
 * Reusable section card with emoji header.
 */
@Composable
private fun SectionCard(
    emoji: String? = null,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = BoneSurfaceDark.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (emoji != null) {
                    Text(emoji, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.width(8.dp))
                }
                if (icon != null) {
                    Icon(icon, contentDescription = null, tint = LacquerRed, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}
