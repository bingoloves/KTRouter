package com.kt.adapter

import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

/**
 * Created by Administrator on 2020/8/31 0031.
 */
class BasePagerAdapter : FragmentPagerAdapter {

    private var mFragments = mutableListOf<Fragment>()
    private var fm: FragmentManager


    constructor(fm: FragmentManager, fragments: MutableList<Fragment>) : super(fm) {
        this.fm = fm
        this.mFragments = fragments
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    fun setFragments(container: ViewGroup, @NonNull fragments: MutableList<Fragment>) {
        for (i in fragments.indices) {
            val fragment = findFragment(container.id, i) // 重点就是这里，会根据id去找是否有缓存的Fragment
            if (fragment != null) { // 如果有就替换，不然用户看到的，和你实际使用的会是两个不同的Fragment
                fragments[i] = fragment
            }
        }
        mFragments = fragments
    }

    private fun findFragment(viewId: Int, position: Int): Fragment? {
        val name = makeFragmentName(viewId, getItemId(position))
        return fm.findFragmentByTag(name)
    }

    private fun makeFragmentName(viewId: Int, id: Long): String {
        return "android:switcher:$viewId:$id"
    }
}