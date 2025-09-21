package com.example.app_grupo7.perfume.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.app_grupo7.perfume.store.PerfumeEntity
import com.example.app_grupo7.perfume.store.PerfumeStoreSqlite
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PerfumeCrudViewModel(
    private val store: PerfumeStoreSqlite
) : ViewModel() {

    val perfumes: StateFlow<List<PerfumeEntity>> =
        store.items.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun create(nombre: String, precio: Int, uri: String?) {
        viewModelScope.launch { store.insert(nombre, precio, uri) }
    }

    fun update(id: Long, nombre: String, precio: Int, uri: String?) {
        viewModelScope.launch { store.update(id, nombre, precio, uri) }
    }

    fun delete(id: Long) {
        viewModelScope.launch { store.delete(id) }
    }
}

class PerfumeCrudVMFactory(
    private val store: PerfumeStoreSqlite
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return PerfumeCrudViewModel(store) as T
    }
}
