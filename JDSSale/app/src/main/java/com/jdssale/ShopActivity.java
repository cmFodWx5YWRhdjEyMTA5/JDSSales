package com.jdssale;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jdssale.Adapter.ShopAdapter;
import com.jdssale.Response.AllQuoteResponse;
import com.jdssale.Response.ShopResponse;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.JDSSale;
import com.jdssale.Utilities.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopActivity extends BaseActivity {
    String custName,custId,tag, items;
    ShopAdapter shopAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
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
        custName= getIntent().getStringExtra("customer_name");
        custId= getIntent().getStringExtra("customer_id");
        tag=getIntent().getStringExtra("tag");
        title.setText(custName);
        if (getIntent().getStringExtra("customer_id")!=null)
        {
            String custId= getIntent().getStringExtra("customer_id");
            getShopList(custId);
            if (tag.equalsIgnoreCase("quote")) {
                getAllQuote(custId);
                tvHead.setText("+ ADD QUOTATION");
            }
            else {
                getAllSale(custId);
                tvHead.setText("+ ADD SALE");
            }
        }
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 shopAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getShopList(final String custId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient2().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.shopList(custId, Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ShopResponse>() {
                    @Override
                    public void onResponse(Call<ShopResponse> call, Response<ShopResponse> response) {
                        if (response.body().getFlag()==1){
                            shopAdapter = new ShopAdapter(ShopActivity.this, response.body().getShopDataList(),response.body().getImagePath(),custName,custId,tag);
                            rvItems.setAdapter(shopAdapter);
                        }
                        else
                        {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<ShopResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAllQuote(String custId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        jdsSaleService.getAllQuote(custId, Preferences.getInstance().getUserId(),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag()==1){
                             items= String.valueOf(response.body().getQuoteItemList().size());
                            if (items.equalsIgnoreCase("0")){
                                tvCount.setText("ITEMS (" + "0" + ")");
                            }
                            else {
                                tvCount.setText("ITEMS (" + items + ")");
                            }
                        }
                        else if (response.body().getFlag()==0)
                        {
                            tvCount.setText("ITEMS (" + "0" + ")");
                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAllSale(String custId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        jdsSaleService.getAllSale(custId, Preferences.getInstance().getUserId(),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag()==1){
                             items= String.valueOf(response.body().getQuoteItemList().size());
                            if (items.equalsIgnoreCase("0")){
                                tvCount.setText("ITEMS (" + "0" + ")");
                            }
                            else {
                                tvCount.setText("ITEMS (" + items + ")");
                            }
                        }
                        else
                        {
                            tvCount.setText("ITEMS (" + "0" + ")");
                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void setRecycle() {
        GridLayoutManager layoutManager = new GridLayoutManager(ShopActivity.this, 3);
        rvItems.setLayoutManager(layoutManager);
    }


    @BindView(R.id.rv_items)
    RecyclerView rvItems;

    @OnClick(R.id.rl_home)
    void rlHome(){
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.rl_cart)
    void rlCart(){
            Intent intent = new Intent(getApplicationContext(), AllQuoteActivity.class);
            intent.putExtra("custId", custId);
            intent.putExtra("custName", custName);
            intent.putExtra("tag",tag );
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_count)
    TextView tvCount;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.tv_head)
    TextView tvHead;


}
