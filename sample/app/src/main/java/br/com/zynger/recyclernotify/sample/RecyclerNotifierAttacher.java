package br.com.zynger.recyclernotify.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

public class RecyclerNotifierAttacher {

    public static final int ANCHOR_TOP = 0;
    public static final int ANCHOR_BOTTOM = ANCHOR_TOP + 1;

    public static void attach(RecyclerNotifier recyclerNotifier, RecyclerView recyclerView) {
        attach(recyclerNotifier, recyclerView, ANCHOR_TOP);
    }

    public static void attach(RecyclerNotifier recyclerNotifier, RecyclerView recyclerView,
                              final int anchor) {
        Context context = recyclerView.getContext();
        ViewParent recyclerViewParent = recyclerView.getParent();

        if (!(recyclerViewParent instanceof ViewGroup)) {
            throw new RuntimeException("You can only attach a " +
                    RecyclerNotifier.class.getSimpleName() + " to a ViewGroup!");
        }

        final boolean parentAlreadyContainsNotifier;
        final RelativeLayout wrappingLayout;
        if (recyclerViewParent instanceof RelativeLayout &&
                containsRecyclerNotifier((ViewGroup) recyclerViewParent)) {
            parentAlreadyContainsNotifier = true;
            wrappingLayout = (RelativeLayout) recyclerViewParent;
        } else {
            parentAlreadyContainsNotifier = false;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            wrappingLayout = (RelativeLayout) layoutInflater
                    .inflate(R.layout.component_recyclernotifywrapper, null);
        }

        setRecyclerNotifierLayoutParams(recyclerNotifier, anchor, context);
        wrappingLayout.addView(recyclerNotifier);

        if (!parentAlreadyContainsNotifier) {
            ViewGroup recyclerViewParentViewGroup = (ViewGroup) recyclerViewParent;
            recyclerViewParentViewGroup.removeView(recyclerView);

            wrappingLayout.addView(recyclerView, 0);
            recyclerViewParentViewGroup.addView(wrappingLayout);
        }
    }

    private static void setRecyclerNotifierLayoutParams(RecyclerNotifier recyclerNotifier,
                                                        int anchor, Context context) {
        int margin = Math.round(context.getResources().getDimension(R.dimen.rn_margin));
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        if (anchor == ANCHOR_BOTTOM) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        } else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        }
        layoutParams.setMargins(margin, margin, margin, margin);
        recyclerNotifier.setLayoutParams(layoutParams);
    }

    private static boolean containsRecyclerNotifier(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); ++i) {
            View nextChild = viewGroup.getChildAt(i);
            if (nextChild instanceof RecyclerNotifier) {
                return true;
            }
        }
        return false;
    }
}
