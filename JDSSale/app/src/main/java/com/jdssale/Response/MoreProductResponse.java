package com.jdssale.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 17-07-2018.
 */

public class MoreProductResponse implements Parcelable{
    @SerializedName("flag")
    @Expose
    public Integer flag;

    protected MoreProductResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
        imagePath = in.readString();
        moreProducts = in.createTypedArrayList(MoreProduct.CREATOR);
    }

    public static final Creator<MoreProductResponse> CREATOR = new Creator<MoreProductResponse>() {
        @Override
        public MoreProductResponse createFromParcel(Parcel in) {
            return new MoreProductResponse(in);
        }

        @Override
        public MoreProductResponse[] newArray(int size) {
            return new MoreProductResponse[size];
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

    public List<MoreProduct> getMoreProducts() {
        return moreProducts;
    }

    public void setMoreProducts(List<MoreProduct> moreProducts) {
        this.moreProducts = moreProducts;
    }

    @SerializedName("image_path")
    @Expose
    public String imagePath;

    @SerializedName("data")
    @Expose
    List<MoreProduct> moreProducts;

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
        dest.writeTypedList(moreProducts);
    }

    public static class MoreProduct implements Parcelable{

        @SerializedName("product_id")
        @Expose
        public String productId;

        @SerializedName("image")
        @Expose
        public String image;

        @SerializedName("category_id")
        @Expose
        public String categoryId;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getNoOfItemsInPacking() {
            return noOfItemsInPacking;
        }

        public void setNoOfItemsInPacking(String noOfItemsInPacking) {
            this.noOfItemsInPacking = noOfItemsInPacking;
        }

        public String getPromotion() {
            return promotion;
        }

        public void setPromotion(String promotion) {
            this.promotion = promotion;
        }

        public String getPromoPrice() {
            return promoPrice;
        }

        public void setPromoPrice(String promoPrice) {
            this.promoPrice = promoPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        @SerializedName("no_of_items_in_packing")
        @Expose
        public String noOfItemsInPacking;

        @SerializedName("promotion")
        @Expose
        public String promotion;

        @SerializedName("promo_price")
        @Expose
        public String promoPrice;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("price")
        @Expose
        public String price;

        protected MoreProduct(Parcel in) {
            productId = in.readString();
            image = in.readString();
            categoryId = in.readString();
            noOfItemsInPacking = in.readString();
            promotion = in.readString();
            promoPrice = in.readString();
            name = in.readString();
            price = in.readString();
        }

        public static final Creator<MoreProduct> CREATOR = new Creator<MoreProduct>() {
            @Override
            public MoreProduct createFromParcel(Parcel in) {
                return new MoreProduct(in);
            }

            @Override
            public MoreProduct[] newArray(int size) {
                return new MoreProduct[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(productId);
            dest.writeString(image);
            dest.writeString(noOfItemsInPacking);
            dest.writeString(promotion);
            dest.writeString(promoPrice);
            dest.writeString(name);
            dest.writeString(price);
        }
    }
}
