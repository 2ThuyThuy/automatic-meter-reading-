package com.example.phamtruong.haui.thuy;

public class Predict {
    String ans;
    String position;

    public Predict(String ans, String position) {
        this.ans = ans;
        this.position = position;
    }

    public Predict() {
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
