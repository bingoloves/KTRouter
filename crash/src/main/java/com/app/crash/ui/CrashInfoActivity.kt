package com.app.crash.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.crash.R
import com.app.crash.db.DbManager
import kotlinx.android.synthetic.main.activity_crash_info.*

class CrashInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash_info)

        val intExtra = intent.getIntExtra("id", 0)
        val queryById = DbManager.getInstance(this).queryById(intExtra)
        tvDetail.text = queryById[0]["detail"]
    }
}