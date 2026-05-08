package com.example.channapatna_namma_pride.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.channapatna_namma_pride.model.Artisan
import com.example.channapatna_namma_pride.model.Resource
import com.example.channapatna_namma_pride.repository.ArtisanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Artisan Profile screen.
 */
class ArtisanViewModel : ViewModel() {
    private val repository = ArtisanRepository()

    private val _artisanState = MutableStateFlow<Resource<Artisan>>(Resource.Loading)
    val artisanState: StateFlow<Resource<Artisan>> = _artisanState.asStateFlow()

    fun loadArtisan(artisanId: String) {
        _artisanState.value = Resource.Loading
        viewModelScope.launch {
            _artisanState.value = repository.getArtisanById(artisanId)
        }
    }
}
