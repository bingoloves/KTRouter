package com.kt.fragment

import com.kt.R
import com.kt.base.BaseLazyFragment
import com.kt.utils.DialogUtils
import kotlinx.android.synthetic.main.fragment_1.*

/**
 * Created by Administrator on 2020/9/2 0002.
 */
class Fragment1:BaseLazyFragment(){
    override fun getContentViewLayoutID(): Int {
       return R.layout.fragment_1
    }

    override fun onInit() {
        btnTest.setOnClickListener { DialogUtils.showRangeDate(this!!.context!!) }
    }

}