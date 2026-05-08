package com.example.channapatna_namma_pride.repository

import com.example.channapatna_namma_pride.model.Resource
import com.example.channapatna_namma_pride.model.Workshop
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repository for workshop / artisan location data.
 * Currently serves static mock data; ready to swap in Firestore calls.
 */
class WorkshopRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val workshopsCollection = firestore.collection("workshops")

    /**
     * Fetches all workshops from Firestore.
     */
    suspend fun getWorkshops(): Resource<List<Workshop>> {
        return try {
            val snapshot = workshopsCollection.get().await()
            val workshops = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Workshop::class.java)?.copy(id = doc.id)
            }
            if (workshops.isNotEmpty()) {
                Resource.Success(workshops)
            } else {
                Resource.Error("No workshops found in the database.")
            }
        } catch (e: Exception) {
            Resource.Error("Failed to fetch workshops: ${e.message}", e)
        }
    }
}
