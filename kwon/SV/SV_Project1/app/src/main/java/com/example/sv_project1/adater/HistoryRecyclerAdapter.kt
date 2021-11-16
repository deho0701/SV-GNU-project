package com.example.sv_project1.adater


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.sv_project1.MainActivity
import com.example.sv_project1.R
import com.example.sv_project1.SitPageActivity
import com.example.sv_project1.data.ListData
import java.io.Serializable


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

            itemView.setOnClickListener {
                var builder = AlertDialog.Builder(context)
                builder.setTitle("예약 취소")
                builder.setMessage("선택하신 예약을 취소하시겠습니까?")

                var listener = object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        when (which) {
                            DialogInterface.BUTTON_NEGATIVE ->{

                            }
                            DialogInterface.BUTTON_POSITIVE ->{
                                Toast.makeText(context, "예약이 취소되었습니다.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
                builder.setPositiveButton("예", listener)
                builder.setNegativeButton("아니오", listener)

                builder.show()
            }
        }
    }

}