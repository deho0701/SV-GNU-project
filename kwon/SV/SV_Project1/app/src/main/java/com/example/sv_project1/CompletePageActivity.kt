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

class CompletePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_page)

        val data = intent.getSerializableExtra("completed data") as SitCompleteData

        iv_profile.setImageResource(data.icon)
        tv_profile_name.text = data.name
        tv_date.text = "%d | %d | %d | %d : %d".format(data.year, data.month, data.day, data.hour, data.minute)
        tv_selected_sit.text = data.str
        tv_price.text = data.price.toString()
    }
}

