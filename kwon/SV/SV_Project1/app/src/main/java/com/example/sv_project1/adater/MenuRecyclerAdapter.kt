package com.example.sv_project1.adater


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sv_project1.R
import com.example.sv_project1.data.MenuData



class MenuRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<MenuRecyclerAdapter.MenuRecyclerViewHolder>() {

    var datas = mutableListOf<MenuData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuRecyclerViewHolder { // 생성
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_list, parent, false)
        return MenuRecyclerViewHolder(view as ViewGroup)
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: MenuRecyclerViewHolder, position: Int) { // 생성한 뷰에 어떤 데이터를 넣을 것인가
        holder.bind(datas[position])
    }

    inner class MenuRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val icon: ImageView = itemView.findViewById(R.id.iv_menu)
        private val name: TextView = itemView.findViewById(R.id.tv_menu_name)
        private val content: TextView = itemView.findViewById(R.id.tv_menu_content)

        fun bind(item: MenuData) {
            icon.setImageResource(item.icon)
            name.text = item.name
            content.text = item.content
        }
    }

}