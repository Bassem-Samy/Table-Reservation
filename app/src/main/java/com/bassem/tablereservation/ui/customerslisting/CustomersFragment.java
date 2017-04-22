package com.bassem.tablereservation.ui.customerslisting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bassem.tablereservation.R;
import com.bassem.tablereservation.adapters.CustomersAdapter;
import com.bassem.tablereservation.models.CustomerDataModel;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A Fragment That displays list of customers
 * when a customer is clicked it notifies the hosting activity
 */
public class CustomersFragment extends Fragment implements CustomersListingView {
    public static final String TAG = "customers_fragment";
    @BindView(R.id.rclr_customers)
    RecyclerView customersRecyclerView;
    @BindView(R.id.prgrs_main)
    ProgressBar mainProgressBar;
    private OnCustomersFragmentInteractionListener mListener;
    @BindString(R.string.general_error)
    String generalError;
    CustomersListingPresenter mPresenter;
    CustomersAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;

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
        View view = inflater.inflate(R.layout.fragment_customers, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new CustomersListingPresenterImpl(this, new CustomersListingInteractorImpl());
        mPresenter.getCustomers();
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

    @Override
    public void updateData(List<CustomerDataModel> items) {
        if (mAdapter == null) {
            mAdapter = new CustomersAdapter(items, mOnCustomerClickListener);
            customersRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(items);
        }
        if (mLinearLayoutManager == null) {
            mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            customersRecyclerView.setLayoutManager(mLinearLayoutManager);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        mainProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mainProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), generalError, Toast.LENGTH_SHORT).show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity  when a customer is clicked
     */
    public interface OnCustomersFragmentInteractionListener {
        void onCustomerClicked();
    }

    View.OnClickListener mOnCustomerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = customersRecyclerView.getChildAdapterPosition(view);
            CustomerDataModel model = mAdapter.getItemByPosition(position);
            Toast.makeText(getContext(), model.getFullName(), Toast.LENGTH_SHORT).show();
            if (mListener != null) {
                mListener.onCustomerClicked();
            }

        }
    };
}
