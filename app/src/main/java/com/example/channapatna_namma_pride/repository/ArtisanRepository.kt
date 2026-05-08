package com.example.channapatna_namma_pride.repository

import com.example.channapatna_namma_pride.model.Artisan
import com.example.channapatna_namma_pride.model.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await
import java.io.IOException

/**
 * Repository for artisan related operations against Firestore.
 */
class ArtisanRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val artisansCollection = firestore.collection("artisans")

    /**
     * Fetches artisan details by ID.
     */
    suspend fun getArtisanById(artisanId: String): Resource<Artisan> {
        return try {
            val document = artisansCollection.document(artisanId).get().await()
            if (document.exists()) {
                val artisan = document.toObject(Artisan::class.java)
                if (artisan != null) {
                    Resource.Success(artisan.copy(id = document.id))
                } else {
                    Resource.Error("Failed to parse artisan data.")
                }
            } else {
                Resource.Error("Artisan not found.")
            }
        } catch (e: Exception) {
            Resource.Error(
                message = "Error fetching artisan: ${e.message ?: "Unknown error"}",
                exception = e
            )
        }
    }
}
