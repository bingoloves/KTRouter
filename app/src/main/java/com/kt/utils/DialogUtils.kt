package com.kt.utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.cq.ui.dialog.SuperDialog
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarLayout
import com.haibin.calendarview.CalendarView
import com.kt.R

/**
 * Created by Administrator on 2020/9/2 0002.
 */
object DialogUtils{
    /**
     * 日历弹窗
     * @param context
     */
    fun showCalendar(context: Context) {
        var mYear: Int = 0
        val view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_calendar, null)
        val mCalendarLayout = view.findViewById<CalendarLayout>(R.id.calendarLayout)
        val mCalendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val mTextMonthDay = view.findViewById<TextView>(R.id.tv_month_day)
        val mTextYear =  view.findViewById<TextView>(R.id.tv_year)
        val mTextLunar =  view.findViewById<TextView>(R.id.tv_lunar)
        val mTextSure =  view.findViewById<TextView>(R.id.tv_sure)
        mTextMonthDay.setOnClickListener {
            if (!mCalendarLayout.isExpand) {
                mCalendarLayout.expand()
            } else{
                mCalendarView.showYearSelectLayout(mYear)
                mTextLunar.visibility = View.GONE
                mTextYear.visibility = View.GONE
                mTextMonthDay.text = mYear.toString()
            }
        }

        mCalendarView.setOnViewChangeListener {isMonthView -> Log.e("onViewChange", "  ---  " + if (isMonthView) "月视图" else "周视图") }
        mCalendarView.setOnYearChangeListener { year -> mTextMonthDay.text = year.toString() }
        mCalendarView.setOnCalendarSelectListener(object :CalendarView.OnCalendarSelectListener{
            override fun onCalendarOutOfRange(calendar: Calendar?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
                mTextLunar.visibility = View.VISIBLE
                mTextYear.visibility = View.VISIBLE
                mTextMonthDay.text = calendar?.month.toString() + "月" + calendar?.day + "日"
                mTextYear.text = calendar?.year.toString()
                mTextLunar.text = calendar?.lunar
                mYear = calendar?.year!!
            }
        })
        mCalendarView.setOnMonthChangeListener { year, month ->
            Log.e("onMonthChange", "  -- $year  --  $month")
            val calendar = mCalendarView.selectedCalendar
            mTextLunar.visibility = View.VISIBLE
            mTextYear.visibility = View.VISIBLE
            mTextMonthDay.text = calendar.month.toString() + "月" + calendar.day + "日"
            mTextYear.text = calendar.year.toString()
            mTextLunar.text = calendar.lunar
            mYear = calendar.year
        }
        mCalendarView.setOnWeekChangeListener { weekCalendars ->
            for (calendar in weekCalendars!!) {
                Log.e("onWeekChange", calendar.toString())
            }
        }
        mCalendarView.setOnYearViewChangeListener { isClose -> Log.e("onYearViewChange", "年视图 -- " + if (isClose) "关闭" else "打开") }

        mTextYear.text = mCalendarView.curYear.toString()
        mYear = mCalendarView.curYear
        mTextMonthDay.text = mCalendarView.curMonth.toString() + "月" + mCalendarView.curDay + "日"
        mTextLunar.text = "今日"
        val dialog = SuperDialog.Builder(context)
                .setDialogView(view)
                .setScreenWidth(0.75f)
                .setScreenHeight(0.46f)
                .setCancelableOutSide(true)
                .setAnimStyle(0)
                .show()
        mTextSure.setOnClickListener { dialog.dismiss() }
    }
    /**
     * 日期区间
     * @param context
     */
    fun showRangeDate(context: Context) {
        val WEEK = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
        var calendarStart:Calendar ?= null
        var calendarEnd:Calendar ?= null

        val view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_range_calendar, null)
        val mCalendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val mTextRange = view.findViewById<TextView>(R.id.tv_time)
        val mTextSure =  view.findViewById<TextView>(R.id.tv_sure)
        mCalendarView.setOnCalendarRangeSelectListener(object :CalendarView.OnCalendarRangeSelectListener{
            override fun onCalendarSelectOutOfRange(calendar: Calendar?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCalendarRangeSelect(calendar: Calendar?, isEnd: Boolean) {
                if (!isEnd) {
                    calendarStart = calendar
                    calendarEnd = null
                } else {
                    calendarEnd = calendar
                }
                if (calendarStart != null && calendarEnd !=null ){//WEEK[calendar?.week!!]
                    var startTime = calendarStart?.month.toString() + "月" + calendarStart?.day + "日"
                    var endTime  = calendarEnd?.month.toString() + "月" + calendarEnd?.day + "日"
                    mTextRange.text = "$startTime ~ $endTime"
                } else if (calendarStart != null){
                    var startTime = calendarStart?.month.toString() + "月" + calendarStart?.day + "日"
                    mTextRange.text = "$startTime"
                }
            }

            override fun onSelectOutOfRange(calendar: Calendar?, isOutOfMinRange: Boolean) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        val dialog = SuperDialog.Builder(context)
                .setDialogView(view)
                .setScreenWidth(0.75f)
                .setScreenHeight(0.4f)
                .setCancelableOutSide(true)
                .setAnimStyle(0)
                .show()
        mTextSure.setOnClickListener { dialog.dismiss() }
    }
}