package com.jdssale.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 23-07-2018.
 */

public class OrderResponse implements Parcelable{


    protected OrderResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
        orderData = in.createTypedArrayList(OrderData.CREATOR);
    }

    public static final Creator<OrderResponse> CREATOR = new Creator<OrderResponse>() {
        @Override
        public OrderResponse createFromParcel(Parcel in) {
            return new OrderResponse(in);
        }

        @Override
        public OrderResponse[] newArray(int size) {
            return new OrderResponse[size];
        }
    };

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<OrderData> getOrderData() {
        return orderData;
    }

    public void setOrderData(List<OrderData> orderData) {
        this.orderData = orderData;
    }

    @SerializedName("flag")
    @Expose

    public Integer flag;

    @SerializedName("data")
    @Expose
    List<OrderData> orderData;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (flag == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(flag);
        }
        dest.writeTypedList(orderData);
    }


    public static class OrderData implements Parcelable {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("reference_no")
        @Expose
        public String referenceNo;

        protected OrderData(Parcel in) {
            id = in.readString();
            referenceNo = in.readString();
            saleId = in.readString();
            customer = in.readString();
            name = in.readString();
            address = in.readString();
            customerId = in.readString();
            phone = in.readString();
        }

        public static final Creator<OrderData> CREATOR = new Creator<OrderData>() {
            @Override
            public OrderData createFromParcel(Parcel in) {
                return new OrderData(in);
            }

            @Override
            public OrderData[] newArray(int size) {
                return new OrderData[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReferenceNo() {
            return referenceNo;
        }

        public void setReferenceNo(String referenceNo) {
            this.referenceNo = referenceNo;
        }

        public String getSaleId() {
            return saleId;
        }

        public void setSaleId(String saleId) {
            this.saleId = saleId;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
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

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @SerializedName("sale_id")
        @Expose

        public String saleId;

        @SerializedName("customer")
        @Expose
        public String customer;

        @SerializedName("name")
        @Expose
        public String name;


        @SerializedName("address")
        @Expose
        public String address;

        @SerializedName("customer_id")
        @Expose
        public String customerId;

        @SerializedName("phone")
        @Expose
        public String phone;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(referenceNo);
            dest.writeString(saleId);
            dest.writeString(customer);
            dest.writeString(name);
            dest.writeString(address);
            dest.writeString(customerId);
            dest.writeString(phone);
        }
    }
}
