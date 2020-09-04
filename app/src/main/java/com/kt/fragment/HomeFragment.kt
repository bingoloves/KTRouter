package com.kt.fragment

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import com.chaychan.demo.WxPayH5Activity
import com.cq.ui.layout_manager.PagerConfig
import com.cq.ui.layout_manager.PagerGridLayoutManager
import com.cq.ui.layout_manager.PagerGridSnapHelper
import com.cqs.ysa.bean.GridBean
import com.kt.R
import com.kt.activity.PlayerActivity
import com.kt.adapter.recyclerview.CommonAdapter
import com.kt.adapter.recyclerview.MultiItemTypeAdapter
import com.kt.adapter.recyclerview.base.ViewHolder
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
        initGridList()
        val layoutManager = PagerGridLayoutManager(2, 4, PagerGridLayoutManager.HORIZONTAL)
        val adapter = object : CommonAdapter<GridBean>(context,R.layout.layout_grid_item,list){
            override fun convert(viewHolder: ViewHolder?, item: GridBean?, position: Int) {
                val titleTv = viewHolder!!.getView<TextView>(R.id.tv_title)
                titleTv.text = item!!.name
                val drawable = resources.getDrawable(item.resId)
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                titleTv.setCompoundDrawables(null, drawable, null, null)
            }
        }
        // 设置滚动辅助工具
        val pageSnapHelper = PagerGridSnapHelper()
        pageSnapHelper.attachToRecyclerView(gridRv)
        // 如果需要查看调试日志可以设置为true，一般情况忽略即可
        PagerConfig.setShowLog(true)
        layoutManager.setPageListener(object :PagerGridLayoutManager.PageListener{
            override fun onPageSizeChanged(pageSize: Int) {

            }

            override fun onPageSelect(pageIndex: Int) {

            }
        })
        gridRv.layoutManager = layoutManager
        gridRv.adapter = adapter
        adapter.setOnItemClickListener(object :MultiItemTypeAdapter.OnItemClickListener{
            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
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

            override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                return false
            }

        })
    }

    private fun initGridList() {
        list.add(GridBean(R.drawable.ic_assessment_black,"wechatPay", WxPayH5Activity::class.java.canonicalName))
        list.add(GridBean(R.drawable.ic_assessment_black,"player", PlayerActivity::class.java.canonicalName))
        list.add(GridBean(R.drawable.ic_assessment_black,"player", PlayerActivity::class.java.canonicalName))
        list.add(GridBean(R.drawable.ic_assessment_black,"player", PlayerActivity::class.java.canonicalName))
        list.add(GridBean(R.drawable.ic_assessment_black,"player", PlayerActivity::class.java.canonicalName))
        list.add(GridBean(R.drawable.ic_assessment_black,"player", PlayerActivity::class.java.canonicalName))
        list.add(GridBean(R.drawable.ic_assessment_black,"player", PlayerActivity::class.java.canonicalName))
        list.add(GridBean(R.drawable.ic_assessment_black,"player", PlayerActivity::class.java.canonicalName))
        list.add(GridBean(R.drawable.ic_assessment_black,"player", PlayerActivity::class.java.canonicalName))
        list.add(GridBean(R.drawable.ic_assessment_black,"player", PlayerActivity::class.java.canonicalName))
    }

    override fun onViewEvent() {
        super.onViewEvent()
        Log.e("TAG","onViewEvent")
    }
    override fun onVisibleToUser(visible: Boolean) {
        Log.e("TAG","onVisibleToUser = "+ visible)
    }
}