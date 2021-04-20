package com.example.intentslearning

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
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
            if (editText_registration_name.text.toString() == ""){ // really ugly code coming :(
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
                editText_registration_name.setHintTextColor(android.graphics.Color.RED)
                editText_registration_name.requestFocus()
            } else if(editText_registration_newUsername.text.toString() == ""){
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
                editText_registration_newUsername.setHintTextColor(android.graphics.Color.RED)
                editText_registration_newUsername.requestFocus()
            } else if(editText_registration_password.text.toString() == ""){
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                editText_registration_password.setHintTextColor(android.graphics.Color.RED)
                editText_registration_password.requestFocus()
            } else if(editText_registration_confirmPassword.text.toString() != editText_registration_password.text.toString()){
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                editText_registration_confirmPassword.setHintTextColor(android.graphics.Color.RED)
                editText_registration_confirmPassword.setText("")
                editText_registration_confirmPassword.requestFocus()
            } else if(editText_registration_email.text.toString() == ""){
                Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show()
                editText_registration_email.setHintTextColor(android.graphics.Color.RED)
                editText_registration_email.requestFocus()
            } else { // Start "mailing" back all the answers :D
                registerUser()
            }

        editText_registration_name.setOnClickListener { editText_registration_name.setHintTextColor(
            android.graphics.Color.GRAY
        ) } // These don't exactly work as expected, have to type twice in order to change color
        editText_registration_newUsername.setOnClickListener { editText_registration_newUsername.setHintTextColor(
            android.graphics.Color.GRAY
        ) } // update, these work. Just make sure to "setfocus" first and clicking on them changes
        editText_registration_password.setOnClickListener { editText_registration_password.setHintTextColor(
            android.graphics.Color.GRAY
        ) }
        editText_registration_confirmPassword.setOnClickListener { editText_registration_confirmPassword.setHintTextColor(
            android.graphics.Color.GRAY
        ) }
        editText_registration_email.setOnClickListener { editText_registration_email.setHintTextColor(
            android.graphics.Color.GRAY
        ) }

        button_registration_cancelRegistration.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
            }
    }

    private fun registerUser(){
        val user = BackendlessUser()
        user.setProperty("email", editText_registration_email.text.toString())
        user.setProperty("name", editText_registration_name.text.toString())
        user.setProperty("username", editText_registration_newUsername.text.toString())
        user.password = editText_registration_password.text.toString()

        Backendless.UserService.register(user, object : AsyncCallback<BackendlessUser?> {
            override fun handleResponse(registeredUser: BackendlessUser?) {
                // user has been registered and now can login
                val loginIntent = Intent().apply{
                    putExtra("newusername", editText_registration_newUsername.text.toString())
                    putExtra("newpassword", editText_registration_password.text.toString())
                    // Note to future developer self: Make sure that the variable agrees with whatever is trying to get data from. Otherwise it's like trying to find ice cubes in the dishwasher. Doesn't make sense.
                    // Thank you for that note past self - future developer self
                }
                setResult(RESULT_OK, loginIntent)
                finish()
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Toast.makeText(this@RegistrationActivity, fault.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}