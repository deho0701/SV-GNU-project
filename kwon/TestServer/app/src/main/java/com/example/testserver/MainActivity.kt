package com.example.testserver


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.testserver.R.id.btn_get
import com.example.testserver.R.id.btn_post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tableNum = 0
    private var dataMap = mutableMapOf(0 to listOf(0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getBtn = findViewById<Button>(btn_get)
        val postBtn = findViewById<Button>(btn_post)

        getBtn.setOnClickListener {
            tableNum()
            Toast.makeText(this, "get", Toast.LENGTH_SHORT)
        }

        postBtn.setOnClickListener {
            postTest()
        }
    }

    private fun tableNum() {
        val callGetNum = RetrofitClass.api.getTableNum()

        callGetNum.enqueue(object : Callback<TableNumData> {
            override fun onResponse(call: Call<TableNumData>, response: Response<TableNumData>) {
                if (response.isSuccessful) { // <--> response.code == 200

                    Log.d("Server table response", response.body().toString())
                    tableNum = response.body()!!.table_num
                    Log.d("Server table num", tableNum.toString())
                    retrofitTest(tableNum)
                } else { // code == 400
                    // 실패 처리
                    Log.d("Server fail", "code: 400, table num")
                }
            }

            override fun onFailure(call: Call<TableNumData>, t: Throwable) {
                Log.d("Server fail", t.toString())
                Log.d("Server fail code", "code: 500")
            }
        })
    }

    private fun retrofitTest(tableNum : Int) {

        for (table_id in 1..tableNum+1){
            val callGetStudent = RetrofitClass.api.getUser(table_id)

            callGetStudent.enqueue(object : Callback<TestServerData> {
                override fun onResponse(
                    call: Call<TestServerData>,
                    response: Response<TestServerData>
                ) {
                    if (response.isSuccessful) { // <--> response.code == 200

                        Log.d("Server call", call.request().toString())

                        val id = response.body()!!.table_id
                        val x = response.body()!!.table_x
                        val y = response.body()!!.table_y

                        dataMap[id] = listOf(x, y)
                        Log.d("Server success", response.body().toString())


                        Log.d("Mapped table", "$id: "+dataMap[id])

                        findViewById<TextView>(R.id.tv_test).text = dataMap[0].toString()

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

    private fun postTest() {


        val bookData = BookData(
            booker_id = 0,
            booker = "정대호",
            cafe_name = "cafe",
            peopleNum = 3,
            table_num = 2,
            time = "2021-11-22"
        )
        val callPostBook = RetrofitClass.api.postBookData(bookData)

        callPostBook.enqueue(object : Callback<BookData> {

            override fun onResponse(call: Call<BookData>, response: Response<BookData>) {
                if (response.isSuccessful) {
                    Log.d("Post test", response.body().toString())
                    var data = response.body()
                }
                else {
                    Log.d("Server fail", "code: 400, table num")
                }
            }

            override fun onFailure(call: Call<BookData>, t: Throwable) {
                Log.d("Server fail", t.toString())
                Log.d("Server fail code", "code: 500")
            }
        })
    }
}
