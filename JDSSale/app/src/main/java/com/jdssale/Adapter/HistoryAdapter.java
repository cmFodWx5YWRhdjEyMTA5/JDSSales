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
import com.jdssale.Response.HistoryResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by dikhong on 11-07-2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<HistoryResponse.HistoryData> historyData= new ArrayList<HistoryResponse.HistoryData>();;
    private ArrayList<HistoryResponse.HistoryData> arraylist;
    private Context context;
    String finalDate,finalPrice;

    public HistoryAdapter(Context context, List<HistoryResponse.HistoryData> historyData) {
        this.historyData = historyData;
        this.context = context;
        this.arraylist = new ArrayList<HistoryResponse.HistoryData>();
        this.arraylist.addAll(historyData);
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_list, viewGroup, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final HistoryAdapter.ViewHolder viewHolder, final int i) {
        final HistoryResponse.HistoryData historyData1 =historyData.get(i);
        String[] splited = historyData1.getDate().split("\\s+");
        finalDate=splited[0];
        viewHolder.tvDate.setText(finalDate);
        viewHolder.tvShop.setText(historyData1.getCompany());
        viewHolder.tvPaid.setText(historyData1.getReferenceNo());
        double price=Double.parseDouble(historyData1.getAmount());
        finalPrice=String.format("%.2f", price);
        viewHolder.tvAmount.setText("€ "+finalPrice);
        viewHolder.tvCirle.setText(String.valueOf(i+1));
    }

    @Override
    public int getItemCount() {
        if (historyData == null)
            return 0;
        else
            return  historyData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDate,tvAmount,tvPaid,tvCirle,tvShop;
        public ViewHolder(View view) {
            super(view);
            tvCirle= (TextView)view.findViewById(R.id.tv_cirle);
            tvDate= (TextView)view.findViewById(R.id.tv_date);
            tvPaid = (TextView)view.findViewById(R.id.tv_paid_by);
            tvAmount = (TextView) view.findViewById(R.id.tv_amount);
            tvShop = (TextView) view.findViewById(R.id.tv_shop);
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        historyData.clear();
        if (charText.length() == 0) {
            historyData.addAll(arraylist);
        } else {
            for (HistoryResponse.HistoryData wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    historyData.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

