package com.jdssale;

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

import com.jdssale.Adapter.QuoteItemsAdapter;
import com.jdssale.Response.ForgetResponse;
import com.jdssale.Response.QuoteDetailResponse;
import com.jdssale.Response.QuoteModel;
import com.jdssale.Response.QuoteResponse;
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

public class QuoteDetailActivity extends BaseActivity implements QuoteItemsAdapter.OnItemClick {
    String listSize="0";
    String finalPrice,referenceNo,quoteId;
    List<QuoteModel> quoteModelList;
    public static final String QUOTE = "client_data";
    QuoteResponse.QuoteData quoteData;
    QuoteItemsAdapter quotationAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_detail);
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
        quoteData = (QuoteResponse.QuoteData) getIntent().getParcelableExtra(QUOTE);
        if (quoteData!=null){
            quoteId=quoteData.getId();
            title.setText(quoteData.getCustomer());
            String[] splited = quoteData.getDate().split("\\s+");
            finalPrice=String.format("%.2f", Double.parseDouble(quoteData.getGrandTotal()));
            setItems(finalPrice,splited[0]);
            getQuoteItems(quoteData.getId());
        }





    }

    private void setRecycle() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvItems.setLayoutManager(mLayoutManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getQuoteItems(String quoteId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.quoteDetail(quoteId,Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<QuoteDetailResponse>() {
                    @Override
                    public void onResponse(Call<QuoteDetailResponse> call, Response<QuoteDetailResponse> response) {
                        if (response.body().getFlag()==1){
                            referenceNo=response.body().getQuoteDetailList().getReferenceNo();
                            quotationAdapter = new QuoteItemsAdapter(getApplicationContext(), response.body().getQuoteDetailList().getQuoteItemsList(),response.body().getImagePath(),QuoteDetailActivity.this);
                            listSize=String.valueOf(response.body().getQuoteDetailList().getQuoteItemsList().size());
                            rvItems.setAdapter(quotationAdapter);
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
                    public void onFailure(Call<QuoteDetailResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateQuote(List<QuoteModel> quoteModels) {
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
                reqObj.put("single_product_quantity", quoteModel.getSingleProductQuantity());
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
        //total=net_price-promo_price*quantity
        //order_discount=promo_price*quantity
        //total_tax=quantity*item_tax add
        //shipping=""
        //grand_total=total+total_tax
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Adding Slots...");
        jdsSaleService.updateQuote(quoteId,referenceNo,"8.00","0.00","0.00","8.00",Preferences.getInstance().getUserId(),String.valueOf(req),Preferences.getInstance().getAuthKey()).
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.rl_update)
    void update(){
        updateQuote(quoteModelList);
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


    @Override
    public void onClick(String value) {
        double val = 0;
        val=Double.parseDouble(value);
        double sum = 0;
        sum += val;
        double totalPrice=sum;
        tvPrice.setText(String.valueOf(totalPrice));
        Log.d("sum","sum"+totalPrice);

    }

    @Override
    public void sendList(List<QuoteModel> quoteModels) {
        quoteModelList=quoteModels;
        for (int i=0;i<quoteModels.size();i++)
        {
            Log.d("quoteModels","quoteModels"+quoteModels.get(i).getSubtotal());
        }
    }
}
