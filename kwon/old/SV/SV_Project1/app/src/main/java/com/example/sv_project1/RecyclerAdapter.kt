package com.example.sv_project1


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_cafe_list.view.*
import kotlinx.android.synthetic.main.custom_list.view.*


class Data(val icon:Int, val name:String, val content:String)


class CustomViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.custom_list, parent, false))  {
    val icon = itemView.iv_custom
    val name = itemView.tv_name_custom
    val content = itemView.tv_content_custom
    val btn = itemView.btn_menu
}

class CustomAdapter(val dataList:ArrayList<Data>) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder { // 생성
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_list, parent, false)
        view.btn_menu
        return CustomViewHolder(view as ViewGroup)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) { // 수정

        with(holder) {
            icon.setImageResource(dataList[position].icon)
            name.text = dataList[position].name
            content.text = dataList[position].content
            btn.setOnClickListener(object:View.OnClickListener{

                override fun onClick(v: View?) {
                    val dataIntent = Intent(holder.itemView?.context, CafeListActivity::class.java).apply {
                        putExtra("open", 1)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run { holder.itemView?.context.startActivity(this) }

                }
            })
        }

    }

    override fun getItemCount() = dataList.size

}