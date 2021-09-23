package com.example.sv_project1.adater


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sv_project1.R
import com.example.sv_project1.data.ListData




class HistoryRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<HistoryRecyclerAdapter.HistoryRecyclerViewHolder>() {


    var datas = mutableListOf<ListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryRecyclerViewHolder { // 생성
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cafe_list, parent, false)
        return HistoryRecyclerViewHolder(view as ViewGroup)
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: HistoryRecyclerViewHolder, position: Int) { // 생성한 뷰에 어떤 데이터를 넣을 것인가
        holder.bind(datas[position])
    }

    inner class HistoryRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val icon: ImageView = itemView.findViewById(R.id.iv_cafe)
        private val name: TextView = itemView.findViewById(R.id.tv_cafe_name)
        private val content: TextView = itemView.findViewById(R.id.tv_cafe_content)

        fun bind(item: ListData) {
            icon.setImageResource(item.icon)
            name.text = item.name
            content.text = item.content
        }
    }

}