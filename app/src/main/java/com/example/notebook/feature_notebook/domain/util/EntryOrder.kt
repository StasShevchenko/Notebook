package com.example.notebook.feature_notebook.domain.util


sealed class EntryOrder(val orderType: OrderType){
    class Alphabet(orderType: OrderType): EntryOrder(orderType)
    class Date(orderType: OrderType): EntryOrder(orderType)

    fun copy(orderType: OrderType): EntryOrder{
        return when (this) {
            is Alphabet -> Alphabet(orderType)
            is Date -> Date(orderType)
        }
    }
}

