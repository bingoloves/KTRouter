package com.kt

import android.app.Application
import com.kk.taurus.exoplayer.ExoMediaPlayer
import com.kk.taurus.playerbase.record.PlayRecordManager
import com.kk.taurus.playerbase.config.PlayerConfig
import com.kk.taurus.ijkplayer.IjkPlayer
import com.kk.taurus.playerbase.config.PlayerLibrary
import com.kk.taurus.playerbase.entity.DecoderPlan

/**
 * Created by Administrator on 2020/9/3 0003.
 */
class App:Application(){

    companion object {
        const val PLAN_ID_IJK = 1
        const val PLAN_ID_EXO = 2
        @JvmField
        var ignoreMobile = false
    }

    override fun onCreate() {
        super.onCreate()
        initPlayerBase()

    }

    /**
     * 初始化播放器组件
     */
    private fun initPlayerBase() {
        //初始化库
        PlayerConfig.addDecoderPlan(DecoderPlan(PLAN_ID_IJK, IjkPlayer::class.java.name, "IjkPlayer"))
        PlayerConfig.addDecoderPlan(DecoderPlan(PLAN_ID_EXO, ExoMediaPlayer::class.java.name, "ExoPlayer"))
        ExoMediaPlayer.init(this)
        IjkPlayer.init(this)
        PlayerConfig.setDefaultPlanId(PLAN_ID_IJK)
        //use default NetworkEventProducer.
        PlayerConfig.setUseDefaultNetworkEventProducer(true)
        PlayerConfig.playRecord(true)
        PlayRecordManager.setRecordConfig(
                PlayRecordManager.RecordConfig.Builder()
                        .setMaxRecordCount(100).build())
        PlayerLibrary.init(this)
    }
}