package com.jdssale;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdssale.Adapter.ClientPendingAdapter;
import com.jdssale.Response.ClientPayResponse;
import com.jdssale.Response.CustInfoResponse;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.JDSSale;
import com.jdssale.Utilities.Preferences;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientInfoActivity extends BaseActivity {

    String custId;
    String imagePath;
    ClientPendingAdapter clientPendingAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);
        ButterKnife.bind(this);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("CLIENT INFO");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.drawable.left_arrow));
        drawable.setTint(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
        if (getIntent().getStringExtra("customer_id")!=null){
            custId=getIntent().getStringExtra("customer_id");
            setClientInfo(custId);
            clientPayment(custId);
        }



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setClientInfo(String custId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.clientInfo(custId, Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<CustInfoResponse>() {
                    @Override
                    public void onResponse(Call<CustInfoResponse> call, Response<CustInfoResponse> response) {
                        if (response.body().getFlag()==1){
                             imagePath=response.body().getImage();
                           setFields(response.body().getCustDataList().get(0));
                        }
                        else
                        {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<CustInfoResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void clientPayment(final String custId) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPending.setLayoutManager(mLayoutManager);
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.clientPayment(custId, Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ClientPayResponse>() {
                    @Override
                    public void onResponse(Call<ClientPayResponse> call, Response<ClientPayResponse> response) {
                        if (response.body().getFlag()==1){
                            clientPendingAdapter = new ClientPendingAdapter(ClientInfoActivity.this, response.body().getClientDataList(),custId);
                            rvPending.setAdapter(clientPendingAdapter);
                        }
                        else
                        {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<ClientPayResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setFields(CustInfoResponse.CustData custData) {
        tvCname.setText(custData.getCustomerName());
        tvCloc.setText(custData.getAddress()+", "+custData.getCity()+", "+custData.getCountry()+", "+custData.getPostalCode());
        if (custData.getPhone()==null){
            tvCcall.setText("N/A");
        }
        else {
            tvCcall.setText(custData.getPhone());
        }
        if (custData.getEmail()==null){
            tvCmail.setText("N/A");
        }
        else {
            tvCmail.setText(custData.getEmail());
        }
        if (custData.getImage()==null){
            Picasso.with(getApplicationContext()).load(R.drawable.noimage).into(cvImage);
        }
        else {

            Picasso.with(getApplicationContext()).load(imagePath + custData.getImage()).into(cvImage);
        }
    }


      public void showHide(){
          if (rlRecyler.getVisibility() == View.VISIBLE) {
              View view = this.getCurrentFocus();
              if (view != null) {
                  InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                  imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
              }
              rlRecyler.setVisibility(View.GONE);
          }
      }


    @BindView(R.id.toolbar_driver)
    Toolbar toolbar;

    @BindView(R.id.cv_image)
    CircleImageView cvImage;

    @BindView(R.id.tv_cloc)
    TextView tvCloc;

    @BindView(R.id.tv_ccall)
    TextView tvCcall;

    @BindView(R.id.tv_cmail)
    TextView tvCmail;

    @BindView(R.id.tv_cname)
    TextView tvCname;

    @BindView(R.id.rv_pending)
    RecyclerView rvPending;

    @BindView(R.id.rl_recyler)
    RelativeLayout rlRecyler;

    @OnClick(R.id.rl_down)
    void rlDown(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if (rlRecyler.getVisibility() == View.VISIBLE) {
            rlRecyler.setVisibility(View.GONE);
        } else {
            rlRecyler.setVisibility(View.VISIBLE);
        }
    }





}
