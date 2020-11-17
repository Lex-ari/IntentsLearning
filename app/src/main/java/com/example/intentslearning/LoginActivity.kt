package com.example.intentslearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        val EXTRA_USERNAME = "username"
        val EXTRA_PASSWORD = "password"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_login_signUp.setOnClickListener {
            val username = editText_login_username.text.toString()
            val password = editText_login_password.text.toString()
            val registrationIntent = Intent(this, RegistrationActivity::class.java).apply{
                putExtra(EXTRA_USERNAME, username)
                putExtra(EXTRA_PASSWORD, password)
            }
            //startActivity(registrationIntent) // SEEEEEEEEEEENNNNNNND IIIIIIIIIIIITT
            startActivityForResult(registrationIntent, 1)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                editText_login_username.setText(data?.getStringExtra("newusername"))
                editText_login_password.setText(data?.getStringExtra("newpassword"))
            }
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Registration Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}