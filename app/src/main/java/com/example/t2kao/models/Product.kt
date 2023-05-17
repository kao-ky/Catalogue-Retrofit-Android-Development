package com.example.t2kao.models

import java.io.Serializable

data class Product(val id: Int,
                   val title: String,
                   val description: String,
                   val price: Double,
                   val brand: String,
                   val rating: Double,
                   val thumbnail: String): Serializable