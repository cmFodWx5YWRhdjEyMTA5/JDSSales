package com.jdssale.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdssale.R;
import com.jdssale.Response.PayDetailResponse;

import java.util.List;

/**
 * Created by dikhong on 11-07-2018.
 */

public class PayDetailAdapter extends RecyclerView.Adapter<PayDetailAdapter.ViewHolder> {
    private List<PayDetailResponse.PayData> payData;
    private Context context;
    String finalDate,finalPrice;

    public PayDetailAdapter(Context context, List<PayDetailResponse.PayData> payData) {
        this.payData = payData;
        this.context = context;
    }

    @Override
    public PayDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pay_detsil_list, viewGroup, false);
        return new PayDetailAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final PayDetailAdapter.ViewHolder viewHolder, final int i) {
        final PayDetailResponse.PayData payData1 =payData.get(i);
        String[] splited = payData1.getDate().split("\\s+");
        finalDate=splited[0];
        viewHolder.tvDate.setText(finalDate);
        viewHolder.tvPaid.setText(payData1.getPaidBy());
        double price=Double.parseDouble(payData1.getAmount());
        finalPrice=String.format("%.2f", price);
        viewHolder.tvAmount.setText("€ "+finalPrice);
        viewHolder.tvCirle.setText(String.valueOf(i+1));
    }

    @Override
    public int getItemCount() {
        return payData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDate,tvAmount,tvPaid,tvCirle;
        public ViewHolder(View view) {
            super(view);
            tvCirle= (TextView)view.findViewById(R.id.tv_cirle);
            tvDate= (TextView)view.findViewById(R.id.tv_date);
            tvPaid = (TextView)view.findViewById(R.id.tv_paid_by);
            tvAmount = (TextView) view.findViewById(R.id.tv_amount);
        }
    }

}

