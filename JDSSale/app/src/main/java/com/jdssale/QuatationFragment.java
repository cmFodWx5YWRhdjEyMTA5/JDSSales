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

import com.jdssale.Adapter.QuotationAdapter;
import com.jdssale.Response.QuoteResponse;
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


public class QuatationFragment extends Fragment {
    QuotationAdapter quotationAdapter;

    private OnFragmentInteractionListener mListener;

    public QuatationFragment() {
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
        View view= inflater.inflate(R.layout.fragment_quatation, container, false);
        ButterKnife.bind(this,view);
        ((DashboardActivity)getActivity()).setTitle("QUOTATION LIST");
        setRecycle();
        getQuoteList();
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                quotationAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void setRecycle() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvQuote.setLayoutManager(mLayoutManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getQuoteList() {
        JDSSaleService jdsSaleService = RestClient.getInstance().getClient().create(JDSSaleService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsSaleService.getquotation(Preferences.getInstance().getUserId(),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<QuoteResponse>() {
                    @Override
                    public void onResponse(Call<QuoteResponse> call, Response<QuoteResponse> response) {
                        if (response.body().getFlag()==1){
                            quotationAdapter = new QuotationAdapter(getContext(), response.body().getQuoteDataList(),response.body().getImagePath());
                            rvQuote.setAdapter(quotationAdapter);
                        }
                        else
                        {

                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<QuoteResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), JDSSale.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
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

    @BindView(R.id.rv_quote)
    RecyclerView rvQuote;

    @BindView(R.id.et_search)
    EditText etSearch;

    @OnClick(R.id.rl_addquote)
    void addQuote(){
        Intent intent = new Intent(getContext(),AddQuoteListActivity.class);
        intent.putExtra("tag","quote");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
}
