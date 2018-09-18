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

import com.jdssale.AddPaymentActivity;
import com.jdssale.R;
import com.jdssale.Response.ClientPayResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by dikhong on 11-07-2018.
 */

public class ClientPayAdapter extends RecyclerView.Adapter<ClientPayAdapter.ViewHolder> {
    private List<ClientPayResponse.ClientData> clientDataList;
    private ArrayList<ClientPayResponse.ClientData> arraylist;
    private Context context;
    String finalDate,finalPrice;

    public ClientPayAdapter(Context context, List<ClientPayResponse.ClientData> clientDataList) {
        this.clientDataList = clientDataList;
        this.context = context;
        this.arraylist = new ArrayList<ClientPayResponse.ClientData>();
        this.arraylist.addAll(clientDataList);
    }

    @Override
    public ClientPayAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.client_pay_list, viewGroup, false);
        return new ClientPayAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ClientPayAdapter.ViewHolder viewHolder, final int i) {
        final ClientPayResponse.ClientData clientData=clientDataList.get(i);
        String[] splited = clientData.getDate().split("\\s+");
        finalDate=splited[0];
        viewHolder.tvDate.setText(finalDate);
        viewHolder.tvReferNo.setText(clientData.getReferenceNo());
        double price=Double.parseDouble(clientData.getPendingPayment());
        finalPrice=String.format("%.2f", price);
        viewHolder.tvPrice.setText("€ "+finalPrice);
        if (clientData.getPaymentStatus().equalsIgnoreCase("partial")) {
            viewHolder.rlComplete.setVisibility(View.GONE);
            viewHolder.rlPending.setVisibility(View.GONE);
            viewHolder.rlPartial.setVisibility(View.VISIBLE);
            viewHolder.tvStatusPa.setText("Partial");
        }
        else if (clientData.getPaymentStatus().equalsIgnoreCase("pending")){
            viewHolder.rlPartial.setVisibility(View.GONE);
            viewHolder.rlComplete.setVisibility(View.GONE);
            viewHolder.rlPending.setVisibility(View.VISIBLE);
            viewHolder.tvStatusP.setText("Pending");
        }
        else {
            viewHolder.rlPartial.setVisibility(View.GONE);
            viewHolder.rlPending.setVisibility(View.GONE);
            viewHolder.rlComplete.setVisibility(View.VISIBLE);
            viewHolder.tvStatusC.setText("Done");
        }
        viewHolder.tvCirle.setText(String.valueOf(i+1));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) context;
                ClientPayResponse.ClientData clientData1 = (ClientPayResponse.ClientData) clientDataList.get(i);
                Intent intent = new Intent(activity,AddPaymentActivity.class);
                intent.putExtra(AddPaymentActivity.CLIENT_DATA, clientData1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });




    }

    @Override
    public int getItemCount() {
        return clientDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDate,tvReferNo,tvPrice,tvStatusP,tvStatusC,tvStatusPa,tvCirle;RelativeLayout rlPending,rlComplete,rlPartial;
        public ViewHolder(View view) {
            super(view);
            tvCirle= (TextView)view.findViewById(R.id.tv_cirle);
            tvDate= (TextView)view.findViewById(R.id.tv_date);
            tvReferNo = (TextView)view.findViewById(R.id.tv_refer_no);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvStatusP=(TextView)view.findViewById(R.id.tv_status_pend);
            tvStatusPa=(TextView)view.findViewById(R.id.tv_status_part);
            tvStatusC=(TextView)view.findViewById(R.id.tv_status_comp);
            rlPending=(RelativeLayout)view.findViewById(R.id.rl_pending);
            rlComplete=(RelativeLayout)view.findViewById(R.id.rl_complete);
            rlPartial=(RelativeLayout)view.findViewById(R.id.rl_partial);
        }
    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        clientDataList.clear();
        if (charText.length() == 0) {
            clientDataList.addAll(arraylist);
        } else {
            for (ClientPayResponse.ClientData wp : arraylist) {
                if (wp.getReferenceNo().toLowerCase(Locale.getDefault()).contains(charText)) {
                    clientDataList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
