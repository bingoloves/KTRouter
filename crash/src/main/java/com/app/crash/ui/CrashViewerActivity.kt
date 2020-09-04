package com.app.crash.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.app.crash.R
import com.app.crash.adapter.LogAdapter
import com.app.crash.bean.LogBean
import com.app.crash.db.DbManager
import kotlinx.android.synthetic.main.activity_crash_viewer.*


class CrashViewerActivity : AppCompatActivity() {
    lateinit var adapter: LogAdapter
    private val mData = ArrayList<LogBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash_viewer)

        initView()
        initData()
    }

    private fun initView() {
        adapter = LogAdapter(this, mData)
        adapter.setOnDeleteListener {
            DbManager.getInstance(this).deleteById(it)
            initData()
        }
        rvLog.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvLog.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvLog.adapter = adapter
    }

    private fun initData() {
        val query = DbManager.getInstance(this).queryAll()
        mData.clear()
        for (hashMap in query) {
            val item = LogBean()
            item.id = hashMap["id"]!!.toInt()
            item.info = hashMap["profile"]
            item.time = hashMap["time"]!!.toLong()
            mData.add(item)
        }
        adapter.notifyDataSetChanged()
    }
}