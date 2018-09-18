package com.jdssale.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 11-07-2018.
 */

public class ClientPayResponse implements Parcelable {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    protected ClientPayResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
        clientDataList = in.createTypedArrayList(ClientData.CREATOR);
    }

    public static final Creator<ClientPayResponse> CREATOR = new Creator<ClientPayResponse>() {
        @Override
        public ClientPayResponse createFromParcel(Parcel in) {
            return new ClientPayResponse(in);
        }

        @Override
        public ClientPayResponse[] newArray(int size) {
            return new ClientPayResponse[size];
        }
    };

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<ClientData> getClientDataList() {
        return clientDataList;
    }

    public void setClientDataList(List<ClientData> clientDataList) {
        this.clientDataList = clientDataList;
    }

    @SerializedName("data")
    @Expose
    List<ClientData> clientDataList;

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
        dest.writeTypedList(clientDataList);
    }

    public static class ClientData implements Parcelable{

        protected ClientData(Parcel in) {
            customerId = in.readString();
            saleId = in.readString();
            date = in.readString();
            referenceNo = in.readString();
            productDiscount = in.readString();
            totalDiscount = in.readString();
            orderDiscount = in.readString();
            dueDate = in.readString();
            paymentStatus = in.readString();
            grandTotal = in.readString();
            paid = in.readString();
            pendingPayment = in.readString();
            customerName = in.readString();
        }

        public static final Creator<ClientData> CREATOR = new Creator<ClientData>() {
            @Override
            public ClientData createFromParcel(Parcel in) {
                return new ClientData(in);
            }

            @Override
            public ClientData[] newArray(int size) {
                return new ClientData[size];
            }
        };

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
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

        public String getReferenceNo() {
            return referenceNo;
        }

        public void setReferenceNo(String referenceNo) {
            this.referenceNo = referenceNo;
        }

        public String getProductDiscount() {
            return productDiscount;
        }

        public void setProductDiscount(String productDiscount) {
            this.productDiscount = productDiscount;
        }

        public String getTotalDiscount() {
            return totalDiscount;
        }

        public void setTotalDiscount(String totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public String getOrderDiscount() {
            return orderDiscount;
        }

        public void setOrderDiscount(String orderDiscount) {
            this.orderDiscount = orderDiscount;
        }

        public String getDueDate() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

        public String getPaid() {
            return paid;
        }

        public void setPaid(String paid) {
            this.paid = paid;
        }

        public String getPendingPayment() {
            return pendingPayment;
        }

        public void setPendingPayment(String pendingPayment) {
            this.pendingPayment = pendingPayment;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        @SerializedName("customer_id")
        @Expose
        public String customerId;

        @SerializedName("sale_id")
        @Expose
        public String saleId;

        @SerializedName("date")
        @Expose
        public String date;


        @SerializedName("reference_no")
        @Expose
        public String referenceNo;

        @SerializedName("product_discount")
        @Expose
        public String productDiscount;

        @SerializedName("total_discount")
        @Expose
        public String totalDiscount;

        @SerializedName("order_discount")
        @Expose
        public String orderDiscount;


        @SerializedName("due_date")
        @Expose
        public String dueDate;


        @SerializedName("payment_status")
        @Expose
        public String paymentStatus;


        @SerializedName("grand_total")
        @Expose
        public String grandTotal;


        @SerializedName("paid")
        @Expose
        public String paid;


        @SerializedName("pending_payment")
        @Expose
        public String pendingPayment;


        @SerializedName("customer_name")
        @Expose
        public String customerName;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(customerId);
            dest.writeString(saleId);
            dest.writeString(date);
            dest.writeString(referenceNo);
            dest.writeString(productDiscount);
            dest.writeString(totalDiscount);
            dest.writeString(orderDiscount);
            dest.writeString(dueDate);
            dest.writeString(paymentStatus);
            dest.writeString(grandTotal);
            dest.writeString(paid);
            dest.writeString(pendingPayment);
            dest.writeString(customerName);
        }
    }
}
