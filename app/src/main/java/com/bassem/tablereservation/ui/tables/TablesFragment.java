package com.bassem.tablereservation.ui.tables;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bassem.tablereservation.R;
import com.bassem.tablereservation.adapters.TablesAdapter;
import com.bassem.tablereservation.database.DatabaseHelper;
import com.bassem.tablereservation.models.Table;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * A Fragment that displays tables to reserve them
 */
public class TablesFragment extends Fragment implements TablesView {
    public static final String TAG = "tables_fragment";
    private static final String ARG_CUSTOMER_NAME = "customer_name";
    TablesPresenter mPresenter;
    private String mCustomerName;
    private OnFragmentInteractionListener mListener;
    TablesAdapter mAdapter;
    @BindString(R.string.general_error)
    String generalError;
    @BindString(R.string.got_offline_data)
    String gotOfflineData;
    @BindString(R.string.reserve_table_for)
    String reserveTableFor;
    @BindView(R.id.prgrs_tables)
    ProgressBar tablesProgressBar;
    @BindView(R.id.rclr_tables)
    RecyclerView tablesRecyclerView;
    @BindView(R.id.txt_for_customer)
    TextView forCustomerTextView;
    @BindInt(R.integer.tables_column_span_count)
    int columnSpanCount;
    GridLayoutManager mGridLayoutManager;

    public TablesFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to make a new instance of the fragment
     *
     * @param customerName
     * @return new instance of TablesFragment
     */
    public static TablesFragment newInstance(String customerName) {
        TablesFragment fragment = new TablesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CUSTOMER_NAME, customerName);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCustomerName = getArguments().getString(ARG_CUSTOMER_NAME);

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new TablesPresenterImpl(this, new TablesInteractorImpl(new DatabaseHelper(Realm.getDefaultInstance())));
        mPresenter.getCustomers();
        forCustomerTextView.setText(reserveTableFor+" "+mCustomerName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tables, container, false);
        ButterKnife.bind(this, view);
        return view;
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

    @Override
    public void updateData(List<Table> items) {
        if (mAdapter == null) {
            mAdapter = new TablesAdapter(items, mOnTableClickListener);
            tablesRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(items);
        }
        if (mGridLayoutManager == null) {

            mGridLayoutManager = new GridLayoutManager(getContext(), columnSpanCount, LinearLayoutManager.VERTICAL, false);
            tablesRecyclerView.setLayoutManager(mGridLayoutManager);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        tablesProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        tablesProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), generalError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGotOfflineData() {
        Toast.makeText(getContext(), gotOfflineData, Toast.LENGTH_SHORT).show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    View.OnClickListener mOnTableClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}
