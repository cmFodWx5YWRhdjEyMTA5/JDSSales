package com.jdssale.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdssale.ProductDetailActivity;
import com.jdssale.R;
import com.jdssale.Response.AllQuoteResponse;
import com.jdssale.Response.DataResponse;
import com.jdssale.Response.ForgetResponse;
import com.jdssale.Response.ProductResponse;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.SubCategoryActivity;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.JDSSale;
import com.jdssale.Utilities.Preferences;
import com.jdssale.Utilities.QuantityView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dikhong on 13-07-2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductResponse.Product> productsModels;
    private ArrayList<ProductResponse.Product> arraylist;
    private Context context;
    String imagePath;
    List<String> productIds=new ArrayList<>();
    String finalPrice, calPrice, finalPrice1, custId, custName,tag1,saleId;
    String referenceNo="0";
    String quoteId;

    public ProductAdapter(Context context, List<ProductResponse.Product> productsModels, String imagePath, String custId, String custName,String tag1) {
        this.productsModels = productsModels;
        this.context = context;
        this.imagePath = imagePath;
        this.custId = custId;
        this.custName = custName;
        this.tag1=tag1;
        this.arraylist = new ArrayList<ProductResponse.Product>();
        this.arraylist.addAll(productsModels);
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list, viewGroup, false);
        return new ProductAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ProductAdapter.ViewHolder viewHolder, final int i) {
        final ProductResponse.Product product = productsModels.get(i);
        if (tag1.equalsIgnoreCase("quote")) {
            getAllQuote(product.getProductId(), viewHolder);
        }
        else {
            getAllSale(product.getProductId(), viewHolder);
        }
        viewHolder.tvName.setText(product.getProductName());
        String[] splited = product.getStockLeft().split("\\.");
        viewHolder.tvStock.setText("Stock Left: " + splited[0]);
        final double price = Double.parseDouble(product.getPrice());
        finalPrice = String.format("%.2f", price);

        if (product.getPromoPrice() == null || product.getPromoPrice().equalsIgnoreCase("")) {
            viewHolder.tvCut.setVisibility(View.GONE);
            viewHolder.tvPriceDown.setText("€ " + finalPrice);
        } else {
            double discount = Double.parseDouble(product.getPrice()) - Double.parseDouble(product.getPromoPrice());
            finalPrice1 = String.format("%.2f", discount);
            viewHolder.tvCut.setVisibility(View.VISIBLE);
            viewHolder.tvPriceDown.setText("€ " + finalPrice1);
            viewHolder.tvCut.setText("€ " + finalPrice);
            viewHolder.tvCut.setPaintFlags(viewHolder.tvCut.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        Picasso.with(context).load(imagePath + product.getImage()).into(viewHolder.ivImage);
        viewHolder.qvCarton.setMinQuantity(0);
        viewHolder.qvItem.setMinQuantity(0);
        viewHolder.qvCarton.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically, String TAG) {
                calPrice = "";
                viewHolder.qvCarton.setMinQuantity(0);
                if (TAG.equalsIgnoreCase("add")) {
                    if (tag1.equalsIgnoreCase("quote")) {
                        matchQuote(product,viewHolder,product.getProductId());
                    }
                    else {
                        if (referenceNo.equalsIgnoreCase("0"))
                        {
                            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
                            final View dialogView = inflater.inflate(R.layout.refer_layout, null);
                            dialogBuilder.setView(dialogView);
                            final AlertDialog alertDialog = dialogBuilder.create();
                            final EditText editText = (EditText) dialogView.findViewById(R.id.et_refer);
                            Button button=(Button) dialogView.findViewById(R.id.bt_add);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (editText.getText().length()!=0) {
                                        matchSale(product,viewHolder,product.getProductId(),editText.getText().toString());
                                        alertDialog.dismiss();
                                    }
                                    else {
                                        Toast.makeText(context,"Please enter reference number",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            alertDialog.show();
                        }
                        else {
                            matchSale(product,viewHolder,product.getProductId(),referenceNo);
                        }
                    }
                }
                if (TAG.equalsIgnoreCase("sub")) {
                    if (viewHolder.qvCarton.getQuantity()==0 && viewHolder.qvItem.getQuantity()==0)
                    { if (tag1.equalsIgnoreCase("quote")) {
                        removeQuote(viewHolder.tvQuoteId.getText().toString());
                    }
                    else {
                        removeSale(viewHolder.tvQuoteId.getText().toString());
                    }
                    }
                    else {
                        if (tag1.equalsIgnoreCase("quote")) {
                            editQuote(viewHolder.tvQuoteId.getText().toString(), product.getPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(product.getPrice())));
                        }
                        else {
                            editSale(viewHolder.tvQuoteId.getText().toString(), product.getPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(product.getPrice())));
                        }
                    }
                }

            }

            @Override
            public void onLimitReached() {

            }
        });

        viewHolder.qvItem.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically, String tag) {
                calPrice = "";
                viewHolder.qvItem.setMinQuantity(0);
                if (tag.equalsIgnoreCase("add")) {
                    if (tag1.equalsIgnoreCase("quote")) {
                        matchQuote(product,viewHolder,product.getProductId());
                    }
                    else {
                        if (referenceNo.equalsIgnoreCase("0"))
                        {
                            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
                            final View dialogView = inflater.inflate(R.layout.refer_layout, null);
                            dialogBuilder.setView(dialogView);
                            final AlertDialog alertDialog = dialogBuilder.create();
                            final EditText editText = (EditText) dialogView.findViewById(R.id.et_refer);
                            Button button=(Button) dialogView.findViewById(R.id.bt_add);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (editText.getText().length()!=0) {
                                        addSale(editText.getText().toString(), product, viewHolder);
                                        alertDialog.dismiss();
                                    }
                                    else {
                                        Toast.makeText(context,"Please enter reference number",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            alertDialog.show();
                        }
                        else {
                            matchSale(product,viewHolder,product.getProductId(),referenceNo);
                       }
                    }
                }
                if (tag.equalsIgnoreCase("sub")) {
                    if (viewHolder.qvItem.getQuantity()==0 && viewHolder.qvCarton.getQuantity()==0) {
                        if (tag1.equalsIgnoreCase("quote")) {
                            removeQuote(viewHolder.tvQuoteId.getText().toString());
                        }
                        else {
                            removeSale(viewHolder.tvQuoteId.getText().toString());
                        }
                    }
                    else {
                        if (tag1.equalsIgnoreCase("quote")) {
                            editQuote(viewHolder.tvQuoteId.getText().toString(), product.getPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(product.getPrice())));
                        }
                        else {
                            editSale(viewHolder.tvQuoteId.getText().toString(), product.getPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(product.getPrice())));
                        }
                    }
                }
            }

            @Override
            public void onLimitReached() {

            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                ProductResponse.Product product1 = (ProductResponse.Product) productsModels.get(i);
                Intent intent = new Intent(activity, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.PRODUCT, product1);
                intent.putExtra("custId", custId);
                intent.putExtra("custName", custName);
                intent.putExtra("tag",tag1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAllQuote(final String productId, final ViewHolder viewHolder) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        jdsSaleService.getAllQuote(custId, Preferences.getInstance().getUserId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag() == 1) {
                            for (int i=0;i<response.body().getQuoteItemList().size();i++)
                                if (response.body().getQuoteItemList().get(i).getProductId().matches(productId)){
                                    viewHolder.tvQuoteId.setText(response.body().getQuoteItemList().get(i).getId());
                                    viewHolder.qvCarton.setQuantity(Integer.parseInt(response.body().getQuoteItemList().get(i).getPackingQuantity()));
                                    viewHolder.qvItem.setQuantity(Integer.parseInt(response.body().getQuoteItemList().get(i).getSingleProductQuantity()));
                                }

                        } else{

                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(context, JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void matchQuote(final ProductResponse.Product product, final ViewHolder viewHolder, final String productId) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        jdsSaleService.getAllQuote(custId, Preferences.getInstance().getUserId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag() == 1) {
                            for (int i = 0; i < response.body().getQuoteItemList().size(); i++) {
                                productIds.add(response.body().getQuoteItemList().get(i).getProductId());
                            }

                            if (productIds.contains(productId)) {
                                editQuote(viewHolder.tvQuoteId.getText().toString(), product.getPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(product.getPrice())));
                            }
                                else {
                                addQuote(product, viewHolder);
                                }
                        }else {
                            productIds.add("00");
                            if (productIds.contains(productId)) {

                                    editQuote(viewHolder.tvQuoteId.getText().toString(), product.getPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(product.getPrice())));

                            } else {
                                    addQuote(product, viewHolder);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(context, JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void matchSale(final ProductResponse.Product product, final ViewHolder viewHolder, final String productId, final String referenceNo) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        jdsSaleService.getAllSale(custId, Preferences.getInstance().getUserId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag() == 1) {
                            for (int i = 0; i < response.body().getQuoteItemList().size(); i++) {
                                productIds.add(response.body().getQuoteItemList().get(i).getProductId());
                            }

                            if (productIds.contains(productId)) {
                                editSale(viewHolder.tvQuoteId.getText().toString(), product.getPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(product.getPrice())));
                            }
                            else {
                                addSale(referenceNo,product,viewHolder);
                            }
                        }else {
                            productIds.add("00");
                            if (productIds.contains(productId)) {
                                    editSale(viewHolder.tvQuoteId.getText().toString(), product.getPrice(), String.valueOf(viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity()), String.valueOf(viewHolder.qvCarton.getQuantity() + viewHolder.qvItem.getQuantity() * Double.parseDouble(product.getPrice())));
                            } else {
                                    addSale(referenceNo,product,viewHolder);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(context, JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAllSale(final String productId, final ViewHolder viewHolder) {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        jdsSaleService.getAllSale(custId, Preferences.getInstance().getUserId(),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag()==1){
                            if (response.body().getQuoteInfo().getReferenceNo()==null) {
                                referenceNo = "0";
                            }
                            else {
                                referenceNo = response.body().getQuoteInfo().getReferenceNo();
                            }
                            if (response.body().getQuoteInfo().getId()==null) {
                                saleId = "";
                            }
                            else {
                                saleId = response.body().getQuoteInfo().getId();
                            }
                            for (int i=0;i<response.body().getQuoteItemList().size();i++)
                                if (response.body().getQuoteItemList().get(i).getProductId().matches(productId)){
                                    viewHolder.tvQuoteId.setText(response.body().getQuoteItemList().get(i).getId());
                                    viewHolder.qvCarton.setQuantity(Integer.parseInt(response.body().getQuoteItemList().get(i).getPackingQuantity()));
                                    String mainChapterNum = response.body().getQuoteItemList().get(i).getUnitQuantity().substring(0, response.body().getQuoteItemList().get(i).getUnitQuantity().indexOf("."));
                                    viewHolder.qvItem.setQuantity(Integer.parseInt(mainChapterNum));


                                }
                        }
                        else
                        {
                            referenceNo = "0";
                            saleId = "0";
                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(context, JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void editQuote(String id, String unitPrice, String single, String carton, String quantity, String subTotal) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsSaleService.editQuote(id, unitPrice, single, carton, quantity, subTotal, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
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



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void removeSale(String id) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsSaleService.deleteSale(id, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    ((SubCategoryActivity)context).getAllSale(custId);
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
    private void removeQuote(String id) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsSaleService.deleteQuote(id, Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body().getFlag() == 1) {
                    ((SubCategoryActivity)context).getAllQuote(custId);
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
    private void addQuote(final ProductResponse.Product product, final ViewHolder viewHolder) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsSaleService.addQuote(custId, Preferences.getInstance().getUserId(), custName, product.getProductId(), product.getPrice(), "1", "1", "0", product.getPrice(), "", "", "", Preferences.getInstance().getAuthKey()).enqueue(new Callback<ForgetResponse>() {
            @Override
            public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                if (response.body() != null) {
                    if (response.body().getFlag() == 1) {
                        quoteId = response.body().getQuoteId();
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        getAllQuote(product.getProductId(), viewHolder);
                        ((SubCategoryActivity)context).getAllQuote(custId);
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
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
    private void addSale(String referenceNo, final ProductResponse.Product product, final ViewHolder viewHolder) {
        final JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait....");
        jdsSaleService.addSale(referenceNo,custId,saleId,product.getProductId(),custName, Preferences.getInstance().getUserId(), product.getPrice(), "1", String.valueOf(product.getSingleProductTax()), "1", product.getPrice(), "0", "1", Preferences.getInstance().getAuthKey()).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.body() != null) {
                    if (response.body().getFlag() == 1) {
                        getAllSale(product.getProductId(), viewHolder);
                        ((SubCategoryActivity)context).getAllSale(custId);
                    } else {
                        Toast.makeText(context, response.body().getData(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, response.body().getData(), Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity) context).hideProgressBar();
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                ((BaseActivity) context).hideProgressBar();
                Toast.makeText(context, JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return productsModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvStock, tvPriceDown, tvCut,tvQuoteId;
        ImageView ivImage;
        LinearLayout rlQuantity;
        QuantityView qvCarton, qvItem;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvStock = (TextView) view.findViewById(R.id.tv_stock);
            tvPriceDown = (TextView) view.findViewById(R.id.tv_price_down);
            rlQuantity = (LinearLayout) view.findViewById(R.id.rl_quantity);
            ivImage = (ImageView) view.findViewById(R.id.iv_image);
            qvItem = (QuantityView) view.findViewById(R.id.qv_item);
            qvCarton = (QuantityView) view.findViewById(R.id.qv_carton);
            tvCut = (TextView) view.findViewById(R.id.tv_price_cut);
            tvQuoteId=(TextView) view.findViewById(R.id.tv_quote_id);


        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        productsModels.clear();
        if (charText.length() == 0) {
            productsModels.addAll(arraylist);
        } else {
            for (ProductResponse.Product wp : arraylist) {
                if (wp.getProductName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    productsModels.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }



}


