package com.sottocorp.materialdesignnavdrawer.customViews;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.sottocorp.materialdesignnavdrawer.R;

/**
 * ImageView that changes it's image color depending on the state (pressed, selected...)
 */
public class TintOnStateImageView extends ImageView
{
    private Context mContext;
    private ColorStateList mColorStateList;

    public TintOnStateImageView(Context context)
    {
        super(context);
    }

    public TintOnStateImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialise(context, attrs, 0);
    }

    public TintOnStateImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initialise(context, attrs, defStyleAttr);
    }

    /**
     * Creates, binds and sets up the resources
     *
     * @param context is the context to get the resources from
     * @param attributeSet is the attributeSet
     * @param defStyle is the style
     */
    private void initialise
    (
            @NonNull final Context context,
            AttributeSet attributeSet,
            final int defStyle
    )
    {
        mContext = context;
        TypedArray a =
                context.obtainStyledAttributes(attributeSet, R.styleable.TintOnStateImageView, defStyle, 0);
        mColorStateList = a.getColorStateList(R.styleable.TintOnStateImageView_colorStateList);
        a.recycle();
    }

    @Override
    protected void drawableStateChanged()
    {
        super.drawableStateChanged();

        if (mColorStateList != null && mColorStateList.isStateful())
        {
            updateTintColor();
        }
    }

    /**
     * Updates the color of the image
     */
    private void updateTintColor()
    {
        final int color = mColorStateList.getColorForState(getDrawableState(),
                ContextCompat.getColor(mContext, R.color.nav_drawer_item_icon_normal));

        super.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }
}
