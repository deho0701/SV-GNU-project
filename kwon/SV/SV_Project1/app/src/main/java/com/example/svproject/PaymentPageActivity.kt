package com.example.svproject


import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.svproject.data.SitSelectData
import com.example.svproject.server.BookData
import com.example.svproject.server.RetrofitClass
import kotlinx.android.synthetic.main.activity_payment_page.*
import kotlinx.android.synthetic.main.activity_sit_page.*
import kotlinx.android.synthetic.main.activity_sit_page.iv_profile
import kotlinx.android.synthetic.main.activity_sit_page.tv_profile_name
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class PaymentPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_page)

        val data = intent.getSerializableExtra("selected data") as SitSelectData
        var peopleStr = ""
        val price = 500

        val id = data.id

        print(data.sit)

        iv_profile.setImageResource(data.icon)
        tv_profile_name.text = data.name

        Log.d("Data time", "${data.year},${data.month},${data.day},${data.hour},${data.minute}")
        val dateStr = dateToStr(data)
        val timeStr = timeToStr(data)
        tv_date.text = "$dateStr | $timeStr"
        for(i in data.sit) {
            peopleStr += "$i "
        }
        tv_selected_sit.text = "%s 번".format(peopleStr)
        tv_price.text = "%d 원".format(price)

        payBtn.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("결제 확인")
            builder.setMessage("선택하진 예약 정보로 %d원 결제하시겠습니까?".format(price))

            var listener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_NEGATIVE ->{

                    }
                    DialogInterface.BUTTON_POSITIVE ->{
                        payCompleteDialog(id)
                        postPay(booker_id = id,
                            cafe_name = data.name,
                            tables = data.sit,
                            date = dateStr,
                            time = timeStr)
                    }
                }
            }
            builder.setPositiveButton("결제", listener)
            builder.setNegativeButton("취소", listener)
            builder.show()
        }
    }

    private fun payCompleteDialog(id: String) {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("결제 완료!")
        builder.setMessage("선택하신 정보로 예약이 완료되었습니다.")

        var listener = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    DialogInterface.BUTTON_POSITIVE ->{
                        val intent = Intent(this@PaymentPageActivity, MainActivity::class.java).apply {
                            putExtra("id", id)
                        }
                        startActivity(intent)
                    }
                }
            }
        }
        builder.setPositiveButton("확인", listener)
        builder.show()
    }

    private fun postPay(booker_id: String, cafe_name: String, tables: ArrayList<Int>, date: String, time: String) {
        val bookData = BookData(booker_id, cafe_name, tables, date, time)
        val callPostBook = RetrofitClass.api.postBookData(bookData)

        callPostBook.enqueue(object : Callback<BookData> {
            override fun onResponse(call: Call<BookData>, response: Response<BookData>) {
                if (response.isSuccessful) {
                    Log.d("Post test", response.body().toString())
                    var data = response.body()
                    Log.d("Server post", data.toString())
                    Log.d("Server post", response.headers().toString())
                }
                else {
                    Log.d("Server fail", "code: 400, post")
                }
            }

            override fun onFailure(call: Call<BookData>, t: Throwable) {
                Log.d("Server fail", t.toString())
                Log.d("Server fail code", "code: 500")
            }
        })
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
            hour = '0' + sitData.month.toString()
        }
        if (sitData.minute < 10) {
            minute = '0' + sitData.minute.toString()
        }
        return "${hour}${minute}"
    }
}

