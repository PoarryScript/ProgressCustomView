package com.poarry.dimain.progresscustomview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.poarry.dimain.progresscustomview.R;

/**
 * Created by j-yangbo on 2017/4/1.
 */
public class ProgressView extends View {
    public static final String DEBUG_LOG_TAG = "LOG_TAG";
    private static final boolean DEBUGABLE = true;
    private int mWidth, mHeight;
    private int mRoundAngle;
    private int mBgColor;
    private int mFrontColor;
    private Paint mPaint;
    private int mValue = 0;
    private double drawValue;//transform #mValue range from 0 to 100;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        mWidth = (int) array.getDimension(R.styleable.ProgressView_width, getResources().getDimension(R.dimen.progress_view_default_width));
        mHeight = (int) array.getDimension(R.styleable.ProgressView_height, getResources().getDimension(R.dimen.progress_view_default_height));
        mRoundAngle = (int) array.getDimension(R.styleable.ProgressView_round_angle, getResources().getDimension(R.dimen.progress_view_default_angle));
        mBgColor = array.getColor(R.styleable.ProgressView_bg_color, getResources().getColor(R.color.default_bg_progress_color));
        mFrontColor = array.getColor(R.styleable.ProgressView_front_color, getResources().getColor(R.color.default_front_progress_color));
        mValue = array.getInt(R.styleable.ProgressView_default_progress_value, getResources().getInteger(R.integer.progress_view_default_value));
        mPaint = new Paint();
        mRoundAngle = mHeight / 2;
        array.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
       /* Log.d(DEBUG_LOG_TAG, "height" + mHeight);
        Log.d(DEBUG_LOG_TAG, "width" + mWidth);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        if (DEBUGABLE) {
            Log.d(DEBUG_LOG_TAG, "onMeasure width" + mWidth);
            Log.d(DEBUG_LOG_TAG, "onMeasure height" + mHeight);
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw bg
        mPaint.setColor(mBgColor);
        canvas.drawCircle(mRoundAngle, mRoundAngle, mRoundAngle, mPaint);
        canvas.drawRect(mRoundAngle, 0, mWidth - 2 * mRoundAngle, mHeight, mPaint);
        canvas.drawCircle(mWidth - 2 * mRoundAngle, mRoundAngle, mRoundAngle, mPaint);

        //draw value

        if (DEBUGABLE) {
            Log.d(DEBUG_LOG_TAG, "onDraw mWidth-->" + mWidth);
            Log.d(DEBUG_LOG_TAG, "onDraw mValue" + mValue);
        }

        mPaint.setColor(mFrontColor);
        //get proportion
        drawValue = (mWidth * mValue) / 100;
        if (drawValue < mHeight / 2) {
            mRoundAngle = (int) (drawValue / 2);
            canvas.drawCircle(mRoundAngle, mHeight / 2, mRoundAngle, mPaint);
            mPaint.setColor(mBgColor);
            canvas.drawRect(mRoundAngle, 0, mRoundAngle * 2, mRoundAngle * 2, mPaint);
        } else {
            mRoundAngle = mHeight / 2;
            mPaint.setColor(mFrontColor);


            if (DEBUGABLE) {
                Log.d(DEBUG_LOG_TAG, "onDraw drawValue" + drawValue);
            }
            if (drawValue>mWidth){
                drawValue = mWidth;
            }
            canvas.drawCircle(mRoundAngle, mRoundAngle, mRoundAngle, mPaint);
            canvas.drawRect(mRoundAngle, 0, (float) (drawValue - 2 * mRoundAngle), mHeight, mPaint);
            canvas.drawCircle((float) (drawValue - 2 * mRoundAngle), mRoundAngle, mRoundAngle, mPaint);
        }
    }

    public void setmValue(int mValue) {
        this.mValue = mValue;
        invalidate();
    }
}
