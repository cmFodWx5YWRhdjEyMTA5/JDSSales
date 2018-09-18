package com.jdssale.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 24-07-2018.
 */

public class CustInfoResponse {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("image")
    @Expose
    public String image;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<CustData> getCustDataList() {
        return custDataList;
    }

    public void setCustDataList(List<CustData> custDataList) {
        this.custDataList = custDataList;
    }

    @SerializedName("data")
    @Expose
    List<CustData> custDataList;

    public class CustData {

        @SerializedName("customer_name")
        @Expose
        public String customerName;

        @SerializedName("customer_id")
        @Expose
        public String customerId;

        @SerializedName("address")
        @Expose
        public String address;

        @SerializedName("city")
        @Expose
        public String city;

        @SerializedName("state")
        @Expose
        public String state;

        @SerializedName("postal_code")
        @Expose
        public String postalCode;

        @SerializedName("country")
        @Expose
        public String country;


        @SerializedName("image")
        @Expose
        public String image;

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("phone")
        @Expose
        public String phone;

        @SerializedName("company")
        @Expose
        public String company;

    }
}
