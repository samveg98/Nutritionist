package com.training.nutritionist.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getUsernameModel {

    @SerializedName("result")
    @Expose

    private List<getUsernameResult> result= null;

    public List<getUsernameResult>getResult() {
        return result;
    }

    public void setResult(List<getUsernameResult>result) {
        this.result = result;
    }
}
