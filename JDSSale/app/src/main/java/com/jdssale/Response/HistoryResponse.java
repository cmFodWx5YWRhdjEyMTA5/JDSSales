package com.jdssale.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 11-07-2018.
 */

public class HistoryResponse {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("data")
    @Expose
    List<HistoryData> historyDataList;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<HistoryData> getHistoryDataList() {
        return historyDataList;
    }

    public void setHistoryDataList(List<HistoryData> historyDataList) {
        this.historyDataList = historyDataList;
    }

    public class HistoryData {

        @SerializedName("amount")
        @Expose
        public String amount;

        @SerializedName("reference_no")
        @Expose
        public String referenceNo;

        @SerializedName("status")
        @Expose
        public String status;

        @SerializedName("customer_id")
        @Expose
        public String customerId;

        @SerializedName("name")
        @Expose
        public String name;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getReferenceNo() {
            return referenceNo;
        }

        public void setReferenceNo(String referenceNo) {
            this.referenceNo = referenceNo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDriverFirstName() {
            return driverFirstName;
        }

        public void setDriverFirstName(String driverFirstName) {
            this.driverFirstName = driverFirstName;
        }

        public String getDriverLastName() {
            return driverLastName;
        }

        public void setDriverLastName(String driverLastName) {
            this.driverLastName = driverLastName;
        }

        public String getSaleId() {
            return saleId;
        }

        public void setSaleId(String saleId) {
            this.saleId = saleId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @SerializedName("company")

        @Expose
        public String company;

        @SerializedName("address")
        @Expose
        public String address;

        @SerializedName("phone")
        @Expose
        public String phone;

        @SerializedName("driver_first_name")
        @Expose
        public String driverFirstName;

        @SerializedName("driver_last_name")
        @Expose
        public String driverLastName;

        @SerializedName("sale_id")
        @Expose
        public String saleId;

        @SerializedName("date")
        @Expose
        public String date;
    }
}
