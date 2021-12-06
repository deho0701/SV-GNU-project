package com.example.svproject.data

import java.io.Serializable

class SitSelectData(
    val id: String,
    val icon: Int,
    val name: String,
    var sit: ArrayList<Int>,
    var year: Int,
    var month: Int,
    var day: Int,
    var hour: Int,
    var minute: Int,
): Serializable