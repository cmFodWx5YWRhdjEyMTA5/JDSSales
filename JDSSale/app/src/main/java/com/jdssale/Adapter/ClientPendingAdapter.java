package com.jdssale.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdssale.ClientInfoActivity;
import com.jdssale.R;
import com.jdssale.Response.ClientPayResponse;
import com.jdssale.Response.DataResponse;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.JDSSale;
import com.jdssale.Utilities.Preferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dikhong on 24-07-2018.
 */

public class ClientPendingAdapter extends RecyclerView.Adapter<ClientPendingAdapter.ViewHolder> {
    private List<ClientPayResponse.ClientData> clientDataList;
    private Context context;
    String calPrice,custId;

    public ClientPendingAdapter(Context context, List<ClientPayResponse.ClientData> clientDataList,String custId) {
        this.clientDataList = clientDataList;
        this.context = context;
        this.custId=custId;
    }

    @Override
    public ClientPendingAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.client_pending, viewGroup, false);
        return new ClientPendingAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ClientPendingAdapter.ViewHolder viewHolder, final int i) {
        final ClientPayResponse.ClientData clientData =clientDataList.get(i);
        viewHolder.tvRefer.setText(clientData.getReferenceNo());
        calPrice = String.format("%.2f", Double.parseDouble(clientData.getPendingPayment()));
        viewHolder.tvAmt.setText("€ "+calPrice);
        viewHolder.tvDate.setText(convertDate(clientData.getDate()));
        viewHolder.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.etAmt.getText().length()!=0)
                {
                    collectPay(viewHolder.etAmt.getText().toString(),clientData,viewHolder);
                }
                else {
                    Toast.makeText(context, "Please add amount before submit", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewHolder.llRefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.llEnter.getVisibility() == View.VISIBLE ) {
                    viewHolder.llEnter.setVisibility(View.GONE);
                } else {
                    viewHolder.llEnter.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void collectPay(String amount, ClientPayResponse.ClientData clientData, final ViewHolder viewHolder) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsSaleService.collectPay(amount,Preferences.getInstance().getUserId(),clientData.getCustomerId(), clientData.getSaleId(),Preferences.getInstance().getAuthKey()).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.body() != null) {
                    if (response.body().getFlag() == 1) {
                        viewHolder.etAmt.setText("");
                        Toast.makeText(context, response.body().getData(), Toast.LENGTH_SHORT).show();
                        viewHolder.llEnter.setVisibility(View.GONE);
                        ((ClientInfoActivity)context).showHide();
                        ((ClientInfoActivity)context).clientPayment(custId);

                    } else {
                        Toast.makeText(context, response.body().getData(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, response.body().getData(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) context).hideProgressBar();
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                ((BaseActivity) context).hideProgressBar();
                Toast.makeText(context, JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return clientDataList.size();
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


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDate,tvRefer,tvAmt;LinearLayout llEnter,llRefer;
        Button btSubmit;
        EditText etAmt;
        public ViewHolder(View view) {
            super(view);
            tvRefer= (TextView)view.findViewById(R.id.tv_refer);
            tvDate = (TextView)view.findViewById(R.id.tv_date);
            tvAmt = (TextView) view.findViewById(R.id.tv_amt);
            etAmt = (EditText) view.findViewById(R.id.et_amt);
            btSubmit= (Button) view.findViewById(R.id.bt_submit);
            llEnter= (LinearLayout) view.findViewById(R.id.ll_enter);
            llRefer= (LinearLayout) view.findViewById(R.id.ll_refer);
        }
    }

}