package com.kt.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Administrator on 2020/8/31 0031.
 */
abstract class BaseLazyFragment : Fragment() {
    /** 布局.xml文件 */
    protected abstract fun getContentViewLayoutID(): Int
    /** 首次加载完成 */
    protected abstract fun onInit()
    /** 是否对用户可见 */
    protected abstract fun onVisibleToUser(visible:Boolean)
    /** 预加载方法 */
    open fun onPrepared(){}
    /** 重新加载方法 */
    open fun onReLoad(){}

    /**是否初始化完成*/
    private var isInit: Boolean = false
    /**是否首次加载*/
    private var isFirstLoad: Boolean = true
    /**是否预加载*/
    private var isPrepared: Boolean = false
    /**根布局View*/
    open var rootView: View ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = if (getContentViewLayoutID() != 0) {
            inflater.inflate(getContentViewLayoutID(), container, false)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
        isInit = true
        return rootView
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lazyLoad()
    }
    /**
     * 设置是否预加载
     */
    protected fun setPreLoad(preLoad:Boolean){
        this.isPrepared = preLoad
    }

    @Synchronized
    private fun lazyLoad() {
        if (isInit && isFirstLoad){
            isFirstLoad = false
            onInit()
        }
        if (isInit && isPrepared){
            onPrepared()
        }
    }

    /**
     * viewPager方式的添加fragment
     * 可以重写此方法，是否对用户可见
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isInit){
            onVisibleToUser(isVisibleToUser)
        }
    }
}