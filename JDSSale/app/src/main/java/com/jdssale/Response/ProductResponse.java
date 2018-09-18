package com.jdssale.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 13-07-2018.
 */

public class ProductResponse implements Parcelable {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    protected ProductResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
        imagePath = in.readString();
        productsList = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Creator<ProductResponse> CREATOR = new Creator<ProductResponse>() {
        @Override
        public ProductResponse createFromParcel(Parcel in) {
            return new ProductResponse(in);
        }

        @Override
        public ProductResponse[] newArray(int size) {
            return new ProductResponse[size];
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

    public List<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    @SerializedName("image_path")
    @Expose
    public String imagePath;

    @SerializedName("data")
    @Expose
    List<Product> productsList;

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
        dest.writeTypedList(productsList);
    }

    public static class Product implements Parcelable{

        @SerializedName("product_id")
        @Expose
        public String productId;

        @SerializedName("product_code")
        @Expose
        public String productCode;

        @SerializedName("product_type")
        @Expose
        public String productType;

        @SerializedName("tax_method")
        @Expose
        public String taxMethod;

        @SerializedName("product_name")
        @Expose
        public String productName;

        @SerializedName("image")
        @Expose
        public String image;

        @SerializedName("unit")
        @Expose
        public String unit;

        @SerializedName("cost")
        @Expose
        public String cost;

        @SerializedName("price")
        @Expose
        public String price;

        @SerializedName("alert_quantity")
        @Expose
        public String alertQuantity;

        @SerializedName("category_id")
        @Expose
        public String categoryId;

        @SerializedName("quantity")
        @Expose
        public String quantity;

        @SerializedName("tax_rate")
        @Expose
        public String taxRate;

        @SerializedName("tax_percent")
        @Expose
        public String taxPercent;

        @SerializedName("tax_name")
        @Expose
        public String taxName;

        @SerializedName("unit_name")
        @Expose
        public String unitName;

        @SerializedName("no_of_items_in_packing")
        @Expose
        public String noOfItemsInPacking;

        @SerializedName("promotion")
        @Expose
        public String promotion;

        @SerializedName("promo_price")
        @Expose
        public String promoPrice;

        protected Product(Parcel in) {
            productId = in.readString();
            productCode = in.readString();
            productType = in.readString();
            taxMethod = in.readString();
            productName = in.readString();
            image = in.readString();
            unit = in.readString();
            cost = in.readString();
            price = in.readString();
            alertQuantity = in.readString();
            categoryId = in.readString();
            quantity = in.readString();
            taxRate = in.readString();
            taxPercent = in.readString();
            taxName = in.readString();
            unitName = in.readString();
            noOfItemsInPacking = in.readString();
            promotion = in.readString();
            promoPrice = in.readString();
            startDate = in.readString();
            endDate = in.readString();
            stockLeft = in.readString();
            singleProductTax = in.readFloat();
        }

        public static final Creator<Product> CREATOR = new Creator<Product>() {
            @Override
            public Product createFromParcel(Parcel in) {
                return new Product(in);
            }

            @Override
            public Product[] newArray(int size) {
                return new Product[size];
            }
        };

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getTaxMethod() {
            return taxMethod;
        }

        public void setTaxMethod(String taxMethod) {
            this.taxMethod = taxMethod;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAlertQuantity() {
            return alertQuantity;
        }

        public void setAlertQuantity(String alertQuantity) {
            this.alertQuantity = alertQuantity;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getTaxRate() {
            return taxRate;
        }

        public void setTaxRate(String taxRate) {
            this.taxRate = taxRate;
        }

        public String getTaxPercent() {
            return taxPercent;
        }

        public void setTaxPercent(String taxPercent) {
            this.taxPercent = taxPercent;
        }

        public String getTaxName() {
            return taxName;
        }

        public void setTaxName(String taxName) {
            this.taxName = taxName;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
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

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getStockLeft() {
            return stockLeft;
        }

        public void setStockLeft(String stockLeft) {
            this.stockLeft = stockLeft;
        }

        public float getSingleProductTax() {
            return singleProductTax;
        }

        public void setSingleProductTax(float singleProductTax) {
            this.singleProductTax = singleProductTax;
        }

        @SerializedName("start_date")
        @Expose
        public String startDate;

        @SerializedName("end_date")
        @Expose
        public String endDate;

        @SerializedName("stock_left")
        @Expose
        public String stockLeft;

        @SerializedName("single_product_tax")
        @Expose
        public float singleProductTax;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(productId);
            dest.writeString(productCode);
            dest.writeString(productType);
            dest.writeString(taxMethod);
            dest.writeString(productName);
            dest.writeString(image);
            dest.writeString(unit);
            dest.writeString(cost);
            dest.writeString(price);
            dest.writeString(alertQuantity);
            dest.writeString(categoryId);
            dest.writeString(quantity);
            dest.writeString(taxRate);
            dest.writeString(taxPercent);
            dest.writeString(taxName);
            dest.writeString(unitName);
            dest.writeString(noOfItemsInPacking);
            dest.writeString(promotion);
            dest.writeString(promoPrice);
            dest.writeString(startDate);
            dest.writeString(endDate);
            dest.writeString(stockLeft);
            dest.writeFloat(singleProductTax);
        }
    }
}
