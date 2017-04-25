package com.bassem.tablereservation.ui.tables;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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
import com.bassem.tablereservation.background.UpdateTablesIntentService;
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
    private static final String ARG_LOAD_FROM_DATABASE = "load_from_database";
    private static final String SAVED_TABLES = "saved_tables";
    TablesPresenter mPresenter;
    private String mCustomerName;
    private boolean mLoadFromDb;
    private OnFragmentInteractionListener mListener;
    TablesAdapter mAdapter;
    @BindString(R.string.table_reserved)
    String tableReserved;
    @BindString(R.string.reservation_cancel)
    String reservationCanceled;
    @BindString(R.string.general_error)
    String generalError;
    @BindString(R.string.got_offline_data)
    String gotOfflineData;
    @BindString(R.string.reserve_table_for)
    String reserveTableFor;
    @BindString(R.string.data_updated_from_service)
    String updatedDataFromBackgroundService;
    @BindView(R.id.prgrs_tables)
    ProgressBar tablesProgressBar;
    @BindView(R.id.rclr_tables)
    RecyclerView tablesRecyclerView;
    @BindView(R.id.txt_for_customer)
    TextView forCustomerTextView;
    @BindInt(R.integer.tables_column_span_count)
    int columnSpanCount;
    GridLayoutManager mGridLayoutManager;
    private BroadcastReceiver tablesUpdatedBraodCastReceiver;
    private IntentFilter mFilter;
    public boolean isLoading = false;


    public TablesFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to make a new instance of the fragment
     *
     * @param customerName
     * @param loadFromDatabase boolean to indicate whether to load tables from db ( to preserve a current run tables reservations statuses) or not
     * @return new instance of TablesFragment
     */
    public static TablesFragment newInstance(String customerName, boolean loadFromDatabase) {
        TablesFragment fragment = new TablesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CUSTOMER_NAME, customerName);
        args.putBoolean(ARG_LOAD_FROM_DATABASE, loadFromDatabase);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCustomerName = getArguments().getString(ARG_CUSTOMER_NAME);
            mLoadFromDb = getArguments().getBoolean(ARG_LOAD_FROM_DATABASE, false);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new TablesPresenterImpl(this, new TablesInteractorImpl(new DatabaseHelper(Realm.getDefaultInstance())));
        // check saved instance
        List<Table> savedTables = null;
        if (savedInstanceState != null) {
            savedTables = savedInstanceState.getParcelableArrayList(SAVED_TABLES);
        }
        if (savedTables == null || savedTables.size() == 0) {
            if (!mLoadFromDb) {
                mPresenter.getTables();
            } else {
                int size = mPresenter.getTablesFromDatabase();
                // check if db is already filled great!, else try api
                if (size == 0) {
                    mPresenter.getTables();
                }
            }
        } else {
            updateData(savedTables);
        }
        forCustomerTextView.setText(reserveTableFor + " " + mCustomerName);
        registerTablesUbdatedReceiver();
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
        isLoading = true;
        tablesProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        isLoading = false;
        tablesProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        showShortToast(generalError);
    }

    @Override
    public void showGotOfflineData() {
        showShortToast(gotOfflineData);
    }

    @Override
    public void showTableUpdated(boolean available) {
        if (available) {
            showShortToast(reservationCanceled);
        } else {
            showShortToast(tableReserved);
        }
    }

    @Override
    public void showUpdatedDataFromService() {
        showShortToast(updatedDataFromBackgroundService);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    private void showShortToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    View.OnClickListener mOnTableClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = tablesRecyclerView.getChildLayoutPosition(view);
            Table selectedTable = mAdapter.getItemByPosition(position);
            mPresenter.updateTableReservation(selectedTable);
            mAdapter.notifyItemChanged(position);
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }

        if (tablesUpdatedBraodCastReceiver != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(tablesUpdatedBraodCastReceiver);
        }
    }


    void registerTablesUbdatedReceiver() {
        tablesUpdatedBraodCastReceiver = new BroadcastReceiver() {


            @Override
            public void onReceive(Context context, Intent intent) {
                if (!isLoading) {
                    mPresenter.getTablesAfterServiceUpdate();
                }
            }
        };
        if (mFilter == null) {
            mFilter = new IntentFilter(UpdateTablesIntentService.ACTION);
        }
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(tablesUpdatedBraodCastReceiver, mFilter);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVED_TABLES, mAdapter.getItems());
    }
}
