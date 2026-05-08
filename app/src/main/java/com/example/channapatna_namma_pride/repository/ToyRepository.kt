package com.example.channapatna_namma_pride.repository

import com.example.channapatna_namma_pride.model.Resource
import com.example.channapatna_namma_pride.model.Toy
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await
import java.io.IOException

/**
 * Repository for toy verification operations against Firestore.
 * Returns [Resource] wrappers so callers always get typed, meaningful feedback.
 */
class ToyRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val toysCollection = firestore.collection("toys")

    /**
     * Looks up a toy by its 6-digit ID.
     *
     * @return [Resource.Success] with the [Toy] if found,
     *         [Resource.Error] with a descriptive message otherwise.
     */
    suspend fun verifyToy(toyId: String): Resource<Toy> {
        return try {
            val document = toysCollection.document(toyId).get().await()
            if (document.exists()) {
                val toy = document.toObject(Toy::class.java)
                if (toy != null) {
                    Resource.Success(toy.copy(id = document.id))
                } else {
                    Resource.Error("Failed to parse toy data. Please try again.")
                }
            } else {
                Resource.Error("No toy found with ID $toyId. Please check the ID and try again.")
            }
        } catch (e: FirebaseFirestoreException) {
            Resource.Error(
                message = "Database error: ${e.message ?: "Unknown Firestore error"}",
                exception = e
            )
        } catch (e: IOException) {
            Resource.Error(
                message = "Network error. Please check your internet connection and try again.",
                exception = e
            )
        } catch (e: Exception) {
            Resource.Error(
                message = "An unexpected error occurred: ${e.message ?: "Unknown error"}",
                exception = e
            )
        }
    }
}

