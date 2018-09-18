package com.jdssale.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdssale.R;
import com.jdssale.Response.HistoryResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by dikhong on 23-07-2018.
 */

public class DHistoryAdapter extends RecyclerView.Adapter<DHistoryAdapter.ViewHolder> {
    private List<HistoryResponse.HistoryData> historyData;
    private ArrayList<HistoryResponse.HistoryData> arraylist;
    private Context context;
    String finalDate,finalPrice;

    public DHistoryAdapter(Context context, List<HistoryResponse.HistoryData> historyData) {
        this.historyData = historyData;
        this.context = context;
        this.arraylist = new ArrayList<HistoryResponse.HistoryData>();
        this.arraylist.addAll(historyData);
    }

    @Override
    public DHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.driver_history_list, viewGroup, false);
        return new DHistoryAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final DHistoryAdapter.ViewHolder viewHolder, final int i) {
        final HistoryResponse.HistoryData historyData1 =historyData.get(i);
        String[] splited = convertDate(historyData1.getDate()).split("-");
        viewHolder.tvDate1.setText(splited[0]);
        viewHolder.tvDate2.setText(splited[1]+"/"+splited[2]);
        viewHolder.tvName.setText(historyData1.getCompany());
        viewHolder.tvRefer.setText("#"+historyData1.getReferenceNo());
        double price=Double.parseDouble(historyData1.getAmount());
        finalPrice=String.format("%.2f", price);
        viewHolder.tvPrice.setText("€ "+finalPrice);
        if (historyData1.getStatus().equalsIgnoreCase("1")){
            viewHolder.tvPrice.setBackgroundResource(R.drawable.green_price);
        }
        else {
            viewHolder.tvPrice.setBackgroundResource(R.drawable.red_price);
        }


    }

    @Override
    public int getItemCount() {
        return historyData.size();
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
        private TextView tvDate1,tvDate2,tvName,tvRefer,tvPrice;
        public ViewHolder(View view) {
            super(view);
            tvDate1= (TextView)view.findViewById(R.id.tv_date1);
            tvDate2= (TextView)view.findViewById(R.id.tv_date2);
            tvName = (TextView)view.findViewById(R.id.tv_name);
            tvRefer = (TextView) view.findViewById(R.id.tv_refer);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
        }
    }

    public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            historyData.clear();
            if (charText.length() == 0) {
                historyData.addAll(arraylist);
            } else {
                for (HistoryResponse.HistoryData wp : arraylist) {
                    if (wp.getCompany().toLowerCase(Locale.getDefault()).contains(charText)) {
                        historyData.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
    }

}

