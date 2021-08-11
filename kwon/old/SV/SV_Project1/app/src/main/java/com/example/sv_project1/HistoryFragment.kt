package com.example.sv_project1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_cafe_list.*
import kotlinx.android.synthetic.main.activity_cafe_list.cafe_recyclerView
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history,container,false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        history_recyclerView.layoutManager = LinearLayoutManager(activity)
        history_recyclerView.adapter = CustomAdapter(dataList)
    }
}