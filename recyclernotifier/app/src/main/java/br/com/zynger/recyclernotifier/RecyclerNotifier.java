package br.com.zynger.recyclernotifier;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecyclerNotifier extends LinearLayout {

    private static final int NO_RECYCLER_VIEW = -1;

    private
    @IdRes
    int mRecyclerViewId = NO_RECYCLER_VIEW;
    private boolean mShouldAddScrollListener = false;
    private int mAttributeSetAnchor = RecyclerNotifierAttacher.ANCHOR_TOP;

    private Animation mShrinkAnimation;
    private Animation mGrowAnimation;

    private boolean isAnimating = false;

    private RecyclerNotifierScrollListener mOnScrollListener;

    private ImageView mIconImageView;
    private TextView mTitleTextView;

    public RecyclerNotifier(Context context) {
        super(context);
        init(context, null);
    }

    public RecyclerNotifier(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RecyclerNotifier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attributeSet) {
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

        mOnScrollListener = new RecyclerNotifierScrollListener(this);
        initAnimations();

        if (attributeSet != null) {
            setAttributesFromSet(attributeSet);
        }
    }

    private void setAttributesFromSet(@NonNull AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.RecyclerNotifier);

        mRecyclerViewId = array.getResourceId(R.styleable.RecyclerNotifier_rn_attachTo,
                NO_RECYCLER_VIEW);
        String text = array.getString(R.styleable.RecyclerNotifier_rn_text);
        @ColorInt int textColor = array.getColor(R.styleable.RecyclerNotifier_rn_textColor,
                getResources().getColor(android.R.color.white));
        Drawable imageSrc = array.getDrawable(R.styleable.RecyclerNotifier_rn_imageSrc);
        mShouldAddScrollListener =
                array.getBoolean(R.styleable.RecyclerNotifier_rn_scrollListenerEnabled, false);
        mAttributeSetAnchor = array.getInt(R.styleable.RecyclerNotifier_rn_anchor,
                RecyclerNotifierAttacher.ANCHOR_TOP);
        int textVisibility = RecyclerNotifierUtil.visibilityFromString(array,
                R.styleable.RecyclerNotifier_rn_textVisibility, View.VISIBLE);
        int imageVisibility = RecyclerNotifierUtil.visibilityFromString(array,
                R.styleable.RecyclerNotifier_rn_imageVisibility, View.VISIBLE);

        if (mRecyclerViewId == NO_RECYCLER_VIEW) {
            throw new RuntimeException("If providing XML attributes, you need to " +
                    "use the attachTo property to define a RecyclerView to " +
                    "attach the notifier!");
        }

        setText(text);
        setTextColor(textColor);
        setImageDrawable(imageSrc);
        setImageVisibility(imageVisibility);
        setTextVisibility(textVisibility);

        array.recycle();
    }

    // To be called only in case the View is inflated from XML
    public void onParentInflated() {
        if (mRecyclerViewId != NO_RECYCLER_VIEW) {
            ViewParent viewParent = getParent();
            if (!(viewParent instanceof ViewGroup)) {
                throw new RuntimeException("You can only use " +
                        RecyclerNotifier.class.getSimpleName() + " inside a ViewGroup!");
            }

            ViewGroup recyclerNotifierParentViewGroup = (ViewGroup) viewParent;
            View view = recyclerNotifierParentViewGroup.findViewById(mRecyclerViewId);
            if (!(view instanceof RecyclerView)) {
                throw new RuntimeException("You can only attachTo an instance of RecyclerView!");
            }

            RecyclerView recyclerView = (RecyclerView) view;

            if (mShouldAddScrollListener) {
                recyclerView.addOnScrollListener(getOnScrollListener());
            }

            RecyclerNotifierAttacher.attachThroughXML(this, recyclerView, mAttributeSetAnchor);
        }
    }

    private void initAnimations() {
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

    public void setChangeVisibilityWithScrollListener(boolean changeVisibilityWithScrollListener) {
        if (mOnScrollListener != null) {
            mOnScrollListener.setEnabled(changeVisibilityWithScrollListener);
        }
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
