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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jdssale.Adapter.DHistoryAdapter;
import com.jdssale.Response.HistoryResponse;
import com.jdssale.Retrofit.JDSSaleService;
import com.jdssale.Retrofit.RestClient;
import com.jdssale.Utilities.BaseActivity;
import com.jdssale.Utilities.Preferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DPayHistoryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    DHistoryAdapter historyAdapter;
    List<HistoryResponse.HistoryData> historyDataList=new ArrayList<>();
    public DPayHistoryFragment() {
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
        View view= inflater.inflate(R.layout.fragment_dpay_history, container, false);
        ButterKnife.bind(this,view);
        ((Driver1Activity)getActivity()).setTitle("PAYMENT HISTORY");
        setRecycle();
        getHistory();
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (historyDataList != null && historyDataList.isEmpty()){
                    Toast.makeText(getContext(), "Nothing to search", Toast.LENGTH_SHORT).show();
                }
                else {
                    historyAdapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getHistory() {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsSaleService.getHistory(Preferences.getInstance().getUserId(),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<HistoryResponse>() {
                    @Override
                    public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                        Log.d("history","history"+response);
                        if (response.body().getFlag()==1){
                            historyDataList=response.body().getHistoryDataList();
                            if (historyDataList.size()==0){
                                ivNo.setVisibility(View.VISIBLE);
                                rvHistory.setVisibility(View.GONE);
                            }
                            else {
                                ivNo.setVisibility(View.GONE);
                                rvHistory.setVisibility(View.VISIBLE);
                                historyAdapter = new DHistoryAdapter(getContext(), historyDataList);
                                rvHistory.setAdapter(historyAdapter);
                            }
                        }
                        else
                        {
                            rvHistory.setVisibility(View.GONE);
                            ivNo.setVisibility(View.VISIBLE);
                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<HistoryResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        //Toast.makeText(getContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setRecycle() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvHistory.setLayoutManager(mLayoutManager);
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

    @BindView(R.id.rv_pay_history)
    RecyclerView rvHistory;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.iv_no)
    ImageView ivNo;
}
