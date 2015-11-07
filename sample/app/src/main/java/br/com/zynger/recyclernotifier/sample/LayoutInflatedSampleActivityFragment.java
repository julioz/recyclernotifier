package br.com.zynger.recyclernotifier.sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.zynger.recyclernotifier.RecyclerNotifier;

public class LayoutInflatedSampleActivityFragment extends BaseSampleFragment {

    private RecyclerNotifier mRecyclerNotifier2;

    public LayoutInflatedSampleActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_inflated_sample, container, false);

        setupButtons(view);
        setupRecyclerView(view);

        mRecyclerNotifier = (RecyclerNotifier) view.findViewById(R.id.recycler_notifier);
        mRecyclerNotifier2 = (RecyclerNotifier) view.findViewById(R.id.recycler_notifier2);
        mRecyclerNotifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "RecyclerNotifier clicked!", Toast.LENGTH_SHORT).show();
                mRecyclerNotifier.hide();
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

        mRecyclerNotifier2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "RecyclerNotifier 2 clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerNotifier.onParentInflated();
        mRecyclerNotifier2.onParentInflated();
    }
}
