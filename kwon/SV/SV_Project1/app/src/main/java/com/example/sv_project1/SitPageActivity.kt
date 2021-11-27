package com.example.sv_project1

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sv_project1.data.ListData
import com.example.sv_project1.data.SitData
import com.example.sv_project1.data.SitSelectData
import com.example.sv_project1.server.RetrofitClass
import com.example.sv_project1.server.CafeData
import com.example.sv_project1.server.TableNumData
import kotlinx.android.synthetic.main.activity_sit_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SitPageActivity : AppCompatActivity() {

    private var tableNum = 0
    private var dataMap = mutableMapOf(0 to listOf(0f))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sit_page)

        val data = intent.getSerializableExtra("list data") as ListData

        val sitView = findViewById<ConstraintLayout>(R.id.sitLayout)
        iv_profile.setImageResource(data.icon)
        tv_profile_name.text = data.name

        var selectSits = ArrayList<Int>()

        //time set
        val current : Long = System.currentTimeMillis()
        val dayFormatter = SimpleDateFormat("yyyy | MM | dd |")
        val timeFormatter = SimpleDateFormat("HH : mm")

        btn_date.text = dayFormatter.format(current)
        btn_time.text = timeFormatter.format(current)


        var sitData = SitSelectData(data.icon, data.name, selectSits, 0, 0, 0, 0, 0)

        btn_date.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                btn_date.text = "${year} | ${month+1} | ${dayOfMonth} |"
                sitData.year = year
                sitData.month = month
                sitData.day = dayOfMonth
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        btn_time.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                btn_time.text = "$hourOfDay : $minute"
                sitData.hour = hourOfDay
                sitData.minute = minute
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }

        //sit button set
        val btn_list = ArrayList<Button>()
        val chk_list = ArrayList<Boolean>()
        val btn_data_list = ArrayList<SitData>()

        // server access
        tableNum(data.name, sitView, btn_list, btn_data_list, chk_list)

        //addDataList(btn_data_list, 100f, 100f, 100)
        //addDataList(btn_data_list, 200f, 100f, 100)
        //addDataList(btn_data_list, 100f, 200f, 100)

        //createButtons(sitView, btn_list, btn_data_list, chk_list)




        completeBtn.setOnClickListener {
            selectSits.clear() // 자리 중복
            for (i in 0 until chk_list.size) if (chk_list[i]) selectSits.add(i+1)
            sitData.sit = selectSits
            Log.d("chk", chk_list.toString())
            showAlert(sitData)
        }
    }



    private fun addDataList(dataList: ArrayList<SitData>, x: Float, y: Float, size: Int) {
        var data = SitData(x, y, size)
        dataList.add(data)
    }


    private fun createButton(sitView: ConstraintLayout, buttons: ArrayList<Button>, x: Float, y: Float, size: Int, check_list: ArrayList<Boolean>){
        buttons.add(Button(this))
        val index = buttons.size - 1
        sitView.addView(buttons[index])
        buttons[index].width = size
        buttons[index].height = size
        buttons[index].x = x
        buttons[index].y = y
        buttons[index].layoutParams = ConstraintLayout.LayoutParams(size, size)
        buttons[index].text = index.toString()
        check_list.add(false)

        buttons[index].setBackgroundColor(Color.parseColor("#e1eef6"))
        buttons[index].setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_UP -> if (!check_list[index]) {
                    buttons[index].setBackgroundColor(Color.parseColor("#004e66"))
                    check_list[index] = true
                } else if (check_list[index]) {
                    buttons[index].setBackgroundColor(Color.parseColor("#e1eef6"))
                    check_list[index] = false
                }
            }
            false
        }
    }

    private fun createButtons(sitView: ConstraintLayout, buttons: ArrayList<Button>, sitDatas: ArrayList<SitData>, checks: ArrayList<Boolean>) {
        for(i in sitDatas) {
            createButton(sitView, buttons, i.x, i.y, i.size, checks)
            Log.d("x, y", i.x.toString() + i.y.toString())
        }
    }

    private fun showAlert(selectData: SitSelectData) {

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("선택된 자리로 예약을 진행 하시겠습니까?")
            .setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                Intent(this@SitPageActivity, PaymentPageActivity::class.java).apply {
                    putExtra("selected data", selectData as Serializable)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { startActivity(this) }
            }
            .setNegativeButton("취소") { dialogInterface: DialogInterface, i: Int -> }
            .show()


        (alertDialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)

    }

    private fun tableNum(cafe_name: String,
                         sitView: ConstraintLayout,
                         btn_list: ArrayList<Button>,
                         btn_data_list: ArrayList<SitData>,
                         chk_list: ArrayList<Boolean>) {
        val callGetNum = RetrofitClass.api.getTableNum(cafe_name)

        callGetNum.enqueue(object : Callback<TableNumData> {
            override fun onResponse(call: Call<TableNumData>, response: Response<TableNumData>) {
                if (response.isSuccessful) { // <--> response.code == 200

                    Log.d("Server table response", response.body().toString())
                    tableNum = response.body()!!.table_num
                    Log.d("Server table num", tableNum.toString())
                    getTableToServer(cafe_name, tableNum, sitView, btn_list, btn_data_list, chk_list)

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

    private fun getTableToServer(cafe_name: String,
                                 tableNum: Int,
                                 sitView: ConstraintLayout,
                                 btn_list: ArrayList<Button>,
                                 btn_data_list: ArrayList<SitData>,
                                 chk_list: ArrayList<Boolean>) {

        for (table_id in 1..tableNum){
            val callGetStudent = RetrofitClass.api.getUser(cafe_name, table_id)

            callGetStudent.enqueue(object : Callback<CafeData> {
                override fun onResponse(
                    call: Call<CafeData>,
                    response: Response<CafeData>
                ) {
                    if (response.isSuccessful) { // <--> response.code == 200

                        Log.d("Server call", call.request().toString())

                        val id = response.body()!!.table_id
                        val x = response.body()!!.table_x
                        val y = response.body()!!.table_y

                        dataMap[id] = listOf(x, y)
                        Log.d("Server success", response.body().toString())

                        Log.d("Mapped table", "$id: "+dataMap[id])

                        addDataList(btn_data_list, x, y, 100)

                        createButton(sitView, btn_list, x, y, 100, chk_list)


                    } else { // code == 400
                        // 실패 처리
                        Log.d("Server fail", "code: 400")
                    }
                }

                override fun onFailure(call: Call<CafeData>, t: Throwable) {
                    Log.d("Server fail", t.toString())
                    Log.d("Server fail code", "code: 500")
                }
            })
        }
    }
}
