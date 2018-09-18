package com.jdssale;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class DashboardFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public DashboardFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this,view);
        ((DashboardActivity)getActivity()).setTitle("");
        return view;
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

    @OnClick(R.id.ll_quote)
    void openQuote(){
        ((DashboardActivity)getActivity()).replaceFragment(QuatationFragment.class);
    }

    @OnClick(R.id.ll_sale)
    void openSale(){
        ((DashboardActivity)getActivity()).replaceFragment(SaleFragment.class);
    }

    @OnClick(R.id.ll_pending)
    void opebPending(){
        ((DashboardActivity)getActivity()).replaceFragment(PendingPayFragment.class);
    }

    @OnClick(R.id.ll_history)
    void openHistory(){
        ((DashboardActivity)getActivity()).replaceFragment(PayHistoryFragment.class);
    }
}
