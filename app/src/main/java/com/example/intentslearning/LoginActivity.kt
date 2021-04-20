package com.example.intentslearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        val EXTRA_USERNAME = "username"
        val EXTRA_PASSWORD = "password"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val TAG = "LoginActivity"

        // initialize backendless
        Backendless.initApp(this, Constants.APP_ID, Constants.API_KEY)

        // make the backendless call to login when they click the login button
        button_login_login.setOnClickListener{
            // extra the username and password from the edittexts
            val username = editText_login_username.text.toString()
            val password = editText_login_password.text.toString()

            // any place in the documentation you see new AsyncCallBack<Blah>
            // Kotlin version is object : AsyncCallBack<Blah> {}
            Backendless.UserService.login(username, password, object : AsyncCallback<BackendlessUser> {
                override fun handleResponse(response: BackendlessUser?) {
                    Toast.makeText(this@LoginActivity, "${response?.userId} has logged in.", Toast.LENGTH_SHORT).show()
                    //TODO Bring the user to a new activity that's the "home" activity
                    // GradeListActivity

                    val gradeListIntent = Intent(this@LoginActivity, GradeListActivity::class.java)
                    startActivity(gradeListIntent)
                    // to close the login screen so it's not there when they click back
                    finish()
                }
                override fun handleFault(fault: BackendlessFault?) {
                    Toast.makeText(this@LoginActivity, "Somethign went wrong. Check the logs.", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "handleFault: " + fault?.message)
                }
            })


        }

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