package com.kt.player.bean;

/**
 * Created by Administrator on 2020/9/3 0003.
 */

public class VideoBean extends VideoItem{
    private String cover;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public VideoBean(String displayName,String cover,String path) {
        super(displayName,path);
        this.cover = cover;
    }
}
