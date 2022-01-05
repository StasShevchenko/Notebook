package com.example.notebook.feature_notebook.domain.util

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
