package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ShoeViewModel (
    private val dao: ShoeDao
        ): ViewModel() {

            private val _sortType = MutableStateFlow(SortType.SHOE_NAME)
            private val _shoes = _sortType
                .flatMapLatest {sortType ->
                    when(sortType) {
                        SortType.SHOE_NAME -> dao.getShoesOrderedByShoeName()
                        SortType.SHOE_TYPE -> dao.getShoesOrderedByShoeType()
                        SortType.IMAGE_ID -> dao.getShoesOrderedByShoeImageID()
                    }
                }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(ShoeState())
    val state = combine(_state,_sortType,_shoes) {state, sortType, shoes ->
        state.copy(
            shoes = shoes,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ShoeState())

    fun onEvent( event: ShoeEvent){
        when(event){
            is ShoeEvent.DeleteShoes -> {
                viewModelScope.launch {
                    dao.deleteShoe(event.shoe)
                }
            }
            ShoeEvent.HideDialog -> {
                _state.update {it.copy(
                    isAddingShoe = false
                ) }
            }
            ShoeEvent.SaveShoe -> {
                val shoeName = state.value.shoeName
                val shoeType = state.value.shoeType
                val imageID = state.value.imageID

                if(shoeName.isBlank() || shoeType.isBlank() || imageID.isBlank()) {
                    return
                }
                val shoe = Shoe(
                    shoeName = shoeName,
                    shoeType = shoeType,
                    imageID = imageID
                )
                viewModelScope.launch {
                    dao.upsertShoe(shoe)
                }
                _state.update { it.copy(
                    isAddingShoe = false,
                    shoeName = "",
                    shoeType = "",
                    imageID = ""
                ) }
            }
            is ShoeEvent.SetShoeName -> {
                _state.update { it.copy(
                    shoeName = event.shoeName
                ) }
            }
            is ShoeEvent.SetShoeType -> {
                _state.update { it.copy(
                    shoeType = event.shoeType
                ) }
            }
            is ShoeEvent.SetShoeImageID -> {
                _state.update { it.copy(
                    imageID = event.imageID
                ) }
            }
            ShoeEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingShoe = true
                ) }
            }
            is ShoeEvent.SortShoes -> {
                _sortType.value = event.sortType
            }

        }
    }
}