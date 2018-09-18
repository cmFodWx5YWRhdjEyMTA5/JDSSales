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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdssale.PendingListActivity;
import com.jdssale.R;
import com.jdssale.Response.PendingResponse;
import com.jdssale.ShopActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dikhong on 11-07-2018.
 */

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder> {
    private List<PendingResponse.MessageList> messageLists;
    private ArrayList<PendingResponse.MessageList> arraylist;
    private Context context;
    String imagePath;
    String tag;

    public PendingAdapter(Context context, List<PendingResponse.MessageList> messageLists, String imagePath,String tag) {
        this.messageLists = messageLists;
        this.context = context;
        this.imagePath=imagePath;
        this.tag=tag;
        this.arraylist = new ArrayList<PendingResponse.MessageList>();
        this.arraylist.addAll(messageLists);
    }

    @Override
    public PendingAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pending_list, viewGroup, false);
        return new PendingAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final PendingAdapter.ViewHolder viewHolder, final int i) {
        final PendingResponse.MessageList messageList=messageLists.get(i);
        viewHolder.tvUname.setText(messageList.getName());
        viewHolder.tvUaddress.setText(messageList.getAddress());
        viewHolder.tvUphone.setText(messageList.getPhone());
        if (messageList.getPhone()==null|| messageList.getPhone().equalsIgnoreCase(""))
        {
            viewHolder.ivCall.setVisibility(View.GONE);
        }
        else {
            viewHolder.ivCall.setVisibility(View.VISIBLE);
        }
        if (messageList.getImage()==null|| messageList.getImage().equalsIgnoreCase(""))
        {
            Picasso.with(context).load(R.drawable.user).into(viewHolder.cvUimage);
        }
        else {
            Picasso.with(context).load(imagePath+messageList.getImage()).into(viewHolder.cvUimage);
        }
        viewHolder.llCall.setOnClickListener(new View.OnClickListener() {
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
                if (tag.equalsIgnoreCase("quote")){
                    Activity activity = (Activity) context;
                    Intent intent = new Intent(activity, ShopActivity.class);
                    intent.putExtra("customer_id", messageList.getId());
                    intent.putExtra("customer_name", messageList.getName());
                    intent.putExtra("tag","quote");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

                }
                else if (tag.equalsIgnoreCase("pending")){
                    Activity activity = (Activity) context;
                    Intent intent = new Intent(activity, PendingListActivity.class);
                    intent.putExtra("customer_id", messageList.getId());
                    intent.putExtra("customer_name", messageList.getName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                else {
                    Activity activity = (Activity) context;
                    Intent intent = new Intent(activity, ShopActivity.class);
                    intent.putExtra("customer_id", messageList.getId());
                    intent.putExtra("customer_name", messageList.getName());
                    intent.putExtra("tag","sale");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return messageLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUname,tvUaddress,tvUphone;
        CircleImageView cvUimage;
        LinearLayout llCall;
        ImageView ivCall;
        public ViewHolder(View view) {
            super(view);
            tvUname= (TextView)view.findViewById(R.id.tv_uname);
            tvUaddress = (TextView)view.findViewById(R.id.tv_uaddress);
            tvUphone = (TextView) view.findViewById(R.id.tv_uphone);
            cvUimage=(CircleImageView)view.findViewById(R.id.tv_cirle);
            llCall=(LinearLayout)view.findViewById(R.id.ll_call);
            ivCall=(ImageView) view.findViewById(R.id.iv_call);
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        messageLists.clear();
        if (charText.length() == 0) {
            messageLists.addAll(arraylist);
        } else {
            for (PendingResponse.MessageList wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    messageLists.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}

