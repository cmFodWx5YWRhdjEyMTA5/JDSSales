package com.jdssale.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdssale.R;
import com.jdssale.Response.QuoteDetailResponse;
import com.jdssale.Response.QuoteModel;
import com.jdssale.Utilities.QuantityView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikhong on 09-07-2018.
 */

public class QuoteItemsAdapter extends RecyclerView.Adapter<QuoteItemsAdapter.ViewHolder> {
    private List<QuoteDetailResponse.QuoteDetail.QuoteItems> quoteResponses;
    private Context context;
    String imagePath;
    int countItem=0,countCarton=0;
    List<QuoteModel> quoteModels=new ArrayList<>();
    private OnItemClick mCallback;
    String finalPrice,calPrice,calPrice1;

    public QuoteItemsAdapter(Context context, List<QuoteDetailResponse.QuoteDetail.QuoteItems> quoteResponses, String imagePath,OnItemClick listener) {
        this.quoteResponses = quoteResponses;
        this.context = context;
        this.imagePath=imagePath;
        this.mCallback = listener;
    }

    @Override
    public QuoteItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quote_items_list, viewGroup, false);
        return new QuoteItemsAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final QuoteItemsAdapter.ViewHolder viewHolder, final int i) {
        final QuoteDetailResponse.QuoteDetail.QuoteItems quoteResponse=quoteResponses.get(i);
        final QuoteModel quoteModel=new QuoteModel();
        viewHolder.tvName.setText(quoteResponse.getProductName());
        viewHolder.tvQuat.setText("Items in each Carton: "+quoteResponse.getNoItemsPacking());
        double price=Double.parseDouble(quoteResponse.getNetUnitPrice());
        double calculatedValue = Double.parseDouble(quoteResponse.getSubtotal());
        finalPrice=String.format("%.2f", price);
        calPrice1=String.format("%.2f", calculatedValue);
        viewHolder.tvPrice.setText("€ "+calPrice1);
        viewHolder.tvPriceDown.setText("€ "+finalPrice);
        Picasso.with(context).load(imagePath+quoteResponse.getImage()).into(viewHolder.ivImage);
        viewHolder.qvCarton.setMinQuantity(0);
        viewHolder.qvItem.setMinQuantity(0);
        if (quoteResponse.getPackingQuantity()==null){
            viewHolder.qvCarton.setQuantity(0);
        }
        else {
            viewHolder.qvCarton.setQuantity(Integer.parseInt(quoteResponse.getPackingQuantity()));
        }
        if (quoteResponse.getSingleProductQuantity()==null) {
            viewHolder.qvItem.setQuantity(0);
        }
        else {
            viewHolder.qvItem.setQuantity(Integer.parseInt(quoteResponse.getSingleProductQuantity()));
        }
        viewHolder.qvCarton.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically,String TAG) {
                quoteModel.setPackingQuantity(String.valueOf(newQuantity));
                double totalItems1 = Double.parseDouble(quoteResponse.getNoItemsPacking());
                double noOfItems1 = totalItems1 * Double.parseDouble(String.valueOf(newQuantity));
                double singles1=viewHolder.qvItem.getQuantity();
                double allitems1=singles1+noOfItems1;
                quoteModel.setSubtotal(String.valueOf(Double.parseDouble(quoteResponse.getNetUnitPrice())*allitems1));
                quoteModel.setQuantity(String.valueOf(allitems1));
                calPrice="";
                viewHolder.qvCarton.setMinQuantity(0);
                if (TAG.equalsIgnoreCase("add")) {
                    if (Integer.parseInt(String.valueOf(viewHolder.qvItem.getQuantity()))==0)
                    {
                        viewHolder.qvCarton.setMinQuantity(1);
                    }
                    else {
                        viewHolder.qvCarton.setMinQuantity(0);
                    }
                    double totalItems = Double.parseDouble(quoteResponse.getNoItemsPacking());
                    double noOfItems = totalItems * Double.parseDouble(String.valueOf(newQuantity));
                    double singles=viewHolder.qvItem.getQuantity();
                    double allitems=singles+noOfItems;
                    Log.d("noOfItems","noOfItems"+noOfItems);
                    double price = Double.parseDouble(quoteResponse.getNetUnitPrice());
                    double calculatedValue = price * allitems;
                    double total = calculatedValue;
                    calPrice = String.format("%.2f", total);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(quoteModel.getSubtotal());
                }
                if (TAG.equalsIgnoreCase("sub")){
                    if (Integer.parseInt(String.valueOf(viewHolder.qvItem.getQuantity()))==0)
                    {
                        viewHolder.qvCarton.setMinQuantity(1);
                    }
                    else {
                        viewHolder.qvCarton.setMinQuantity(0);
                    }
                    double totalItems = Double.parseDouble(quoteResponse.getNoItemsPacking());
                    double noOfItems = totalItems * Double.parseDouble(String.valueOf(newQuantity));
                    double singles=viewHolder.qvItem.getQuantity();
                    double allitems=singles+noOfItems;
                    Log.d("noOfItems","noOfItems"+allitems);
                    double price = Double.parseDouble(quoteResponse.getNetUnitPrice());
                    double calculatedValue = price * allitems;
                    double total = calculatedValue;
                    calPrice = String.format("%.2f", total);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(quoteModel.getSubtotal());
                }


            }

            @Override
            public void onLimitReached() {

            }
        });

        viewHolder.qvItem.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically,String tag) {
                quoteModel.setSingleProductQuantity(String.valueOf(newQuantity));
                int total=newQuantity+viewHolder.qvCarton.getQuantity()* Integer.parseInt(quoteResponse.getNoItemsPacking());
                quoteModel.setSubtotal(String.valueOf(Float.parseFloat(quoteResponse.getNetUnitPrice())*total));
                quoteModel.setQuantity(String.valueOf(total));
                calPrice="";
                viewHolder.qvItem.setMinQuantity(0);
                if (tag.equalsIgnoreCase("add")) {
                    if (Integer.parseInt(String.valueOf(viewHolder.qvCarton.getQuantity()))==0)
                    {
                        viewHolder.qvItem.setMinQuantity(1);
                    }
                    else {
                        viewHolder.qvItem.setMinQuantity(0);
                    }
                    double unitPrice=Double.parseDouble(quoteResponse.getNetUnitPrice());
                    double cartonQuant=Double.parseDouble(String.valueOf(viewHolder.qvCarton.getQuantity()));
                    double noInCarton= Double.parseDouble(quoteResponse.getNoItemsPacking());
                    double totalCartonItems=cartonQuant*noInCarton;
                    double overAll=totalCartonItems+Double.parseDouble(String.valueOf(newQuantity));
                    double totalPrice=unitPrice*overAll;
                    calPrice = String.format("%.2f", totalPrice);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(quoteModel.getSubtotal());
                }
                if (tag.equalsIgnoreCase("sub")){
                    if (Integer.parseInt(String.valueOf(viewHolder.qvCarton.getQuantity()))==0)
                    {
                        viewHolder.qvItem.setMinQuantity(1);
                    }
                    else {
                        viewHolder.qvItem.setMinQuantity(0);
                    }
                    double unitPrice=Double.parseDouble(quoteResponse.getNetUnitPrice());
                    double cartonQuant=Double.parseDouble(String.valueOf(viewHolder.qvCarton.getQuantity()));
                    double noInCarton= Double.parseDouble(quoteResponse.getNoItemsPacking());
                    double totalCartonItems=cartonQuant*noInCarton;
                    double overAll=totalCartonItems+Double.parseDouble(String.valueOf(newQuantity));
                    double totalPrice=unitPrice*overAll;
                    calPrice = String.format("%.2f", totalPrice);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(quoteModel.getSubtotal());
                }
            }

            @Override
            public void onLimitReached() {

            }
        });
        quoteModel.setQuoteItemId(quoteResponse.getQuoteItemId());
        quoteModel.setSingleProductQuantity(String.valueOf(viewHolder.qvItem.getQuantity()));
        quoteModel.setPackingQuantity(String.valueOf(viewHolder.qvCarton.getQuantity()));
        quoteModel.setUnitPrice(quoteResponse.getUnitPrice());
        quoteModel.setNetUnitPrice(quoteResponse.getNetUnitPrice());
        quoteModel.setItemTax(quoteResponse.getItemTax());
        int total=viewHolder.qvItem.getQuantity()+viewHolder.qvCarton.getQuantity()*Integer.parseInt(quoteResponse.getNoItemsPacking());
        quoteModel.setSubtotal(String.valueOf(Float.parseFloat(quoteResponse.getNetUnitPrice())*total));
        quoteModel.setQuantity(String.valueOf(total));
        quoteModels.add(quoteModel);
        mCallback.onClick(quoteModel.getSubtotal());
        mCallback.sendList(quoteModels);

    }

    @Override
    public int getItemCount() {
        return quoteResponses.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName,tvQuat,tvPrice,tvPriceDown;
        ImageView ivImage;
        QuantityView qvCarton,qvItem;
        public ViewHolder(View view) {
            super(view);
            tvName= (TextView)view.findViewById(R.id.tv_name);
            tvQuat= (TextView)view.findViewById(R.id.tv_quat);
            tvPriceDown = (TextView)view.findViewById(R.id.tv_price_down);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            ivImage=(ImageView)view.findViewById(R.id.iv_image);
            qvItem=(QuantityView)view.findViewById(R.id.qv_item);
            qvCarton=(QuantityView)view.findViewById(R.id.qv_carton);


        }
    }

    public interface OnItemClick {
        void onClick (String value);
        void sendList (List<QuoteModel> quoteModels);
    }





}
