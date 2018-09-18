package com.jdssale.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 09-07-2018.
 */

public class QuoteDetailResponse implements Parcelable{

    @SerializedName("flag")
    @Expose
    public Integer flag;

    protected QuoteDetailResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
        imagePath = in.readString();
        quoteDetailList = in.readParcelable(QuoteDetail.class.getClassLoader());
    }

    public static final Creator<QuoteDetailResponse> CREATOR = new Creator<QuoteDetailResponse>() {
        @Override
        public QuoteDetailResponse createFromParcel(Parcel in) {
            return new QuoteDetailResponse(in);
        }

        @Override
        public QuoteDetailResponse[] newArray(int size) {
            return new QuoteDetailResponse[size];
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



    @SerializedName("image_path")
    @Expose
    public String imagePath;

    public QuoteDetail getQuoteDetailList() {
        return quoteDetailList;
    }

    public void setQuoteDetailList(QuoteDetail quoteDetailList) {
        this.quoteDetailList = quoteDetailList;
    }

    @SerializedName("quotation_detail")
    @Expose
    public QuoteDetail quoteDetailList;

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
        dest.writeParcelable(quoteDetailList, flags);
    }

    public static class QuoteDetail implements Parcelable{

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

        @SerializedName("total")
        @Expose
        public String total;

        protected QuoteDetail(Parcel in) {
            id = in.readString();
            date = in.readString();
            referenceNo = in.readString();
            customer = in.readString();
            biller = in.readString();
            total = in.readString();
            totalDiscount = in.readString();
            grandTotal = in.readString();
        }

        public static final Creator<QuoteDetail> CREATOR = new Creator<QuoteDetail>() {
            @Override
            public QuoteDetail createFromParcel(Parcel in) {
                return new QuoteDetail(in);
            }

            @Override
            public QuoteDetail[] newArray(int size) {
                return new QuoteDetail[size];
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

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getTotalDiscount() {
            return totalDiscount;
        }

        public void setTotalDiscount(String totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

        public List<QuoteItems> getQuoteItemsList() {
            return quoteItemsList;
        }

        public void setQuoteItemsList(List<QuoteItems> quoteItemsList) {
            this.quoteItemsList = quoteItemsList;
        }

        @SerializedName("total_discount")
        @Expose
        public String totalDiscount;

        @SerializedName("grand_total")
        @Expose
        public String grandTotal;


        @SerializedName("items")
        @Expose
        List<QuoteItems> quoteItemsList;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(date);
            dest.writeString(referenceNo);
            dest.writeString(customer);
            dest.writeString(biller);
            dest.writeString(total);
            dest.writeString(totalDiscount);
            dest.writeString(grandTotal);
        }

        public static class QuoteItems implements Parcelable{

            @SerializedName("quote_item_id")
            @Expose
            public String quoteItemId;

            @SerializedName("quote_id")
            @Expose
            public String quoteId;

            @SerializedName("product_id")
            @Expose
            public String productId;

            @SerializedName("product_name")
            @Expose
            public String productName;

            @SerializedName("product_type")
            @Expose
            public String productType;

            @SerializedName("quantity")
            @Expose
            public String quantity;

            @SerializedName("subtotal")
            @Expose
            public String subtotal;

            @SerializedName("image")
            @Expose
            public String image;

            @SerializedName("unit_price")
            @Expose
            public String unitPrice;

            @SerializedName("no_of_items_in_packing")
            @Expose
            public String noItemsPacking;

            @SerializedName("net_unit_price")
            @Expose
            public String netUnitPrice;

            @SerializedName("promo_price")
            @Expose
            public String promoPrice;

            protected QuoteItems(Parcel in) {
                quoteItemId = in.readString();
                quoteId = in.readString();
                productId = in.readString();
                productName = in.readString();
                productType = in.readString();
                quantity = in.readString();
                subtotal = in.readString();
                image = in.readString();
                unitPrice = in.readString();
                noItemsPacking = in.readString();
                netUnitPrice = in.readString();
                promoPrice = in.readString();
                singleProductQuantity = in.readString();
                packingQuantity = in.readString();
                itemTax = in.readString();
            }

            public static final Creator<QuoteItems> CREATOR = new Creator<QuoteItems>() {
                @Override
                public QuoteItems createFromParcel(Parcel in) {
                    return new QuoteItems(in);
                }

                @Override
                public QuoteItems[] newArray(int size) {
                    return new QuoteItems[size];
                }
            };

            public String getQuoteItemId() {
                return quoteItemId;
            }

            public void setQuoteItemId(String quoteItemId) {
                this.quoteItemId = quoteItemId;
            }

            public String getQuoteId() {
                return quoteId;
            }

            public void setQuoteId(String quoteId) {
                this.quoteId = quoteId;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
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

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getSubtotal() {
                return subtotal;
            }

            public void setSubtotal(String subtotal) {
                this.subtotal = subtotal;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getUnitPrice() {
                return unitPrice;
            }

            public void setUnitPrice(String unitPrice) {
                this.unitPrice = unitPrice;
            }

            public String getNoItemsPacking() {
                return noItemsPacking;
            }

            public void setNoItemsPacking(String noItemsPacking) {
                this.noItemsPacking = noItemsPacking;
            }

            public String getNetUnitPrice() {
                return netUnitPrice;
            }

            public void setNetUnitPrice(String netUnitPrice) {
                this.netUnitPrice = netUnitPrice;
            }

            public String getPromoPrice() {
                return promoPrice;
            }

            public void setPromoPrice(String promoPrice) {
                this.promoPrice = promoPrice;
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

            public String getItemTax() {
                return itemTax;
            }

            public void setItemTax(String itemTax) {
                this.itemTax = itemTax;
            }

            @SerializedName("single_product_quantity")
            @Expose
            public String singleProductQuantity;

            @SerializedName("packing_quantity")
            @Expose
            public String packingQuantity;

            @SerializedName("item_tax")
            @Expose
            public String itemTax;


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(quoteItemId);
                dest.writeString(quoteId);
                dest.writeString(productId);
                dest.writeString(productName);
                dest.writeString(productType);
                dest.writeString(quantity);
                dest.writeString(subtotal);
                dest.writeString(image);
                dest.writeString(unitPrice);
                dest.writeString(noItemsPacking);
                dest.writeString(netUnitPrice);
                dest.writeString(promoPrice);
                dest.writeString(singleProductQuantity);
                dest.writeString(packingQuantity);
                dest.writeString(itemTax);
            }
        }
    }
}
