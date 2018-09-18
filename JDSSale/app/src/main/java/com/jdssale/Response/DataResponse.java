package com.jdssale.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dikhong on 11-07-2018.
 */

public class DataResponse {
    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("data")
    @Expose
    public String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }



}
