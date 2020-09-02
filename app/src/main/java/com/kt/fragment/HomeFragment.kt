package com.kt.fragment

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.TextView
import com.chaychan.demo.WxPayH5Activity
import com.cqs.ysa.bean.GridBean
import com.kt.R
import com.kt.adapter.listview.CommonAdapter
import com.kt.adapter.listview.ViewHolder
import com.kt.base.BaseLazyFragment
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList

/**
 * Created by bingo on 2020/9/2 0002.
 */
class HomeFragment : BaseLazyFragment(){
    var title = null as String?
    var list = ArrayList<GridBean>()
    companion object{
        fun getInstance(title:String): Fragment {
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
        //title = arguments?.get("key") as String?
        //Log.e("TAG"+title,"onInit:" + title)
        initGridList()
        val adapter = object : CommonAdapter<GridBean>(context,R.layout.layout_grid_item,list){
            override fun convert(viewHolder: ViewHolder?, item: GridBean?, position: Int) {
                val titleTv = viewHolder!!.getView<TextView>(R.id.tv_title)
                titleTv.text = item!!.name
                val drawable = resources.getDrawable(item.resId)
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                titleTv.setCompoundDrawables(null, drawable, null, null)
            }
        }
        gridView.adapter = adapter
        gridView.setOnItemClickListener { _, _, position, _ ->
            val className = list[position].className
            if (className != null) {
                val activity = ComponentName(context?.packageName, className)
                val intent = Intent()
                intent.component = activity
                startActivity(intent)
            } else {
                toast("功能正在开发中...")
            }
        }
    }

    private fun initGridList() {
        list.add(GridBean(R.drawable.ic_assessment_black,"wechatPay", WxPayH5Activity::class.java.canonicalName))
    }

    override fun onViewEvent() {
        super.onViewEvent()
        Log.e("TAG","onViewEvent")
    }
    override fun onVisibleToUser(visible: Boolean) {
        Log.e("TAG","onVisibleToUser = "+ visible)
    }
}