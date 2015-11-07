package br.com.zynger.recyclernotifier;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public class RecyclerNotifierScrollListener extends RecyclerView.OnScrollListener {
    private boolean isEnabled = true;

    private static final int SCROLL_THRESHOLD = 20;
    private int mTotalScrollAmount = 0;
    private boolean isVisible = true;
    private RecyclerNotifier mRecyclerNotifier;

    public RecyclerNotifierScrollListener(@NonNull RecyclerNotifier recyclerNotifier) {
        mRecyclerNotifier = recyclerNotifier;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (!isEnabled) {
            return;
        }

        if (mTotalScrollAmount > SCROLL_THRESHOLD && isVisible) {
            mRecyclerNotifier.hide();
            isVisible = false;
            mTotalScrollAmount = 0;
        } else if (mTotalScrollAmount < -SCROLL_THRESHOLD && !isVisible) {
            mRecyclerNotifier.show();
            isVisible = true;
            mTotalScrollAmount = 0;
        }

        boolean scrollingDown = dy > 0;
        boolean scrollingUp = !scrollingDown;
        if ((isVisible && scrollingDown) || (!isVisible && scrollingUp)) {
            mTotalScrollAmount += dy;
        }
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
