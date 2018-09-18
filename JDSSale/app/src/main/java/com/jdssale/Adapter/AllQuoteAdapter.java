package com.jdssale.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdssale.R;
import com.jdssale.Response.AllQuoteResponse;
import com.jdssale.Response.ForgetResponse;
import com.jdssale.Response.QuoteModel;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.JDSSale;
import com.jdssale.Utilities.Preferences;
import com.jdssale.Utilities.QuantityView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dikhong on 13-07-2018.
 */

public class AllQuoteAdapter extends RecyclerView.Adapter<AllQuoteAdapter.ViewHolder> {
    private List<AllQuoteResponse.QuoteItem> quoteResponses;
    private Context context;
    String imagePath;
    int countItem = 0, countCarton = 0;
    ArrayList<QuoteModel> quoteModels = new ArrayList<>();
    OnItemClick mCallback;
    String finalPrice, calPrice, noDiscount, withDiscount, subDis,tag;

    public AllQuoteAdapter(Context context, List<AllQuoteResponse.QuoteItem> quoteResponses, String imagePath, OnItemClick listener,String tag) {
        this.quoteResponses = quoteResponses;
        this.context = context;
        this.imagePath = imagePath;
        this.mCallback = listener;
        this.tag=tag;
    }

    @Override
    public AllQuoteAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quote_items, viewGroup, false);
        return new AllQuoteAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final AllQuoteAdapter.ViewHolder viewHolder, final int i) {
        final AllQuoteResponse.QuoteItem quoteResponse = quoteResponses.get(i);
        final QuoteModel quoteModel = new QuoteModel();
        viewHolder.tvName.setText(quoteResponse.getProductName());
        viewHolder.tvQuat.setText("Items in each Carton: " + quoteResponse.getNoOfItemsInPacking());
        Double calculatedValue = Double.parseDouble(quoteResponse.getSubtotal());
        calPrice = String.format("%.2f", calculatedValue);

        if (quoteResponse.getPromoPrice() == null || quoteResponse.getPromoPrice().equals("")) {
            double price = Double.parseDouble(quoteResponse.getNetUnitPrice());
            finalPrice = String.format("%.2f", price);
            viewHolder.tvCut.setVisibility(View.GONE);
            viewHolder.tvPriceDown.setText("€ " + finalPrice);
            viewHolder.tvPrice.setText("€ " + calPrice);
        } else {
            double price = Double.parseDouble(quoteResponse.getNetUnitPrice());
            double discount = Double.parseDouble(quoteResponse.getNetUnitPrice()) - Double.parseDouble(quoteResponse.getPromoPrice());
            noDiscount = String.format("%.2f", price);
            withDiscount = String.format("%.2f", discount);
            viewHolder.tvCut.setVisibility(View.VISIBLE);
            viewHolder.tvCut.setText("€ " + noDiscount);
            viewHolder.tvPriceDown.setText("€ " + withDiscount);
            viewHolder.tvCut.setPaintFlags(viewHolder.tvCut.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            Double subDiscount = Double.parseDouble(quoteResponse.getSubtotal()) - Double.parseDouble(quoteResponse.getPromoPrice());
            subDis = String.format("%.2f", subDiscount);
            viewHolder.tvPrice.setText("€ " + subDis);

        }

        Picasso.with(context).load(imagePath + quoteResponse.getImage()).into(viewHolder.ivImage);
        viewHolder.qvCarton.setMinQuantity(0);
        viewHolder.qvItem.setMinQuantity(0);
        viewHolder.qvCarton.setQuantity(Integer.parseInt(quoteResponse.getPackingQuantity()));
        if (tag.equalsIgnoreCase("quote")){
            viewHolder.qvItem.setQuantity(Integer.parseInt(quoteResponse.getSingleProductQuantity()));
            quoteModel.setSingleProductQuantity(quoteResponse.getSingleProductQuantity());
        }
        else {
            String mainChapterNum = quoteResponse.getUnitQuantity().substring(0, quoteResponse.getUnitQuantity().indexOf("."));
            viewHolder.qvItem.setQuantity(Integer.parseInt(mainChapterNum));
            quoteModel.setSingleProductQuantity(mainChapterNum);
        }
        quoteModel.setPackingQuantity(quoteResponse.getPackingQuantity());
        quoteModel.setItemTax(quoteResponse.getItemTax());
        quoteModel.setNetUnitPrice(quoteResponse.getNetUnitPrice());
        quoteModel.setUnitPrice(quoteResponse.getUnitPrice());
        quoteModel.setItemTax(quoteResponse.getItemTax());
        quoteModel.setQuantity(String.valueOf(viewHolder.qvItem.getQuantity() + viewHolder.qvCarton.getQuantity() * Integer.parseInt(quoteResponse.getNoOfItemsInPacking())));
        quoteModel.setQuoteItemId(quoteResponse.getId());
        quoteModel.setSubtotal(quoteResponse.getSubtotal());
        viewHolder.qvCarton.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically, String TAG) {
                calPrice = "";
                viewHolder.qvCarton.setMinQuantity(0);
                if (TAG.equalsIgnoreCase("add")) {
                    if (Integer.parseInt(String.valueOf(viewHolder.qvItem.getQuantity())) == 0) {
                        viewHolder.qvCarton.setMinQuantity(1);
                    } else {
                        viewHolder.qvCarton.setMinQuantity(0);
                    }
                    double totalItems = Double.parseDouble(quoteResponse.getNoOfItemsInPacking());
                    double noOfItems = totalItems * Double.parseDouble(String.valueOf(newQuantity));
                    double singles = viewHolder.qvItem.getQuantity();
                    double allitems = singles + noOfItems;
                    Log.d("noOfItems", "noOfItems" + noOfItems);
                    double price = Double.parseDouble(quoteResponse.getNetUnitPrice());
                    double calculatedValue = price * allitems;
                    double total = calculatedValue;
                    calPrice = String.format("%.2f", total);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(calPrice);
                    if (tag.equalsIgnoreCase("quote")) {
                        editQuote(quoteResponse.getId(), quoteResponse.getUnitPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(quoteResponse.getUnitPrice())));
                    }
                    else {
                        editSale(quoteResponse.getId(), quoteResponse.getUnitPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(quoteResponse.getUnitPrice())));
                    }
                }
                if (TAG.equalsIgnoreCase("sub")) {
                    if (Integer.parseInt(String.valueOf(viewHolder.qvItem.getQuantity())) == 0) {
                        viewHolder.qvCarton.setMinQuantity(1);
                    } else {
                        viewHolder.qvCarton.setMinQuantity(0);
                    }
                    double totalItems = Double.parseDouble(quoteResponse.getNoOfItemsInPacking());
                    double noOfItems = totalItems * Double.parseDouble(String.valueOf(newQuantity));
                    double singles = viewHolder.qvItem.getQuantity();
                    double allitems = singles + noOfItems;
                    Log.d("noOfItems", "noOfItems" + allitems);
                    double price = Double.parseDouble(quoteResponse.getNetUnitPrice());
                    double calculatedValue = price * allitems;
                    double total = calculatedValue;
                    calPrice = String.format("%.2f", total);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(calPrice);
                    if (tag.equalsIgnoreCase("quote")) {
                        editQuote(quoteResponse.getId(), quoteResponse.getUnitPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(quoteResponse.getUnitPrice())));
                    }
                    else {
                        editSale(quoteResponse.getId(), quoteResponse.getUnitPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(quoteResponse.getUnitPrice())));
                    }
                }

                quoteModel.setSubtotal(calPrice);
            }

            @Override
            public void onLimitReached() {

            }
        });
        viewHolder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag.equalsIgnoreCase("quote")) {
                    removeQuote(quoteResponse.getId(), i);
                }
                else {
                    removeSale(quoteResponse.getId(),i);
                }
            }
        });

        viewHolder.qvItem.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically, String tag) {
                calPrice = "";
                viewHolder.qvItem.setMinQuantity(0);
                if (tag.equalsIgnoreCase("add")) {
                    if (Integer.parseInt(String.valueOf(viewHolder.qvCarton.getQuantity())) == 0) {
                        viewHolder.qvItem.setMinQuantity(1);
                    } else {
                        viewHolder.qvItem.setMinQuantity(0);
                    }
                    double unitPrice = Double.parseDouble(quoteResponse.getNetUnitPrice());
                    double cartonQuant = Double.parseDouble(String.valueOf(viewHolder.qvCarton.getQuantity()));
                    double noInCarton = Double.parseDouble(quoteResponse.getNoOfItemsInPacking());
                    double totalCartonItems = cartonQuant * noInCarton;
                    double overAll = totalCartonItems + Double.parseDouble(String.valueOf(newQuantity));
                    double totalPrice = unitPrice * overAll;
                    calPrice = String.format("%.2f", totalPrice);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(calPrice);
                    if (tag.equalsIgnoreCase("quote")) {
                        editQuote(quoteResponse.getId(), quoteResponse.getUnitPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(quoteResponse.getUnitPrice())));
                    }
                    else {
                        editSale(quoteResponse.getId(), quoteResponse.getUnitPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(quoteResponse.getUnitPrice())));
                    }
                }
                if (tag.equalsIgnoreCase("sub")) {
                    if (Integer.parseInt(String.valueOf(viewHolder.qvCarton.getQuantity())) == 0) {
                        viewHolder.qvItem.setMinQuantity(1);
                    } else {
                        viewHolder.qvItem.setMinQuantity(0);
                    }
                    double unitPrice = Double.parseDouble(quoteResponse.getNetUnitPrice());
                    double cartonQuant = Double.parseDouble(String.valueOf(viewHolder.qvCarton.getQuantity()));
                    double noInCarton = Double.parseDouble(quoteResponse.getNoOfItemsInPacking());
                    double totalCartonItems = cartonQuant * noInCarton;
                    double overAll = totalCartonItems + Double.parseDouble(String.valueOf(newQuantity));
                    double totalPrice = unitPrice * overAll;
                    calPrice = String.format("%.2f", totalPrice);
                    viewHolder.tvPrice.setText("€ " + calPrice);
                    mCallback.onClick(calPrice);
                    if (tag.equalsIgnoreCase("quote")) {
                        editQuote(quoteResponse.getId(), quoteResponse.getUnitPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(quoteResponse.getUnitPrice())));
                    }
                    else {
                        editSale(quoteResponse.getId(), quoteResponse.getUnitPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(quoteResponse.getUnitPrice())));
                    }
                }
            }

            @Override
            public void onLimitReached() {

            }
        });
        quoteModels.add(quoteModel);
        mCallback.sendList(quoteModels);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void removeQuote(String id, final int i) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsSaleService.deleteQuote(id, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    removeAt(i);
                }
                ((BaseActivity) context).hideProgressBar();
            }

            @Override
            public void onFailure(Call<ForgetResponse> call, Throwable t) {
                ((BaseActivity) context).hideProgressBar();
                Toast.makeText(context, JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void editQuote(String id,String unitPrice,String single,String carton,String quantity,String subTotal) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsSaleService.editQuote(id,unitPrice,single,carton,quantity,subTotal, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) context).hideProgressBar();
            }

            @Override
            public void onFailure(Call<ForgetResponse> call, Throwable t) {
                ((BaseActivity) context).hideProgressBar();
                Toast.makeText(context, JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void removeSale(String id, final int i) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsSaleService.deleteSale(id, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    removeAt(i);
                }
                ((BaseActivity) context).hideProgressBar();
            }

            @Override
            public void onFailure(Call<ForgetResponse> call, Throwable t) {
                ((BaseActivity) context).hideProgressBar();
                Toast.makeText(context, JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void editSale(String saleId, String unitPrice, String single, String carton, String quantity, String subTotal) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsSaleService.editSale(saleId, unitPrice, single, carton, quantity, subTotal, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) context).hideProgressBar();
            }

            @Override
            public void onFailure(Call<ForgetResponse> call, Throwable t) {
                ((BaseActivity) context).hideProgressBar();
                Toast.makeText(context, JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }




    @Override
    public int getItemCount() {
        return quoteResponses.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvQuat, tvPrice, tvPriceDown, tvCut, tvRemove;
        ImageView ivImage;
        QuantityView qvCarton, qvItem;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvQuat = (TextView) view.findViewById(R.id.tv_quat);
            tvPriceDown = (TextView) view.findViewById(R.id.tv_price_down);
            tvCut = (TextView) view.findViewById(R.id.tv_promo);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            ivImage = (ImageView) view.findViewById(R.id.iv_image);
            qvItem = (QuantityView) view.findViewById(R.id.qv_item);
            qvCarton = (QuantityView) view.findViewById(R.id.qv_carton);
            tvRemove = (TextView) view.findViewById(R.id.tv_remove);


        }
    }

    public interface OnItemClick {
        void onClick(String value);

        void sendList(ArrayList<QuoteModel> quoteModels);
    }

    public void removeAt(int position) {
        quoteResponses.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, quoteResponses.size());
    }


}
