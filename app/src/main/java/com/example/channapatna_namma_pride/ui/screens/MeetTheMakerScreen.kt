package com.example.channapatna_namma_pride.ui.screens

import android.util.Log

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.channapatna_namma_pride.R
import com.example.channapatna_namma_pride.model.Resource
import com.example.channapatna_namma_pride.model.Workshop
import com.example.channapatna_namma_pride.ui.components.ErrorContent
import com.example.channapatna_namma_pride.ui.components.LoadingContent
import com.example.channapatna_namma_pride.ui.theme.*
import com.example.channapatna_namma_pride.viewmodel.WorkshopViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

private const val TAG = "MeetTheMakerScreen"

// Channapatna town centre
private val CHANNAPATNA_CENTER = LatLng(12.6517, 77.2063)

/**
 * Meet the Maker screen — shows an interactive Google Map of workshop locations
 * alongside a scrollable workshop list.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetTheMakerScreen(workshopViewModel: WorkshopViewModel = viewModel()) {
    val workshopsState by workshopViewModel.workshops.collectAsState()
    val context = LocalContext.current
    val isKannada = com.example.channapatna_namma_pride.util.LocaleManager.isKannada(context)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            stringResource(R.string.meet_the_maker),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = TextPrimary
                        )
                        Text(
                            stringResource(R.string.discover_workshops),
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BoneSurface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BoneSurface)
        ) {
            // ─── Interactive Map ───
            MapSection(
                workshops = when (val state = workshopsState) {
                    is Resource.Success -> state.data
                    else -> emptyList()
                }
            )

            // ─── Section Header ───
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(LacquerRed.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Place,
                        contentDescription = null,
                        tint = LacquerRed,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.nearby_workshops),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.weight(1f))
                when (val state = workshopsState) {
                    is Resource.Success -> {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = PrimaryGreen.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = stringResource(R.string.workshops_found, state.data.size),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = PrimaryGreen,
                                modifier = Modifier.padding(
                                    horizontal = 10.dp,
                                    vertical = 4.dp
                                )
                            )
                        }
                    }
                    else -> {}
                }
            }

            // ─── Workshop List ───
            when (val state = workshopsState) {
                is Resource.Loading -> LoadingContent(message = stringResource(R.string.finding_workshops))
                is Resource.Error -> ErrorContent(
                    message = state.messageId?.let { stringResource(id = it) }
                        ?: state.message
                        ?: stringResource(R.string.error_label),
                    onRetry = { workshopViewModel.refresh() }
                )
                is Resource.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 24.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(state.data) { workshop ->
                            WorkshopCard(
                                workshop = workshop,
                                localizedAddress = localizePlaceTokens(
                                    text = workshop.address,
                                    isKannada = isKannada,
                                    channapatna = stringResource(R.string.place_channapatna),
                                    karnataka = stringResource(R.string.place_karnataka)
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

// ─── Map Section ───────────────────────────────────────────────────────────

@Composable
private fun MapSection(workshops: List<Workshop>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = WoodBrown.copy(alpha = 0.2f),
                spotColor = WoodBrown.copy(alpha = 0.15f)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMapComposable(workshops = workshops)

            // Gradient overlay at bottom for visual polish
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White.copy(alpha = 0.6f)
                            )
                        )
                    )
            )
        }
    }
}

// ─── Google Map Composable ─────────────────────────────────────────────

@Composable
private fun GoogleMapComposable(workshops: List<Workshop>) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(CHANNAPATNA_CENTER, 13.5f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        // Add markers for workshops using their actual locations
        workshops.forEach { workshop ->
            if (workshop.latitude != 0.0 && workshop.longitude != 0.0) {
                Marker(
                    state = MarkerState(position = LatLng(workshop.latitude, workshop.longitude)),
                    title = workshop.name,
                    snippet = stringResource(R.string.by_artisan, workshop.artisanName)
                )
            }
        }
    }
}

// ─── Workshop Card ─────────────────────────────────────────────────────────

@Composable
private fun WorkshopCard(workshop: Workshop, localizedAddress: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                LacquerRed.copy(alpha = 0.15f),
                                TurmericYellow.copy(alpha = 0.1f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = LacquerRed.copy(alpha = 0.8f),
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Workshop info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = workshop.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (workshop.artisanName.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.by_artisan, workshop.artisanName),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = LacquerRed.copy(alpha = 0.8f),
                        modifier = Modifier.padding(vertical = 1.dp)
                    )
                }
                if (workshop.description.isNotEmpty()) {
                    Text(
                        text = workshop.description,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary.copy(alpha = 0.8f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = TextSecondary.copy(alpha = 0.6f),
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = localizedAddress,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Distance badge
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = LacquerRed.copy(alpha = 0.08f)
            ) {
                Text(
                    text = workshop.distance,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = LacquerRed,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
        }
    }
}

private fun localizePlaceTokens(
    text: String,
    isKannada: Boolean,
    channapatna: String,
    karnataka: String
): String {
    if (!isKannada || text.isBlank()) return text
    return text
        .replace("Channapatna", channapatna)
        .replace("Karnataka", karnataka)
}
