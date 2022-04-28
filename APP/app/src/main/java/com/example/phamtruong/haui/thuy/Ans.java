package com.example.phamtruong.haui.thuy;

public class Ans {

    String position;
    String predict;
    String success;

    public String getPosition() {
        return position;
    }

    public Ans() {
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPredict() {
        return predict;
    }

    public void setPredict(String predict) {
        this.predict = predict;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Ans(String position, String predict, String success) {
        this.position = position;
        this.predict = predict;
        this.success = success;
    }


}
