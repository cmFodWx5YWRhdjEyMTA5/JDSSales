package com.jdssale.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 14-07-2018.
 */

public class ProDetailResponse {

    @SerializedName("image_path")
    @Expose
    public String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public ProDetail getProDetail() {
        return proDetail;
    }

    public void setProDetail(ProDetail proDetail) {
        this.proDetail = proDetail;
    }

    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("data")
    @Expose
    public ProDetail proDetail;

    public class ProDetail {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("unit")
        @Expose
        private String unit;
        @SerializedName("cost")
        @Expose
        private String cost;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("alert_quantity")
        @Expose
        private String alertQuantity;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("tax_rate")
        @Expose
        private String taxRate;
        @SerializedName("unit_name")
        @Expose
        private String unitName;
        @SerializedName("product_unit_id")
        @Expose
        private String productUnitId;
        @SerializedName("product_unit_code")
        @Expose
        private String productUnitCode;
        @SerializedName("tax_percent")
        @Expose
        private String taxPercent;
        @SerializedName("tax_name")
        @Expose
        private String taxName;
        @SerializedName("no_of_items_in_packing")
        @Expose
        private String noOfItemsInPacking;
        @SerializedName("promo_price")
        @Expose
        private String promoPrice;
        @SerializedName("stock_left")
        @Expose
        private String stockLeft;
        @SerializedName("single_product_tax")
        @Expose
        private Double singleProductTax;
        @SerializedName("product_photos")
        @Expose
        private List<String> productPhotos = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getProductUnitId() {
            return productUnitId;
        }

        public void setProductUnitId(String productUnitId) {
            this.productUnitId = productUnitId;
        }

        public String getProductUnitCode() {
            return productUnitCode;
        }

        public void setProductUnitCode(String productUnitCode) {
            this.productUnitCode = productUnitCode;
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

        public String getNoOfItemsInPacking() {
            return noOfItemsInPacking;
        }

        public void setNoOfItemsInPacking(String noOfItemsInPacking) {
            this.noOfItemsInPacking = noOfItemsInPacking;
        }

        public String getPromoPrice() {
            return promoPrice;
        }

        public void setPromoPrice(String promoPrice) {
            this.promoPrice = promoPrice;
        }

        public String getStockLeft() {
            return stockLeft;
        }

        public void setStockLeft(String stockLeft) {
            this.stockLeft = stockLeft;
        }

        public Double getSingleProductTax() {
            return singleProductTax;
        }

        public void setSingleProductTax(Double singleProductTax) {
            this.singleProductTax = singleProductTax;
        }

        public List<String> getProductPhotos() {
            return productPhotos;
        }

        public void setProductPhotos(List<String> productPhotos) {
            this.productPhotos = productPhotos;
        }

    }
}
