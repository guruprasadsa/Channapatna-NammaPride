package com.example.channapatna_namma_pride.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.channapatna_namma_pride.R
import com.example.channapatna_namma_pride.ui.theme.LacquerRed
import com.example.channapatna_namma_pride.ui.theme.TextPrimary
import androidx.compose.material3.Icon

/**
 * The "CHANNAPATNA - NAMMA PRIDE" + language indicator header.
 * Reusable across HomeScreen and any other top-level screen.
 *
 * @param onLanguageClick optional callback when the language label is tapped,
 *                        allowing the parent to open a language picker.
 */
@Composable
fun BrandHeader(
    modifier: Modifier = Modifier,
    onLanguageClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // App Logo instead of hamburger menu
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = stringResource(R.string.brand_line_1),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    ),
                    color = TextPrimary
                )
                Text(
                    text = stringResource(R.string.brand_line_2),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    ),
                    color = TextPrimary
                )
            }
        }
        Text(
            text = stringResource(R.string.language_label).replace(" ▾", ""),
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = LacquerRed,
            modifier = if (onLanguageClick != null) {
                Modifier.clickable { onLanguageClick() }
            } else Modifier
        )
    }
}
