package com.jdssale.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 13-07-2018.
 */

public class AllQuoteResponse {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("image_path")
    @Expose
    public String imagePath;

    @SerializedName("quotes_basic_info")
    @Expose
    public QuoteInfo quoteInfo;

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

    public QuoteInfo getQuoteInfo() {
        return quoteInfo;
    }

    public void setQuoteInfo(QuoteInfo quoteInfo) {
        this.quoteInfo = quoteInfo;
    }

    public List<QuoteItem> getQuoteItemList() {
        return quoteItemList;
    }

    public void setQuoteItemList(List<QuoteItem> quoteItemList) {
        this.quoteItemList = quoteItemList;
    }

    @SerializedName("quote_items")

    @Expose
    List<QuoteItem> quoteItemList;

    public class QuoteInfo {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("date")
        @Expose
        public String date;

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

        public String getOrderDiscount() {
            return orderDiscount;
        }

        public void setOrderDiscount(String orderDiscount) {
            this.orderDiscount = orderDiscount;
        }

        public String getTotalTax() {
            return totalTax;
        }

        public void setTotalTax(String totalTax) {
            this.totalTax = totalTax;
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

        @SerializedName("reference_no")
        @Expose
        public String referenceNo;

        @SerializedName("customer")
        @Expose
        public String customer;

        @SerializedName("biller")
        @Expose
        public String biller;

        @SerializedName("order_discount")
        @Expose
        public String orderDiscount;

        @SerializedName("total_tax")
        @Expose
        public String totalTax;

        @SerializedName("grand_total")
        @Expose
        public String grandTotal;

        @SerializedName("status")
        @Expose
        public String status;
    }

    public class QuoteItem {
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("no_of_items_in_packing")
        @Expose
        private String noOfItemsInPacking;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("quote_id")
        @Expose
        private String quoteId;
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
        private Object optionId;
        @SerializedName("net_unit_price")
        @Expose
        private String netUnitPrice;
        @SerializedName("unit_price")
        @Expose
        private String unitPrice;

        public String getSingleQuantity() {
            return singleQuantity;
        }

        public void setSingleQuantity(String singleQuantity) {
            this.singleQuantity = singleQuantity;
        }

        @SerializedName("quantity")

        @Expose
        private String quantity;
        @SerializedName("warehouse_id")
        @Expose
        private Object warehouseId;
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
        private Object discount;
        @SerializedName("item_discount")
        @Expose
        private Object itemDiscount;
        @SerializedName("subtotal")
        @Expose
        private String subtotal;
        @SerializedName("serial_no")
        @Expose
        private Object serialNo;
        @SerializedName("real_unit_price")
        @Expose
        private String realUnitPrice;
        @SerializedName("product_unit_id")
        @Expose
        private String productUnitId;
        @SerializedName("product_unit_code")
        @Expose
        private String productUnitCode;
        @SerializedName("unit_quantity")
        @Expose
        private String unitQuantity;
        @SerializedName("single_quantity")
        @Expose
        private String singleQuantity;
        @SerializedName("single_product_quantity")
        @Expose
        private String singleProductQuantity;
        @SerializedName("packing_quantity")
        @Expose
        private String packingQuantity;
        @SerializedName("block_quantity_app")
        @Expose
        private Object blockQuantityApp;
        @SerializedName("approved_for_app")
        @Expose
        private Object approvedForApp;
        @SerializedName("item_status")
        @Expose
        private Object itemStatus;
        @SerializedName("item_load_quantity")
        @Expose
        private Object itemLoadQuantity;
        @SerializedName("item_notes")
        @Expose
        private Object itemNotes;
        @SerializedName("is_scan")
        @Expose
        private String isScan;
        @SerializedName("promotion")
        @Expose
        private Object promotion;
        @SerializedName("promo_price")
        @Expose
        private String promoPrice;
        @SerializedName("start_date")
        @Expose
        private Object startDate;
        @SerializedName("end_date")
        @Expose
        private Object endDate;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNoOfItemsInPacking() {
            return noOfItemsInPacking;
        }

        public void setNoOfItemsInPacking(String noOfItemsInPacking) {
            this.noOfItemsInPacking = noOfItemsInPacking;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public Object getOptionId() {
            return optionId;
        }

        public void setOptionId(Object optionId) {
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

        public Object getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(Object warehouseId) {
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

        public Object getDiscount() {
            return discount;
        }

        public void setDiscount(Object discount) {
            this.discount = discount;
        }

        public Object getItemDiscount() {
            return itemDiscount;
        }

        public void setItemDiscount(Object itemDiscount) {
            this.itemDiscount = itemDiscount;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

        public Object getSerialNo() {
            return serialNo;
        }

        public void setSerialNo(Object serialNo) {
            this.serialNo = serialNo;
        }

        public String getRealUnitPrice() {
            return realUnitPrice;
        }

        public void setRealUnitPrice(String realUnitPrice) {
            this.realUnitPrice = realUnitPrice;
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

        public Object getBlockQuantityApp() {
            return blockQuantityApp;
        }

        public void setBlockQuantityApp(Object blockQuantityApp) {
            this.blockQuantityApp = blockQuantityApp;
        }

        public Object getApprovedForApp() {
            return approvedForApp;
        }

        public void setApprovedForApp(Object approvedForApp) {
            this.approvedForApp = approvedForApp;
        }

        public Object getItemStatus() {
            return itemStatus;
        }

        public void setItemStatus(Object itemStatus) {
            this.itemStatus = itemStatus;
        }

        public Object getItemLoadQuantity() {
            return itemLoadQuantity;
        }

        public void setItemLoadQuantity(Object itemLoadQuantity) {
            this.itemLoadQuantity = itemLoadQuantity;
        }

        public Object getItemNotes() {
            return itemNotes;
        }

        public void setItemNotes(Object itemNotes) {
            this.itemNotes = itemNotes;
        }

        public String getIsScan() {
            return isScan;
        }

        public void setIsScan(String isScan) {
            this.isScan = isScan;
        }

        public Object getPromotion() {
            return promotion;
        }

        public void setPromotion(Object promotion) {
            this.promotion = promotion;
        }

        public String getPromoPrice() {
            return promoPrice;
        }

        public void setPromoPrice(String promoPrice) {
            this.promoPrice = promoPrice;
        }

        public Object getStartDate() {
            return startDate;
        }

        public void setStartDate(Object startDate) {
            this.startDate = startDate;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

    }
}
