package com.kt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kt.base.BasePagerAdapter
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
        mFragments.add(HomeFragment())
        mFragments.add(HomeFragment())
        mFragments.add(HomeFragment())

        val pagerAdapter = BasePagerAdapter(supportFragmentManager,mFragments)
        viewPage.adapter = pagerAdapter
        //tab和ViewPager进行关联
        tabLayout.setViewPager(viewPage, mTitles)
    }
}

class HomeFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home,null)
        return view
    }
}
