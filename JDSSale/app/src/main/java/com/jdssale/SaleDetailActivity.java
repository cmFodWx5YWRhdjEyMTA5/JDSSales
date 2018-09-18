package com.jdssale;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jdssale.Adapter.SaleItemAdapter;
import com.jdssale.Response.ForgetResponse;
import com.jdssale.Response.QuoteModel;
import com.jdssale.Response.SaleDetailResponse;
import com.jdssale.Response.SaleResponse;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.JDSSale;
import com.jdssale.Utilities.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleDetailActivity extends BaseActivity implements SaleItemAdapter.OnItemClick {

    String listSize="0";
    String finalPrice,saleId,custName,custId,saleDate,referNo,grandTotal,pending;
    public static final String SALE = "sale_data";
    SaleResponse.SaleData saleData;
    List<QuoteModel> quoteModelList;
    SaleItemAdapter saleItemAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);
        ButterKnife.bind(this);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.drawable.left_arrow));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
        setRecycle();
        saleData = ( SaleResponse.SaleData) getIntent().getParcelableExtra(SALE);
        if (saleData!=null){
            title.setText(saleData.getCustomer());
            String[] splited = saleData.getDate().split("\\s+");
            finalPrice=String.format("%.2f", Double.parseDouble(saleData.getGrandTotal()));
            saleId=saleData.getId();
            custName=saleData.getCustomer();
            custId=saleData.getCustomerId();
            saleDate=splited[0];
            setItems(finalPrice,splited[0]);
            getSaleItems(saleData.getId());
        }





    }

    private void setRecycle() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvItems.setLayoutManager(mLayoutManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getSaleItems(String saleId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.saleDetail(saleId, Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<SaleDetailResponse>() {
                    @Override
                    public void onResponse(Call<SaleDetailResponse> call, Response<SaleDetailResponse> response) {
                        if (response.body().getFlag()==1){
                            referNo=response.body().getSaleDetails().get(0).getReferenceNo();
                            grandTotal=response.body().getSaleDetails().get(0).getGrandTotal();
                            if (response.body().getSaleDetails().get(0).getPaymentReceived()==null||response.body().getSaleDetails().get(0).getPaymentReceived().equalsIgnoreCase("")) {
                                rPayment.setText("€ " + "0.00");
                            }
                            else {
                                rPayment.setText("€ " + response.body().getSaleDetails().get(0).getPaymentReceived());
                            }
                            if (response.body().getSaleDetails().get(0).getPaymentLeft()==null||response.body().getSaleDetails().get(0).getPaymentLeft().equalsIgnoreCase("")) {
                                pPayment.setText("€ " + "0.00");
                                pending="0.00";
                            }
                            else {
                                pPayment.setText("€ " + response.body().getSaleDetails().get(0).getPaymentLeft());
                                pending = response.body().getSaleDetails().get(0).getPaymentLeft();
                            }
                            saleItemAdapter = new SaleItemAdapter(SaleDetailActivity.this, response.body().getSaleDetails().get(0).getSaleItems(),response.body().getImagePath(),SaleDetailActivity.this);
                            listSize=String.valueOf(response.body().getSaleDetails().get(0).getSaleItems().size());
                            rvItems.setAdapter(saleItemAdapter);
                            tvCount.setText("ITEMS("+listSize+")");
                        }
                        else
                        {
                            listSize="0";
                            tvCount.setText("ITEMS("+listSize+")");
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<SaleDetailResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateSale(List<QuoteModel> quoteModels) {
        final JSONArray req = new JSONArray();
        Log.d("packageSlotsList","packageSlotsList"+quoteModels.size());
        for (int i = 0; i < quoteModels.size(); i++) {
            QuoteModel quoteModel = quoteModels.get(i);

                    JSONObject reqObj = new JSONObject();
                    try {
                        reqObj.put("id", quoteModel.getQuoteItemId());
                        reqObj.put("net_unit_price", quoteModel.getNetUnitPrice());
                        reqObj.put("quantity", quoteModel.getQuantity());
                        reqObj.put("unit_price", quoteModel.getUnitPrice());
                        reqObj.put("packing_quantity", quoteModel.getPackingQuantity());
                        reqObj.put("single_quantity", quoteModel.getSingleProductQuantity());
                        reqObj.put("subtotal", quoteModel.getSubtotal());
                        reqObj.put("item_tax", quoteModel.getItemTax());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    req.put(reqObj);
                    try {
                        Log.d("orderPackage", "orderPackage" + req.get(i).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
        }
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Adding Slots...");
        jdsSaleService.updateSale(saleId,referNo,custId,custName,Preferences.getInstance().getUserId(),Preferences.getInstance().getUserName(),tvPrice.getText().toString(),"","","",String.valueOf(req),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ForgetResponse>() {
                    @Override
                    public void onResponse(Call<ForgetResponse> call, final Response<ForgetResponse> response) {
                        if (response.body().getFlag()==1) {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                            overridePendingTransition(R.anim.left_in, R.anim.right_out);
                        } else {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<ForgetResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void setItems(String price, String date) {
        tvDate.setText(date);
        tvPrice.setText(price);
        Log.d("ppppp","ppppp"+price);
        tvCount.setText("ITEMS("+listSize+")");
    }

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_items)
    RecyclerView rvItems;

    @BindView(R.id.tv_count)
    TextView tvCount;

    @BindView(R.id.tv_ppayment)
    TextView pPayment;

    @BindView(R.id.tv_rpayment)
    TextView rPayment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.rl_update)
    void update(){
        updateSale(quoteModelList);
    }

    @OnClick(R.id.rl_pending)
    void rlPending(){
        Intent intent=new Intent(SaleDetailActivity.this,SalePaymentActivity.class);
        intent.putExtra("custId",custId);
        intent.putExtra("custName",custName);
        intent.putExtra("referNo",referNo);
        intent.putExtra("pending",pending);
        intent.putExtra("saleDate",saleDate);
        intent.putExtra("saleId",saleId);
        intent.putExtra("grandTotal",grandTotal);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @Override
    public void onClick(String value) {

    }

    @Override
    public void sendList(List<QuoteModel> quoteModels) {
        quoteModelList=quoteModels;
    }
}
