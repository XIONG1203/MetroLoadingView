package org.createjoy.myview;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by xiong on 2016/12/1.
 * o(一︿一+)o
 */

public class LodingView extends LinearLayout {

    private View[] views;
    private Context context;
    private float barWidth;
    private float barHeight;
    private int barColor;
    private int barCount;
    private int duration;
    private int peakCount;

    public LodingView(Context context) {
        this(context, null);
    }

    public LodingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LodingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.lodingView);
        barWidth = a.getDimension(R.styleable.lodingView_barWidth, 20);
        barHeight = a.getDimension(R.styleable.lodingView_barHeight, 20);
        barColor = a.getColor(R.styleable.lodingView_color, 0xFFFF11FF);
        barCount = a.getInt(R.styleable.lodingView_barCount, 4);
        duration = a.getInt(R.styleable.lodingView_duration, 800);
        peakCount = a.getInt(R.styleable.lodingView_peakCount, 2);
        a.recycle();
        init();
    }

    private void init() {
        views = new View[barCount];
        for (int i = 0; i < views.length; i++) {
            View view = new View(context);
            LayoutParams layoutParams = new LayoutParams((int) barWidth, (int) barHeight);
            layoutParams.setMargins(10, 0, 0, 0);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            view.setLayoutParams(layoutParams);
            view.setBackgroundColor(barColor);
            int m = barCount / peakCount;
            int delaytime;
            int time = duration * 2 / m;
            Log.d("ddddddd", time + "   " + m / 2 + "  " + duration);
            if (i % m <= m / 2) {
                delaytime = (i % m) * time;
            } else {
                delaytime = (m - i % m) * time;
            }

            PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.2f);
            views[i] = view;
            ObjectAnimator customAnim = ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleY);
            customAnim.setStartDelay(delaytime);
            customAnim.setRepeatMode(ValueAnimator.REVERSE);
            customAnim.setRepeatCount(1000);
            customAnim.setDuration(duration);
            customAnim.start();
            addView(view);
        }

    }
}
