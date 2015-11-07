package br.com.zynger.recyclernotifier.sample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {
    private List<String> mItems;

    public SampleAdapter(List<String> items) {
        this.mItems = items;
    }

    @Override
    public SampleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTextView.setText(mItems.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mTextView = (TextView) itemLayoutView.findViewById(R.id.item_title);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}