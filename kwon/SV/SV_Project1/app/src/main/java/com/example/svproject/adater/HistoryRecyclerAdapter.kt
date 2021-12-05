package com.example.svproject.adater


import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.svproject.R
import com.example.svproject.data.ListData
import com.example.svproject.server.HistoryData
import com.example.svproject.server.RetrofitClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.Activity
import androidx.fragment.app.Fragment


class HistoryRecyclerAdapter(private val context: Context, private val id: String)
    : RecyclerView.Adapter<HistoryRecyclerAdapter.HistoryRecyclerViewHolder>() {
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
                                val contents = item.content.split(" | ")
                                removeHistory(id, item.name, contents[0], contents[1].toInt())
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

    private fun removeHistory(id: String, name: String, date: String, time: Int) {
        val historyData = HistoryData(id, name, date, time)
        val removeHistory = RetrofitClass.api.removeHistories(historyData)

        removeHistory.enqueue(object : Callback<HistoryData> {
            override fun onResponse(
                call: Call<HistoryData>,
                response: Response<HistoryData>,
            ) {
                if (response.isSuccessful) { // <--> response.code == 200
                    Log.d("Server call", call.request().toString())
                    Log.d("Server history del", response.body().toString())


                } else { // code == 400
                    // 실패 처리
                    Log.d("Server fail", "code: 400")
                }
            }

            override fun onFailure(call: Call<HistoryData>, t: Throwable) {
                Log.d("Server fail", t.toString())
                Log.d("Server fail code", "code: 500")
            }
        })
    }

}