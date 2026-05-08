package com.example.channapatna_namma_pride.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.channapatna_namma_pride.model.Resource
import com.example.channapatna_namma_pride.model.Workshop
import com.example.channapatna_namma_pride.repository.WorkshopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Meet the Maker / Workshop screen.
 */
class WorkshopViewModel : ViewModel() {
    private val repository = WorkshopRepository()

    private val _workshops = MutableStateFlow<Resource<List<Workshop>>>(Resource.Loading)
    val workshops: StateFlow<Resource<List<Workshop>>> = _workshops.asStateFlow()

    init {
        loadWorkshops()
    }

    private fun loadWorkshops() {
        viewModelScope.launch {
            _workshops.value = repository.getWorkshops()
        }
    }

    fun refresh() {
        _workshops.value = Resource.Loading
        loadWorkshops()
    }
}

