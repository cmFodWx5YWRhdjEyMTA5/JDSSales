package com.jdssale.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 17-07-2018.
 */

public class SaleResponse implements Parcelable{

    @SerializedName("flag")
    @Expose
    public Integer flag;

    protected SaleResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
        imagePath = in.readString();
    }

    public static final Creator<SaleResponse> CREATOR = new Creator<SaleResponse>() {
        @Override
        public SaleResponse createFromParcel(Parcel in) {
            return new SaleResponse(in);
        }

        @Override
        public SaleResponse[] newArray(int size) {
            return new SaleResponse[size];
        }
    };

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

    public List<SaleData> getSaleDataList() {
        return saleDataList;
    }

    public void setSaleDataList(List<SaleData> saleDataList) {
        this.saleDataList = saleDataList;
    }

    @SerializedName("image_path")
    @Expose
    public String imagePath;

    @SerializedName("data")
    @Expose
    List<SaleData> saleDataList;

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
        dest.writeString(imagePath);
    }

    public static class SaleData implements Parcelable {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("date")
        @Expose
        public String date;

        @SerializedName("reference_no")
        @Expose
        public String referenceNo;

        @SerializedName("biller")
        @Expose
        public String biller;

        @SerializedName("customer")
        @Expose
        public String customer;

        @SerializedName("customer_id")
        @Expose
        public String customerId;

        @SerializedName("sale_status")
        @Expose
        public String saleStatus;

        @SerializedName("grand_total")
        @Expose
        public String grandTotal;

        @SerializedName("paid")
        @Expose
        public String paid;

        @SerializedName("balance")
        @Expose
        public String balance;

        protected SaleData(Parcel in) {
            id = in.readString();
            date = in.readString();
            referenceNo = in.readString();
            biller = in.readString();
            customer = in.readString();
            customerId = in.readString();
            saleStatus = in.readString();
            grandTotal = in.readString();
            paid = in.readString();
            balance = in.readString();
            paymentStatus = in.readString();
            attachment = in.readString();
            returnId = in.readString();
        }

        public static final Creator<SaleData> CREATOR = new Creator<SaleData>() {
            @Override
            public SaleData createFromParcel(Parcel in) {
                return new SaleData(in);
            }

            @Override
            public SaleData[] newArray(int size) {
                return new SaleData[size];
            }
        };

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

        public String getBiller() {
            return biller;
        }

        public void setBiller(String biller) {
            this.biller = biller;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getSaleStatus() {
            return saleStatus;
        }

        public void setSaleStatus(String saleStatus) {
            this.saleStatus = saleStatus;
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

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getReturnId() {
            return returnId;
        }

        public void setReturnId(String returnId) {
            this.returnId = returnId;
        }

        @SerializedName("payment_status")
        @Expose
        public String paymentStatus;

        @SerializedName("attachment")
        @Expose
        public String attachment;

        @SerializedName("return_id")
        @Expose
        public String returnId;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(date);
            dest.writeString(referenceNo);
            dest.writeString(biller);
            dest.writeString(customer);
            dest.writeString(customerId);
            dest.writeString(saleStatus);
            dest.writeString(grandTotal);
            dest.writeString(paid);
            dest.writeString(balance);
            dest.writeString(paymentStatus);
            dest.writeString(attachment);
            dest.writeString(returnId);
        }
    }
}
