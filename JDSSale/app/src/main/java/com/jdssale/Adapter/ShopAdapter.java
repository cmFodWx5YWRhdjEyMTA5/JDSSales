package com.jdssale.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdssale.R;
import com.jdssale.Response.ShopResponse;
import com.jdssale.SubCategoryActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by dikhong on 12-07-2018.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<ShopResponse.ShopData> shopData;
    private Context context;
    private ArrayList<ShopResponse.ShopData> arraylist;
    String imagePath,custName,custId,tag;

    public ShopAdapter(Context context, List<ShopResponse.ShopData> shopData,String imagePath,String custName,String custId,String tag) {
        this.shopData = shopData;
        this.context = context;
        this.imagePath=imagePath;
        this.custName=custName;
        this.custId=custId;
        this.tag=tag;
        this.arraylist = new ArrayList<ShopResponse.ShopData>();
        this.arraylist.addAll(shopData);
    }

    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_list, viewGroup, false);
        return new ShopAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ShopAdapter.ViewHolder viewHolder, final int i) {
        final ShopResponse.ShopData shopData1 =shopData.get(i);
        Picasso.with(context).load(imagePath+shopData1.getImage()).into(viewHolder.ivImages);
        viewHolder.tvTitle.setText(shopData1.getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                ShopResponse.ShopData shopData2 = (ShopResponse.ShopData) shopData.get(i);
                Intent intent = new Intent(activity,SubCategoryActivity.class);
                intent.putExtra(SubCategoryActivity.SHOP, shopData2);
                intent.putExtra("custName",custName);
                intent.putExtra("custId",custId);
                intent.putExtra("tag",tag);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView ivImages;
        AppCompatTextView tvTitle;
        public ViewHolder(View view) {
            super(view);
            ivImages= (AppCompatImageView)view.findViewById(R.id.ivImages);
            tvTitle= (AppCompatTextView)view.findViewById(R.id.tvTitle);
        }
    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        shopData.clear();
        if (charText.length() == 0) {
            shopData.addAll(arraylist);
        } else {
            for (ShopResponse.ShopData wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    shopData.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
