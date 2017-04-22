package com.bassem.tablereservation.ui.customerslisting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bassem.tablereservation.R;
import com.bassem.tablereservation.adapters.CustomersAdapter;
import com.bassem.tablereservation.database.DatabaseHelper;
import com.bassem.tablereservation.models.Customer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

/**
 * A Fragment That displays list of customers
 * when a customer is clicked it notifies the hosting activity
 */
public class CustomersFragment extends Fragment implements CustomersListingView {
    public static final String TAG = "customers_fragment";
    private static final long FILTER_WAIT_MILLISECONDS = 500;
    @BindView(R.id.rclr_customers)
    RecyclerView customersRecyclerView;
    @BindView(R.id.prgrs_main)
    ProgressBar mainProgressBar;
    @BindView(R.id.edt_filter)
    EditText filterEditText;
    @BindView(R.id.prgrs_filter)
    ProgressBar filterProgressBar;
    private OnCustomersFragmentInteractionListener mListener;
    @BindString(R.string.general_error)
    String generalError;
    @BindString(R.string.got_offline_data)
    String gotOfflineData;
    CustomersListingPresenter mPresenter;
    CustomersAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    Observable<String> filterTextChangedObservable;
    Disposable mDisposable;

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
        mPresenter = new CustomersListingPresenterImpl(this, new CustomersListingInteractorImpl(new DatabaseHelper(Realm.getDefaultInstance())));
        mPresenter.getCustomers();
        initializeFilterTextObservables();
    }

    private void initializeFilterTextObservables() {
        filterTextChangedObservable = createFilterObservable();
        mDisposable = filterTextChangedObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        hideFilterProgress();
                        if (mAdapter != null) {
                            mAdapter.filter(s);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        hideFilterProgress();
                        showError();
                    }
                });

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
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void updateData(List<Customer> items) {
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
        filterEditText.setEnabled(false);
    }

    @Override
    public void hideProgress() {
        mainProgressBar.setVisibility(View.GONE);
        filterEditText.setEnabled(true);
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
     * to the activity  when a customer is clicked
     */


    View.OnClickListener mOnCustomerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = customersRecyclerView.getChildAdapterPosition(view);
            Customer model = mAdapter.getItemByPosition(position);
            Toast.makeText(getContext(), model.getFullName(), Toast.LENGTH_SHORT).show();
            if (mListener != null) {
                mListener.onCustomerClicked();
            }

        }
    };

    /**
     * Creates an observable of String to listen to filter edit text change
     *
     * @return
     */
    private Observable<String> createFilterObservable() {
        Observable<String> filterTextChangeObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                final TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                        showFilterProgress();
                        emitter.onNext(charSequence.toString());

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                };
                filterEditText.addTextChangedListener(textWatcher);
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        filterEditText.removeTextChangedListener(textWatcher);
                    }
                });
            }
        }).debounce(FILTER_WAIT_MILLISECONDS, TimeUnit.MILLISECONDS);
        return filterTextChangeObservable;
    }

    private void showFilterProgress() {
        filterProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideFilterProgress() {
        filterProgressBar.setVisibility(View.INVISIBLE);
    }

    public interface OnCustomersFragmentInteractionListener {
        void onCustomerClicked();
    }
}
