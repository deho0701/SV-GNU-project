package com.example.sv_project1.navigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sv_project1.adater.HistoryRecyclerAdapter
import com.example.sv_project1.R
import com.example.sv_project1.data.ListData
import com.example.sv_project1.decorator.HorizontalItemDecorator
import com.example.sv_project1.decorator.VerticalItemDecorator
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_home.*


class HistoryFragment : Fragment() {

    lateinit var historyRecyclerAdapter: HistoryRecyclerAdapter
    val datas = mutableListOf<ListData>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(activity).inflate(R.layout.fragment_history,container,false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        history_recyclerView.layoutManager = LinearLayoutManager(activity)
        initRecycler(requireContext())

        history_recyclerView.addItemDecoration(VerticalItemDecorator(20))
        history_recyclerView.addItemDecoration(HorizontalItemDecorator(10))

    }

    private fun initRecycler(context: Context) {
        historyRecyclerAdapter = HistoryRecyclerAdapter(context)
        history_recyclerView.adapter = historyRecyclerAdapter

        datas.apply {
            add(ListData(icon = R.drawable.coffee_icon, name = "history1", content = "history content1"))
            add(ListData(icon = R.drawable.coffee_icon, name = "history2", content = "history content2"))
            add(ListData(icon = R.drawable.coffee_icon, name = "history3", content = "history content3"))
            add(ListData(icon = R.drawable.coffee_icon, name = "history4", content = "history content4"))
            add(ListData(icon = R.drawable.coffee_icon, name = "history5", content = "history content5"))
            add(ListData(icon = R.drawable.coffee_icon, name = "history6", content = "history content6"))

            historyRecyclerAdapter.datas = datas
            historyRecyclerAdapter.notifyDataSetChanged()
        }

    }
}