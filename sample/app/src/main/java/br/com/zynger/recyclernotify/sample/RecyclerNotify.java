package br.com.zynger.recyclernotify.sample;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecyclerNotify extends LinearLayout {

    private final Animation mShrinkAnimation;
    private final Animation mGrowAnimation;
    private boolean isAnimating = false;

    private ImageView mIconImageView;
    private TextView mTitleTextView;

    public RecyclerNotify(Context context) {
        super(context);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewGroup recyclerNotify = (ViewGroup) layoutInflater.inflate(R.layout.component_recyclernotify, this, true);
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
}
