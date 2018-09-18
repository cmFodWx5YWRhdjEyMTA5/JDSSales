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

import com.jdssale.Adapter.AllQuoteAdapter;
import com.jdssale.Response.AllQuoteResponse;
import com.jdssale.Response.ForgetResponse;
import com.jdssale.Response.QuoteModel;
import com.jdssale.Response.QuoteResponse;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.JDSSale;
import com.jdssale.Utilities.Preferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllQuoteActivity extends BaseActivity implements AllQuoteAdapter.OnItemClick {
    String listSize = "0";
    String saleId,custId;
    List<QuoteModel> quoteModels;
    public static final String QUOTE = "client_data";
    QuoteResponse.QuoteData quoteData;
    String tag;
    AllQuoteAdapter allQuoteAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_quote);
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
        tag= getIntent().getStringExtra("tag");
        if (getIntent().getStringExtra("custId") != null) {
            custId=getIntent().getStringExtra("custId");
            if (tag.equalsIgnoreCase("quote")) {
                getAllQuote(custId);
                tvHead.setText("+ ADD QUOTATION");
            }
            else {
                getAllSale(custId);
                tvHead.setText("+ ADD SALE");
                tvSubmit.setText("SUBMIT SALE");
            }
        }
        if (getIntent().getStringExtra("custName") != null) {
            title.setText(getIntent().getStringExtra("custName"));
        }
    }

    private void setRecycle() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvItems.setLayoutManager(mLayoutManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAllQuote(String custId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.getAllQuote(custId, Preferences.getInstance().getUserId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag() == 1) {
                            allQuoteAdapter = new AllQuoteAdapter(AllQuoteActivity.this, response.body().getQuoteItemList(), response.body().getImagePath(), AllQuoteActivity.this,"quote");
                            listSize = String.valueOf(response.body().getQuoteItemList().size());
                            double totalPrice = 0;
                            for (int i = 0; i < response.body().getQuoteItemList().size(); i++) {
                                totalPrice += Double.parseDouble(response.body().getQuoteItemList().get(i).getSubtotal());
                            }
                            setItems(response.body().getQuoteInfo(), totalPrice);
                            rvItems.setAdapter(allQuoteAdapter);
                            tvCount.setText("ITEMS(" + listSize + ")");
                        } else {
                            listSize = "0";
                            tvCount.setText("ITEMS(" + listSize + ")");
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAllSale(String custId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.getAllSale(custId, Preferences.getInstance().getUserId(),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag() == 1) {
                            saleId=response.body().getQuoteInfo().getId();
                            allQuoteAdapter = new AllQuoteAdapter(AllQuoteActivity.this, response.body().getQuoteItemList(), response.body().getImagePath(), AllQuoteActivity.this,"sale");
                            listSize = String.valueOf(response.body().getQuoteItemList().size());
                            double totalPrice = 0;
                            for (int i = 0; i < response.body().getQuoteItemList().size(); i++) {
                                totalPrice += Double.parseDouble(response.body().getQuoteItemList().get(i).getSubtotal());
                            }
                            setItems(response.body().getQuoteInfo(), totalPrice);
                            rvItems.setAdapter(allQuoteAdapter);
                            tvCount.setText("ITEMS(" + listSize + ")");
                        } else {
                            listSize = "0";
                            tvCount.setText("ITEMS(" + listSize + ")");
                        }
                        hideProgressBar();
                        }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void submitQuote() {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.submitQuote(custId, Preferences.getInstance().getUserId(),"","","","" ,"",Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ForgetResponse>() {
                    @Override
                    public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                        if (response.body().getFlag() == 1) {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void submitSale() {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.submitSale(saleId,"12","123","344","1","1",Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ForgetResponse>() {
                    @Override
                    public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                        if (response.body().getFlag() == 1) {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

    private void setItems(AllQuoteResponse.QuoteInfo quoteInfo, double totalPrice) {
        tvDate.setText(convertDate(quoteInfo.getDate()));
        tvPrice.setText("€ " + String.valueOf(totalPrice));
        Log.d("ppppp", "ppppp" + quoteInfo.getGrandTotal());
        tvCount.setText("ITEMS(" + listSize + ")");
    }

    public String convertDate(String date) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
        Date d = null;
        try {
            d = input.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
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

    @BindView(R.id.tv_head)
    TextView tvHead;

    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.rl_submit)
    void rlSubmit(){
        if (tag.equalsIgnoreCase("quote")) {
            submitQuote();
        }
        else {
            submitSale();
        }
    }

    @Override
    public void onClick(String value) {

    }

    @Override
    public void sendList(ArrayList<QuoteModel> quoteModels) {

    }
}
