package com.example.sv_project1.navigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sv_project1.adater.CafeRecyclerAdapter
import com.example.sv_project1.R
import com.example.sv_project1.data.ListData
import com.example.sv_project1.decorator.HorizontalItemDecorator
import com.example.sv_project1.decorator.VerticalItemDecorator
import kotlinx.android.synthetic.main.fragment_home.searchView
import kotlinx.android.synthetic.main.fragment_home.cafe_recyclerView


class HomeFragment: Fragment() {
    lateinit var cafeRecyclerAdapter: CafeRecyclerAdapter
    val datas = mutableListOf<ListData>()
    var MyList = arrayOf("StarBucks", "Ediya", "BanbanSprings")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(activity).inflate(R.layout.fragment_home,container,false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        cafe_recyclerView.layoutManager = LinearLayoutManager(activity)
        initRecycler(requireContext())

        cafe_recyclerView.addItemDecoration(VerticalItemDecorator(20))
        cafe_recyclerView.addItemDecoration(HorizontalItemDecorator(10))

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                println(query + "search")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != "") {
                    val curList = MyList.filter { x -> x.toLowerCase().contains(newText?.toLowerCase().toString()) }
                }
                return true
            }
        })

    }

    private fun initRecycler(context: Context) {
        cafeRecyclerAdapter = CafeRecyclerAdapter(context)
        cafe_recyclerView.adapter = cafeRecyclerAdapter

        datas.apply {
            add(ListData(icon = R.drawable.coffee_icon, name = "test1", content = "test content1"))
            add(ListData(icon = R.drawable.coffee_icon, name = "test2", content = "test content2"))
            add(ListData(icon = R.drawable.coffee_icon, name = "test3", content = "test content3"))
            add(ListData(icon = R.drawable.coffee_icon, name = "test4", content = "test content4"))
            add(ListData(icon = R.drawable.coffee_icon, name = "test5", content = "test content5"))
            add(ListData(icon = R.drawable.coffee_icon, name = "test6", content = "test content6"))

            cafeRecyclerAdapter.datas = datas
            cafeRecyclerAdapter.notifyDataSetChanged()
        }

    }
}

