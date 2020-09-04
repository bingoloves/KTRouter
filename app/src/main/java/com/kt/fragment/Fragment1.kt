package com.kt.fragment

import android.view.View
import android.widget.Toast
import com.cq.ui.float_window.FloatWindow
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
    var dialog: FloatWindow ?= null
    override fun onInit() {
        btnTest.setOnClickListener {
            //DialogUtils.showRangeDate(this!!.context!!)
            dialog?.show("返回高级查询中的搜索结果")
        }
        //浮动层
        floatMasker.setOnClickListener({ dialog?.openOrCloseMenu() })
        dialog = FloatWindow(context, 1, 500, object : FloatWindow.IOnItemClicked{
            override fun onBackItemClick() {
                toast("返回")
                dialog?.openOrCloseMenu()
            }

            override fun onCloseItemClick() {
                toast("关闭")
                floatMasker.hide()
                dialog?.dismiss()
            }

            override fun onClose() {
                floatMasker.hide()
            }

            override fun onExpand() {
                floatMasker.show()
            }
        })
    }
}