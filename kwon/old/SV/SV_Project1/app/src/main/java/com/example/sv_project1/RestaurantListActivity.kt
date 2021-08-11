package com.example.sv_project1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_restaurant_list.*


class RestaurantListActivity : AppCompatActivity() {

    val dataList = arrayListOf<Data>(
        Data(R.drawable.example, "test", "test1"),
        Data(R.drawable.example, "test", "test2"),
        Data(R.drawable.example, "test", "test3"),
        Data(R.drawable.example, "test", "test4"),
        Data(R.drawable.example, "test", "test5"),
        Data(R.drawable.example, "test", "test6"),
        Data(R.drawable.example, "test", "test7"),
        Data(R.drawable.example, "test", "test8"),
        Data(R.drawable.example, "test", "test9"),
        Data(R.drawable.example, "test", "test10")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_list)


        restaurant_recyclerView.layoutManager = LinearLayoutManager(this)
        restaurant_recyclerView.adapter = CustomAdapter(dataList)


        btn_list_back.setOnClickListener {
            finish()
        }

    }

}