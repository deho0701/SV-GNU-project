package com.example.svproject1

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    val TAG: String = "LoginActivity"
    var id : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 서버 세팅
       /* val url = "117.16.164.14"

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(APIInterface::class.java)*/

        // 로그인 버튼

        btn_login.setOnClickListener {

            //test
            Toast.makeText(this,"로그인 성공", Toast.LENGTH_SHORT).show()

            var intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                putExtra("id", id)
            }
            startActivity(intent)

            /*server.getRequest("ID").enqueue(object: Callback<ResponseDC> {
                override fun onResponse(call: Call<ResponseDC>, response: Response<ResponseDC>) {
                    val ID = response?.body().toString()
                    Log.d("response : ", ID)
                }

                override fun onFailure(call: Call<ResponseDC>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "없는 아이디 입니다.", Toast.LENGTH_SHORT).show()
                }
            })*/
        }

        // 회원가입
        btn_signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    // 로그인 성공/실패 시 다이얼로그를 띄워주는 메소드
    fun dialog(type: String){
        var dialog = AlertDialog.Builder(this)

        if(type.equals("success")){
            dialog.setTitle("로그인 성공")
            dialog.setMessage("로그인 성공!")
        }
        else if(type.equals("fail")){
            dialog.setTitle("로그인 실패")
            dialog.setMessage("아이디와 비밀번호를 확인해주세요")
        }

        var dialog_listener = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> {
                        Log.d(TAG, "")
                        var intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                            putExtra("id", id)
                        }
                        startActivity(intent)
                    }

                }
            }
        }

        dialog.setPositiveButton("확인",dialog_listener)
        dialog.show()
    }
}