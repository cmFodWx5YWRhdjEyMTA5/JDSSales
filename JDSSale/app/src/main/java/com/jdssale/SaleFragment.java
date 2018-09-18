package com.jdssale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jdssale.Adapter.SalesAdapter;
import com.jdssale.Response.SaleResponse;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.JDSSale;
import com.jdssale.Utilities.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SaleFragment extends Fragment {

    SalesAdapter salesAdapter;
    private OnFragmentInteractionListener mListener;
    int itemCount=0;

    public SaleFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sale, container, false);
        ButterKnife.bind(this,view);
        ((DashboardActivity)getActivity()).setTitle("SALE LIST");
        setRecycle();
        getSaleList();
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (itemCount==0){

                }
                else {
                    salesAdapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getSaleList() {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsSaleService.getSale(Preferences.getInstance().getUserId(),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<SaleResponse>() {
                    @Override
                    public void onResponse(Call<SaleResponse> call, Response<SaleResponse> response) {
                        if (response.body().getFlag()==1){
                            if (response.body().getSaleDataList().size()==0)
                            {
                                itemCount=0;
                            }
                            else {
                                itemCount=response.body().getSaleDataList().size();
                            }
                            salesAdapter = new SalesAdapter(getContext(), response.body().getSaleDataList(),response.body().getImagePath());
                            rvSale.setAdapter(salesAdapter);
                        }
                        else
                        {
                            itemCount=0;
                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<SaleResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setRecycle() {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            rvSale.setLayoutManager(mLayoutManager);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @BindView(R.id.rv_sale)
    RecyclerView rvSale;

    @BindView(R.id.et_search)
    EditText etSearch;

    @OnClick(R.id.rl_add_sale)
    void add(){
        Intent intent = new Intent(getContext(),AddQuoteListActivity.class);
        intent.putExtra("tag","sale");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
}
