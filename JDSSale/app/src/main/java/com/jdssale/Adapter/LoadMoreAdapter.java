package com.jdssale.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdssale.ProductDetailActivity;
import com.jdssale.R;
import com.jdssale.Response.MoreProductResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dikhong on 18-07-2018.
 */

public class LoadMoreAdapter extends RecyclerView.Adapter<LoadMoreAdapter.ViewHolder> {
    private List<MoreProductResponse.MoreProduct> moreProducts;
    private Context context;
    String imagePath,finalPrice,custId,custName;

    public LoadMoreAdapter(Context context, List<MoreProductResponse.MoreProduct> moreProducts,String imagePath,String custId,String custName) {
        this.moreProducts = moreProducts;
        this.context = context;
        this.imagePath=imagePath;
        this.custId=custId;
        this.custName=custName;
    }

    @Override
    public LoadMoreAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.load_more, viewGroup, false);
        return new LoadMoreAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final LoadMoreAdapter.ViewHolder viewHolder, final int i) {
        final MoreProductResponse.MoreProduct moreProduct =moreProducts.get(i);
        viewHolder.tvTitle.setText(moreProduct.getName());
        double price=Double.parseDouble(moreProduct.getPrice());
        finalPrice=String.format("%.2f", price);
        if (moreProduct.getPromoPrice()==null||moreProduct.getPromoPrice().equals("")){
            viewHolder.tvPromo.setVisibility(View.GONE);
            viewHolder.tvPrice.setText("€ "+finalPrice);
        }
        else {
            double discount=Double.parseDouble(moreProduct.getPrice())-Double.parseDouble(moreProduct.getPromoPrice());
            viewHolder.tvPromo.setVisibility(View.VISIBLE);
            String discountPrice=String.format("%.2f", discount);
            viewHolder.tvPrice.setText("€ "+discountPrice);
            viewHolder.tvPromo.setText("€ "+finalPrice);
            viewHolder.tvPromo.setPaintFlags(viewHolder.tvPromo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        viewHolder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                MoreProductResponse.MoreProduct moreProduct1 = (MoreProductResponse.MoreProduct) moreProducts.get(i);
                Intent intent = new Intent(activity,ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.PRODUCT_DATA, moreProduct1);
                intent.putExtra("custId",custId);
                intent.putExtra("custName",custName);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                ((Activity)context).finish();
            }
        });

        Picasso.with(context).load(imagePath+moreProduct.getImage()).into(viewHolder.ivImage);
    }

    @Override
    public int getItemCount() {
        return moreProducts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle,tvPromo,tvPrice,tvAdd;
        ImageView ivImage;
        public ViewHolder(View view) {
            super(view);
            tvTitle= (TextView)view.findViewById(R.id.tv_title);
            tvPromo= (TextView)view.findViewById(R.id.tv_promo);
            tvPrice = (TextView)view.findViewById(R.id.tv_price);
            tvAdd = (TextView) view.findViewById(R.id.tv_add);
            ivImage= (ImageView) view.findViewById(R.id.iv_image);
        }
    }

}


