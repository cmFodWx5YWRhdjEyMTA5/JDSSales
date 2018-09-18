package com.jdssale.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 12-07-2018.
 */

public class ShopResponse implements Parcelable {

    protected ShopResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
        imagePath = in.readString();
        counter = in.readString();
        deliveryDay = in.readString();
        shopDataList = in.createTypedArrayList(ShopData.CREATOR);
        quoteItems = in.createTypedArrayList(QuoteDetailResponse.QuoteDetail.QuoteItems.CREATOR);
    }

    public static final Creator<ShopResponse> CREATOR = new Creator<ShopResponse>() {
        @Override
        public ShopResponse createFromParcel(Parcel in) {
            return new ShopResponse(in);
        }

        @Override
        public ShopResponse[] newArray(int size) {
            return new ShopResponse[size];
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

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryDay(String deliveryDay) {
        this.deliveryDay = deliveryDay;
    }

    public List<ShopData> getShopDataList() {
        return shopDataList;
    }

    public void setShopDataList(List<ShopData> shopDataList) {
        this.shopDataList = shopDataList;
    }

    @SerializedName("flag")
    @Expose

    public Integer flag;

    @SerializedName("image_path")
    @Expose
    public String imagePath;

    @SerializedName("counter")
    @Expose
    public String counter;

    @SerializedName("delivery_day")
    @Expose
    public String deliveryDay;

    @SerializedName("data")
    @Expose
    List<ShopData> shopDataList;

    public List<QuoteDetailResponse.QuoteDetail.QuoteItems> getQuoteItems() {
        return quoteItems;
    }

    public void setQuoteItems(List<QuoteDetailResponse.QuoteDetail.QuoteItems> quoteItems) {
        this.quoteItems = quoteItems;
    }

    @SerializedName("quote_items")

    @Expose
    List<QuoteDetailResponse.QuoteDetail.QuoteItems> quoteItems;

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
        dest.writeString(counter);
        dest.writeString(deliveryDay);
        dest.writeTypedList(shopDataList);
        dest.writeTypedList(quoteItems);
    }


    public static class ShopData implements Parcelable {

        @SerializedName("id")
        @Expose
        public String id;

        protected ShopData(Parcel in) {
            id = in.readString();
            code = in.readString();
            name = in.readString();
            image = in.readString();
            parentId = in.readString();
            childCategoryCount = in.readString();
        }

        public static final Creator<ShopData> CREATOR = new Creator<ShopData>() {
            @Override
            public ShopData createFromParcel(Parcel in) {
                return new ShopData(in);
            }

            @Override
            public ShopData[] newArray(int size) {
                return new ShopData[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getChildCategoryCount() {
            return childCategoryCount;
        }

        public void setChildCategoryCount(String childCategoryCount) {
            this.childCategoryCount = childCategoryCount;
        }

        @SerializedName("code")
        @Expose

        public String code;

        @SerializedName("name")
        @Expose
        public String name;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @SerializedName("image")
        @Expose

        public String image;

        @SerializedName("parent_id")
        @Expose
        public String parentId;

        @SerializedName("child_category_count")
        @Expose
        public String childCategoryCount;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(code);
            dest.writeString(name);
            dest.writeString(image);
            dest.writeString(parentId);
            dest.writeString(childCategoryCount);
        }
    }
}
