package com.jdssale.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dikhong on 06-07-2018.
 */

public class LoginResponse {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("user_data")
    @Expose
    public UserData userData;

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

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public class UserData {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("username")
        @Expose
        public String username;

        @SerializedName("company")
        @Expose
        public String company;

        @SerializedName("phone")
        @Expose
        public String phone;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("group_id")
        @Expose
        public String groupId;

        @SerializedName("auth_token")
        @Expose
        public String authToken;

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
        }

        @SerializedName("vehicle_id")
        @Expose
        public String vehicleId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

    }
}
