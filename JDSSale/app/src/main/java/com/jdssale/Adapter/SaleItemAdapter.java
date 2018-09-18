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
import com.jdssale.Response.QuoteModel;
import com.jdssale.Response.SaleDetailResponse;
import com.jdssale.Utilities.QuantityView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikhong on 19-07-2018.
 */

public class SaleItemAdapter extends RecyclerView.Adapter<SaleItemAdapter.ViewHolder> {
    private List<SaleDetailResponse.SaleDetail.SaleItems> saleItems;
    List<QuoteModel> quoteModels=new ArrayList<>();
    private Context context;
    String imagePath;
    private OnItemClick mCallback;
    String finalPrice,calPrice;

    public SaleItemAdapter(Context context, List<SaleDetailResponse.SaleDetail.SaleItems> saleItems, String imagePath, OnItemClick listener) {
        this.saleItems = saleItems;
        this.context = context;
        this.imagePath=imagePath;
        this.mCallback = listener;
    }

    @Override
    public SaleItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quote_items_list, viewGroup, false);
        return new SaleItemAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final SaleItemAdapter.ViewHolder viewHolder, final int i) {
        final SaleDetailResponse.SaleDetail.SaleItems saleItem=saleItems.get(i);
        final QuoteModel quoteModel=new QuoteModel();
        viewHolder.tvName.setText(saleItem.getProductName());
        viewHolder.tvQuat.setText("Items in each Carton: "+saleItem.getNoOfItemsInPacking());
        double price=Double.parseDouble(saleItem.getNetUnitPrice());
        Double calculatedValue = Double.parseDouble(saleItem.getSubtotal());
        finalPrice=String.format("%.2f", price);
        calPrice=String.format("%.2f", calculatedValue);
        viewHolder.tvPrice.setText("€ "+calPrice);
        viewHolder.tvPriceDown.setText("€ "+finalPrice);
        Picasso.with(context).load(imagePath+"/"+saleItem.getImage()).into(viewHolder.ivImage);
        viewHolder.qvCarton.setMinQuantity(0);
        viewHolder.qvItem.setMinQuantity(0);
        viewHolder.qvCarton.setQuantity(saleItem.getPackingQuantity());
        if (saleItem.getSingleQuantity().contains(".")) {
            String signle = saleItem.getSingleQuantity().substring(0, saleItem.getSingleQuantity().indexOf("."));
            viewHolder.qvItem.setQuantity(Integer.parseInt(signle));
        }
        else {
            viewHolder.qvItem.setQuantity(Integer.parseInt(saleItem.getSingleQuantity()));
        }
        viewHolder.qvCarton.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically,String TAG) {
                quoteModel.setPackingQuantity(String.valueOf(newQuantity));
                quoteModel.setQuantity(String.valueOf(viewHolder.qvItem.getQuantity()+newQuantity* Integer.parseInt(saleItem.getNoOfItemsInPacking())));
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
                    double totalItems = Double.parseDouble(saleItem.getNoOfItemsInPacking());
                    double noOfItems = totalItems * Double.parseDouble(String.valueOf(newQuantity));
                    double singles=viewHolder.qvItem.getQuantity();
                    double allitems=singles+noOfItems;
                    Log.d("noOfItems","noOfItems"+noOfItems);
                    double price = Double.parseDouble(saleItem.getNetUnitPrice());
                    double calculatedValue = price * allitems;
                    double total = calculatedValue;
                    calPrice = String.format("%.2f", total);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(calPrice);
                }
                if (TAG.equalsIgnoreCase("sub")){
                    if (Integer.parseInt(String.valueOf(viewHolder.qvItem.getQuantity()))==0)
                    {
                        viewHolder.qvCarton.setMinQuantity(1);
                    }
                    else {
                        viewHolder.qvCarton.setMinQuantity(0);
                    }
                    double totalItems = Double.parseDouble(saleItem.getNoOfItemsInPacking());
                    double noOfItems = totalItems * Double.parseDouble(String.valueOf(newQuantity));
                    double singles=viewHolder.qvItem.getQuantity();
                    double allitems=singles+noOfItems;
                    Log.d("noOfItems","noOfItems"+allitems);
                    double price = Double.parseDouble(saleItem.getNetUnitPrice());
                    double calculatedValue = price * allitems;
                    double total = calculatedValue;
                    calPrice = String.format("%.2f", total);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(calPrice);
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
                quoteModel.setQuantity(String.valueOf(newQuantity+viewHolder.qvCarton.getQuantity()* Integer.parseInt(saleItem.getNoOfItemsInPacking())));
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
                    double unitPrice=Double.parseDouble(saleItem.getNetUnitPrice());
                    double cartonQuant=Double.parseDouble(String.valueOf(viewHolder.qvCarton.getQuantity()));
                    double noInCarton= Double.parseDouble(saleItem.getNoOfItemsInPacking());
                    double totalCartonItems=cartonQuant*noInCarton;
                    double overAll=totalCartonItems+Double.parseDouble(String.valueOf(newQuantity));
                    double totalPrice=unitPrice*overAll;
                    calPrice = String.format("%.2f", totalPrice);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(calPrice);
                }
                if (tag.equalsIgnoreCase("sub")){
                    if (Integer.parseInt(String.valueOf(viewHolder.qvCarton.getQuantity()))==0)
                    {
                        viewHolder.qvItem.setMinQuantity(1);
                    }
                    else {
                        viewHolder.qvItem.setMinQuantity(0);
                    }
                    double unitPrice=Double.parseDouble(saleItem.getNetUnitPrice());
                    double cartonQuant=Double.parseDouble(String.valueOf(viewHolder.qvCarton.getQuantity()));
                    double noInCarton= Double.parseDouble(saleItem.getNoOfItemsInPacking());
                    double totalCartonItems=cartonQuant*noInCarton;
                    double overAll=totalCartonItems+Double.parseDouble(String.valueOf(newQuantity));
                    double totalPrice=unitPrice*overAll;
                    calPrice = String.format("%.2f", totalPrice);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(calPrice);
                }
            }

            @Override
            public void onLimitReached() {

            }
        });
        quoteModel.setQuoteItemId(saleItem.getId());
        quoteModel.setSingleProductQuantity(String.valueOf(viewHolder.qvItem.getQuantity()));
        quoteModel.setPackingQuantity(String.valueOf(viewHolder.qvCarton.getQuantity()));
        quoteModel.setUnitPrice(saleItem.getUnitPrice());
        quoteModel.setNetUnitPrice(saleItem.getNetUnitPrice());
        quoteModel.setItemTax(saleItem.getItemTax());
        int total=viewHolder.qvItem.getQuantity()+viewHolder.qvCarton.getQuantity()* Integer.parseInt(saleItem.getNoOfItemsInPacking());
        quoteModel.setSubtotal(String.valueOf(Float.parseFloat(saleItem.getUnitPrice())*total));
        quoteModel.setQuantity(String.valueOf(total));
        quoteModels.add(quoteModel);
        mCallback.sendList(quoteModels);
    }

    @Override
    public int getItemCount() {
        return saleItems.size();
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
