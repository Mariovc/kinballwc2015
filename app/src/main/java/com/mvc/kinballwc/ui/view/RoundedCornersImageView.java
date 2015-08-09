package com.mvc.kinballwc.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.mvc.kinballwc.R;

/**
 * Author: Mario Velasco Casquero
 * Date: 09/08/2015
 * Email: m3ario@gmail.com
 */
public class RoundedCornersImageView extends ImageView {

    private int radius;

    public RoundedCornersImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundedCornersImageView, 0, 0);
        try {
//            float radius = context.getResources().getInteger(R.integer.abc_config_activityDefaultDur);
            int defaultRadius = 20; // TODO
            radius = ta.getInteger(R.styleable.RoundedCornersImageView_cornerRadius, defaultRadius);
        } finally {
            ta.recycle();
        }
    }

    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);

        super.onDraw(canvas);
    }
}
