package com.example.svproject // package 이름에 _ 삭제

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.svproject.data.ListData
import com.example.svproject.data.SitData
import com.example.svproject.data.SitSelectData
import com.example.svproject.server.CafeData
import com.example.svproject.server.RetrofitClass
import com.example.svproject.server.TableNumData
import kotlinx.android.synthetic.main.activity_sit_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.typeOf

class SitPageActivity : AppCompatActivity() {

    private var tableNum = 0
    private var dataMap = mutableMapOf(0 to listOf(0f))

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sit_page)

        val data = intent.getSerializableExtra("list data") as ListData
        val sitView = findViewById<ConstraintLayout>(R.id.sitLayout)
        iv_profile.setImageResource(data.icon)
        tv_profile_name.text = data.name
        val selectSits = ArrayList<Int>()


        // 도면 불러오기
        val imageUrl = getString(R.string.blueprint)
        Log.d("imageUrl", imageUrl)
        Glide.with(this).load(imageUrl).into(iv_blueprint)

        //time set
        val current : Long = System.currentTimeMillis()
        val dayFormatter = SimpleDateFormat("yyyy-MM-dd |")
        val timeFormatter = SimpleDateFormat("HHmm")

        btn_date.text = dayFormatter.format(current)
        btn_time.text = timeFormatter.format(current)

        val sitData = SitSelectData(data.userId, data.icon, data.name, selectSits, 0, 0, 0, 0, 0) // id, 가게 이름, 자리, 시간

        var dateStr = dateToStr(sitData)
        var timeStr = timeToStr(sitData)
        Log.d("Server date", "$dateStr / $timeStr")

        //sit button set
        val btnList = ArrayList<Button>() // 변수는 camelCase
        val chkList = ArrayList<Boolean>()
        val btnDataList = ArrayList<SitData>() // 필요없나?

        btn_date.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                sitData.year = year
                sitData.month = month
                sitData.day = dayOfMonth
                dateStr = dateToStr(sitData)
                timeStr = timeToStr(sitData)
                btn_date.text = "$dateStr | "
                Log.d("Server date", "$dateStr / $timeStr")
                tableNum(data.name, sitView, btnList, btnDataList, chkList, dateStr, timeStr)
            }
            DatePickerDialog(this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).apply {
                datePicker.minDate = System.currentTimeMillis()
            }.show()
        }

        btn_time.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                sitData.hour = hourOfDay
                sitData.minute = minute
                dateStr = dateToStr(sitData)
                timeStr = timeToStr(sitData)
                btn_time.text = timeStr
                Log.d("Server date", "$dateStr / $timeStr")
                tableNum(data.name, sitView, btnList, btnDataList, chkList, dateStr, timeStr)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }

        tableNum(data.name, sitView, btnList, btnDataList, chkList, dateStr, timeStr)

        completeBtn.setOnClickListener {
            selectSits.clear()
            if (sitData.year == 0 || sitData.hour == 0) {
                Toast.makeText(this,"날짜와 시간을 선택해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                for (i in 0 until chkList.size) if (chkList[i]) selectSits.add(i+1)
                if (selectSits.isEmpty()) {
                    Toast.makeText(this,"자리를 선택해 주세요", Toast.LENGTH_SHORT).show()
                }
                else {
                    sitData.sit = selectSits
                    Log.d("chk", chkList.toString())
                    intentToPay(sitData)
                }
            }
        }
    }

    private fun addDataList(dataList: ArrayList<SitData>, x: Float, y: Float, size: Int) {
        val data = SitData(x, y, size)
        dataList.add(data)
    }

    private fun createButton(
        sitView: ConstraintLayout, // 100자 이상 줄바꿈
        buttons: ArrayList<Button>,
        id: Int,
        x: Float,
        y: Float,
        size: Int,
        check_list: ArrayList<Boolean>,
        booked:Boolean
    ){
        buttons.add(Button(this))
        sitView.removeView(buttons[id-1])
        sitView.addView(buttons[id-1])
        buttons[id-1].width = size
        buttons[id-1].height = size
        buttons[id-1].x = x
        buttons[id-1].y = y
        buttons[id-1].layoutParams = ConstraintLayout.LayoutParams(size, size)
        //buttons[id-1].text = id.toString()
        check_list.add(false)
        if (booked) {
            buttons[id-1].background = getDrawable(R.drawable.seat_icon)
            buttons[id-1].setOnTouchListener { _, event -> //view 사용하지 않음 -> _
                when (event?.action) {
                    MotionEvent.ACTION_UP -> if (!check_list[id-1]) {
                        buttons[id-1].background = getDrawable(R.drawable.seat_icon_selected)
                        check_list[id-1] = true
                    } else if (check_list[id-1]) {
                        buttons[id-1].background = getDrawable(R.drawable.seat_icon)
                        check_list[id-1] = false
                    }
                }
                false
            }
        }
        else {
            buttons[id-1].background = getDrawable(R.drawable.seat_icon_false)
            buttons[id-1].setOnTouchListener { _, event -> //view 사용하지 않음 -> _
                when (event?.action) {
                    MotionEvent.ACTION_UP -> Toast.makeText(this, "이미 예약된 자리입니다.", Toast.LENGTH_SHORT).show()
                }
                false

            }
        }
    }

    private fun intentToPay(selectData: SitSelectData) { // showAlter 에서 더 직관적인 이름으로 (결제 페이지로 이동)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("선택된 자리로 예약을 진행 하시겠습니까?")
            .setPositiveButton("확인") { _: DialogInterface, _: Int -> // dialogInterface, i 사용하지 않음 -> _
                Intent(this@SitPageActivity, PaymentPageActivity::class.java).apply {
                    putExtra("selected data", selectData as Serializable)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { startActivity(this) }
            }
            .setNegativeButton("취소") { _: DialogInterface, _: Int -> } // dialogInterface, i 사용하지 않음 -> _
            .show()

        (alertDialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    }

    private fun tableNum(
        cafeName: String, // 변수는 camelCase
        sitView: ConstraintLayout,
        btnList: ArrayList<Button>,
        sitDataList: ArrayList<SitData>,
        chkList: ArrayList<Boolean>,
        date: String,
        time: String,
    ) {
        sitDataList.clear()
        val callGetNum = RetrofitClass.api.getTableNum(cafeName)

        callGetNum.enqueue(object : Callback<TableNumData> {
            override fun onResponse(call: Call<TableNumData>, response: Response<TableNumData>) {
                if (response.isSuccessful) { // <--> response.code == 200
                    Log.d("Server Call", call.request().toString())
                    Log.d("Server table response", response.body().toString())
                    tableNum = response.body()!!.tableNum
                    Log.d("Server table num", tableNum.toString())
                    getTableToServer(cafeName,
                        tableNum,
                        sitView,
                        btnList,
                        sitDataList,
                        chkList,
                        date,
                        time,
                        0) /** if 뒤에 줄바꿈 하지 않음*/
                } else { // 실패 처리
                    Log.d("Server fail", "code: 400, table num")
                }
            }

            override fun onFailure(call: Call<TableNumData>, t: Throwable) {
                Log.d("Server fail", t.toString())
                Log.d("Server fail code", "code: 500")
            }

        })
    }

    private fun getTableToServer(
        cafeName: String, // 자리 불러오기, 예약 데이터 요청
        tableNum: Int,
        sitView: ConstraintLayout,
        btnList: ArrayList<Button>,
        btnDataList: ArrayList<SitData>,
        chkList: ArrayList<Boolean>,
        date: String,
        time: String,
        idx: Int
    ) {
        if (idx <= tableNum-1) {
            val callGetStudent = RetrofitClass.api.getUser(cafeName, idx, date, time)
            Log.d("Server idx", idx.toString())

            callGetStudent.enqueue(object : Callback<CafeData> {
                override fun onResponse(call: Call<CafeData>,response: Response<CafeData>) {
                    if (response.isSuccessful) {
                        Log.d("Server call", call.request().toString())

                        val id = response.body()!!.tableId // 변수는 camelCase
                        val x = dpToPx(rateCompute(response.body()!!.tableX), "Float") as Float
                        val y = dpToPx(rateCompute(response.body()!!.tableY), "Float") as Float
                        val booked = response.body()!!.booked
                        val size = dpToPx((WEP_BLOCK_SIZE * 3) / 4, "Int") as Int

                        dataMap[id] = listOf(x, y)
                        Log.d("Server success", response.body().toString())
                        Log.d("Mapped table", "$id: "+dataMap[id])

                        addDataList(btnDataList, x, y, size)
                        createButton(sitView, btnList, id, x, y, size, chkList, booked)
                        getTableToServer(cafeName,
                            tableNum,
                            sitView,
                            btnList,
                            btnDataList,
                            chkList,
                            date,
                            time,
                            idx+1)
                    } else { // 실패 처리
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

    private fun rateCompute(value: Float): Float {
        return value * (APP_FRAME_SIZE / WEP_FRAME_SIZE)
    }

    private fun dpToPx(preValue: Any, type: String): Any {
        val density = resources.displayMetrics.density

        if (type == "Float") {
            return (preValue as Float * density).toFloat()
        }
        else if(type == "Int") {
            return (preValue as Int * density).toInt()
        }
        return false
    }

    private fun dateToStr(sitData: SitSelectData): String {
        var month: String = (sitData.month + 1).toString()
        var day: String = sitData.day.toString()
        if (sitData.month + 1 < 10) {
            month = '0' + (sitData.month + 1).toString()
        }
        if (sitData.day < 10) {
            day = '0' + sitData.day.toString()
        }
        return "${sitData.year}-${month}-${day}"
    }

    private fun timeToStr(sitData: SitSelectData): String {
        var hour: String = sitData.hour.toString()
        var minute: String = sitData.minute.toString()
        if (sitData.hour < 10) {
            hour = '0' + sitData.hour.toString()
        }
        if (sitData.minute < 10) {
            minute = '0'+sitData.minute.toString()
        }
        return "${hour}${minute}"
    }

    companion object { // 상수로 선언 (대문자)
        private const val APP_FRAME_SIZE = 420f
        private const val WEP_FRAME_SIZE = 560f
        private const val WEP_BLOCK_SIZE = 80
    }
}
