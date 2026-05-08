package com.example.channapatna_namma_pride.viewmodel

import androidx.lifecycle.ViewModel
import com.example.channapatna_namma_pride.model.CatalogItem
import com.example.channapatna_namma_pride.model.Resource
import com.example.channapatna_namma_pride.repository.CatalogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for the Toy Catalog screen.
 * Manages the catalog list and active category filter.
 */
class CatalogViewModel : ViewModel() {
    private val repository = CatalogRepository()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _items = MutableStateFlow<Resource<List<CatalogItem>>>(Resource.Loading)
    val items: StateFlow<Resource<List<CatalogItem>>> = _items.asStateFlow()

    val categories: List<String> = repository.categories

    init {
        loadItems()
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        loadItems()
    }

    private fun loadItems() {
        _items.value = Resource.Loading
        _items.value = repository.getCatalogItems(_selectedCategory.value)
    }
}

