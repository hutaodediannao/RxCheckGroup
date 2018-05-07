package com.test.openandcloserecycleview;

/**
 * Created by hutao on 2018/4/4.
 */

public class Model {

    private String title;
    private int resId;
    private String content;
    private boolean isOpen;

    public Model(String title, int resId, String content, boolean isOpen) {
        this.title = title;
        this.resId = resId;
        this.content = content;
        this.isOpen = isOpen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
