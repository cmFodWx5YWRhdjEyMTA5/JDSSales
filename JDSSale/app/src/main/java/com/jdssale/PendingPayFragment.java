package com.jdssale;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.jdssale.Adapter.PendingAdapter;
import com.jdssale.Response.PendingResponse;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.JDSSale;
import com.jdssale.Utilities.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PendingPayFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    PendingAdapter pendingAdapter;
    int itemCount=0;
    public PendingPayFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pending_pay, container, false);
        ButterKnife.bind(this,view);
        ((DashboardActivity)getActivity()).setTitle("PENDING PAYMENT");
        setRecycle();
        getPayList();
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (itemCount==0){

                }
                else {
                    pendingAdapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getPayList() {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsSaleService.pendingList(Preferences.getInstance().getUserId(),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<PendingResponse>() {
                    @Override
                    public void onResponse(Call<PendingResponse> call, Response<PendingResponse> response) {
                        if (response.body().getFlag()==1){
                            if (response.body().getMessageListList().size()==0){
                                itemCount=0;
                                ivNoData.setVisibility(View.VISIBLE);
                                rvPay.setVisibility(View.GONE);
                            }
                            else {
                                itemCount=response.body().getMessageListList().size();
                                ivNoData.setVisibility(View.GONE);
                                rvPay.setVisibility(View.VISIBLE);
                            }

                            pendingAdapter = new PendingAdapter(getContext(), response.body().getMessageListList(),response.body().getImagePath(),"pending");
                            rvPay.setAdapter(pendingAdapter);
                        }
                        else
                        {    itemCount=0;
                            ivNoData.setVisibility(View.VISIBLE);
                            rvPay.setVisibility(View.GONE);
                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<PendingResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setRecycle() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvPay.setLayoutManager(mLayoutManager);
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

    @BindView(R.id.rv_pay_list)
    RecyclerView rvPay;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.iv_no_data)
    ImageView ivNoData;


}
