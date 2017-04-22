package com.bassem.tablereservation.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bassem.tablereservation.R;
import com.bassem.tablereservation.models.Customer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.ViewHolder> {
    private List<Customer> mDataset;
    private ArrayList<Customer> mFilteredDataset;
    private View.OnClickListener mOnClickListener;

    public CustomersAdapter(List<Customer> items, View.OnClickListener onClickListener) {
        mDataset = items;
        mFilteredDataset = new ArrayList<>(items);
        mOnClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!TextUtils.isEmpty(mFilteredDataset.get(position).getFullName())) {
            holder.fullNameTextView.setText(mFilteredDataset.get(position).getFullName());
        } else {
            holder.fullNameTextView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if (mFilteredDataset != null) {
            return mFilteredDataset.size();
        }
        return 0;
    }

    public Customer getItemByPosition(int position) {
        if (mFilteredDataset != null) {
            return mFilteredDataset.get(position);
        }
        return null;
    }

    public void setItems(List<Customer> items) {
        mDataset = items;
        mFilteredDataset.clear();
        mFilteredDataset.addAll(mDataset);
        notifyDataSetChanged();
    }

    public void filter(String filterText) {
        mFilteredDataset.clear();
        if (TextUtils.isEmpty(filterText)) {
            mFilteredDataset.addAll(mDataset);
        } else {
            for (Customer customer : mDataset
                    ) {
                if (customer.isInFilter(filterText)) {
                    mFilteredDataset.add(customer);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_full_name)
        TextView fullNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
