package br.com.zynger.recyclernotifier.sample;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecyclerNotifier extends LinearLayout {

    private final Animation mShrinkAnimation;
    private final Animation mGrowAnimation;
    private boolean isAnimating = false;

    private RecyclerView.OnScrollListener mOnScrollListener;

    private ImageView mIconImageView;
    private TextView mTitleTextView;
    private boolean mChangeVisibilityWithScrollListener = true;

    public RecyclerNotifier(Context context) {
        super(context);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewGroup recyclerNotify = (ViewGroup) layoutInflater
                .inflate(R.layout.component_recyclernotify, this, true);
        setOrientation(HORIZONTAL);
        setClickable(true);
        setBackgroundResource(R.drawable.component_recyclernotify_round_background);
        int padding = Math.round(getResources().getDimension(R.dimen.rn_padding));
        setPadding(padding, padding, padding, padding);

        mIconImageView = (ImageView) recyclerNotify.findViewById(R.id.rn_icon);
        mTitleTextView = (TextView) recyclerNotify.findViewById(R.id.rn_text);

        mShrinkAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_fade_out);
        mShrinkAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(View.GONE);

                isAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mGrowAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.grow_fade_in);
        mGrowAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimating = true;
                setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mOnScrollListener = new RecyclerView.OnScrollListener() {
            private static final int SCROLL_THRESHOLD = 20;
            private int mTotalScrollAmount = 0;
            private boolean isVisible = true;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!mChangeVisibilityWithScrollListener) {
                    return;
                }

                if (mTotalScrollAmount > SCROLL_THRESHOLD && isVisible) {
                    hide();
                    isVisible = false;
                    mTotalScrollAmount = 0;
                } else if (mTotalScrollAmount < -SCROLL_THRESHOLD && !isVisible) {
                    show();
                    isVisible = true;
                    mTotalScrollAmount = 0;
                }

                boolean scrollingDown = dy > 0;
                boolean scrollingUp = !scrollingDown;
                if ((isVisible && scrollingDown) || (!isVisible && scrollingUp)) {
                    mTotalScrollAmount += dy;
                }
            }
        };
    }

    public void setChangeVisibilityWithScrollListener(boolean changeVisibilityWithScrollListener) {
        mChangeVisibilityWithScrollListener = changeVisibilityWithScrollListener;
    }

    public void setImageResource(@DrawableRes int resId) {
        mIconImageView.setImageResource(resId);
    }

    public void setImageDrawable(@Nullable Drawable drawable) {
        mIconImageView.setImageDrawable(drawable);
    }

    public void setImageVisibility(int visibility) {
        mIconImageView.setVisibility(visibility);
    }

    public void setTextVisibility(int visibility) {
        mTitleTextView.setVisibility(visibility);
    }

    public void setText(CharSequence text) {
        mTitleTextView.setText(text);
    }

    public void setTextColor(@ColorInt int color) {
        mTitleTextView.setTextColor(color);
    }

    public void setTextColor(ColorStateList colors) {
        mTitleTextView.setTextColor(colors);
    }

    public void hide() {
        if (isAnimating || getVisibility() != View.VISIBLE) {
            return;
        }
        startAnimation(mShrinkAnimation);
    }

    public void show() {
        if (isAnimating || getVisibility() == View.VISIBLE) {
            return;
        }

        startAnimation(mGrowAnimation);
    }

    public RecyclerView.OnScrollListener getOnScrollListener() {
        return mOnScrollListener;
    }
}
