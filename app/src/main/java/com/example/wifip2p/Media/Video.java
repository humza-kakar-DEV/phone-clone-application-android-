package com.example.wifip2p.Media;

import android.net.Uri;

public class Video {

    private final Uri uri;
    private final String name;
    private final int duration;
    private final int size;
    private boolean isSelected;

    public Video(Uri uri, String name, int duration, int size, boolean isSelected) {
        this.uri = uri;
        this.name = name;
        this.duration = duration;
        this.size = size;
        this.isSelected = isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public Uri getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getSize() {
        return size;
    }
}
