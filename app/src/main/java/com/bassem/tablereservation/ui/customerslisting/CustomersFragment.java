package com.bassem.tablereservation.ui.customerslisting;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bassem.tablereservation.R;

/**
 * A Fragment That displays list of customers
 * when a customer is clicked it notifies the hosting activity
 */
public class CustomersFragment extends Fragment {


    private OnCustomersFragmentInteractionListener mListener;

    public CustomersFragment() {
        // Required empty public constructor
    }


    public static CustomersFragment newInstance() {
        CustomersFragment fragment = new CustomersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customers, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCustomersFragmentInteractionListener) {
            mListener = (OnCustomersFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCustomersFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity  when a customer is clicked
     */
    public interface OnCustomersFragmentInteractionListener {
        void onCustomerClicked();
    }
}
