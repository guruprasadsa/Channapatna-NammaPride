package com.example.channapatna_namma_pride.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.channapatna_namma_pride.R
import com.example.channapatna_namma_pride.ui.components.BrandHeader
import com.example.channapatna_namma_pride.ui.theme.*

// ─── Stitch Design Color Constants ───
private val HeroTextWhite = Color.White
private val WoodBrownDark = Color(0xFF5D4037)
private val WoodBrownMedium = Color(0xFF8D6E63)
private val TerracottaRed = Color(0xFFBF360C)
private val CreamBackground = Color(0xFFFFF8F6)
private val CreamCard = Color(0xFFFFFBF5)
private val CardBorder = Color(0xFFD7CCC8)
private val PrimaryFixedPeach = Color(0xFFFFDBD0)
private val TertiaryTeal = Color(0xFF1C3433)
private val TertiaryFixed = Color(0xFFCDE8E5)
private val SecondaryFixed = Color(0xFFFFDBD1)

// ─── Stitch Image URLs ───
private const val HERO_IMAGE_URL =
    "https://lh3.googleusercontent.com/aida-public/AB6AXuBYrSPNpNK6NHLIGMj7VJDF6B48W_bHLjDTNuMx7N4r4Wk5ugu6erGTWkYjvtcyYlb1QhT-MODpoB4UJ2FretaBIWBlm-z2D8Q5uGNRlVxJ0r2-BTI2lhFKOcOBpILzH8aPA98y0dloYSuGIUVVUDiZto81_81AhbWfcNjVG28YMTwWKY4jXCE6eozkX9sx9PoIUUuE29dvCA46KSNH4kUHKeCkdznlue2LmRyZH0DDqAo1uvTgemO5O2-IW1lhMtTeVgtrkz1xRC4"

private const val ARTISAN_1_IMAGE_URL =
    "https://lh3.googleusercontent.com/aida-public/AB6AXuALIVGegWq2eaelJ8o6565X7xYEpDWBT_jw7ziYn2xxM4Y0yvcPVepvb3CQul9h2ASG0CSp_B_3Gbq-RSt9WS00PLINfktHOXYlgqU8zPSkx3uvZ55wtcPwsMe2pOtv1ssmnpR1zPtp94kDJH57rScoKDYT1d8X8bJhQxnxoTLqUrt1uOZmSTGv7g1VML4B1ftLYrHrOlpbJ4tfbQ60Indh1CPr9bvgFwzf5Sz45a-yHZeheMISe7R5XJnAMCHtN-BkNM1VPIXxTsU"

private const val ARTISAN_2_IMAGE_URL =
    "https://lh3.googleusercontent.com/aida-public/AB6AXuDtJXOzm8bZOug9tWm4oF0fdI6IftB9UJhxi0TUIqNo4jxGSQBb-0GRGDBeQAgJKJxnMB2Qo2usRFlZK4HqYacyXfDc5us3A3bj99ZDzPyWwJq1WPElEDTyMxDjYgWy94RqEuIozAEXLpcwD8hnp86iYFhVuzv4tdjl1J_fFHvYm-9xYkk-ZKMUO4dksOHkDQkdX30x-xpCQMYrtFqZpocugk283lB_hMqRXNy608uOYKs0NJVM-WCE62HJIe1kQzHsL8n3dOeIcQw"

private const val ARTISAN_3_IMAGE_URL =
    "https://lh3.googleusercontent.com/aida-public/AB6AXuBIJivT1JmdDBYIDJXDXx3T74MQZrM9NICmwzuFylIUeNy4YjPq3O1qX5rUgeiw8QknzSbyQE6JMMcnEoOq8dImUT37-4ox01ZM6WALGgxTeHOG8EekSPsiHS91MvpD4tY0I08bamNtzfs3Zm3b_Zfb-oZ75hquIpEDcCToE6V9aBwj0Cb3xdJdT0PDyW1L2zFtMm4cH-BDc0SOW3eKDpI1jiGm0diiutDBI9QswEPVvZGLplj8kINMYn7tnZvHH5aohEQS0cphYWE"

