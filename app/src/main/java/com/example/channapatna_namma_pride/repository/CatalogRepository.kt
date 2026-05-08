package com.example.channapatna_namma_pride.repository

import com.example.channapatna_namma_pride.model.CatalogItem
import com.example.channapatna_namma_pride.model.Resource

/**
 * Repository for toy catalog data.
 * Currently serves static mock data; ready to swap in Firestore calls.
 */
class CatalogRepository {

    private val allItems = listOf(
        CatalogItem("1", "Rocking Horse", "Classic", "₹850"),
        CatalogItem("2", "Stacking Rings", "Educational", "₹450"),
        CatalogItem("3", "Spinning Top", "Classic", "₹150"),
        CatalogItem("4", "Abacus", "Educational", "₹600"),
        CatalogItem("5", "Pull-along Train", "Vehicles", "₹950"),
        CatalogItem("6", "Wooden Rattle", "Infant", "₹250"),
        CatalogItem("7", "Nesting Dolls", "Classic", "₹700"),
        CatalogItem("8", "Animal Set", "Educational", "₹550")
    )

    val categories: List<String> = listOf("All") + allItems.map { it.category }.distinct().sorted()

    fun getCatalogItems(category: String = "All"): Resource<List<CatalogItem>> {
        return try {
            val filtered = if (category == "All") allItems
            else allItems.filter { it.category == category }
            Resource.Success(filtered)
        } catch (e: Exception) {
            Resource.Error("Failed to load catalog: ${e.message}", e)
        }
    }
}

