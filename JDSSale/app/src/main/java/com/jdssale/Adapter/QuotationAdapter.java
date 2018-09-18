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

import com.jdssale.QuoteDetailActivity;
import com.jdssale.R;
import com.jdssale.Response.QuoteResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by dikhong on 09-07-2018.
 */

public class QuotationAdapter extends RecyclerView.Adapter<QuotationAdapter.ViewHolder> {
    private List<QuoteResponse.QuoteData> quoteResponses;
    private ArrayList<QuoteResponse.QuoteData> arraylist;
    private Context context;
    String imagePath;
    String finalPrice,finalDate;
    double price;

    public QuotationAdapter(Context context, List<QuoteResponse.QuoteData> quoteResponses,String imagePath) {
        this.quoteResponses = quoteResponses;
        this.context = context;
        this.imagePath=imagePath;
        this.arraylist = new ArrayList<QuoteResponse.QuoteData>();
        this.arraylist.addAll(quoteResponses);
    }

    @Override
    public QuotationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quote_list, viewGroup, false);
        return new QuotationAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final QuotationAdapter.ViewHolder viewHolder, final int i) {
        final QuoteResponse.QuoteData quoteResponse=quoteResponses.get(i);
        String[] splited = quoteResponse.getDate().split("\\s+");
        finalDate=splited[0];
        viewHolder.tvQuotname.setText(quoteResponse.getCustomer());
        viewHolder.tvReferNo.setText(quoteResponse.getReferenceNo());
        price=Double.parseDouble(quoteResponse.getGrandTotal());
        finalPrice=String.format("%.2f", price);
        viewHolder.tvPrice.setText("€ "+finalPrice);
        if (quoteResponse.getStatus().equalsIgnoreCase("pending")) {
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
                QuoteResponse.QuoteData quoteData = (QuoteResponse.QuoteData) quoteResponses.get(i);
                Intent intent = new Intent(activity,QuoteDetailActivity.class);
                intent.putExtra(QuoteDetailActivity.QUOTE, quoteData);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });




    }

    @Override
    public int getItemCount() {
        return quoteResponses.size();
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
        quoteResponses.clear();
        if (charText.length() == 0) {
            quoteResponses.addAll(arraylist);
        } else {
            for (QuoteResponse.QuoteData wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    quoteResponses.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
