package com.example.sv_project1

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.sv_project1.data.ListData
import com.example.sv_project1.decorator.HorizontalItemDecorator
import com.example.sv_project1.decorator.VerticalItemDecorator
import com.example.sv_project1.navigation.HistoryFragment
import com.example.sv_project1.navigation.HomeFragment
import com.example.sv_project1.navigation.MypageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.activity_main.cafe_recyclerView
//import kotlinx.android.synthetic.main.activity_main.searchView


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    //lateinit var cafeRecyclerAdapter: CafeRecyclerAdapter
    //var MyList = arrayOf("StarBucks", "Ediya", "BanbanSprings")
    //val datas = mutableListOf<ListData>()


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home ->{
                supportFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment()).commit()
                return true
            }
            R.id.history -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frame, HistoryFragment()).commit()
                return true
            }
            R.id.myPage -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_frame, MypageFragment()).commit()
                return true
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initRecycler(this)

        bottomNavigationView.setOnNavigationItemSelectedListener(this)


        /*// 리스트 간격
        cafe_recyclerView.addItemDecoration(VerticalItemDecorator(20))
        cafe_recyclerView.addItemDecoration(HorizontalItemDecorator(10))


        //search
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

        })*/
    }

    /*private fun initRecycler(context: Context) {
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

    }*/
}

