package net.toan.circleciexample

import android.util.Log

class TestObject(var programName: String) {
    fun printProgramName() {
        Log.e("TestObject", "programName: $programName")
    }
}