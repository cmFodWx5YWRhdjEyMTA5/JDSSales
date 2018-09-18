package com.jdssale.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 11-07-2018.
 */

public class PayDetailResponse {

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<PayData> getPayDataList() {
        return payDataList;
    }

    public void setPayDataList(List<PayData> payDataList) {
        this.payDataList = payDataList;
    }

    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("data")
    @Expose
    List<PayData> payDataList;


    public class PayData {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("date")
        @Expose
        public String date;

        @SerializedName("sale_id")
        @Expose
        public String saleId;

        @SerializedName("return_id")
        @Expose
        public String returnId;

        @SerializedName("purchase_id")
        @Expose
        public String purchaseId;

        @SerializedName("reference_no")
        @Expose
        public String referenceNo;

        @SerializedName("transaction_id")
        @Expose
        public String transactionId;

        @SerializedName("paid_by")
        @Expose
        public String paidBy;

        @SerializedName("cheque_no")
        @Expose
        public String chequeNo;

        @SerializedName("cc_no")
        @Expose
        public String ccNo;

        @SerializedName("cc_holder")
        @Expose
        public String ccHolder;

        @SerializedName("cc_month")
        @Expose
        public String ccMonth;

        @SerializedName("cc_year")
        @Expose
        public String ccYear;

        @SerializedName("cc_type")
        @Expose
        public String ccType;

        @SerializedName("amount")
        @Expose
        public String amount;

        @SerializedName("currency")
        @Expose
        public String currency;

        @SerializedName("created_by")
        @Expose
        public String createdBy;

        @SerializedName("attachment")
        @Expose
        public String attachment;

        @SerializedName("type")
        @Expose
        public String type;

        @SerializedName("note")
        @Expose
        public String note;

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

        public String getSaleId() {
            return saleId;
        }

        public void setSaleId(String saleId) {
            this.saleId = saleId;
        }

        public String getReturnId() {
            return returnId;
        }

        public void setReturnId(String returnId) {
            this.returnId = returnId;
        }

        public String getPurchaseId() {
            return purchaseId;
        }

        public void setPurchaseId(String purchaseId) {
            this.purchaseId = purchaseId;
        }

        public String getReferenceNo() {
            return referenceNo;
        }

        public void setReferenceNo(String referenceNo) {
            this.referenceNo = referenceNo;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getPaidBy() {
            return paidBy;
        }

        public void setPaidBy(String paidBy) {
            this.paidBy = paidBy;
        }

        public String getChequeNo() {
            return chequeNo;
        }

        public void setChequeNo(String chequeNo) {
            this.chequeNo = chequeNo;
        }

        public String getCcNo() {
            return ccNo;
        }

        public void setCcNo(String ccNo) {
            this.ccNo = ccNo;
        }

        public String getCcHolder() {
            return ccHolder;
        }

        public void setCcHolder(String ccHolder) {
            this.ccHolder = ccHolder;
        }

        public String getCcMonth() {
            return ccMonth;
        }

        public void setCcMonth(String ccMonth) {
            this.ccMonth = ccMonth;
        }

        public String getCcYear() {
            return ccYear;
        }

        public void setCcYear(String ccYear) {
            this.ccYear = ccYear;
        }

        public String getCcType() {
            return ccType;
        }

        public void setCcType(String ccType) {
            this.ccType = ccType;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getPosPaid() {
            return posPaid;
        }

        public void setPosPaid(String posPaid) {
            this.posPaid = posPaid;
        }

        public String getPosBalance() {
            return posBalance;
        }

        public void setPosBalance(String posBalance) {
            this.posBalance = posBalance;
        }

        public String getApprovalCode() {
            return approvalCode;
        }

        public void setApprovalCode(String approvalCode) {
            this.approvalCode = approvalCode;
        }

        @SerializedName("pos_paid")
        @Expose
        public String posPaid;

        @SerializedName("pos_balance")
        @Expose
        public String posBalance;

        @SerializedName("approval_code")
        @Expose
        public String approvalCode;


    }
}
