package com.training.nutritionist.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class setDailyModel {
    @SerializedName("result")
    @Expose

    private List<setDailyResult> result= null;

    public List<setDailyResult>getResult() {
        return result;
    }

    public void setResult(List<setDailyResult>result) {
        this.result = result;
    }
}
