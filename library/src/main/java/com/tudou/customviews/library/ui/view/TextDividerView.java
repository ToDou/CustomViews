package com.tudou.customviews.library.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.tudou.customviews.library.R;

/**
 * Created by tudou on 15-4-1.
 */
public class TextDividerView extends View {

    private Paint mTextPaint;
    private Paint mDividerPaint;
    private RectF mDividerRectFPre;
    private RectF mDividerRectFAfter;

    private int mTextColor;
    private int mDividerColor;
    private int mColor;

    private float mTextSize;
    private float mRectHeight;

    private float mTextWidth;
    private float mTextBaseX;
    private float mTextBaseY;
    private float mTextOffset;

    private String mFix = " ";
    private String mText = "or";

    public TextDividerView(Context context) {
        this(context, null);
    }

    public TextDividerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextDividerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTextColor = getResources().getColor(android.R.color.darker_gray);
        mDividerColor = getResources().getColor(android.R.color.darker_gray);
        mColor = mTextColor;
        mTextSize = sp2px(16);
        mRectHeight = dp2px(1.0f);
        mTextOffset = dp2px(5.0f);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextDividerView);
        mTextColor = array.getColor(R.styleable.TextDividerView_text_color, mTextColor);
        mDividerColor = array.getColor(R.styleable.TextDividerView_line_color, mDividerColor);
        mColor = mTextColor;
        mTextSize = array.getDimension(R.styleable.TextDividerView_text_size, mTextSize);
        mRectHeight = array.getDimension(R.styleable.TextDividerView_line_height, mRectHeight);
        mTextOffset = array.getDimension(R.styleable.TextDividerView_text_offset, mTextOffset);

        array.recycle();

        initPainters();
        initRectFs();
    }

    private void initRectFs() {
        mDividerRectFAfter = new RectF(0, 0, 0, 0);
        mDividerRectFPre = new RectF(0, 0, 0, 0);
    }

    private void initPainters() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

        mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDividerPaint.setColor(mDividerColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculateRectFs();

        canvas.drawRect(mDividerRectFPre, mDividerPaint);
        canvas.drawRect(mDividerRectFAfter, mDividerPaint);

        canvas.drawText(mText, mTextBaseX, mTextBaseY, mTextPaint);
    }

    private void calculateRectFs() {
        mText = mFix + mText + mFix;
        mTextWidth = mTextPaint.measureText(mText);

        mTextBaseY =
                (int) ((getHeight() / 2.0f) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2.0f));

        mDividerRectFPre.left = getPaddingLeft();
        mDividerRectFPre.top = getHeight() / 2 - mRectHeight / 2;
        mDividerRectFPre.right = getWidth() / 2 - mTextWidth / 2 - mTextOffset;
        mDividerRectFPre.bottom = getHeight() / 2 + mRectHeight / 2;

        mTextBaseX = mDividerRectFPre.right + mTextOffset;

        mDividerRectFAfter.left = getWidth() / 2 + mTextWidth / 2 + mTextOffset;
        mDividerRectFAfter.top = getHeight() / 2 - mRectHeight / 2;
        mDividerRectFAfter.right = getWidth() - getPaddingRight();
        mDividerRectFAfter.bottom = getHeight() / 2 + mRectHeight / 2;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public int getDividerColor() {
        return mDividerColor;
    }

    public void setDividerColor(int mDividerColor) {
        this.mDividerColor = mDividerColor;
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }

    public float getRectHeight() {
        return mRectHeight;
    }

    public void setRectHeight(float mRectHeight) {
        this.mRectHeight = mRectHeight;
    }

    public float getTextOffset() {
        return mTextOffset;
    }

    public void setTextOffset(float mTextOffset) {
        this.mTextOffset = mTextOffset;
    }

    public float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public float sp2px(float sp) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

}
