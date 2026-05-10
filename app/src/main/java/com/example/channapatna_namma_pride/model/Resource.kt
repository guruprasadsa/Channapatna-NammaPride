package com.example.channapatna_namma_pride.model

/**
 * A generic wrapper for data-layer results.
 * Provides a single, consistent way to represent Loading, Success, and Error
 * states across the entire app.
 */
sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String? = null, val messageId: Int? = null, val exception: Throwable? = null) : Resource<Nothing>()

    val isLoading get() = this is Loading
    val isSuccess get() = this is Success
    val isError get() = this is Error

    fun getOrNull(): T? = (this as? Success)?.data
    fun errorMessageOrNull(): String? = (this as? Error)?.message
    fun errorMessageIdOrNull(): Int? = (this as? Error)?.messageId
}

