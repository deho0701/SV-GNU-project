package com.example.sv_project1.fargment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sv_project1.adater.HistoryRecyclerAdapter
import com.example.sv_project1.R
import com.example.sv_project1.data.ListData
import com.example.sv_project1.decorator.HorizontalItemDecorator
import com.example.sv_project1.decorator.VerticalItemDecorator
import com.example.sv_project1.server.*
import kotlinx.android.synthetic.main.fragment_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HistoryFragment : Fragment() {
    var history_num = -1

    lateinit var historyRecyclerAdapter: HistoryRecyclerAdapter
    val datas = mutableListOf<ListData>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(activity).inflate(R.layout.fragment_history,container,false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        history_recyclerView.layoutManager = LinearLayoutManager(activity)
        initRecycler(requireContext(), "test", "test")
        val id = 1
        historyNum(id)

        history_recyclerView.addItemDecoration(VerticalItemDecorator(20))
        history_recyclerView.addItemDecoration(HorizontalItemDecorator(10))

    }

    private fun initRecycler(context: Context, name: String, date: String) {
        historyRecyclerAdapter = HistoryRecyclerAdapter(context)
        history_recyclerView.adapter = historyRecyclerAdapter

        datas.apply {
            add(ListData(icon = R.drawable.coffee_icon, name = name, content = date))

            historyRecyclerAdapter.datas = datas
            historyRecyclerAdapter.notifyDataSetChanged()
        }

    }

    private fun historyNum(id: Int) {
        val callGetNum = RetrofitClass.api.getHistoryNum(id)

        callGetNum.enqueue(object : Callback<HistoryNumData> {
            override fun onResponse(call: Call<HistoryNumData>, response: Response<HistoryNumData>) {
                if (response.isSuccessful) { // <--> response.code == 200

                    Log.d("Server table response", response.body().toString())
                    history_num = response.body()!!.history_num
                    Log.d("Server history num", history_num.toString())
                    getHistoties(id, history_num)

                } else { // code == 400
                    // 실패 처리
                    Log.d("Server fail", "code: 400, history num")
                }
            }

            override fun onFailure(call: Call<HistoryNumData>, t: Throwable) {
                Log.d("Server fail", t.toString())
                Log.d("Server fail code", "code: 500")
            }

        })
    }

    private fun getHistoties(id: Int, history_num: Int) {

        for (history_id in 1..history_num){
            val callGetStudent = RetrofitClass.api.getHistories(id, history_id)

            callGetStudent.enqueue(object : Callback<BookData> {
                override fun onResponse(
                    call: Call<BookData>,
                    response: Response<BookData>
                ) {
                    if (response.isSuccessful) { // <--> response.code == 200

                        Log.d("Server call", call.request().toString())

                        val name = response.body()!!.cafe_name
                        val date = response.body()!!.time

                        Log.d("Server success", response.body().toString())

                        datas.apply {
                            add(ListData(icon = R.drawable.coffee_icon, name = name, content = date))

                            historyRecyclerAdapter.datas = datas
                            historyRecyclerAdapter.notifyDataSetChanged()
                        }


                    } else { // code == 400
                        // 실패 처리
                        Log.d("Server fail", "code: 400")
                    }
                }

                override fun onFailure(call: Call<BookData>, t: Throwable) {
                    Log.d("Server fail", t.toString())
                    Log.d("Server fail code", "code: 500")
                }
            })
        }
    }
}