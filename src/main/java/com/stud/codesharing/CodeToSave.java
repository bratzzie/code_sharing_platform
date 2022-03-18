package com.stud.codesharing;

public class CodeToSave {
    private String code;
    private long time;
    private int views;

    public CodeToSave(String code, long time, int views) {
        this.code = code;
        this.time = time;
        this.views = views;
    }

    public CodeToSave() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
