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
public class AnimProgressView extends View {
    public static final String DEBUG_LOG_TAG = "LOG_TAG";
    private static final boolean DEBUGABLE = true;
    private int mWidth, mHeight;
    private int mRoundAngle;
    private int mBgColor;
    private int mFrontColor;
    private Paint mPaint;

    /**
     * get progress value use defaultValue or from {@link #setValue(int)} ;
     */
    private int mValue = 0;

    /**
     * transform #mValue range from 0 to 100;
     */
    private double drawValue;


    private int accelerator = 0;

    public AnimProgressView(Context context) {
        this(context, null);
    }

    public AnimProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
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
            if (drawValue > mWidth) {
                drawValue = mWidth;
            }
            if (drawValue - accelerator > 10) {
                accelerator += 10;
            } else {
                accelerator = (int) drawValue;
            }
            postInvalidateDelayed(6L);
            canvas.drawCircle(mRoundAngle, mRoundAngle, mRoundAngle, mPaint);
            canvas.drawRect(mRoundAngle, 0, (float) (accelerator - 2 * mRoundAngle), mHeight, mPaint);
            canvas.drawCircle((float) (accelerator - 2 * mRoundAngle), mRoundAngle, mRoundAngle, mPaint);
        }
    }

    public void setValue(int mValue) {
        this.mValue = mValue;
        accelerator = 0;
    }
}
