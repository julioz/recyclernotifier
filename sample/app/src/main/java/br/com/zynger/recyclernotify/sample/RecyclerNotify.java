package br.com.zynger.recyclernotify.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

public class RecyclerNotify {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private RecyclerView mRecyclerView;

    public void attach(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);

        ViewParent recyclerViewParent = recyclerView.getParent();
        if (recyclerViewParent instanceof ViewGroup) {
            ViewGroup recyclerViewParentViewGroup = (ViewGroup) recyclerViewParent;
            recyclerViewParentViewGroup.removeView(recyclerView);

            attachInViewGroup(recyclerViewParentViewGroup);
        }
    }

    private void attachInViewGroup(ViewGroup viewGroup) {
        RelativeLayout wrappingLayout = (RelativeLayout) mLayoutInflater.inflate(R.layout.component_recyclernotify, null);
        wrappingLayout.addView(mRecyclerView, 0);
        viewGroup.addView(wrappingLayout);
    }
}