private const val TOY_1_IMAGE_URL =
    "https://lh3.googleusercontent.com/aida-public/AB6AXuDv8znlgkD4dMmWF2dQLAYchR6-hGEu0OQOk1gfmtoozrLn5s6oDqXyPzUoGgL1MJpyc3sLMnJi82EeylSyWRrGxIa3dQZXFnlCREsIxiC-odBbhnUIWicg30yZlLznbCPfrlA0WDw3JozDMkNeNA57mK0ZzFv4ATsNi2XxpCJIqt00U1gl7wb7prAR83LUqe4x8yDvIvlpnxwVdQVXpvVjs_YpTeR5PnRJ0dA9CwmOuO7baAoVuZzSltNgk_qs21C3ZvTm5o-opO0"

private const val TOY_2_IMAGE_URL =
    "https://lh3.googleusercontent.com/aida-public/AB6AXuC4CsWkA8xJhmPslpCa9VJtzS7uo6Zd4oMFejYJVMcr_i172Y1nlQJT5G558Ik33CKind_wHBU3-S-y1kJGE4MCHt_boWkIqd8Oteo9X4duzGm-VNHLZd98ANvvl_acOBzsLEizf1ebPFHhMrvjrXGeSG_p0RIWtctxfaCifUFW1k-kNl-k2QE-VIxhkKBPUQahbyICE4CX3os7SXdWV_WzyD_tcdydSHk7mBzcvNxtC91q22Cy9arhGpQbmV4UTrXqmZO_eQSmbxw"

private const val TOY_3_IMAGE_URL =
    "https://lh3.googleusercontent.com/aida-public/AB6AXuAHkvG44iJMHkqoR2U3qcoQFecR8BsZjYi9ZeCWtmwNO-QAK-1nf1RbBLEbGQMlTYg0dk2vRyAelxUWHrcTIqGz-iHk-tngZKJhpeJDrB4Kjp_fvSztzA-a9-mlRkNzkTYl7Zx6nbsk-OaOsLjweElpu_QH4AWH9PS6VwgupNLkzV1VSfy6pwy_e-zQ6CLOb0Ip4yERKA53YYHQ_eDbWbIx6zDJUM0fxg5lRY2PgEEBIQff3EaQxAFz4KQRx2FapK3Oy8wYatdpvAM"

/**
 * Home screen — Enhanced design matching the Stitch prototype.
 * Sections: Hero, GI Tag, Craft Journey, Support Local Artisans, New Arrivals.
 */
@Composable
fun HomeScreen(
    onNavigateToVerify: () -> Unit,
    onNavigateToMap: () -> Unit,
    onNavigateToHowItsMade: () -> Unit,
    onNavigateToCatalog: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(rememberScrollState())
    ) {
        // ─── Brand Header ───
        BrandHeader()

        // ─── Main Content ───
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // ─── 1. Hero Section ───
            HeroSection(onNavigateToVerify)

            // ─── 2. GI Tag Heritage Card ───
            GiTagCard()

            // ─── 3. The Craft Journey ───
            CraftJourneySection(onNavigateToHowItsMade)

            // ─── 4. Support Local Artisans ───
            ArtisansSection(onNavigateToMap)

            // ─── 5. New Arrivals ───
            NewArrivalsSection(onNavigateToCatalog)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ─── 1. Hero Section ───

@Composable
private fun HeroSection(onNavigateToVerify: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(32.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(HERO_IMAGE_URL)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.heritage_toys),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.8f)
                            )
                        )
                    )
            )

            // Content over gradient
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = stringResource(R.string.home_hero_title),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        lineHeight = 36.sp
                    ),
                    color = HeroTextWhite
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.home_hero_subtitle),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = HeroTextWhite.copy(alpha = 0.9f)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onNavigateToVerify,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaRed),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_search),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.verify_my_toy),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White
                    )
                }
            }
        }
    }
}

// ─── 2. GI Tag Heritage Card ───

@Composable
private fun GiTagCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Icon circle
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(PrimaryFixedPeach),
                contentAlignment = Alignment.Center
            ) {
                // Using a simple text emoji for the workspace_premium icon
                Text(
                    text = "🏅",
                    fontSize = 24.sp
                )
            }

            // Text content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.gi_tag_title),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 24.sp
                    ),
                    color = WoodBrownDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.gi_tag_description),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    color = WoodBrownMedium,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

// ─── 3. Craft Journey Section ───

