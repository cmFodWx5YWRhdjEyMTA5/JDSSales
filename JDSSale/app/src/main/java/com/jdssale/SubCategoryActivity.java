package com.jdssale;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jdssale.Adapter.ProductAdapter;
import com.jdssale.Adapter.SubCateAdapter;
import com.jdssale.Response.AllQuoteResponse;
import com.jdssale.Response.ProductResponse;
import com.jdssale.Response.ProductsModel;
import com.jdssale.Response.ShopResponse;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.JDSSale;
import com.jdssale.Utilities.Preferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryActivity extends BaseActivity implements SubCateAdapter.OnItemClick {

    public static final String SHOP = "shop";
    ShopResponse.ShopData shopData;
    String parentId, custName, custId,tag;
    ProductAdapter productAdapter;
    List<String> productIds;
    String items;
    List<AllQuoteResponse.QuoteItem> quoteItems;
    ProductsModel productsModel;
    List<ProductsModel> productsModels;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        ButterKnife.bind(this);
        productIds = new ArrayList<>();
        productsModels = new ArrayList<>();
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
        shopData = (ShopResponse.ShopData) getIntent().getParcelableExtra(SHOP);
        custName = getIntent().getStringExtra("custName");
        custId = getIntent().getStringExtra("custId");
        tag=getIntent().getStringExtra("tag");
        if (tag.equalsIgnoreCase("quote")) {
            getAllQuote(custId);
            tvHead.setText("+ ADD QUOTATION");
        }
        else {
            getAllSale(custId);
            tvHead.setText("+ ADD SALE");
        }
        setRecycle();
        if (shopData != null) {
            tvShop.setText(shopData.getName());
            parentId = shopData.getId();
            title.setText(custName);
            if (shopData.getChildCategoryCount().equalsIgnoreCase("0")) {
                rvSub.setVisibility(View.GONE);
                getOnlyProduct(shopData.getId());
            } else {
                rvSub.setVisibility(View.VISIBLE);
                getSubCategory(shopData.getId());
            }
        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void setRecycle() {
        rvSub.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getSubCategory(String parentId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient2().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.getSubCat(parentId, Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ShopResponse>() {
                    @Override
                    public void onResponse(Call<ShopResponse> call, Response<ShopResponse> response) {
                        if (response.body().getFlag() == 1) {
                            SubCateAdapter subCateAdapter = new SubCateAdapter(SubCategoryActivity.this, response.body().getShopDataList(), SubCategoryActivity.this);
                            rvSub.setAdapter(subCateAdapter);
                            getProucts(response.body().getShopDataList().get(0).getId());
                        } else {

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
    private void getProucts(String subCatId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient2().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.getProducts("", subCatId, "", Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                        if (response.body().getFlag() == 1) {
                            productAdapter=new ProductAdapter(SubCategoryActivity.this,response.body().getProductsList(),response.body().getImagePath(),custId,custName,tag);
                            rvItems.setAdapter(productAdapter);
                        } else {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<ProductResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getOnlyProduct(String catId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient2().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.getProducts(catId, "", "", Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                        if (response.body().getFlag() == 1) {
                            productAdapter = new ProductAdapter(SubCategoryActivity.this, response.body().getProductsList(), response.body().getImagePath(), custId, custName,tag);
                            rvItems.setAdapter(productAdapter);
                        } else {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<ProductResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getAllQuote(String custId) {
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
                        else
                        {

                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getAllSale(String custId) {
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


    @BindView(R.id.tv_shop)
    TextView tvShop;

    @BindView(R.id.rv_sub)
    RecyclerView rvSub;

    @BindView(R.id.rv_items)
    RecyclerView rvItems;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.tv_count)
    TextView tvCount;

    @BindView(R.id.tv_head)
    TextView tvHead;

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
        intent.putExtra("custId",custId);
        intent.putExtra("custName",custName);
        intent.putExtra("tag",tag);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(String value) {
        getProucts(value);
    }
}
