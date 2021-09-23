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
        val price : Int = 3000

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
            Intent(this@PaymentPageActivity, CompletePageActivity::class.java).apply {
                putExtra("completed data", sitCompleteData as Serializable)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.run { startActivity(this) }
        }
    }
}

