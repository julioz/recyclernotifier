package br.com.zynger.recyclernotifier.sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.zynger.recyclernotifier.RecyclerNotifier;
import br.com.zynger.recyclernotifier.RecyclerNotifierAttacher;

public class CodeInflatedActivityFragment extends BaseSampleFragment {

    public CodeInflatedActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_codeinflated, container, false);

        setupButtons(view);
        setupRecyclerView(view);

        mRecyclerNotifier = new RecyclerNotifier(getActivity());
        mRecyclerNotifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "RecyclerNotifier clicked!", Toast.LENGTH_SHORT).show();
                mRecyclerNotifier.hide();
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

        mRecyclerNotifier.setText("New stories");
        mRecyclerNotifier.setImageResource(R.mipmap.ic_launcher);
        mRecyclerView.addOnScrollListener(mRecyclerNotifier.getOnScrollListener());
        RecyclerNotifierAttacher.attach(mRecyclerNotifier, mRecyclerView);
        return view;
    }
}
