package com.kt.player.bean;

/**
 * Created by Administrator on 2020/9/3 0003.
 */

public class VideoItem {
    private String path;
    private String displayName;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public VideoItem() {
    }

    public VideoItem(String displayName ,String path) {
        this.path = path;
        this.displayName = displayName;
    }
}
