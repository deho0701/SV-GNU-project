package com.example.sv_project1

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sv_project1.adater.MenuData
import com.example.sv_project1.adater.MenuRecyclerAdapter
import com.example.sv_project1.decorator.HorizontalItemDecorator
import com.example.sv_project1.decorator.VerticalItemDecorator
import kotlinx.android.synthetic.main.activity_cafe_list.*


class MenuListActivity : AppCompatActivity() {
    lateinit var menuRecyclerAdapter: MenuRecyclerAdapter

    var MyList = arrayOf("StarBucks", "Ediya", "BanbanSprings")

    val datas = mutableListOf<MenuData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe_list)

        initRecycler(this)

        // 리스트 간격
        cafe_recyclerView.addItemDecoration(VerticalItemDecorator(20))
        cafe_recyclerView.addItemDecoration(HorizontalItemDecorator(10))
    }

    private fun initRecycler(context: Context) {
        menuRecyclerAdapter = MenuRecyclerAdapter(this)
        cafe_recyclerView.adapter = menuRecyclerAdapter

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