package com.jdssale.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdssale.ClientInfoActivity;
import com.jdssale.R;
import com.jdssale.Response.OrderResponse;

import java.util.List;

/**
 * Created by dikhong on 23-07-2018.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<OrderResponse.OrderData> historyData;
    private Context context;
    String finalDate,finalPrice;

    public OrderAdapter(Context context, List<OrderResponse.OrderData> historyData) {
        this.historyData = historyData;
        this.context = context;
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_list, viewGroup, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final OrderAdapter.ViewHolder viewHolder, final int i) {
        final OrderResponse.OrderData historyData1 =historyData.get(i);
        viewHolder.tvShop.setText(historyData1.getName()+" (TODAY)");
        viewHolder.tvPaid.setText(historyData1.getAddress());
        viewHolder.tvDate.setText(String.valueOf(i+1));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                Intent intent = new Intent(activity, ClientInfoActivity.class);
                intent.putExtra("customer_id", historyData1.getCustomerId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        viewHolder.rlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (historyData1.getPhone()==null|| historyData1.getPhone().equalsIgnoreCase(""))
                {

                }
                else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage(historyData1.getPhone());
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Call",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                                    dialIntent.setData(Uri.parse("tel:" + historyData1.getPhone()));
                                    context.startActivity(dialIntent);
                                }
                            });

                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    Button pbutton = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
                    Button nbutton = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
                    pbutton.setTextColor(Color.parseColor("#000000"));
                    nbutton.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        if (i == 0) {
            viewHolder.llNo.setBackgroundColor(Color.parseColor("#81c7b8"));
        } else {
            viewHolder.llNo.setBackgroundColor(Color.parseColor("#b9b9b9"));
        }
    }

    @Override
    public int getItemCount() {
        return historyData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDate,tvPrice,tvPaid,tvShop;LinearLayout llNo;RelativeLayout rlCall;
        public ViewHolder(View view) {
            super(view);
            tvDate= (TextView)view.findViewById(R.id.tv_date);
            tvPaid = (TextView)view.findViewById(R.id.tv_paid_by);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvShop = (TextView) view.findViewById(R.id.tv_shop);
            llNo= (LinearLayout) view.findViewById(R.id.ll__no);
            rlCall=(RelativeLayout)view.findViewById(R.id.rl_call);
        }
    }

}