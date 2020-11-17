package com.example.intentslearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME)
        val password = intent.getStringExtra(LoginActivity.EXTRA_PASSWORD)

        editText_registration_newUsername.setText(username)
        editText_registration_password.setText(password)

        button_registration_register.setOnClickListener {// Checks if all the fields are filled in and correct
            if (editText_registration_name.text.toString() == ""){
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
            } else if(editText_registration_newUsername.text.toString() == ""){
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
            } else if(editText_registration_password.text.toString() == ""){
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
            } else if(editText_registration_confirmPassword.text.toString() != editText_registration_password.text.toString()){
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else if(editText_registration_email.text.toString() == ""){
                Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show()
            } else { // Start "mailing" back all the answers :D
                val loginIntent = Intent(this, LoginActivity::class.java).apply{
                    putExtra("newusername", editText_registration_newUsername.text.toString())
                    putExtra("newpassword", editText_registration_password.text.toString())
                    // Note to future developer self: Make sure that the variable agrees with whatever is trying to get data from. Otherwise it's like trying to find ice cubes in the dishwasher. Doesn't make sense.
                }
                setResult(RESULT_OK, loginIntent)
                finish()
            }
        }

        button_registration_cancelRegistration.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}