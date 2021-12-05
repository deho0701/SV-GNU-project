package com.example.svproject.fargment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.svproject.adater.HomeRecyclerAdapter
import com.example.svproject.R
import com.example.svproject.data.ListData
import com.example.svproject.decorator.HorizontalItemDecorator
import com.example.svproject.decorator.VerticalItemDecorator
import kotlinx.android.synthetic.main.fragment_home.searchView
import kotlinx.android.synthetic.main.fragment_home.cafe_recyclerView


class HomeFragment: Fragment() {
    lateinit var homeRecyclerAdapter: HomeRecyclerAdapter

    lateinit var cafeList: ArrayList<String>
    lateinit var cafeMap: MutableMap<String, List<String>>
    lateinit var queryCafeMap: MutableMap<String, List<String>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(activity).inflate(R.layout.fragment_home,container,false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val id = requireArguments().getString("id")

        cafe_recyclerView.layoutManager = LinearLayoutManager(activity)

        //cafe list 불러오기
        val assetManager = resources.assets
        val inputStream = assetManager.open("cafe_list.txt")

        inputStream.bufferedReader().readLines().forEach {
            var tmp = it.split(',')

            if (tmp[1] == "name") false

            else if (::cafeMap.isInitialized || ::cafeList.isInitialized) {
                cafeMap[tmp[0]] = listOf(tmp[1], tmp[2])
                cafeList.add(tmp[1])

            }

            else {
                cafeMap = mutableMapOf(tmp[0] to listOf(tmp[1], tmp[2]))
                cafeList = arrayListOf(tmp[1])
            }
            //cafeMap[tmp[0]] = listOf(tmp[1], tmp[2])

        }

        for (cafe in cafeMap) {
            Log.d("cafe_key", cafe.key)
            for (c_val in cafe.value) Log.d("cafe_val", c_val)
        }

        //리사이클러 뷰 생성
        id?.let { initRecycler(requireContext(), cafeMap, it) }

        cafe_recyclerView.addItemDecoration(VerticalItemDecorator(20))
        cafe_recyclerView.addItemDecoration(HorizontalItemDecorator(10))

        //검색 기능
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (::queryCafeMap.isInitialized) queryCafeMap.clear()
                if(newText != "") { // 검색창에 키워드 작성
                    val tmpStr : CharSequence = newText
                    val curList = cafeList.filter { x -> x.contains(tmpStr) }
                    //val curList = cafeList.filter { x -> x.toLowerCase().contains(newText?.toLowerCase().toString()) }

                    for (i in cafeMap) {
                        if (i.value[0] in curList) {
                            if (::queryCafeMap.isInitialized) {
                                queryCafeMap[i.key] = listOf(i.value[0], i.value[1])
                            }

                            else {
                                queryCafeMap = mutableMapOf(i.key to listOf(i.value[0], i.value[1]))
                            }
                        }
                    }
                    id?.let { initRecycler(requireContext(), queryCafeMap, it) }
                }
                else { // 검색창 글을 다 지웠을 때
                    id?.let { initRecycler(requireContext(), cafeMap, it) }
                }
                return true
            }
        })

    }

    private fun initRecycler(context: Context, cafeMap: MutableMap<String, List<String>>, id: String) {
        var datas = mutableListOf<ListData>()
        homeRecyclerAdapter = HomeRecyclerAdapter(context)
        cafe_recyclerView.adapter = homeRecyclerAdapter

        datas.apply {
            for (cafe in cafeMap) {
                val icon = resources.getIdentifier("icon_"+cafe.key, "drawable", context.packageName)
                //Log.d("cafe_icon", icon.toString())
                if (cafe.value[0] != "name" || cafe.value[0] != "query_name" ) {
                    if (icon != 0) {
                        add(ListData(userId = id,
                            icon = icon,
                            name = cafe.value[0],
                            content = cafe.value[1]))
                    }
                    // no icon image
                    else {
                        add(ListData(userId = id,
                            icon = R.drawable.default_cafe_icon,
                            name = cafe.value[0],
                            content = cafe.value[1]))
                    }
                }
            }

            homeRecyclerAdapter.datas = datas
            homeRecyclerAdapter.notifyDataSetChanged()
        }
    }
}

