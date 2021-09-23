package com.example.sv_project1.data

import java.io.Serializable

class SitSelectData(
    val icon: Int,
    val name: String,
    var sit: ArrayList<Int>,
    var year: Int,
    var month: Int,
    var day: Int,
    var hour: Int,
    var minute: Int,
): Serializable

class SitCompleteData(
    val icon: Int,
    val name: String,
    var year: Int,
    var month: Int,
    var day: Int,
    var hour: Int,
    var minute: Int,
    val str: String,
    val price: Int
): Serializable