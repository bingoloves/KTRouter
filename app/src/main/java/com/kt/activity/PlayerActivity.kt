package com.kt.activity

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.Toast
import com.kk.taurus.playerbase.assist.InterEvent
import com.kk.taurus.playerbase.assist.OnVideoViewEventHandler
import com.kk.taurus.playerbase.config.PlayerConfig
import com.kk.taurus.playerbase.entity.DataSource
import com.kk.taurus.playerbase.event.OnPlayerEventListener
import com.kk.taurus.playerbase.player.IPlayer
import com.kk.taurus.playerbase.receiver.IReceiver
import com.kk.taurus.playerbase.receiver.ReceiverGroup
import com.kk.taurus.playerbase.render.AspectRatio
import com.kk.taurus.playerbase.render.IRender
import com.kk.taurus.playerbase.widget.BaseVideoView
import com.kt.App
import com.kt.R
import com.kt.player.DataInter
import com.kt.player.ReceiverGroupManager
import com.kt.player.adapter.SettingAdapter
import com.kt.player.bean.SettingItem
import com.kt.player.cover.ControllerCover
import com.kt.player.utils.DataUtils
import com.kt.player.utils.PUtil
import kotlinx.android.synthetic.main.activity_player.*

/**
 * Created by Administrator on 2020/9/3 0003.
 */
class PlayerActivity:AppCompatActivity(), OnPlayerEventListener, SettingAdapter.OnItemClickListener<SettingAdapter.SettingItemHolder, SettingItem> {
    private var mReceiverGroup:ReceiverGroup ?= null
    private var userPause: Boolean = false
    private var isLandscape: Boolean = false
    private var margin: Int = 0

