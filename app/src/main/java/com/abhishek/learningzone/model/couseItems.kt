package com.abhishek.learningzone.model

data  class couseItems (
    val filename :String,
val downloadUri :String
)

data  class DatabaseCourse (
    val id : String,
    val filename :String,
    val downloadUri :String
){
    constructor():this("","",""){

    }
}