package com.jdssale.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 09-07-2018.
 */

public class QuoteResponse implements Parcelable {

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

    public List<QuoteData> getQuoteDataList() {
        return quoteDataList;
    }

    public void setQuoteDataList(List<QuoteData> quoteDataList) {
        this.quoteDataList = quoteDataList;
    }

    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("image_path")
    @Expose
    public String imagePath;

    @SerializedName("data")
    @Expose
    List<QuoteData> quoteDataList;

    protected QuoteResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
        imagePath = in.readString();
        quoteDataList = in.createTypedArrayList(QuoteData.CREATOR);
    }

    public static final Creator<QuoteResponse> CREATOR = new Creator<QuoteResponse>() {
        @Override
        public QuoteResponse createFromParcel(Parcel in) {
            return new QuoteResponse(in);
        }

        @Override
        public QuoteResponse[] newArray(int size) {
            return new QuoteResponse[size];
        }
    };

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
        dest.writeTypedList(quoteDataList);
    }

    public static class QuoteData implements Parcelable{

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

        @SerializedName("supplier")
        @Expose
        public String supplier;

        @SerializedName("grand_total")
        @Expose
        public String grandTotal;

        @SerializedName("status")
        @Expose
        public String status;

        @SerializedName("sale_status")
        @Expose
        public String saleStatus;

        public String getSaleStatus() {
            return saleStatus;
        }

        public void setSaleStatus(String saleStatus) {
            this.saleStatus = saleStatus;
        }

        @SerializedName("attachment")
        @Expose
        public String attachment;

        @SerializedName("customer_id")
        @Expose
        public String customerId;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("company")
        @Expose
        public String company;

        @SerializedName("single_product_quantity")
        @Expose
        public String singleProductQuantity;

        @SerializedName("packing_quantity")
        @Expose
        public String packingQuantity;

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

        public String getSupplier() {
            return supplier;
        }

        public void setSupplier(String supplier) {
            this.supplier = supplier;
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

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
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

        public String getSingleProductQuantity() {
            return singleProductQuantity;
        }

        public void setSingleProductQuantity(String singleProductQuantity) {
            this.singleProductQuantity = singleProductQuantity;
        }

        public String getPackingQuantity() {
            return packingQuantity;
        }

        public void setPackingQuantity(String packingQuantity) {
            this.packingQuantity = packingQuantity;
        }

        public String getNoItemsPacking() {
            return noItemsPacking;
        }

        public void setNoItemsPacking(String noItemsPacking) {
            this.noItemsPacking = noItemsPacking;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @SerializedName("no_of_items_in_packing")
        @Expose
        public String noItemsPacking;

        @SerializedName("image")
        @Expose
        public String image;

        protected QuoteData(Parcel in) {
            id = in.readString();
            date = in.readString();
            referenceNo = in.readString();
            biller = in.readString();
            customer = in.readString();
            supplier = in.readString();
            grandTotal = in.readString();
            status = in.readString();
            saleStatus=in.readString();
            attachment = in.readString();
            customerId = in.readString();
            name = in.readString();
            company = in.readString();
            singleProductQuantity = in.readString();
            packingQuantity = in.readString();
            noItemsPacking = in.readString();
            image = in.readString();
        }

        public static final Creator<QuoteData> CREATOR = new Creator<QuoteData>() {
            @Override
            public QuoteData createFromParcel(Parcel in) {
                return new QuoteData(in);
            }

            @Override
            public QuoteData[] newArray(int size) {
                return new QuoteData[size];
            }
        };

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
            dest.writeString(supplier);
            dest.writeString(grandTotal);
            dest.writeString(status);
            dest.writeString(saleStatus);
            dest.writeString(attachment);
            dest.writeString(customerId);
            dest.writeString(name);
            dest.writeString(company);
            dest.writeString(singleProductQuantity);
            dest.writeString(packingQuantity);
            dest.writeString(noItemsPacking);
            dest.writeString(image);
        }
    }
}
