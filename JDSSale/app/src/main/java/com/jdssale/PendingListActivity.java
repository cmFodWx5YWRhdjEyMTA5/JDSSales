package com.jdssale;

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

import com.jdssale.Adapter.ClientPayAdapter;
import com.jdssale.Response.ClientPayResponse;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.JDSSale;
import com.jdssale.Utilities.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingListActivity extends BaseActivity {
    ClientPayAdapter clientPayAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_list);
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
        String custName= getIntent().getStringExtra("customer_name");
        title.setText(custName);
        if (getIntent().getStringExtra("customer_id")!=null)
        {
            String custId= getIntent().getStringExtra("customer_id");
            getPendingList(custId);
        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clientPayAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getPendingList(String custId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        showProgressbar("Loading", "Please wait...");
        jdsSaleService.clientPay(custId,Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ClientPayResponse>() {
                    @Override
                    public void onResponse(Call<ClientPayResponse> call, Response<ClientPayResponse> response) {
                        if (response.body().getFlag()==1){
                            clientPayAdapter = new ClientPayAdapter(PendingListActivity.this, response.body().getClientDataList());
                            rvPending.setAdapter(clientPayAdapter);
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

    private void setRecycle() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPending.setLayoutManager(mLayoutManager);
    }


    @BindView(R.id.rv_pending)
    RecyclerView rvPending;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_search)
    EditText etSearch;

}
