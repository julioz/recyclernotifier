package br.com.zynger.recyclernotifier.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.zynger.recyclernotifier.RecyclerNotifier;
import br.com.zynger.recyclernotifier.RecyclerNotifierAttacher;

public class MainActivityFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerNotifier mRecyclerNotifier;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        setupButtons(view);
        setupRecyclerView(view);

        mRecyclerNotifier = new RecyclerNotifier(getContext());
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

    private void setupButtons(View view) {
        view.findViewById(R.id.btn_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerNotifier.hide();
            }
        });

        view.findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerNotifier.show();
            }
        });
    }

    private void setupRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<String> items = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            items.add("Item " + String.valueOf(i));
        }
        SampleAdapter adapter = new SampleAdapter(items);
        mRecyclerView.setAdapter(adapter);
    }
}
