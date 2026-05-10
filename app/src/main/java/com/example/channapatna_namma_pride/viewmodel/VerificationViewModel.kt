package com.example.channapatna_namma_pride.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.channapatna_namma_pride.model.Resource
import com.example.channapatna_namma_pride.model.Toy
import com.example.channapatna_namma_pride.repository.ToyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Verify My Toy flow.
 * Manages the toy ID input, validation, and Firestore lookup.
 * The [toyIdInput] is the single source of truth for the text field state.
 */
class VerificationViewModel : ViewModel() {
    private val repository = ToyRepository()

    private val _toyIdInput = MutableStateFlow("")
    val toyIdInput: StateFlow<String> = _toyIdInput.asStateFlow()

    private val _verificationResult = MutableStateFlow<Resource<Toy>?>(null)
    val verificationResult: StateFlow<Resource<Toy>?> = _verificationResult.asStateFlow()

    private val _inputError = MutableStateFlow<Int?>(null)
    val inputError: StateFlow<Int?> = _inputError.asStateFlow()

    /**
     * Updates the toy ID input. Only digits are accepted, max 6 characters.
     */
    fun onToyIdChanged(value: String) {
        if (value.length <= 6 && value.all { it.isDigit() }) {
            _toyIdInput.value = value
            _inputError.value = null // Clear errors on new input
        }
    }

    /**
     * Validates the input and triggers the verification lookup.
     */
    fun verifyToy() {
        val id = _toyIdInput.value.trim()

        // Input validation
        when {
            id.isEmpty() -> {
                _inputError.value = com.example.channapatna_namma_pride.R.string.error_enter_toy_id
                return
            }
            id.length != 6 -> {
                _inputError.value = com.example.channapatna_namma_pride.R.string.error_id_digits
                return
            }
            !id.all { it.isDigit() } -> {
                _inputError.value = com.example.channapatna_namma_pride.R.string.error_id_digits
                return
            }
        }

        _inputError.value = null
        _verificationResult.value = Resource.Loading

        viewModelScope.launch {
            _verificationResult.value = repository.verifyToy(id)
        }
    }

    /**
     * Resets the verification state to allow a new lookup.
     */
    fun reset() {
        _toyIdInput.value = ""
        _verificationResult.value = null
        _inputError.value = null
    }

    /**
     * Returns the verified toy if the result is a success, otherwise null.
     */
    fun getVerifiedToy(): Toy? = _verificationResult.value?.getOrNull()
}

