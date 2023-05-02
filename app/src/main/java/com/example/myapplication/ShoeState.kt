package com.example.myapplication

data class ShoeState(
    val shoes: List<Shoe> = emptyList(),
    val shoeName: String = "",
    val shoeType: String = "",
    val imageID: String = "",
    val isAddingShoe: Boolean = false,
    val sortType: SortType = SortType.SHOE_NAME
)
