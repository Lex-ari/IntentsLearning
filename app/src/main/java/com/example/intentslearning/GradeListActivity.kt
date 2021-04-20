package com.example.intentslearning

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault


class GradeListActivity : AppCompatActivity() {
    companion object {
        val TAG = "GradeListActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade_list)


        //TODO: Make a whole recyclerview layout and stuff for the grades

        //For now, log a list of all the grades.

        Backendless.Data.of(Grade::class.java).find(object : AsyncCallback<List<Grade?>?> {
            override fun handleResponse(foundContacts: List<Grade?>?) {
                // all Grade instances have been found
                Log.d(TAG, "handleResponse: ${foundContacts.toString()}")
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d(TAG, "handleFault: " + fault.message)
            }
        })
    }
}