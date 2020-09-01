package com.kt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.kt.base.BaseLazyFragment
import com.kt.adapter.BasePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {
     var mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("新闻", "娱乐", "头条", "八卦")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        mFragments.add(HomeFragment.getInstance(mTitles[0]))
        mFragments.add(HomeFragment.getInstance(mTitles[1]))
        mFragments.add(HomeFragment.getInstance(mTitles[2]))
        mFragments.add(HomeFragment.getInstance(mTitles[3]))

        val pagerAdapter = BasePagerAdapter(supportFragmentManager, mFragments)
        viewPage.adapter = pagerAdapter
        //tab和ViewPager进行关联
        tabLayout.setViewPager(viewPage, mTitles)
    }
}

class HomeFragment : BaseLazyFragment(){
    var title = null as String?
    companion object{
        fun getInstance(title:String):Fragment{
            val fragment = HomeFragment()
            val bundle = Bundle()
            bundle.putString("key",title)
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun getContentViewLayoutID(): Int {
        return R.layout.fragment_home
    }

    override fun onInit() {
        title = arguments?.get("key") as String?
        homeTv.text = title
        Log.e("TAG"+title,"onInit:" + title)
    }

    override fun onReLoad() {
        super.onReLoad()
        Log.e("TAG"+title,"onReLoad")
    }
    override fun onVisibleToUser(visible: Boolean) {
        Log.e("TAG"+title,"onVisibleToUser = "+ visible)
        if (visible)onReLoad()
    }
}
