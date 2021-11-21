package com.example.testserver

import org.json.JSONObject

class json_parsing(json: String) : JSONObject(json) {
    //val type: String? = this.optString("type")
    val data = this.optJSONArray("table")
        ?.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } } // returns an array of JSONObject
        ?.map { Foo(it.toString()) } // transforms each JSONObject of the array into Foo
}

class Foo(json: String) : JSONObject(json) {
    val id = this.optInt("ID")
    val table_num = this.optInt("table_num")
    val x = this.optInt("x")
    val y = this.optInt("y")
}