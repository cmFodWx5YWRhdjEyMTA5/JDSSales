package com.jdssale.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 19-07-2018.
 */

public class SaleDetailResponse implements Parcelable{

    @SerializedName("flag")
    @Expose
    public Integer flag;

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

    public List<SaleDetail> getSaleDetails() {
        return saleDetails;
    }

    public void setSaleDetails(List<SaleDetail> saleDetails) {
        this.saleDetails = saleDetails;
    }

    @SerializedName("image_path")
    @Expose
    public String imagePath;

    @SerializedName("data")
    @Expose
    List<SaleDetail> saleDetails;

    protected SaleDetailResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
        imagePath = in.readString();
        saleDetails = in.createTypedArrayList(SaleDetail.CREATOR);
    }

    public static final Creator<SaleDetailResponse> CREATOR = new Creator<SaleDetailResponse>() {
        @Override
        public SaleDetailResponse createFromParcel(Parcel in) {
            return new SaleDetailResponse(in);
        }

        @Override
        public SaleDetailResponse[] newArray(int size) {
            return new SaleDetailResponse[size];
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
        dest.writeTypedList(saleDetails);
    }

    public static class SaleDetail implements Parcelable{

        @SerializedName("payment_received")
        @Expose
        public String paymentReceived;

        @SerializedName("payment_left")
        @Expose
        public String paymentLeft;

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("reference_no")
        @Expose
        public String referenceNo;

        @SerializedName("grand_total")
        @Expose
        public String grandTotal;

        @SerializedName("total")
        @Expose
        public String total;

        @SerializedName("total_tax")
        @Expose
        public String totalTax;

        @SerializedName("total_discount")
        @Expose
        public String totalDiscount;


        public String getPaymentReceived() {
            return paymentReceived;
        }

        public void setPaymentReceived(String paymentReceived) {
            this.paymentReceived = paymentReceived;
        }

        public String getPaymentLeft() {
            return paymentLeft;
        }

        public void setPaymentLeft(String paymentLeft) {
            this.paymentLeft = paymentLeft;
        }

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

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getTotalTax() {
            return totalTax;
        }

        public void setTotalTax(String totalTax) {
            this.totalTax = totalTax;
        }

        public String getTotalDiscount() {
            return totalDiscount;
        }

        public void setTotalDiscount(String totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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

        public String getBillerId() {
            return billerId;
        }

        public void setBillerId(String billerId) {
            this.billerId = billerId;
        }

        public String getSaleStatus() {
            return saleStatus;
        }

        public void setSaleStatus(String saleStatus) {
            this.saleStatus = saleStatus;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public List<SaleItems> getSaleItems() {
            return saleItems;
        }

        public void setSaleItems(List<SaleItems> saleItems) {
            this.saleItems = saleItems;
        }

        @SerializedName("date")

        @Expose
        public String date;

        @SerializedName("customer")
        @Expose
        public String customer;

        @SerializedName("customer_id")
        @Expose
        public String customerId;

        @SerializedName("biller_id")
        @Expose
        public String billerId;

        @SerializedName("sale_status")
        @Expose
        public String saleStatus;

        @SerializedName("payment_status")
        @Expose
        public String paymentStatus;


        @SerializedName("sale_items")
        @Expose
        List<SaleItems> saleItems;

        protected SaleDetail(Parcel in) {
            paymentReceived = in.readString();
            paymentLeft = in.readString();
            id = in.readString();
            referenceNo = in.readString();
            grandTotal = in.readString();
            total = in.readString();
            totalTax = in.readString();
            totalDiscount = in.readString();
            date = in.readString();
            customer = in.readString();
            customerId = in.readString();
            billerId = in.readString();
            saleStatus = in.readString();
            paymentStatus = in.readString();
            saleItems = in.createTypedArrayList(SaleItems.CREATOR);
        }

        public static final Creator<SaleDetail> CREATOR = new Creator<SaleDetail>() {
            @Override
            public SaleDetail createFromParcel(Parcel in) {
                return new SaleDetail(in);
            }

            @Override
            public SaleDetail[] newArray(int size) {
                return new SaleDetail[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(paymentReceived);
            dest.writeString(paymentLeft);
            dest.writeString(id);
            dest.writeString(referenceNo);
            dest.writeString(grandTotal);
            dest.writeString(total);
            dest.writeString(totalTax);
            dest.writeString(totalDiscount);
            dest.writeString(date);
            dest.writeString(customer);
            dest.writeString(customerId);
            dest.writeString(billerId);
            dest.writeString(saleStatus);
            dest.writeString(paymentStatus);
            dest.writeTypedList(saleItems);
        }


        public static class SaleItems implements Parcelable{
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("sale_id")
            @Expose
            private String saleId;
            @SerializedName("product_id")
            @Expose
            private String productId;
            @SerializedName("product_code")
            @Expose
            private String productCode;
            @SerializedName("product_name")
            @Expose
            private String productName;
            @SerializedName("product_type")
            @Expose
            private String productType;
            @SerializedName("option_id")
            @Expose
            private String optionId;
            @SerializedName("net_unit_price")
            @Expose
            private String netUnitPrice;
            @SerializedName("unit_price")
            @Expose
            private String unitPrice;
            @SerializedName("quantity")
            @Expose
            private String quantity;
            @SerializedName("warehouse_id")
            @Expose
            private String warehouseId;
            @SerializedName("item_tax")
            @Expose
            private String itemTax;
            @SerializedName("tax_rate_id")
            @Expose
            private String taxRateId;
            @SerializedName("tax")
            @Expose
            private String tax;
            @SerializedName("discount")
            @Expose
            private String discount;
            @SerializedName("item_discount")
            @Expose
            private String itemDiscount;
            @SerializedName("subtotal")
            @Expose
            private String subtotal;
            @SerializedName("serial_no")
            @Expose
            private String serialNo;
            @SerializedName("real_unit_price")
            @Expose
            private String realUnitPrice;
            @SerializedName("sale_item_id")
            @Expose
            private String saleItemId;
            @SerializedName("product_unit_id")
            @Expose
            private String productUnitId;
            @SerializedName("product_unit_code")
            @Expose
            private String productUnitCode;
            @SerializedName("unit_quantity")
            @Expose
            private String unitQuantity;
            @SerializedName("comment")
            @Expose
            private String comment;
            @SerializedName("purchase_price")
            @Expose
            private String purchasePrice;
            @SerializedName("total_purchase_price")
            @Expose
            private String totalPurchasePrice;
            @SerializedName("profit")
            @Expose
            private String profit;
            @SerializedName("packing_quantity")
            @Expose
            private Integer packingQuantity;
            @SerializedName("single_quantity")
            @Expose
            private String singleQuantity;
            @SerializedName("position_no")
            @Expose
            private String positionNo;
            @SerializedName("no_of_items_in_packing")
            @Expose
            private String noOfItemsInPacking;
            @SerializedName("promo_price")
            @Expose
            private String promoPrice;
            @SerializedName("image")
            @Expose
            private String image;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSaleId() {
                return saleId;
            }

            public void setSaleId(String saleId) {
                this.saleId = saleId;
            }

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

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductType() {
                return productType;
            }

            public void setProductType(String productType) {
                this.productType = productType;
            }

            public String getOptionId() {
                return optionId;
            }

            public void setOptionId(String optionId) {
                this.optionId = optionId;
            }

            public String getNetUnitPrice() {
                return netUnitPrice;
            }

            public void setNetUnitPrice(String netUnitPrice) {
                this.netUnitPrice = netUnitPrice;
            }

            public String getUnitPrice() {
                return unitPrice;
            }

            public void setUnitPrice(String unitPrice) {
                this.unitPrice = unitPrice;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getWarehouseId() {
                return warehouseId;
            }

            public void setWarehouseId(String warehouseId) {
                this.warehouseId = warehouseId;
            }

            public String getItemTax() {
                return itemTax;
            }

            public void setItemTax(String itemTax) {
                this.itemTax = itemTax;
            }

            public String getTaxRateId() {
                return taxRateId;
            }

            public void setTaxRateId(String taxRateId) {
                this.taxRateId = taxRateId;
            }

            public String getTax() {
                return tax;
            }

            public void setTax(String tax) {
                this.tax = tax;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getItemDiscount() {
                return itemDiscount;
            }

            public void setItemDiscount(String itemDiscount) {
                this.itemDiscount = itemDiscount;
            }

            public String getSubtotal() {
                return subtotal;
            }

            public void setSubtotal(String subtotal) {
                this.subtotal = subtotal;
            }

            public String getSerialNo() {
                return serialNo;
            }

            public void setSerialNo(String serialNo) {
                this.serialNo = serialNo;
            }

            public String getRealUnitPrice() {
                return realUnitPrice;
            }

            public void setRealUnitPrice(String realUnitPrice) {
                this.realUnitPrice = realUnitPrice;
            }

            public String getSaleItemId() {
                return saleItemId;
            }

            public void setSaleItemId(String saleItemId) {
                this.saleItemId = saleItemId;
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

            public String getUnitQuantity() {
                return unitQuantity;
            }

            public void setUnitQuantity(String unitQuantity) {
                this.unitQuantity = unitQuantity;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getPurchasePrice() {
                return purchasePrice;
            }

            public void setPurchasePrice(String purchasePrice) {
                this.purchasePrice = purchasePrice;
            }

            public String getTotalPurchasePrice() {
                return totalPurchasePrice;
            }

            public void setTotalPurchasePrice(String totalPurchasePrice) {
                this.totalPurchasePrice = totalPurchasePrice;
            }

            public String getProfit() {
                return profit;
            }

            public void setProfit(String profit) {
                this.profit = profit;
            }

            public Integer getPackingQuantity() {
                return packingQuantity;
            }

            public void setPackingQuantity(Integer packingQuantity) {
                this.packingQuantity = packingQuantity;
            }

            public String getSingleQuantity() {
                return singleQuantity;
            }

            public void setSingleQuantity(String singleQuantity) {
                this.singleQuantity = singleQuantity;
            }

            public String getPositionNo() {
                return positionNo;
            }

            public void setPositionNo(String positionNo) {
                this.positionNo = positionNo;
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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            protected SaleItems(Parcel in) {
                id = in.readString();
                saleId = in.readString();
                productId = in.readString();
                productCode = in.readString();
                productName = in.readString();
                productType = in.readString();
                optionId = in.readString();
                netUnitPrice = in.readString();
                unitPrice = in.readString();
                quantity = in.readString();
                warehouseId = in.readString();

                itemTax = in.readString();
                taxRateId = in.readString();
                tax = in.readString();
                discount = in.readString();
                itemDiscount = in.readString();
                subtotal = in.readString();
                serialNo = in.readString();
                realUnitPrice = in.readString();
                saleItemId = in.readString();
                productUnitId = in.readString();
                productUnitCode = in.readString();
                unitQuantity = in.readString();
                comment = in.readString();
                purchasePrice = in.readString();
                totalPurchasePrice = in.readString();
                profit = in.readString();
                if (in.readByte() == 0) {
                    packingQuantity = null;
                } else {
                    packingQuantity = in.readInt();
                }
                singleQuantity = in.readString();
                positionNo = in.readString();
                noOfItemsInPacking = in.readString();
                promoPrice = in.readString();
                image = in.readString();
            }

            public static final Creator<SaleItems> CREATOR = new Creator<SaleItems>() {
                @Override
                public SaleItems createFromParcel(Parcel in) {
                    return new SaleItems(in);
                }

                @Override
                public SaleItems[] newArray(int size) {
                    return new SaleItems[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(saleId);
                dest.writeString(productId);
                dest.writeString(productCode);
                dest.writeString(productName);
                dest.writeString(productType);
                dest.writeString(optionId);
                dest.writeString(netUnitPrice);
                dest.writeString(unitPrice);
                dest.writeString(quantity);
                dest.writeString(warehouseId);
                dest.writeString(itemTax);
                dest.writeString(taxRateId);
                dest.writeString(tax);
                dest.writeString(discount);
                dest.writeString(itemDiscount);
                dest.writeString(subtotal);
                dest.writeString(serialNo);
                dest.writeString(realUnitPrice);
                dest.writeString(saleItemId);
                dest.writeString(productUnitId);
                dest.writeString(productUnitCode);
                dest.writeString(unitQuantity);
                dest.writeString(comment);
                dest.writeString(purchasePrice);
                dest.writeString(totalPurchasePrice);
                dest.writeString(profit);
                if (packingQuantity == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(packingQuantity);
                }
                dest.writeString(singleQuantity);
                dest.writeString(positionNo);
                dest.writeString(noOfItemsInPacking);
                dest.writeString(promoPrice);
                dest.writeString(image);
            }
        }
    }
}
