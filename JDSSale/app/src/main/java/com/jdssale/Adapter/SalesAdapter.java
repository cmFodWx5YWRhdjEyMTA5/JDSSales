package com.jdssale.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdssale.R;
import com.jdssale.Response.SaleResponse;
import com.jdssale.SaleDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by dikhong on 17-07-2018.
 */

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> {
    private List<SaleResponse.SaleData> saleDataList;
    private ArrayList<SaleResponse.SaleData> arraylist;
    private Context context;
    String imagePath;
    String finalPrice,finalDate;
    double price;

    public SalesAdapter(Context context, List<SaleResponse.SaleData> saleDataList,String imagePath) {
        this.saleDataList = saleDataList;
        this.context = context;
        this.imagePath=imagePath;
        this.arraylist = new ArrayList<SaleResponse.SaleData>();
        this.arraylist.addAll(saleDataList);
    }

    @Override
    public SalesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quote_list, viewGroup, false);
        return new SalesAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final SalesAdapter.ViewHolder viewHolder, final int i) {
        final SaleResponse.SaleData quoteResponse=saleDataList.get(i);
        String[] splited = quoteResponse.getDate().split("\\s+");
        finalDate=splited[0];
        viewHolder.tvQuotname.setText(quoteResponse.getCustomer());
        viewHolder.tvReferNo.setText(quoteResponse.getReferenceNo());
        price=Double.parseDouble(quoteResponse.getGrandTotal());
        finalPrice=String.format("%.2f", price);
        viewHolder.tvPrice.setText("€ "+finalPrice);
        if (quoteResponse.getPaymentStatus().equalsIgnoreCase("due")) {
            viewHolder.rlComplete.setVisibility(View.GONE);
            viewHolder.rlPending.setVisibility(View.VISIBLE);
            viewHolder.tvStatusP.setText("Pending");
        }
        else {
            viewHolder.rlPending.setVisibility(View.GONE);
            viewHolder.rlComplete.setVisibility(View.VISIBLE);
            viewHolder.tvStatusC.setText("Done");
        }
        viewHolder.tvCirle.setText(String.valueOf(i+1));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) context;
                SaleResponse.SaleData saleData = (SaleResponse.SaleData) saleDataList.get(i);
                Intent intent = new Intent(activity,SaleDetailActivity.class);
                intent.putExtra(SaleDetailActivity.SALE, saleData);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });




    }

    @Override
    public int getItemCount() {
        return saleDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvQuotname,tvReferNo,tvPrice,tvStatusP,tvStatusC,tvCirle;RelativeLayout rlPending,rlComplete;
        public ViewHolder(View view) {
            super(view);
            tvCirle= (TextView)view.findViewById(R.id.tv_cirle);
            tvQuotname= (TextView)view.findViewById(R.id.tv_quotname);
            tvReferNo = (TextView)view.findViewById(R.id.tv_refer_no);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvStatusP=(TextView)view.findViewById(R.id.tv_status_pend);
            tvStatusC=(TextView)view.findViewById(R.id.tv_status_comp);
            rlPending=(RelativeLayout)view.findViewById(R.id.rl_pending);
            rlComplete=(RelativeLayout)view.findViewById(R.id.rl_complete);
        }
    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        saleDataList.clear();
        if (charText.length() == 0) {
            saleDataList.addAll(arraylist);
        } else {
            for (SaleResponse.SaleData wp : arraylist) {
                if (wp.getCustomer().toLowerCase(Locale.getDefault()).contains(charText)) {
                    saleDataList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}