    private var hasStart: Boolean = false
    private var mAdapter: SettingAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        margin = PUtil.dip2px(this,2f)
        updateVideo(false)
        mReceiverGroup = ReceiverGroupManager.get().getReceiverGroup(this)
        mReceiverGroup?.groupValue?.putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, true)
        videoView.setReceiverGroup(mReceiverGroup)
        videoView.setEventHandler(object : OnVideoViewEventHandler() {
            override fun onAssistHandle(assist: BaseVideoView?, eventCode: Int, bundle: Bundle?) {
                super.onAssistHandle(assist, eventCode, bundle)
                when (eventCode) {
                    InterEvent.CODE_REQUEST_PAUSE -> userPause = true
                    DataInter.Event.EVENT_CODE_REQUEST_BACK -> if (isLandscape) {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    } else {
                        finish()
                    }
                    DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN -> requestedOrientation = if (isLandscape)
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    else
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    DataInter.Event.EVENT_CODE_ERROR_SHOW -> videoView.stop()
                }
            }
            override fun requestRetry(videoView: BaseVideoView, bundle: Bundle?) {
                if (PUtil.isTopActivity(this@PlayerActivity)) {
                    super.requestRetry(videoView, bundle)
                }
            }
        })
        videoView.setOnPlayerEventListener(this)
    }

    private fun initPlay() {
        if (!hasStart) {
            val dataSource = DataSource(DataUtils.VIDEO_URL_12)
            dataSource.title = "音乐和艺术如何改变世界"
            videoView.setDataSource(dataSource)
            videoView.start()
            hasStart = true
        }
    }

    private fun updateVideo(landscape: Boolean) {
        val layoutParams = videoView.layoutParams as RelativeLayout.LayoutParams
        if (landscape) {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.setMargins(0, 0, 0, 0)
        } else {
            layoutParams.width = PUtil.getScreenW(this) - margin * 2
            layoutParams.height = layoutParams.width * 3 / 4
            layoutParams.setMargins(margin, margin, margin, margin)
        }
        videoView.layoutParams = layoutParams
    }

    private fun replay() {
        videoView.setDataSource(DataSource(DataUtils.VIDEO_URL_12))
        videoView.start()
    }

    override fun onBackPressed() {
        if (isLandscape) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            return
        }
        super.onBackPressed()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (newConfig?.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            isLandscape = true
            updateVideo(true)
        } else {
            isLandscape = false
            updateVideo(false)
        }
        mReceiverGroup?.groupValue?.putBoolean(DataInter.Key.KEY_IS_LANDSCAPE, isLandscape)
    }

    override fun onPlayerEvent(eventCode: Int, bundle: Bundle?) {
        when (eventCode) {
            OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START -> if (mAdapter == null) {
                settingRv?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                mAdapter = SettingAdapter(this, SettingItem.initSettingList())
                mAdapter?.setOnItemClickListener(this)
                settingRv.adapter = mAdapter
            }
        }
    }

    override fun onItemClick(holder: SettingAdapter.SettingItemHolder?, item: SettingItem?, position: Int) {
        val code = item?.code
        when(code){
            SettingItem.CODE_RENDER_SURFACE_VIEW ->{
                videoView.setRenderType(IRender.RENDER_TYPE_SURFACE_VIEW)
            }
            SettingItem.CODE_RENDER_TEXTURE_VIEW ->{
                videoView.setRenderType(IRender.RENDER_TYPE_TEXTURE_VIEW)
            }
            SettingItem.CODE_STYLE_ROUND_RECT ->{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    videoView.setRoundRectShape(PUtil.dip2px(this@PlayerActivity,25f).toFloat())
                }else{
                    Toast.makeText(this@PlayerActivity, "not support", Toast.LENGTH_SHORT).show();
                }
            }
            SettingItem.CODE_STYLE_OVAL_RECT ->{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    videoView.setOvalRectShape()
                }else{
                    Toast.makeText(this@PlayerActivity, "not support", Toast.LENGTH_SHORT).show();
                }
            }
            SettingItem.CODE_STYLE_RESET ->{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    videoView.clearShapeStyle()
                }else{
                    Toast.makeText(this@PlayerActivity, "not support", Toast.LENGTH_SHORT).show();
                }
            }
            SettingItem.CODE_ASPECT_16_9 ->{
                videoView.setAspectRatio(AspectRatio.AspectRatio_16_9)
            }
            SettingItem.CODE_ASPECT_4_3 ->{
                videoView.setAspectRatio(AspectRatio.AspectRatio_4_3)
            }
            SettingItem.CODE_ASPECT_FILL ->{
                videoView.setAspectRatio(AspectRatio.AspectRatio_FILL_PARENT)
            }
            SettingItem.CODE_ASPECT_MATCH ->{
                videoView.setAspectRatio(AspectRatio.AspectRatio_MATCH_PARENT)
            }
            SettingItem.CODE_ASPECT_FIT ->{
                videoView.setAspectRatio(AspectRatio.AspectRatio_FIT_PARENT)
            }
            SettingItem.CODE_ASPECT_ORIGIN ->{
                videoView.setAspectRatio(AspectRatio.AspectRatio_ORIGIN)
            }
            SettingItem.CODE_PLAYER_MEDIA_PLAYER ->{
                if(videoView.switchDecoder(PlayerConfig.DEFAULT_PLAN_ID)){
                    replay()
                }
            }
            SettingItem.CODE_PLAYER_IJK_PLAYER ->{
                if(videoView.switchDecoder(App.PLAN_ID_IJK)){
                    replay()
                }
            }
            SettingItem.CODE_PLAYER_EXO_PLAYER ->{
                if(videoView.switchDecoder(App.PLAN_ID_EXO)){
                    replay()
                }
            }
            SettingItem.CODE_SPEED_0_5 ->{
                videoView.setSpeed(0.5f)
            }
            SettingItem.CODE_SPEED_2 ->{
                videoView.setSpeed(2f)
            }
            SettingItem.CODE_SPEED_1 ->{
                videoView.setSpeed(1f)
            }
            SettingItem.CODE_VOLUME_SILENT ->{
                videoView.setVolume(0f, 0f)
            }
            SettingItem.CODE_VOLUME_RESET ->{
                videoView.setVolume(1f, 1f)
            }
            SettingItem.CODE_CONTROLLER_REMOVE ->{
                mReceiverGroup?.removeReceiver(DataInter.ReceiverKey.KEY_CONTROLLER_COVER)
                Toast.makeText(this, "已移除", Toast.LENGTH_SHORT).show()
            }
            SettingItem.CODE_CONTROLLER_RESET ->{
                val receiver:IReceiver = mReceiverGroup?.getReceiver(DataInter.ReceiverKey.KEY_CONTROLLER_COVER)!!
                if(receiver == null){
                    mReceiverGroup?.addReceiver(DataInter.ReceiverKey.KEY_CONTROLLER_COVER,  ControllerCover(this))
                    Toast.makeText(this, "已添加", Toast.LENGTH_SHORT).show()
                }
            }
            SettingItem.CODE_TEST_UPDATE_RENDER ->{
                videoView.updateRender()
            }
        }
    }
    override fun onPause() {
        super.onPause()
        val state = videoView.state
        if (state == IPlayer.STATE_PLAYBACK_COMPLETE)
            return
        if (videoView.isInPlaybackState) {
            videoView.pause()
        } else {
            videoView.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        val state = videoView.state
        if (state == IPlayer.STATE_PLAYBACK_COMPLETE)
            return
        if (videoView.isInPlaybackState) {
            if (!userPause)
                videoView.resume()
        } else {
            videoView.rePlay(0)
        }
        initPlay()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.stopPlayback()
    }
}