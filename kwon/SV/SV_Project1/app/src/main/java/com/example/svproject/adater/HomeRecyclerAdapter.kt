package com.example.svproject.adater


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.svproject.R
import com.example.svproject.SitPageActivity
import com.example.svproject.data.ListData
import java.io.Serializable

class HomeRecyclerAdapter(private val context: Context)
    : RecyclerView.Adapter<HomeRecyclerAdapter.CafeRecyclerViewHolder>() {

    var datas = mutableListOf<ListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeRecyclerViewHolder { // 생성
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cafe_list, parent, false)
        return CafeRecyclerViewHolder(view as ViewGroup)
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: CafeRecyclerViewHolder, position: Int) { // 생성한 뷰에 어떤 데이터를 넣을 것인가
        holder.bind(datas[position])
    }

    inner class CafeRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val icon: ImageView = itemView.findViewById(R.id.iv_cafe)
        private val name: TextView = itemView.findViewById(R.id.tv_cafe_name)
        private val content: TextView = itemView.findViewById(R.id.tv_cafe_content)

        fun bind(item: ListData) {
            icon.setImageResource(item.icon)
            name.text = item.name
            content.text = item.content

            // sit page로 이동
            itemView.setOnClickListener {
                Intent(context, SitPageActivity::class.java).apply {
                    putExtra("list data", item as Serializable)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }
    }
}