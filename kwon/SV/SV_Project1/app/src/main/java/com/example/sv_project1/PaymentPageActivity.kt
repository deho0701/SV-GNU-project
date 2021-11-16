package com.example.sv_project1

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sv_project1.data.ListData
import com.example.sv_project1.data.SitCompleteData
import com.example.sv_project1.data.SitData
import com.example.sv_project1.data.SitSelectData
import kotlinx.android.synthetic.main.activity_payment_page.*
import kotlinx.android.synthetic.main.activity_sit_page.*
import kotlinx.android.synthetic.main.activity_sit_page.completeBtn
import kotlinx.android.synthetic.main.activity_sit_page.iv_profile
import kotlinx.android.synthetic.main.activity_sit_page.tv_profile_name
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PaymentPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_page)

        val data = intent.getSerializableExtra("selected data") as SitSelectData
        var peopleStr = ""
        val price = 3000

        val id = 1

        print(data.sit)

        iv_profile.setImageResource(data.icon)
        tv_profile_name.text = data.name
        tv_date.text = "%d | %d | %d | %d : %d".format(data.year, data.month, data.day, data.hour, data.minute)
        for(i in data.sit) {
            if(i != data.sit.size-1) peopleStr += "%d, ".format(i)
            else peopleStr += i
        }
        tv_selected_sit.text = "%s 번 (총 %d인)".format(peopleStr, data.sit.size*2)
        tv_price.text = "%d 원".format(price)

        val sitCompleteData = SitCompleteData(data.icon, data.name, data.year, data.month, data.day, data.hour, data.minute, peopleStr, price)

        payBtn.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("결제 확인")
            builder.setMessage("선택하진 예약 정보로 %d원 결제하시겠습니까?".format(price))

            var listener = object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when (which) {
                        DialogInterface.BUTTON_NEGATIVE ->{

                        }
                        DialogInterface.BUTTON_POSITIVE ->{
                            pay_complete_dialog(id)
                        }
                    }
                }
            }
            builder.setPositiveButton("결제", listener)
            builder.setNegativeButton("취소", listener)

            builder.show()
        }
    }

    private fun pay_complete_dialog(id: Int) {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("결제 완료!")
        builder.setMessage("선택하신 정보로 예약이 완료되었습니다.")

        var listener = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    DialogInterface.BUTTON_NEUTRAL ->{
                        val intent = Intent(this@PaymentPageActivity, MainActivity::class.java).apply {
                            putExtra("id", id)
                        }
                        startActivity(intent)
                    }
                }
            }
        }
        builder.setNeutralButton("확인", listener)

        builder.show()
    }
}

