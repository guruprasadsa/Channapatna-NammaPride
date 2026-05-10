package com.example.channapatna_namma_pride.repository

import com.example.channapatna_namma_pride.model.CatalogItem
import com.example.channapatna_namma_pride.model.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repository for toy catalog data.
 * Fetches real products from the Firestore "toys" collection.
 */
class CatalogRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val toysCollection = firestore.collection("toys")

    val categories: List<String> = listOf("All", "Classic", "Educational", "Decor", "Infant")

    /**
     * Fetches catalog items from Firestore.
     * Optionally filters by category.
     */
    suspend fun getCatalogItems(category: String = "All"): Resource<List<CatalogItem>> {
        return try {
            val snapshot = toysCollection.get().await()
            val items = snapshot.documents.mapNotNull { doc ->
                try {
                    val isKannada = java.util.Locale.getDefault().language == "kn"
                    val nameField = if (isKannada) "name_kn" else "name_en"
                    val descField = if (isKannada) "description_kn" else "description_en"
                    val artisanNameField = if (isKannada) "artisanName_kn" else "artisanName_en"
                    val locationField = if (isKannada) "location_kn" else "location_en"

                    // Fallback to "name" if the language-specific field doesn't exist
                    val name = doc.getString(nameField) ?: doc.getString("name") ?: return@mapNotNull null
                    val artisanName = doc.getString(artisanNameField) ?: doc.getString("artisanName") ?: ""
                    val artisanId = doc.getString("artisanId") ?: ""
                    val description = doc.getString(descField) ?: doc.getString("description") ?: ""
                    val imageUrl = doc.getString("imageUrl") ?: ""
                    val location = doc.getString(locationField) ?: doc.getString("location") ?: ""
                    val giTagNumber = doc.getString("giTagNumber") ?: ""

                    // Infer category from product name/description
                    val inferredCategory = inferCategory(name, description)

                    CatalogItem(
                        id = doc.id,
                        name = name,
                        artisanName = artisanName,
                        artisanId = artisanId,
                        description = description,
                        imageUrl = imageUrl,
                        location = location,
                        giTagNumber = giTagNumber,
                        category = inferredCategory
                    )
                } catch (e: Exception) {
                    null
                }
            }

            val filtered = if (category == "All") items
            else items.filter { it.category.equals(category, ignoreCase = true) }

            Resource.Success(filtered)
        } catch (e: Exception) {
            Resource.Error(
                message = "An unexpected error occurred: ${e.message ?: "Unknown error"}",
                messageId = com.example.channapatna_namma_pride.R.string.error_unexpected,
                exception = e
            )
        }
    }

    /**
     * Infers a category from the product name and description.
     */
    private fun inferCategory(name: String, description: String): String {
        val combined = "$name $description".lowercase()
        return when {
            combined.contains("stacking") || combined.contains("educational") ||
            combined.contains("stacker") || combined.contains("abacus") ||
            combined.contains("ಶೈಕ್ಷಣಿಕ") || combined.contains("ಶಿಕ್ಷಣ") -> "Educational"

            combined.contains("jewelry") || combined.contains("box") ||
            combined.contains("decor") || combined.contains("ಅಲಂಕಾರ") -> "Decor"

            combined.contains("rattle") || combined.contains("pull") ||
            combined.contains("toddler") || combined.contains("infant") ||
            combined.contains("ಶಿಶು") || combined.contains("ಮಗು") -> "Infant"

            else -> "Classic"
        }
    }
}
