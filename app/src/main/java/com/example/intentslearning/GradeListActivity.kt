package com.example.intentslearning

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import kotlinx.android.synthetic.main.activity_grade_list.*


class GradeListActivity : AppCompatActivity() {
    companion object {
        val TAG = "GradeListActivity"
    }

    private lateinit var userId : String
    private var gradeList : List<Grade?>? = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade_list)
        userId = Backendless.UserService.CurrentUser().userId
        button_gradeList_create.setOnClickListener{
           createNewGrade()
        }
        button_gradeList_read.setOnClickListener{
            readAllUserGrades()
        }
        button_gradeList_update.setOnClickListener{
            updateFirstGrade()
        }
        button_gradeList_delete.setOnClickListener{
            deleteFirstGrade()
        }
    }

    private fun updateFirstGrade(){
        readAllUserGrades() // i think this updates
        if (!gradeList.isNullOrEmpty()){
            val gradeToUpdate = gradeList?.get(0)
            gradeToUpdate?.assignment = "updated assignment wow"
            Backendless.Data.of(Grade::class.java).save(gradeToUpdate, object : AsyncCallback<Grade?> {
                override fun handleResponse(response: Grade?) {
                    Toast.makeText(
                        this@GradeListActivity,
                        "Grade Successfully Updated",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun handleFault(fault: BackendlessFault) {
                    // an error has occurred, the error code can be retrieved with fault.getCode()
                    Log.d(TAG, "handleFault: ${fault.message}")
                    Toast.makeText(
                        this@GradeListActivity,
                        "Unable to update 1st assignment in list",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun deleteFirstGrade(){
        readAllUserGrades() // yep this definitely updates
        if(!gradeList.isNullOrEmpty()){
            //Backendless Call
            val gradeToDelete = gradeList?.get(0)
            Backendless.Data.of(Grade::class.java).remove(gradeToDelete, object : AsyncCallback<Long?> {
                override fun handleResponse(response: Long?) {
                    //rip grade
                    Log.d(TAG, "handleResponse: $response")
                    Toast.makeText(this@GradeListActivity, "Successful Deletion", Toast.LENGTH_SHORT).show()
                }

                override fun handleFault(fault: BackendlessFault) {
                    //grade is resisting...
                    Log.d(TAG, "handleFault: $fault.message")
                }
            })
        }
    }

    private fun createNewGrade() {
        Log.d(TAG, "createNewGrade: $userId")
        val grade = Grade(
            assignment = "Chapter 5 MC",
            studentName = "Mr. Shorr",
            enjoyedAssignment = false
        )
        grade.ownerId = userId
        Backendless.Data.of(Grade::class.java).save(grade, object : AsyncCallback<Grade?> {
            override fun handleResponse(response: Grade?) {
                Toast.makeText(
                    this@GradeListActivity,
                    "Grade Successfully Saved",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d(TAG, "handleFault: ${fault.message}")
                Toast.makeText(
                    this@GradeListActivity,
                    "Unable to create new assignment",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun readAllUserGrades(){
        // Advanced data retrieval with where clause that matches the ownerId to current userId
        // ownerId = 'blach'
        val whereClause = "ownerId = '$userId'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause

        Backendless.Data.of(Grade::class.java).find(
            queryBuilder,
            object : AsyncCallback<List<Grade?>?> {
                override fun handleResponse(foundGrades: List<Grade?>?) {
                    gradeList = foundGrades
                    Log.d(TAG, "handleResponse: ${foundGrades.toString()}")
                }

                override fun handleFault(fault: BackendlessFault?) {
                    Log.d(TAG, "handleFault: " + fault?.message)
                }

            })
    //For now, log a list of all the grades.
        // this retrives all of the grades from every user, then filters it so only the current user's is shown. Generally not best approach. Better to do it server side.
        /*Backendless.Data.of(Grade::class.java).find(object : AsyncCallback<List<Grade?>?> {
            override fun handleResponse(foundGrades: List<Grade?>?) {
                // all Grade instances have been found
                // get current user's objectID
                var currentUserId = Backendless.UserService.CurrentUser().userId
               /* val matchingList = mutableListOf<Grade?>()
                if (foundGrades != null){
                    for(grade in foundGrades){
                        if(grade?.ownerId == currentUserId){
                            matchingList.add(grade)
                        }
                    }
                }
                */
                // returns a new list that only has items that match the condition in the braces
                val matchingList = foundGrades?.filter {
                    it?.ownerId == currentUserId
                }
                Log.d(TAG, "handleResponse: ${matchingList.toString()}")
            }
            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d(TAG, "handleFault: " + fault.message)
            }
        })*/
    }
}