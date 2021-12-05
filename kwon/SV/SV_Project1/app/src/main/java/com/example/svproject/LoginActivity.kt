package com.example.svproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.svproject.server.BookData
import com.example.svproject.server.LoginData
import com.example.svproject.server.RetrofitClass
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            var id = et_id.text.toString()
            var pw = et_pw.text.toString()
            Log.d("id/pw", id+pw)
            postLogin(id, pw)
        }

        // 회원가입
        btn_signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun postLogin(id: String, pw: String) {
        val loginData = LoginData(id, pw)
        val callPostBook = RetrofitClass.api.postLogin(loginData)

        callPostBook.enqueue(object : Callback<LoginData> {
            override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                if (response.isSuccessful) {
                    Log.d("Login req", call.request().toString())
                    var data = response.body()
                    Log.d("Login post", data.toString())
                    Log.d("Login post", response.headers().toString())
                    Toast.makeText(this@LoginActivity, "${data!!.id} 회원님, 반갑습니다!", Toast.LENGTH_LONG).show()
                    Intent(this@LoginActivity, MainActivity::class.java).apply {
                        putExtra("id", id)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run { startActivity(this) }
                }
                else {
                    Log.d("Login fail", "code: 400, login")
                    Toast.makeText(this@LoginActivity, "아이디와 비밀번호를 다시 확인해 주세요.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginData>, t: Throwable) {
                Log.d("Login fail", t.toString())
                Log.d("Login fail code", "code: 500")
            }
        })
    }
}