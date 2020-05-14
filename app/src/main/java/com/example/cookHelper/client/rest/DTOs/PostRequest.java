package com.example.cookHelper.client.rest.DTOs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PostRequest {
    @SerializedName("ingredients")
    @Expose
    private ArrayList<String> list = new ArrayList<String>();

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}
