package com.jdssale.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 11-07-2018.
 */

public class PendingResponse {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<MessageList> getMessageListList() {
        return messageListList;
    }

    public void setMessageListList(List<MessageList> messageListList) {
        this.messageListList = messageListList;
    }

    @SerializedName("image_path")
    @Expose
    public String imagePath;

    @SerializedName("message")
    @Expose
    List<MessageList> messageListList;

    public class MessageList {

        @SerializedName("id")
        @Expose
        public String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("address")
        @Expose
        public String address;

        @SerializedName("image")
        @Expose
        public String image;

        @SerializedName("phone")
        @Expose
        public String phone;

        @SerializedName("company")
        @Expose
        public String company;

    }
}
