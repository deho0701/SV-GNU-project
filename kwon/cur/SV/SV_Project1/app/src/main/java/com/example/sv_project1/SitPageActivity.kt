package com.example.sv_project1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginEnd
import com.example.sv_project1.data.ListData
import com.example.sv_project1.data.RectPosition
import kotlinx.android.synthetic.main.activity_sit_page.*
import kotlinx.android.synthetic.main.activity_sit_page.iv_profile
import kotlinx.android.synthetic.main.activity_sit_page.tv_profile_content
import kotlinx.android.synthetic.main.activity_sit_page.tv_profile_name

class SitPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sit_page)



        val data = intent.getSerializableExtra("list data") as ListData

        /*val sitButton = Button(this)
        val layoutParams = ConstraintLayout.LayoutParams(
            changeToDP(60),
            changeToDP(60)
        )

        setButton(sitButton)

        sitLayout.addView(sitButton)*/





        iv_profile.setImageResource(data.icon)
        tv_profile_name.text = data.name
        tv_profile_content.text = data.content

        val rectPosition = hashMapOf(200f to 300f, 300f to 300f, 400f to 300f, 700f to 400f, 700f to 500f)
        val cirPosition = hashMapOf(300f to 700f, 500f to 700f)
        val itemSize = 100f

        sitLayout.addView(CustomView(this, rectPosition, cirPosition, itemSize))
    }

    private fun changeToPX(dp : Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()
    }

    private fun changeToDP(value : Int) : Int{
        var displayMetrics: DisplayMetrics = resources.displayMetrics
        var dp = Math.round(value * displayMetrics.density)
        return dp
    }

    private fun setButton(button: Button) {
        button?.apply {
            width = changeToPX(60)
            height = changeToPX(60)
            setBackgroundColor(Color.parseColor("#e1eef6"))

            setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    var toggle = 0
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> {
                            setBackgroundColor(Color.parseColor("#004e66"))
                        }
                        MotionEvent.ACTION_UP -> {
                            setBackgroundColor(Color.parseColor("#004e66"))
                        }
                    }
                    return false
                }
            })
        }
    }

    private fun setSit(sitLayout: ConstraintLayout.LayoutParams, x: Float, y: Float) :  ConstraintLayout.LayoutParams{
        sitLayout.bottomToBottom=R.id.sitLayout
        sitLayout.bottomToTop=R.id.sitLayout
        sitLayout.endToEnd=R.id.sitLayout
        sitLayout.startToStart=R.id.sitLayout
        sitLayout.horizontalBias=x
        sitLayout.verticalBias=y

        return sitLayout
    }
}




class CustomView(context: Context, rectPosition: HashMap<Float, Float>, cirPosition: HashMap<Float, Float>, itemSize: Float) : View(context) {
    private val rp = rectPosition
    private val cp = cirPosition
    private val itemSize = itemSize

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        paint.color = Color.GRAY
        canvas?.run {
            for ((x, y) in rp) {
                val comPos = computePosition(x, y, itemSize)
                customDrawRect(canvas, comPos.left, comPos.top, comPos.right, comPos.bot, paint)
            }

            for ((x, y) in cp) {
                customDrawCircle(canvas, x, y, itemSize, paint)
            }
        }
    }
    private fun customDrawCircle(canvas:Canvas, cx: Float, cy: Float, radius: Float, paint: Paint) {
        canvas.drawCircle(cx, cy, radius, paint)
    }
    private fun customDrawRect(canvas:Canvas, left: Float, top: Float, right: Float, bottom: Float, paint: Paint) {
        val rect = RectF(left, top, right, bottom)
        canvas.drawRect(rect, paint)
    }

    private fun computePosition(x: Float, y: Float, rectSize: Float) : RectPosition {
        val left = x-rectSize/2
        val top = y+rectSize/2
        val right = left+rectSize
        val bot = top-rectSize

        return RectPosition(left, top, right, bot)
    }
}

