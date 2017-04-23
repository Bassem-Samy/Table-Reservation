package com.bassem.tablereservation.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bassem.tablereservation.R;
import com.bassem.tablereservation.models.Table;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public class TablesAdapter extends RecyclerView.Adapter<TablesAdapter.ViewHolder> {
    private ArrayList<Table> mDataset;
    View.OnClickListener mOnClickListener;
    int availableCellColor;
    int reservedCellColor;

    public TablesAdapter(List<Table> items, View.OnClickListener onTableClickedListener) {
        this.mDataset = new ArrayList<>(items);
        this.mOnClickListener = onTableClickedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item, parent, false);
        availableCellColor = ContextCompat.getColor(parent.getContext(), R.color.colorAvailableTable);
        reservedCellColor = ContextCompat.getColor(parent.getContext(), R.color.colorReservedTable);
        view.setOnClickListener(mOnClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mDataset.get(position).isAvailable()) {
            holder.cellContainerFrameLayout.setBackgroundColor(availableCellColor);
        } else {
            holder.cellContainerFrameLayout.setBackgroundColor(reservedCellColor);

        }
        holder.tableNameTextView.setText(Integer.toString(position + 1));
    }

    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDataset.size();
        }
        return 0;
    }

    public Table getItemByPosition(int pos) {
        if (mDataset != null) {
            return mDataset.get(pos);
        }
        return null;
    }

    public void setItems(List<Table> items) {
        if (mDataset != null) {
            mDataset.clear();
            mDataset.addAll(items);
        } else {
            mDataset = new ArrayList<>(items);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.frm_table_cell_container)
        FrameLayout cellContainerFrameLayout;
        @BindView(R.id.txt_table_name)
        TextView tableNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
