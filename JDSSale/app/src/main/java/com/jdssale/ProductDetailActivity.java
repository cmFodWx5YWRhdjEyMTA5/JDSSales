package com.jdssale;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdssale.Adapter.LoadMoreAdapter;
import com.jdssale.Adapter.SliderPagerAdapter;
import com.jdssale.Response.AllQuoteResponse;
import com.jdssale.Response.DataResponse;
import com.jdssale.Response.ForgetResponse;
import com.jdssale.Response.MoreProductResponse;
import com.jdssale.Response.ProDetailResponse;
import com.jdssale.Response.ProductResponse;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.JDSSale;
import com.jdssale.Utilities.Preferences;
import com.jdssale.Utilities.QuantityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private int dotsCount = 0;
    private ImageView[] dots;
    String finalPrice1,referenceNo,saleId;
    String tag;
    String custId, custName,productId,quoteItemId,saleItemId;
    private SliderPagerAdapter sliderPagerAdapter;
    public static final String PRODUCT = "product";
    public static final String PRODUCT_DATA = "product_data";
    List<String> productIds=new ArrayList<>();

    ProductResponse.Product product;
    MoreProductResponse.MoreProduct moreProduct;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
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
        tag=getIntent().getStringExtra("tag");
        if (tag.equalsIgnoreCase("sale"))
        {
            tvHead.setText("+ ADD SALE");
        }
        product = (ProductResponse.Product) getIntent().getParcelableExtra(PRODUCT);
        if ((moreProduct = (MoreProductResponse.MoreProduct) getIntent().getParcelableExtra(PRODUCT_DATA))==null){


        }
        else {
            moreProduct = (MoreProductResponse.MoreProduct) getIntent().getParcelableExtra(PRODUCT_DATA);
            getProduct(moreProduct.getProductId());
            //loadProducts(moreProduct.getProductId(),moreProduct.getCategoryId());
            if (getIntent().getStringExtra("custId")!=null){
                if (tag.equalsIgnoreCase("quote")) {
                    getAllQuote(getIntent().getStringExtra("custId"), moreProduct.getProductId());
                }
                else {
                    getAllSale(getIntent().getStringExtra("custId"), moreProduct.getProductId());
                }
            }
            if (getIntent().getStringExtra("custName")!=null){
                title.setText(getIntent().getStringExtra("custName"));
            }
        }
        custId = getIntent().getStringExtra("custId");
        custName = getIntent().getStringExtra("custName");
        title.setText(custName);
        rvMore.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
        viewPager.setOnPageChangeListener(this);
        if (product != null) {
            productId=product.getProductId();
            getProduct(product.getProductId());
            loadProducts(product.getProductId(),product.getCategoryId());
        }
        if (tag.equalsIgnoreCase("quote")) {
            getAllQuote(custId, productId);
        }
        else {
            getAllSale(custId,productId);
        }

      qvItem.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically, String tag1) {
                if (tag1.equalsIgnoreCase("add")) {
                    if (tag.equalsIgnoreCase("quote")) {
                        matchQuote(productId);
                    }
                    else {
                        matchQuote(productId);
                    }
                }
                if (tag1.equalsIgnoreCase("sub")) {
                    if (tag.equalsIgnoreCase("quote")) {
                        if (newQuantity == 0) {
                            removeQuote(quoteItemId);
                        } else {
                            editQuote(quoteItemId, finalPrice1, String.valueOf(qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity() * Double.parseDouble(finalPrice1)));
                        }
                    }
                    else {
                        if (newQuantity == 0) {
                            removeSale(saleItemId);
                        } else {
                            editSale(saleItemId, finalPrice1, String.valueOf(qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity() * Double.parseDouble(finalPrice1)));
                        }
                    }
                }
            }

            @Override
            public void onLimitReached() {

            }
        });

        qvCarton.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically, String tag1) {
                if (tag1.equalsIgnoreCase("add")) {
                    if (tag.equalsIgnoreCase("quote")) {
                        matchQuote(productId);
                    }
                    else {
                        matchQuote(productId);
                    }
                }
                if (tag1.equalsIgnoreCase("sub")) {
                    if (tag.equalsIgnoreCase("quote")) {
                        if (newQuantity == 0) {
                            removeQuote(quoteItemId);
                        } else {
                            editQuote(quoteItemId, finalPrice1, String.valueOf(qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity() * Double.parseDouble(finalPrice1)));
                        }
                    }
                    else {
                        if (newQuantity == 0) {
                            removeSale(saleItemId);
                        } else {
                            editSale(saleItemId, finalPrice1, String.valueOf(qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity() * Double.parseDouble(finalPrice1)));
                        }
                    }
                }
            }

            @Override
            public void onLimitReached() {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void removeSale(String id) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait....");
        jdsSaleService.deleteSale(id, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    getAllSale(custId,productId);
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
    private void getProduct(String productId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient2().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.productDetail(productId, Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ProDetailResponse>() {
                    @Override
                    public void onResponse(Call<ProDetailResponse> call, Response<ProDetailResponse> response) {
                        if (response.body().getFlag() == 1) {
                            dotsCount = response.body().getProDetail().getProductPhotos().size();
                            sliderPagerAdapter = new SliderPagerAdapter(ProductDetailActivity.this, response.body().getProDetail().getProductPhotos(), response.body().getImagePath());
                            viewPager.setAdapter(sliderPagerAdapter);
                            viewPager.setCurrentItem(0);
                            setPageViewIndicator(dotsCount);
                            setFields(response.body().getProDetail());
                        }
                        hideProgressBar();
                    }


                    @Override
                    public void onFailure(Call<ProDetailResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setFields(ProDetailResponse.ProDetail proDetail) {
        finalPrice1=String.format("%.2f", Double.parseDouble(proDetail.getPrice()));
        if (proDetail.getPromoPrice() == null || proDetail.getPromoPrice().equals("")) {
            tvPromoPrice.setVisibility(View.GONE);
            tvPrice.setText("€ "+finalPrice1);
        } else {
            double discount = Double.parseDouble(proDetail.getPrice()) - Double.parseDouble(proDetail.getPromoPrice());
            tvPromoPrice.setVisibility(View.VISIBLE);
            tvPromoPrice.setText(finalPrice1);
            tvPrice.setText(String.valueOf(discount));
            tvPrice.setVisibility(View.GONE);
        }
        tvCarton.setText(proDetail.getNoOfItemsInPacking() + " items in each carton.");
        String[] splited = proDetail.getStockLeft().split("\\.");
        tvStock.setText("Only " + splited[0] + " items left in stock");
        tvShop.setText(proDetail.getName());


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void matchQuote(final String productId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        jdsSaleService.getAllQuote(custId, Preferences.getInstance().getUserId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag() == 1) {
                            for (int i = 0; i < response.body().getQuoteItemList().size(); i++) {
                                productIds.add(response.body().getQuoteItemList().get(i).getProductId());
                            }

                            if (productIds.contains(productId)) {
                                if (tag.equalsIgnoreCase("quote")) {
                                    editQuote(quoteItemId, finalPrice1, String.valueOf(qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity() * Double.parseDouble(finalPrice1)));
                                }
                                else {
                                    if (referenceNo.equalsIgnoreCase("0"))
                                    {
                                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProductDetailActivity.this);
                                        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
                                        final View dialogView = inflater.inflate(R.layout.refer_layout, null);
                                        dialogBuilder.setView(dialogView);
                                        final AlertDialog alertDialog = dialogBuilder.create();
                                        final EditText editText = (EditText) dialogView.findViewById(R.id.et_refer);
                                        Button button=(Button) dialogView.findViewById(R.id.bt_add);
                                        button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (editText.getText().length()!=0) {
                                                    addSale(editText.getText().toString(), product);
                                                    alertDialog.dismiss();
                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(),"Please enter reference number",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                        alertDialog.show();
                                    }
                                    editSale(saleItemId, finalPrice1, String.valueOf(qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity() * Double.parseDouble(finalPrice1)));
                                }
                            }
                            else {
                                addQuote(product);
                            }
                        }else {
                            productIds.add("00");
                            if (productIds.contains(productId)) {

                                if (tag.equalsIgnoreCase("quote")) {
                                    editQuote(quoteItemId, finalPrice1, String.valueOf(qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity() * Double.parseDouble(finalPrice1)));
                                }
                                else {
                                    editSale(saleItemId, finalPrice1, String.valueOf(qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity()), String.valueOf(qvCarton.getQuantity() + qvItem.getQuantity() * Double.parseDouble(finalPrice1)));
                                }
                            } else {
                                addQuote(product);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void editQuote(String id, String unitPrice, String single, String carton, String quantity, String subTotal) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait....");
        jdsSaleService.editQuote(id, unitPrice, single, carton, quantity, subTotal, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    getAllQuote(custId,productId);
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
    private void editSale(String saleId, String unitPrice, String single, String carton, String quantity, String subTotal) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait....");
        jdsSaleService.editSale(saleId, unitPrice, single, carton, quantity, subTotal, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
    private void getAllQuote(String custId, final String productId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        jdsSaleService.getAllQuote(custId, Preferences.getInstance().getUserId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag() == 1) {
                            String items = String.valueOf(response.body().getQuoteItemList().size());
                            tvCount.setText("ITEMS (" + items + ")");
                            for (int i=0;i<response.body().getQuoteItemList().size();i++)
                            {
                                if (response.body().getQuoteItemList().get(i).getProductId().matches(productId)){
                                    String totalItems = response.body().getQuoteItemList().get(i).getQuantity().substring(0, response.body().getQuoteItemList().get(i).getQuantity().indexOf("."));
                                    tvItems.setText("Total Items: "+totalItems);
                                    double totalPrice=Double.parseDouble(totalItems)*Double.parseDouble(response.body().getQuoteItemList().get(i).getUnitPrice());
                                    String finalPrice=String.format("%.2f", totalPrice);
                                    tvTotal.setText("€ "+finalPrice);
                                    quoteItemId=response.body().getQuoteItemList().get(i).getId();
                                    qvCarton.setQuantity(Integer.parseInt(response.body().getQuoteItemList().get(i).getPackingQuantity()));
                                    qvItem.setQuantity(Integer.parseInt(response.body().getQuoteItemList().get(i).getSingleProductQuantity()));
                                }
                                else {

                                }
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAllSale(String custId, final String productId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        jdsSaleService.getAllSale(custId, Preferences.getInstance().getUserId(),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag()==1){
                            String items = String.valueOf(response.body().getQuoteItemList().size());
                            tvCount.setText("ITEMS (" + items + ")");
                            if (response.body().getQuoteInfo().getReferenceNo()==null)
                            {
                                referenceNo=response.body().getQuoteInfo().getReferenceNo();
                            }
                            else {
                                referenceNo=response.body().getQuoteInfo().getReferenceNo();
                            }
                            if (response.body().getQuoteInfo().getId()==null)
                            {
                                saleId=response.body().getQuoteInfo().getId();
                            }
                            else {
                                saleId=response.body().getQuoteInfo().getId();
                            }
                            for (int i=0;i<response.body().getQuoteItemList().size();i++)
                            {
                                if (response.body().getQuoteItemList().get(i).getProductId().matches(productId)){
                                    saleItemId=response.body().getQuoteItemList().get(i).getId();
                                    String totalItems = response.body().getQuoteItemList().get(i).getQuantity().substring(0, response.body().getQuoteItemList().get(i).getQuantity().indexOf("."));
                                    tvItems.setText("Total Items: "+totalItems);
                                    double totalPrice=Double.parseDouble(totalItems)*Double.parseDouble(response.body().getQuoteItemList().get(i).getUnitPrice());
                                    String finalPrice=String.format("%.2f", totalPrice);
                                    tvTotal.setText("€ "+finalPrice);
                                    qvCarton.setQuantity(Integer.parseInt(response.body().getQuoteItemList().get(i).getPackingQuantity()));
                                    String mainChapterNum = response.body().getQuoteItemList().get(i).getUnitQuantity().substring(0, response.body().getQuoteItemList().get(i).getUnitQuantity().indexOf("."));
                                    qvItem.setQuantity(Integer.parseInt(mainChapterNum));
                                }
                                else {

                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void loadProducts(String productId, String categoryId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.loadProducts(productId,categoryId, Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<MoreProductResponse>() {
                    @Override
                    public void onResponse(Call<MoreProductResponse> call, Response<MoreProductResponse> response) {
                        if (response.body().getFlag()==1) {
                            LoadMoreAdapter loadMoreAdapter=new LoadMoreAdapter(ProductDetailActivity.this,response.body().getMoreProducts(),response.body().getImagePath(),custId,custName);
                            rvMore.setAdapter(loadMoreAdapter);
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<MoreProductResponse> call, Throwable t) {
                        hideProgressBar();
                        //Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addSale(String referenceNo, final ProductResponse.Product product) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait....");
        jdsSaleService.addSale(referenceNo,custId,saleId,product.getProductId(),custName, Preferences.getInstance().getUserId(), product.getPrice(), "1", String.valueOf(product.getSingleProductTax()), "1", product.getPrice(), "0", "1", Preferences.getInstance().getAuthKey()).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.body() != null) {
                    if (response.body().getFlag() == 1) {
                        getAllSale(custId, product.getProductId());
                    }
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setPageViewIndicator(int dotsCount) {
        if (dotsCount==1){
            viewPagerCountDots.setVisibility(View.INVISIBLE);
        }
        else {
            dots = new ImageView[dotsCount];
            for (int i = 0; i < dotsCount; i++) {
                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(4, 0, 4, 0);
                final int presentPosition = i;
                dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        viewPager.setCurrentItem(presentPosition);
                        return true;
                    }

                });
                viewPagerCountDots.addView(dots[i], params);
            }
            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

        if (position + 1 == dotsCount) {

        } else {

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addQuote(final ProductResponse.Product product) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
         showProgressbar("Loading", "Please wait....");
        jdsSaleService.addQuote(custId, Preferences.getInstance().getUserId(), custName, product.getProductId(), product.getPrice(), "1", "1", "0", product.getPrice(), "", "", "", Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body() != null) {
                    if (response.body().getFlag() == 1) {
                        getAllQuote(custId, product.getProductId());
                    }
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
    private void removeQuote(String id) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait....");
        jdsSaleService.deleteQuote(id, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    getAllQuote(custId,productId);
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

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.viewPagerCountDots)
    LinearLayout viewPagerCountDots;

    @BindView(R.id.tv_shop)
    TextView tvShop;

    @BindView(R.id.tv_promo_price)
    TextView tvPromoPrice;

    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.tv_items)
    TextView tvItems;

    @BindView(R.id.tv_carton1)
    TextView tvCarton;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.tv_stock)
    TextView tvStock;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_count)
    TextView tvCount;

    @BindView(R.id.rv_more)
    RecyclerView rvMore;

    @BindView(R.id.qv_item)
    QuantityView qvItem;

    @BindView(R.id.qv_carton)
    QuantityView qvCarton;

    @BindView(R.id.tv_head)
    TextView tvHead;

    @OnClick(R.id.rl_cart)
    void rlCart() {
        Intent intent = new Intent(getApplicationContext(), AllQuoteActivity.class);
        intent.putExtra("custId", custId);
        intent.putExtra("tag", tag);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}


