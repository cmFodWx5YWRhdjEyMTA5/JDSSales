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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdssale.ClientInfoActivity;
import com.jdssale.R;
import com.jdssale.Response.PendingResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by dikhong on 25-07-2018.
 */

public class DPendingAdapter extends RecyclerView.Adapter<DPendingAdapter.ViewHolder> {
    private List<PendingResponse.MessageList> messageLists;
    private ArrayList<PendingResponse.MessageList> arraylist;
    private Context context;


    public DPendingAdapter(Context context, List<PendingResponse.MessageList> messageLists) {
        this.messageLists = messageLists;
        this.context = context;
        this.arraylist = new ArrayList<PendingResponse.MessageList>();
        this.arraylist.addAll(messageLists);
    }

    @Override
    public DPendingAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.driver_pending, viewGroup, false);
        return new DPendingAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final DPendingAdapter.ViewHolder viewHolder, final int i) {
        final PendingResponse.MessageList messageList=messageLists.get(i);
        viewHolder.tvCompany.setText(messageList.getCompany());
        viewHolder.tvName.setText(messageList.getName());
        viewHolder.tvAddress.setText(messageList.getAddress());
        viewHolder.rlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageList.getPhone()==null|| messageList.getPhone().equalsIgnoreCase(""))
                {

                }
                else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage(messageList.getPhone());
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Call",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                                    dialIntent.setData(Uri.parse("tel:" + messageList.getPhone()));
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
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Activity activity = (Activity) context;
                    Intent intent = new Intent(activity, ClientInfoActivity.class);
                    intent.putExtra("customer_id", messageList.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });




    }

    @Override
    public int getItemCount() {
        return messageLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvCompany,tvName,tvAddress;
        RelativeLayout rlCall;
        public ViewHolder(View view) {
            super(view);
            tvCompany= (TextView)view.findViewById(R.id.tv_company);
            tvAddress = (TextView)view.findViewById(R.id.tv_address);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            rlCall=(RelativeLayout)view.findViewById(R.id.rl_call);
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        messageLists.clear();
        if (charText.length() == 0) {
            messageLists.addAll(arraylist);
        } else {
            for (PendingResponse.MessageList wp : arraylist) {
                if (wp.getCompany().toLowerCase(Locale.getDefault()).contains(charText)) {
                    messageLists.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}