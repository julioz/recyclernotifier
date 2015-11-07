package br.com.zynger.recyclernotifier.sample;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.zynger.recyclernotifier.RecyclerNotifier;

public class BaseSampleFragment extends Fragment {
    protected RecyclerView mRecyclerView;
    protected RecyclerNotifier mRecyclerNotifier;

    protected void setupButtons(View view) {
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

    protected void setupRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<String> items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            items.add("Item " + String.valueOf(i));
        }
        SampleAdapter adapter = new SampleAdapter(items);
        mRecyclerView.setAdapter(adapter);
    }
}
