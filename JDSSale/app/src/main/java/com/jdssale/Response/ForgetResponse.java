package com.jdssale.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dikhong on 06-07-2018.
 */

public class ForgetResponse {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("message")
    @Expose
    public String message;

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    @SerializedName("quote_id")
    @Expose
    public String quoteId;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
