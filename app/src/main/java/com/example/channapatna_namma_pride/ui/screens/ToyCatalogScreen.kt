package com.example.channapatna_namma_pride.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.channapatna_namma_pride.R
import com.example.channapatna_namma_pride.model.CatalogItem
import com.example.channapatna_namma_pride.model.Resource
import com.example.channapatna_namma_pride.ui.components.ErrorContent
import com.example.channapatna_namma_pride.ui.components.LoadingContent
import com.example.channapatna_namma_pride.ui.theme.*
import com.example.channapatna_namma_pride.viewmodel.CatalogViewModel

/**
 * Toy Catalog screen with category filtering.
 * Fetches real products from Firestore with artisan attributions.
 * State is managed by [CatalogViewModel].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToyCatalogScreen(catalogViewModel: CatalogViewModel = viewModel()) {
    val selectedCategory by catalogViewModel.selectedCategory.collectAsState()
    val itemsState by catalogViewModel.items.collectAsState()
    val context = LocalContext.current
    val isKannada = com.example.channapatna_namma_pride.util.LocaleManager.isKannada(context)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            stringResource(R.string.artisan_catalog),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp
                            ),
                            color = WoodBrownLight
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            stringResource(R.string.toy_catalog),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BoneSurface)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BoneSurface)
        ) {
            // Category Filter Chips
            ScrollableTabRow(
                selectedTabIndex = catalogViewModel.categories.indexOf(selectedCategory).coerceAtLeast(0),
                containerColor = BoneSurface,
                contentColor = LacquerRed,
                edgePadding = 16.dp,
                indicator = {},
                divider = {}
            ) {
                catalogViewModel.categories.forEach { category ->
                    val isSelected = category == selectedCategory
                    val bgColor by animateColorAsState(
                        targetValue = if (isSelected) LacquerRed else BoneSurfaceDark,
                        animationSpec = spring(stiffness = Spring.StiffnessLow),
                        label = "chipBg"
                    )
                    val textColor by animateColorAsState(
                        targetValue = if (isSelected) Color.White else TextPrimary,
                        animationSpec = spring(stiffness = Spring.StiffnessLow),
                        label = "chipText"
                    )

                    val categoryText = when (category) {
                        "All" -> stringResource(R.string.category_all)
                        "Classic" -> stringResource(R.string.category_classic)
                        "Educational" -> stringResource(R.string.category_educational)
                        "Decor" -> stringResource(R.string.category_decor)
                        "Infant" -> stringResource(R.string.category_infant)
                        else -> category
                    }

                    Tab(
                        selected = isSelected,
                        onClick = { catalogViewModel.selectCategory(category) },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(bgColor)
                            .height(36.dp)
                    ) {
                        Text(
                            text = categoryText,
                            color = textColor,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                }
            }

            // Content based on state
            when (val state = itemsState) {
                is Resource.Loading -> LoadingContent(message = stringResource(R.string.loading_catalog))
                is Resource.Error -> ErrorContent(
                    message = state.messageId?.let { stringResource(id = it) }
                        ?: state.message
                        ?: stringResource(R.string.error_label),
                    onRetry = { catalogViewModel.selectCategory(selectedCategory) }
                )
                is Resource.Success -> {
                    if (state.data.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    tint = WoodBrownLight.copy(alpha = 0.4f),
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    stringResource(R.string.no_toys_in_category),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = TextSecondary
                                )
                            }
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Section header
                            item(span = { GridItemSpan(2) }) {
                                Column(modifier = Modifier.padding(bottom = 4.dp)) {
                                    Text(
                                        text = stringResource(R.string.handcrafted_with_love),
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = TextPrimary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = stringResource(R.string.authentic_products_count, state.data.size),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondary
                                    )
                                }
                            }

                            items(state.data) { item ->
                                ArtisanCatalogCard(
                                    item = item,
                                    localizedLocation = localizePlaceTokens(
                                        text = item.location,
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
}

/**
 * Premium product card with artisan attribution, image, and GI tag.
 * Follows the Heritage Toy Narrative design system from Stitch.
 */
@Composable
private fun ArtisanCatalogCard(item: CatalogItem, localizedLocation: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Product Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            ) {
                if (item.imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = item.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Warm gradient placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        WoodBrown.copy(alpha = 0.15f),
                                        LacquerRed.copy(alpha = 0.1f)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = item.name,
                            tint = WoodBrown.copy(alpha = 0.3f),
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                // GI Tag Badge
                if (item.giTagNumber.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(PrimaryGreen.copy(alpha = 0.9f))
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.gi_checkmark),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            ),
                            color = Color.White
                        )
                    }
                }
            }

            // Product Details
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        lineHeight = 18.sp
                    ),
                    color = TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Artisan attribution
                if (item.artisanName.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.by_artisan, item.artisanName),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = LacquerRed.copy(alpha = 0.8f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Short description
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        lineHeight = 16.sp
                    ),
                    color = TextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Location tag
                if (item.location.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(WoodBrown.copy(alpha = 0.5f))
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = localizedLocation,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp
                            ),
                            color = TextSecondary.copy(alpha = 0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
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
