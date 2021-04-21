package com.example.intentslearning

// providing default values for each meets the requirement of having a public, no-argument constructer because now this object can be made without arguments:
// val grade = Grade() works.
data class Grade(var assignment : String = "Assignment",
                 var enjoyedAssignment : Boolean = true,
                 var letterGradeValue : Int = 4,
                 var percentage : Double = 1.0,
                 var studentName : String = "Student",
                 var subject : String = "Default Subject",
                 var objectId : String? = null,
                 var ownerId : String? = null){

}
