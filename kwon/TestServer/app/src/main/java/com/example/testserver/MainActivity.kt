package com.example.testserver

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var tableNum = 0
    var id = 1
    var dataMap = mutableMapOf(0 to listOf(0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tableNum()

        while (id <= tableNum){
            retrofitTest()
            id++
        }


        for (i in dataMap) {
            Log.d("Server data", i.toString())
        }
    }


    private fun tableNum() {
        val callGetNum = RetrofitClass.api.getTableNum(id)

        callGetNum.enqueue(object : Callback<TableNumData> {
            override fun onResponse(call: Call<TableNumData>, response: Response<TableNumData>) {
                if(response.isSuccessful) { // <--> response.code == 200

                    Log.d("Server table response", response.body().toString())
                    tableNum = response.body()!!.table_num

                    //Toast.makeText(this@MainActivity, "${response.body()!!.users.size}", Toast.LENGTH_SHORT).show()
                } else { // code == 400
                    // 실패 처리
                    Log.d("Server fail", "code: 400")
                }
            }

            override fun onFailure(call: Call<TableNumData>, t: Throwable) {
                Log.d("Server fail", t.toString())
                Log.d("Server fail code", "code: 500")
            }
        })
    }

    private fun retrofitTest() {
        val callGetStudent = RetrofitClass.api.getUser(id)

        callGetStudent.enqueue(object : Callback<TestServerData> {
            override fun onResponse(call: Call<TestServerData>, response: Response<TestServerData>) {
                if(response.isSuccessful) { // <--> response.code == 200

                    Log.d("Server call", call.request().toString())

                    Log.d("Server success", "${response.raw()}")
                    val id = response.body()!!.ID
                    val num = response.body()!!.table_num
                    val x = response.body()!!.x
                    val y = response.body()!!.y

                    if (id == 1) {
                        dataMap = mutableMapOf(id to listOf(num, x, y))
                    }
                    else {
                        dataMap[id] = listOf(num, x, y)
                        Log.d("Server success", response.body().toString())
                    }

                    //Toast.makeText(this@MainActivity, "${response.body()!!.users.size}", Toast.LENGTH_SHORT).show()
                } else { // code == 400
                    // 실패 처리
                    Log.d("Server fail", "code: 400")
                }
            }

            override fun onFailure(call: Call<TestServerData>, t: Throwable) {
                Log.d("Server fail", t.toString())
                Log.d("Server fail code", "code: 500")
            }
        })
    }
}

