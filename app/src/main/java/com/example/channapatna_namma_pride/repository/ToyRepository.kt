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
                val isKannada = java.util.Locale.getDefault().language == "kn"
                val nameField = if (isKannada) "name_kn" else "name_en"
                val descField = if (isKannada) "description_kn" else "description_en"
                val artisanNameField = if (isKannada) "artisanName_kn" else "artisanName_en"
                val locationField = if (isKannada) "location_kn" else "location_en"

                val toy = Toy(
                    id = document.id,
                    name = document.getString(nameField) ?: document.getString("name") ?: "",
                    artisanName = document.getString(artisanNameField) ?: document.getString("artisanName") ?: "",
                    artisanId = document.getString("artisanId") ?: "",
                    description = document.getString(descField) ?: document.getString("description") ?: "",
                    imageUrl = document.getString("imageUrl") ?: "",
                    location = document.getString(locationField) ?: document.getString("location") ?: "Channapatna, Karnataka",
                    giTagNumber = document.getString("giTagNumber") ?: ""
                )
                Resource.Success(toy)
            } else {
                Resource.Error(
                    message = "No toy found with ID $toyId. Please check the ID and try again.",
                    messageId = com.example.channapatna_namma_pride.R.string.error_no_toy_found
                ) // We don't have a way to pass format args inside the viewModel cleanly, but let's pass message for logging.
            }
        } catch (e: FirebaseFirestoreException) {
            Resource.Error(
                message = "Database error: ${e.message ?: "Unknown Firestore error"}",
                messageId = com.example.channapatna_namma_pride.R.string.error_database,
                exception = e
            )
        } catch (e: IOException) {
            Resource.Error(
                message = "Network error. Please check your internet connection and try again.",
                messageId = com.example.channapatna_namma_pride.R.string.error_network,
                exception = e
            )
        } catch (e: Exception) {
            Resource.Error(
                message = "An unexpected error occurred: ${e.message ?: "Unknown error"}",
                messageId = com.example.channapatna_namma_pride.R.string.error_unexpected,
                exception = e
            )
        }
    }
}

