package com.example.sv_project1

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.io.Serializable

class LoginActivity : AppCompatActivity() {
    val TAG: String = "LoginupActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // 로그인 버튼
        btn_login.setOnClickListener {

            //editText로부터 입력된 값을 받아온다
            var id = et_id.text.toString()
            var pw = et_pw.text.toString()

            // 쉐어드로부터 저장된 id, pw 가져오기 -> nodejs 서버로 변경
            val sharedPreference = getSharedPreferences("file name", Context.MODE_PRIVATE)
            val savedId = sharedPreference.getString("id", "")
            val savedPw = sharedPreference.getString("pw", "")

            // 유저가 입력한 id, pw값과 쉐어드로 불러온 id, pw값 비교
            if(id == savedId && pw == savedPw){
                // 로그인 성공 다이얼로그 보여주기
                dialog("success")
                Intent(this@LoginActivity, MainActivity::class.java).apply {
                    putExtra("selected data", id)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { startActivity(this) }
            }
            else{
                // 로그인 실패 다이얼로그 보여주기
                dialog("fail")
            }
        }

        // 회원가입 버튼
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
                    DialogInterface.BUTTON_POSITIVE ->
                        Log.d(TAG, "")
                }
            }
        }

        dialog.setPositiveButton("확인",dialog_listener)
        dialog.show()
    }
}