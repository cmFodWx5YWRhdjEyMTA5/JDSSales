package com.jdssale;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jdssale.Adapter.PayDetailAdapter;
import com.jdssale.Response.ClientPayResponse;
import com.jdssale.Response.DataResponse;
import com.jdssale.Response.PayDetailResponse;
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

public class AddPaymentActivity extends BaseActivity {

    public static final String CLIENT_DATA = "client_data";
    ClientPayResponse.ClientData clientData;
    String finalDate,finalPrice,pendingPrice;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
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
        clientData = (ClientPayResponse.ClientData) getIntent().getParcelableExtra(CLIENT_DATA);
        if (clientData!=null)
        {
            title.setText(clientData.getCustomerName());
            setField(clientData);
            paymentDetail(clientData.getCustomerId(),clientData.getSaleId());
        }
    }

    private void setRecycle() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPayment.setLayoutManager(mLayoutManager);
    }

    private void setField( ClientPayResponse.ClientData clientData) {
        String[] splited = clientData.getDate().split("\\s+");
        finalDate=splited[0];
        double price=Double.parseDouble(clientData.getGrandTotal());
        double pendPrice=Double.parseDouble(clientData.getPendingPayment());
        finalPrice=String.format("%.2f", price);
        pendingPrice=String.format("%.2f", pendPrice);
        tvPrice.setText("€ "+finalPrice);
        tvDate.setText(finalDate);
        tvPendPay.setText("€ "+pendingPrice);
        tvReferNo.setText(clientData.getReferenceNo());
    }

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.et_pay)
    EditText etPay;

    @BindView(R.id.tv_pend_pay)
    TextView tvPendPay;

    @BindView(R.id.tv_refer_no)
    TextView tvReferNo;

    @BindView(R.id.rv_payment)
    RecyclerView rvPayment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_submit)
    void submit(){
        if (etPay.getText().length()!=0){
            addPayment(etPay.getText().toString(),clientData.getCustomerId(),clientData.getSaleId());
        }
        else {
            Toast.makeText(AddPaymentActivity.this,"Please add amount to submit",Toast.LENGTH_SHORT).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addPayment(String amount, String customerId, String saleId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.addPay(amount,Preferences.getInstance().getUserId(),customerId,saleId, Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<DataResponse>() {
                    @Override
                    public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                        if (response.body().getFlag()==1) {
                            etPay.setText("");
                            Toast.makeText(getApplicationContext(), response.body().getData(), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), response.body().getData(), Toast.LENGTH_SHORT).show();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void paymentDetail(String customerId, String saleId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.payDetail(customerId,saleId, Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<PayDetailResponse>() {
                    @Override
                    public void onResponse(Call<PayDetailResponse> call, Response<PayDetailResponse> response) {
                        if (response.body().getFlag()==1) {
                            if (response.body().getPayDataList().equals("")){


                            }
                            else {
                                PayDetailAdapter payDetailAdapter = new PayDetailAdapter(AddPaymentActivity.this, response.body().getPayDataList());
                                rvPayment.setAdapter(payDetailAdapter);
                            }
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<PayDetailResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
