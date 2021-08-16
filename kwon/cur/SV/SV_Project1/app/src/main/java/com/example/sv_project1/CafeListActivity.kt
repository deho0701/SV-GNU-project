package com.example.sv_project1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sv_project1.adater.CafeRecyclerAdapter
import com.example.sv_project1.data.ListData
import com.example.sv_project1.decorator.HorizontalItemDecorator
import com.example.sv_project1.decorator.VerticalItemDecorator
import kotlinx.android.synthetic.main.activity_cafe_list.*
import java.io.Serializable


class CafeListActivity : AppCompatActivity() {
    lateinit var cafeRecyclerAdapter: CafeRecyclerAdapter

    var MyList = arrayOf("StarBucks", "Ediya", "BanbanSprings")

    val datas = mutableListOf<ListData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe_list)

        initRecycler(this)

        // 리스트 간격
        cafe_recyclerView.addItemDecoration(VerticalItemDecorator(20))
        cafe_recyclerView.addItemDecoration(HorizontalItemDecorator(10))

        cafeRecyclerAdapter.setOnItemClickListener(object : CafeRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: ListData, pos : Int) {
                Toast.makeText(this@CafeListActivity, pos.toString(), Toast.LENGTH_SHORT).show()
                Intent(this@CafeListActivity, MenuListActivity::class.java).apply {
                    putExtra("list data", data as Serializable)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { startActivity(this) }
            }

        })
    }

    private fun initRecycler(context: Context) {
        cafeRecyclerAdapter = CafeRecyclerAdapter(this)
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