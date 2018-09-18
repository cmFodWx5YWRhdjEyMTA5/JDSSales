package com.jdssale.Retrofit;

import com.jdssale.Response.AllQuoteResponse;
import com.jdssale.Response.ClientPayResponse;
import com.jdssale.Response.CustInfoResponse;
import com.jdssale.Response.DataResponse;
import com.jdssale.Response.ForgetResponse;
import com.jdssale.Response.HistoryResponse;
import com.jdssale.Response.LoginResponse;
import com.jdssale.Response.MoreProductResponse;
import com.jdssale.Response.OrderResponse;
import com.jdssale.Response.PayDetailResponse;
import com.jdssale.Response.PendingResponse;
import com.jdssale.Response.ProDetailResponse;
import com.jdssale.Response.ProductResponse;
import com.jdssale.Response.QuoteDetailResponse;
import com.jdssale.Response.QuoteResponse;
import com.jdssale.Response.RouteResponse;
import com.jdssale.Response.SaleDetailResponse;
import com.jdssale.Response.SaleResponse;
import com.jdssale.Response.ShopResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dikhong on 06-07-2018.
 */

public interface JDSSaleService {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email,
                              @Field("password") String password,
                              @Field("device_token") String deviceToken);

    @FormUrlEncoded
    @POST("forgot_password")
    Call<ForgetResponse> forget(@Field("email") String email);

    @FormUrlEncoded
    @POST("quotation_list")
    Call<QuoteResponse> getquotation(@Field("id") String id,
                                     @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("sale_list")
    Call<SaleResponse> getSale(@Field("id") String id,
                               @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("customer_info")
    Call<PendingResponse> pendingList(@Field("id") String id,
                                      @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("client_pending_payment")
    Call<ClientPayResponse> clientPay(@Field("customer_id") String customerId,
                                      @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("assign_order_list")
    Call<OrderResponse> orderList(@Field("driver_id") String driverId,
                                  @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("delivery_route")
    Call<RouteResponse> getRoute(@Field("driver_id") String driverId,
                                 @Field("authorization") String authorization);


    @GET("get_all_parent_categories")
    Call<ShopResponse> shopList(@Query("customer_id") String customerId,
                                @Query("authorization") String authorization);

    @GET("get_all_sub_categories")
    Call<ShopResponse> getSubCat(@Query("parent_category_id") String parentCategoryId,
                                 @Query("authorization") String authorization);

    @GET("get_all_products")
    Call<ProductResponse> getProducts(@Query("category_id") String category_id,
                                      @Query("subcategory_id") String subcategory_id,
                                      @Query("brand") String brand,
                                      @Query("authorization") String authorization);

    @GET("get_all_quotation_items")
    Call<AllQuoteResponse> getAllQuote(@Query("customer_id") String customerId,
                                       @Query("biller_id") String billerId,
                                       @Query("authorization") String authorization);

    @GET("get_all_sale_items")
    Call<AllQuoteResponse> getAllSale(@Query("customer_id") String customerId,
                                      @Query("biller_id") String billerId,
                                      @Query("authorization") String authorization);


    @GET("product_details")
    Call<ProDetailResponse> productDetail(@Query("product_id") String productId,
                                          @Query("authorization") String authorization);

    @GET("final_submit_quotation_items")
    Call<ForgetResponse> submitQuote(@Query("customer_id") String customerId,
                                     @Query("biller_id") String billerId,
                                     @Query("total") String total,
                                     @Query("grand_total") String grandTotal,
                                     @Query("order_discount") String orderDiscount,
                                     @Query("total_discount") String totalDiscount,
                                     @Query("tax") String tax,
                                     @Query("authorization") String authorization);

    @GET("final_add_sale_item")
    Call<ForgetResponse> submitSale(@Query("sale_id") String saleId,
                                     @Query("total") String total,
                                     @Query("total_discount") String totalDiscount,
                                     @Query("grand_total") String grandTotal,
                                     @Query("total_items") String totalItems,
                                     @Query("tax") String tax,
                                     @Query("authorization") String authorization);



    @FormUrlEncoded
    @POST("sale_pending_payment_detail")
    Call<PayDetailResponse> payDetail(@Field("customer_id") String customerId,
                                      @Field("sale_id") String saleId,
                                      @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("load_more")
    Call<MoreProductResponse> loadProducts(@Field("product_id") String productId,
                                           @Field("category_id") String category_id,
                                           @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("add_quotation")
    Call<ForgetResponse> addQuote(@Field("customer_id") String customerId,
                                  @Field("biller_id") String billerId,
                                  @Field("customer_name") String customerName,
                                  @Field("product_id") String productId,
                                  @Field("unit_price") String unitPrice,
                                  @Field("quantity") String quantity,
                                  @Field("single_product_quantity") String singleProductQuantity,
                                  @Field("packing_quantity") String packingQuantity,
                                  @Field("subtotal") String subTotal,
                                  @Field("item_tax") String itemTax,
                                  @Field("quote_id") String quoteId,
                                  @Field("user_confirm") String userConfirm,
                                  @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("collect_payment")
    Call<DataResponse> collectPay(@Field("amount") String amount,
                                    @Field("driver_id") String driverId,
                                    @Field("customer_id") String customerId,
                                    @Field("sale_id") String saleId,
                                    @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("add_sale_item")
    Call<DataResponse> addSale(@Field("reference_no") String referenceNo,
                               @Field("customer_id") String customerId,
                               @Field("sale_id") String saleId,
                               @Field("product_id") String productId,
                               @Field("customer") String customer,
                               @Field("biller_id") String billerId,
                               @Field("net_unit_price") String netUnitPrice,
                               @Field("unit_quantity") String unitQuantity,
                               @Field("tax") String tax,
                               @Field("quantity") String quantity,
                               @Field("subtotal") String subtotal,
                               @Field("packing_quantity") String packingQuantity,
                               @Field("single_quantity") String singleQuantity,
                               @Field("authorization") String authorization);


    @GET("delete_quotation_item")
    Call<ForgetResponse> deleteQuote(@Query("quote_item_id") String quoteItemId,
                                     @Query("authorization") String authorization);

    @FormUrlEncoded
    @POST("remove_sale_item")
    Call<ForgetResponse> deleteSale(@Field("item_id") String itemId,
                                    @Field("authorization") String authorization);


    @GET("edit_quotation_item")
    Call<ForgetResponse> editQuote(@Query("quote_item_id") String quoteItemId,
                                   @Query("unit_price") String unitPrice,
                                   @Query("single_product_quantity") String singleProductQuantity,
                                   @Query("packing_quantity") String packingQuantity,
                                   @Query("quantity") String quantity,
                                   @Query("subtotal") String subtotal,
                                   @Query("authorization") String authorization);

    @GET("edit_sale_item")
    Call<ForgetResponse> editSale(@Query("sale_item_id") String saleItemId,
                                  @Query("unit_price") String unitPrice,
                                  @Query("unit_quantity") String unitQuantity,
                                  @Query("packing_quantity") String packingQuantity,
                                  @Query("quantity") String quantity,
                                  @Query("subtotal") String subtotal,
                                  @Query("authorization") String authorization);

    @FormUrlEncoded
    @POST("edit_sale_list")
    Call<ForgetResponse> updateSale(@Field("id") String id,
                                  @Field("reference_no") String referenceNo,
                                  @Field("customer_id") String customerId,
                                  @Field("customer") String customer,
                                  @Field("biller_id") String billerId,
                                  @Field("biller") String biller,
                                  @Field("total") String total,
                                  @Field("total_tax") String totalTax,
                                  @Field("grand_total") String grandTotal,
                                  @Field("total_items") String totalItems,
                                  @Field("sale_items") String saleItems,
                                  @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("edit_quote_list")
    Call<ForgetResponse> updateQuote(@Field("id") String id,
                                    @Field("reference_no") String referenceNo,
                                    @Field("total") String total,
                                    @Field("order_discount") String orderDiscount,
                                    @Field("total_tax") String totalTax,
                                    @Field("grand_total") String grandTotal,
                                    @Field("updated_by") String updatedBy,
                                    @Field("quote_items") String quoteItems,
                                    @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("collect_payment")
    Call<DataResponse> addPay(@Field("amount") String amount,
                              @Field("driver_id") String driverId,
                              @Field("customer_id") String customerId,
                              @Field("sale_id") String saleId,
                              @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("check_payment_history")
    Call<HistoryResponse> getHistory(@Field("driver_id") String driverId,
                                     @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("logout")
    Call<DataResponse> logout(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("quotation_detail")
    Call<QuoteDetailResponse> quoteDetail(@Field("quotation_id") String quotationId,
                                          @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("client_info")
    Call<CustInfoResponse> clientInfo(@Field("client_id") String clientId,
                                      @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("client_pending_payment")
    Call<ClientPayResponse> clientPayment(@Field("customer_id") String customerId,
                                      @Field("authorization") String authorization);


    @FormUrlEncoded
    @POST("sale_detail")
    Call<SaleDetailResponse> saleDetail(@Field("sale_id") String saleId,
                                        @Field("authorization") String authorization);

    @FormUrlEncoded
    @POST("complete_order_delivery")
    Call<ForgetResponse> orderDelivery(@Field("quote_id") String quoteId,
                                       @Field("authorization") String authorization);

}
