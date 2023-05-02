package com.example.myapplication

sealed interface ShoeEvent {
    object SaveShoe: ShoeEvent
    data class SetShoeName(val shoeName: String): ShoeEvent
    data class SetShoeType(val shoeType: String): ShoeEvent
    data class SetShoeImageID(val imageID: String): ShoeEvent
    object ShowDialog: ShoeEvent
    object HideDialog: ShoeEvent
    data class SortShoes(val sortType: SortType): ShoeEvent
    data class DeleteShoes(val shoe: Shoe): ShoeEvent
}