@Composable
private fun CraftJourneySection(onNavigateToHowItsMade: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = stringResource(R.string.craft_journey_title),
            style = MaterialTheme.typography.headlineMedium,
            color = WoodBrownDark
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CraftStepCard(
                step = stringResource(R.string.craft_step_1_title),
                description = stringResource(R.string.craft_step_1_desc),
                emoji = "🌳",
                bgColor = PrimaryFixedPeach,
                onClick = onNavigateToHowItsMade
            )
            CraftStepCard(
                step = stringResource(R.string.craft_step_2_title),
                description = stringResource(R.string.craft_step_2_desc),
                emoji = "🔧",
                bgColor = TertiaryFixed,
                onClick = onNavigateToHowItsMade
            )
            CraftStepCard(
                step = stringResource(R.string.craft_step_3_title),
                description = stringResource(R.string.craft_step_3_desc),
                emoji = "🎨",
                bgColor = SecondaryFixed,
                onClick = onNavigateToHowItsMade
            )
        }
    }
}

@Composable
private fun CraftStepCard(
    step: String,
    description: String,
    emoji: String,
    bgColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 22.sp)
            }
            Text(
                text = step,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                    fontSize = 11.sp
                ),
                color = TerracottaRed,
                textAlign = TextAlign.Center
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = WoodBrownMedium,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}

// ─── 4. Support Local Artisans ───

@Composable
private fun ArtisansSection(onNavigateToMap: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(R.string.support_local_artisans),
                style = MaterialTheme.typography.headlineMedium,
                color = WoodBrownDark
            )
            Text(
                text = stringResource(R.string.view_all),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                    fontSize = 11.sp
                ),
                color = TerracottaRed,
                modifier = Modifier.clickable { onNavigateToMap() }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ArtisanCard(
                name = stringResource(R.string.home_artisan_ramesh),
                title = stringResource(R.string.role_master_turner),
                imageUrl = ARTISAN_1_IMAGE_URL,
                onClick = onNavigateToMap
            )
            ArtisanCard(
                name = stringResource(R.string.home_artisan_sunitha),
                title = stringResource(R.string.role_lacquer_artist),
                imageUrl = ARTISAN_2_IMAGE_URL,
                onClick = onNavigateToMap
            )
            ArtisanCard(
                name = stringResource(R.string.home_artisan_venkatesh),
                title = stringResource(R.string.role_wood_carver),
                imageUrl = ARTISAN_3_IMAGE_URL,
                onClick = onNavigateToMap
            )
        }
    }
}

@Composable
private fun ArtisanCard(
    name: String,
    title: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Circular portrait with border
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .border(
                    width = 4.dp,
                    color = WoodBrownDark.copy(alpha = 0.2f),
                    shape = CircleShape
                )
                .shadow(elevation = 8.dp, shape = CircleShape)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        // Name and title
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 16.sp),
                color = WoodBrownDark,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                ),
                color = TerracottaRed,
                textAlign = TextAlign.Center
            )
        }
    }
}

// ─── 5. New Arrivals ───

@Composable
private fun NewArrivalsSection(onNavigateToCatalog: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(R.string.new_arrivals),
                style = MaterialTheme.typography.headlineMedium,
                color = WoodBrownDark
            )
            Text(
                text = stringResource(R.string.view_all),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                    fontSize = 11.sp
                ),
                color = TerracottaRed,
                modifier = Modifier.clickable { onNavigateToCatalog() }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            NewArrivalCard(
                name = stringResource(R.string.home_toy_stacker),
                price = stringResource(R.string.price_450),
                imageUrl = TOY_1_IMAGE_URL,
                onClick = onNavigateToCatalog
            )
            NewArrivalCard(
                name = stringResource(R.string.home_toy_engine),
                price = stringResource(R.string.price_850),
                imageUrl = TOY_2_IMAGE_URL,
                onClick = onNavigateToCatalog
            )
            NewArrivalCard(
                name = stringResource(R.string.home_toy_bambaram),
                price = stringResource(R.string.price_250),
                imageUrl = TOY_3_IMAGE_URL,
                onClick = onNavigateToCatalog
            )
        }
    }
}

@Composable
private fun NewArrivalCard(
    name: String,
    price: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .clickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Image card with favorite button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = RoundedCornerShape(32.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Favorite button
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { /* Favorite */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.favorite_desc),
                        tint = TerracottaRed,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Name and price
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 16.sp),
                color = WoodBrownDark,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = price,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = TerracottaRed
            )
        }
    }
}
