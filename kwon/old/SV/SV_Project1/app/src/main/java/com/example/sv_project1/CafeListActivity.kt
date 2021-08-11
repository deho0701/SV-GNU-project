package com.example.sv_project1

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_cafe_list.*



class CafeListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var MyList = arrayOf("StarBucks", "Ediya", "BanbanSprings")


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
        setContentView(R.layout.activity_cafe_list)


        cafe_recyclerView.layoutManager = LinearLayoutManager(this)
        cafe_recyclerView.adapter = CustomAdapter(dataList)


        //search
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener,
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

        //drawer menu
        if (intent.hasExtra("open")){
            var drawerID = intent.getIntExtra("open", 0)
            if (drawerID == 1){
                cafe_menu_drawer.openDrawer(GravityCompat.END)
                menuNaviView.setNavigationItemSelectedListener(this)
            }

        }else Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show()


        btn_list_back.setOnClickListener {
            finish()
        }

    }





    //menu navi
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        //listener
        when(item.itemId){
            R.id.menu1 -> println("menu1")
            R.id.menu2 -> println("menu2")
            R.id.menu3 -> println("menu3")
        }
        cafe_menu_drawer.closeDrawers()
        return false
    }

    override fun onBackPressed() {
        if(cafe_menu_drawer.isDrawerOpen(GravityCompat.END)) cafe_menu_drawer.closeDrawers()
        else super.onBackPressed()
    }

}