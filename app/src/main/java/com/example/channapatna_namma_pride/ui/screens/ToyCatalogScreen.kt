package com.example.channapatna_namma_pride.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.channapatna_namma_pride.R
import com.example.channapatna_namma_pride.model.CatalogItem
import com.example.channapatna_namma_pride.model.Resource
import com.example.channapatna_namma_pride.ui.components.ErrorContent
import com.example.channapatna_namma_pride.ui.components.LoadingContent
import com.example.channapatna_namma_pride.ui.theme.*
import com.example.channapatna_namma_pride.viewmodel.CatalogViewModel

/**
 * Toy Catalog screen with category filtering.
 * State is managed by [CatalogViewModel].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToyCatalogScreen(catalogViewModel: CatalogViewModel = viewModel()) {
    val selectedCategory by catalogViewModel.selectedCategory.collectAsState()
    val itemsState by catalogViewModel.items.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.toy_catalog),
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextPrimary
                    )
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
            // Category Chips
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
                    Tab(
                        selected = isSelected,
                        onClick = { catalogViewModel.selectCategory(category) },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) LacquerRed else BoneSurfaceDark)
                            .height(36.dp)
                    ) {
                        Text(
                            text = category,
                            color = if (isSelected) BoneSurface else TextPrimary,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content based on state
            when (val state = itemsState) {
                is Resource.Loading -> LoadingContent(message = stringResource(R.string.loading_catalog))
                is Resource.Error -> ErrorContent(
                    message = state.message,
                    onRetry = { catalogViewModel.selectCategory(selectedCategory) }
                )
                is Resource.Success -> {
                    if (state.data.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                stringResource(R.string.no_toys_in_category),
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextSecondary
                            )
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(state.data) { item ->
                                ToyCatalogCard(item)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ToyCatalogCard(item: CatalogItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(LacquerRed.copy(alpha = 0.08f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = item.name,
                    tint = LacquerRed.copy(alpha = 0.4f),
                    modifier = Modifier.size(48.dp)
                )
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary,
                    maxLines = 1
                )
                Text(
                    text = item.category,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = item.price,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = LacquerRed
                )
            }
        }
    }
}
