package com.kt.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.kt.R
import com.kt.adapter.BasePagerAdapter
import com.kt.fragment.Fragment1
import com.kt.fragment.Fragment2
import com.kt.fragment.Fragment3
import com.kt.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
     var mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("新闻", "娱乐", "头条", "八卦")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        mFragments.add(HomeFragment())
        mFragments.add(Fragment1())
        mFragments.add(Fragment2())
        mFragments.add(Fragment3())

        val pagerAdapter = BasePagerAdapter(supportFragmentManager, mFragments)
        viewPage.adapter = pagerAdapter
        //tab和ViewPager进行关联
        tabLayout.setViewPager(viewPage, mTitles)
    }
}
