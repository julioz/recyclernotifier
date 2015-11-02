package br.com.zynger.recyclernotify.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecyclerNotify {

    private Context mContext;
    private RelativeLayout mWrappingLayout;
    private RecyclerView mRecyclerView;
    private ImageView mIconImageView;
    private TextView mTitleTextView;

    public void attach(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        mWrappingLayout = (RelativeLayout) layoutInflater.inflate(R.layout.component_recyclernotify, null);
        mIconImageView = (ImageView) mWrappingLayout.findViewById(R.id.rn_icon);
        mTitleTextView = (TextView) mWrappingLayout.findViewById(R.id.rn_text);

        ViewParent recyclerViewParent = recyclerView.getParent();
        if (recyclerViewParent instanceof ViewGroup) {
            ViewGroup recyclerViewParentViewGroup = (ViewGroup) recyclerViewParent;
            recyclerViewParentViewGroup.removeView(recyclerView);

            attachInViewGroup(recyclerViewParentViewGroup);
        } else {
            throw new RuntimeException("You can only attach a " +
                    RecyclerNotify.class.getSimpleName() + " to a ViewGroup!");
        }
    }

    private void attachInViewGroup(ViewGroup viewGroup) {
        mWrappingLayout.addView(mRecyclerView, 0);
        viewGroup.addView(mWrappingLayout);
    }
}
