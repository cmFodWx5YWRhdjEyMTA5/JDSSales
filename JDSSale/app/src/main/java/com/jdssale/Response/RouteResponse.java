package com.jdssale.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 25-07-2018.
 */

public class RouteResponse {

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<RouteData> getRouteDataList() {
        return routeDataList;
    }

    public void setRouteDataList(List<RouteData> routeDataList) {
        this.routeDataList = routeDataList;
    }

    @SerializedName("flag")
    @Expose

    public Integer flag;

    @SerializedName("data")
    @Expose
    List<RouteData> routeDataList;

    public class RouteData {

        @SerializedName("is_delivered")
        @Expose
        public String isDelivered;

        public String getIsDelivered() {
            return isDelivered;
        }

        public void setIsDelivered(String isDelivered) {
            this.isDelivered = isDelivered;
        }

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("date")
        @Expose
        public String date;

        @SerializedName("reference_no")
        @Expose
        public String referenceNo;

        @SerializedName("customer")
        @Expose
        public String customer;

        @SerializedName("biller")
        @Expose
        public String biller;

        @SerializedName("order_discount")
        @Expose
        public String orderDiscount;

        @SerializedName("total_tax")
        @Expose
        public String totalTax;

        @SerializedName("grand_total")
        @Expose
        public String grandTotal;

        @SerializedName("status")
        @Expose
        public String status;

        @SerializedName("vehicle_id")
        @Expose
        public String vehicleId;

        @SerializedName("number")
        @Expose
        public String number;

        @SerializedName("customer_name")
        @Expose
        public String customerName;

        @SerializedName("address")
        @Expose
        public String address;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getReferenceNo() {
            return referenceNo;
        }

        public void setReferenceNo(String referenceNo) {
            this.referenceNo = referenceNo;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getBiller() {
            return biller;
        }

        public void setBiller(String biller) {
            this.biller = biller;
        }

        public String getOrderDiscount() {
            return orderDiscount;
        }

        public void setOrderDiscount(String orderDiscount) {
            this.orderDiscount = orderDiscount;
        }

        public String getTotalTax() {
            return totalTax;
        }

        public void setTotalTax(String totalTax) {
            this.totalTax = totalTax;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
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

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        @SerializedName("city")

        @Expose
        public String city;

        @SerializedName("state")
        @Expose
        public String state;

        @SerializedName("postal_code")
        @Expose
        public String postalCode;

        @SerializedName("position")
        @Expose
        public String position;

        @SerializedName("lat")
        @Expose
        public String lat;

        @SerializedName("long")
        @Expose
        public String lng;


    }
}
