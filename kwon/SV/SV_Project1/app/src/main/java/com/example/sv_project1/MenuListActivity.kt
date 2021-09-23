package com.example.sv_project1

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.sv_project1.adater.MenuRecyclerAdapter
import com.example.sv_project1.data.ListData
import com.example.sv_project1.data.MenuData
import com.example.sv_project1.decorator.HorizontalItemDecorator
import com.example.sv_project1.decorator.VerticalItemDecorator
import kotlinx.android.synthetic.main.activity_menu_list.*
import kotlinx.android.synthetic.main.fragment_history.*


class MenuListActivity : AppCompatActivity() {
    lateinit var menuRecyclerAdapter: MenuRecyclerAdapter

    val datas = mutableListOf<MenuData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_list)


        val data = intent.getSerializableExtra("list data") as ListData

        iv_profile.setImageResource(data.icon)
        tv_profile_name.text = data.name
        tv_profile_content.text = data.content


        menu_recyclerView.layoutManager = LinearLayoutManager(this)
        initRecycler()

        // 리스트 간격
        menu_recyclerView.addItemDecoration(VerticalItemDecorator(20))
        menu_recyclerView.addItemDecoration(HorizontalItemDecorator(10))


    }

    private fun initRecycler() {
        menuRecyclerAdapter = MenuRecyclerAdapter(this)
        menu_recyclerView.adapter = menuRecyclerAdapter

        datas.apply {
            add(MenuData(icon = R.drawable.coffee_icon, name = "menu1", content = "menu content1"))
            add(MenuData(icon = R.drawable.coffee_icon, name = "menu2", content = "menu content2"))
            add(MenuData(icon = R.drawable.coffee_icon, name = "menu3", content = "menu content3"))
            add(MenuData(icon = R.drawable.coffee_icon, name = "menu4", content = "menu content4"))
            add(MenuData(icon = R.drawable.coffee_icon, name = "menu5", content = "menu content5"))
            add(MenuData(icon = R.drawable.coffee_icon, name = "menu6", content = "menu content6"))

            menuRecyclerAdapter.datas = datas
            menuRecyclerAdapter.notifyDataSetChanged()
        }


    }

}